package controller.commands;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import controller.ICommandController;
import model.IFlexiblePortfolio;
import model.IUserInterface;
import model.IstockModel;
import model.Operation;
import model.PortfolioType;
import model.Stock;
import view.ViewInterface;

/**
 * The class BuySell implements the command controller interface.
 * It is one of the commands that is supported by our application.
 * BuySell operation is available for flexible portfolios only.
 * It allows the user to buy or sell stocks after user has created the portfolio.
 */
public class BuySell implements ICommandController {

  private ViewInterface view;
  private IUserInterface user;
  private Scanner inputScanner;

  /**
   * Instantiates a new BuySell.
   * It takes in view and model (user) and instantiates them.
   *
   * @param view the view
   * @param user the user
   */
  public BuySell(ViewInterface view, IUserInterface user, Scanner scanner) {
    this.view = view;
    this.user = user;
    inputScanner = scanner;
  }


  @Override
  public void goController() {

    if (user.getPortfolioNamesCreated(PortfolioType.flexible).size() == 0) {
      view.displayMsgToUser("No " + PortfolioType.flexible + " portfolios created till now!!!"
              + "\n Hence cannot add/sell stocks");
      return;
    }
    this.buySell(PortfolioType.flexible);

  }

  /**
   * This is our main buySell method which handles the buy or sell operation.
   * It takes in the type of portfolio.
   * It modifies the portfolio file to update the current buy/sell transaction.*
   *
   * @param portfolioType the portfolio type
   */
  private void buySell(PortfolioType portfolioType) {
    List<String[]> dataToWrite = null;
    int portfolioIndex = 0;
    int buyOrSell = 0;
    String[] stockDetails = null;
    Boolean isSellOkay = false;

    this.view.displayMsgToUser("Following is the list of portfolios available for adding/selling"
            + "stocks");
    view.displayListOfPortfolios(user.getPortfolioNamesCreated(portfolioType));
    portfolioIndex = this.getSelectedPortFolioFromView(portfolioType);
    buyOrSell = this.getBuyOrSellFromView();
    stockDetails = this.takeStockInputFromView(buyOrSell);
    if (buyOrSell == 1) {
      stockDetails[5] = Operation.BUY.toString();
    } else if (buyOrSell == 2) {
      stockDetails[5] = Operation.SELL.toString();
      try {
        if (!this.validateSellOperation(portfolioIndex, stockDetails)) {
          throw new Exception("Cannot sell this stock!");
        }
      } catch (Exception e) {
        this.view.displayMsgToUser(e.getMessage());
        return;
      }
    }

    Stock newStock = Stock.getBuilder().tickerName(stockDetails[0])
            .numOfUnits(Double.valueOf(stockDetails[1]))
            .transactionDate(LocalDate.parse((stockDetails[2])))
            .commission(Double.valueOf(stockDetails[3]))
            .transactionPrice(Double.valueOf(stockDetails[4]))
            .buyOrSell(Operation.valueOf(stockDetails[5])).build();


    IFlexiblePortfolio pf = user.getFlexiblePortfoliosCreatedObjects().get(portfolioIndex - 1);
    pf.addOrSellStocks(newStock);
    dataToWrite = pf.toListOfString();
    user.savePortfolioToFile(dataToWrite, pf.getNameOfPortFolio().strip()
            .split(".csv")[0], portfolioType);
  }

  /**
   * Gets selected port folio from view.
   *
   * @param portfolioType the portfolio type
   * @return the selected port folio from view
   */
  public int getSelectedPortFolioFromView(PortfolioType portfolioType) {
    int index = -1;
    boolean okay = false;
    do {
      try {
        view.getSelectedPortfolio();
        index = inputScanner.nextInt();
        if ((index < 0) || (index > user.getPortfolioNamesCreated(portfolioType).size())) {
          throw new IllegalArgumentException("Invalid Index");
        }
        okay = true;
      } catch (IllegalArgumentException ie) {
        this.view.displayMsgToUser(ie.getMessage());
        okay = false;
      }
    }
    while (!okay);

    return index;
  }

  /**
   * Gets buy or sell from view.
   * It is a helper method which asks the view to display whether
   * the user wants to buy or sell a stock.
   * It returns 1 if the user wants to buy a stock.
   * It returns 2 if the user wants to sell a stock.
   *
   * @return the buy or sell from view
   */
  private int getBuyOrSellFromView() {
    int userOption = 0;
    List<Integer> validMenuOptions = Arrays.asList(1, 2);
    do {
      try {
        this.view.askAddOrSell();
        userOption = Integer.parseInt(inputScanner.next());
      } catch (IllegalArgumentException ie) {
        this.view.displayMsgToUser("Please enter only an integer value from the below options!!");
      }
    }
    while (!validMenuOptions.contains(userOption));
    return userOption;
  }

  /**
   * Take stock input from view string [ ].
   * It is a helper method that takes in details of the stocks that the user wants to buy or sell.
   * It takes in an integer, 1 represents buy and 2 represents sell.
   * It returns the stock details that the user wants to buy or sell.
   *
   * @param buyOrSell the buy or sell
   * @return the string [ ]
   */
  private String[] takeStockInputFromView(int buyOrSell) {
    String[] userStockInput = new String[6];
    String tickerNameFromUser = "";
    Double numUnits = 0.0;
    Double commission = 0.0;
    LocalDate transactionDate = LocalDate.now();
    Boolean isInputValid = false;
    do {
      try {
        this.view.takeTickerName();
        tickerNameFromUser = inputScanner.next();
        if (!user.isTickerValid(tickerNameFromUser)) {
          throw new IllegalArgumentException("Invalid ticker name!");
        }
        if (buyOrSell == 1) {
          this.view.displayMsgToUser("Enter the number of units to buy");
        } else {
          this.view.displayMsgToUser("Enter the number of units to sell");
        }


        numUnits = inputScanner.nextDouble();
        Long numOfUnitsInt = Math.round(numUnits);
        if (numUnits <= 0.0) {
          throw new IllegalArgumentException("Number of units cannot be -ve");
        } else if ((double) numOfUnitsInt != numUnits) {
          throw new IllegalArgumentException("Cannot purchase fractional shares");
        }

        transactionDate = this.getDateFromView();

        this.view.takeCommissionValue();
        commission = inputScanner.nextDouble();
        if (commission <= 0.0) {
          throw new IllegalArgumentException("Commission cannot be -ve");
        }

        isInputValid = true;
      } catch (Exception e) {
        this.view.displayMsgToUser(e.getMessage());
        isInputValid = false;
      }
    }
    while (!isInputValid);

    userStockInput[0] = tickerNameFromUser;
    userStockInput[1] = Double.toString(numUnits);
    userStockInput[2] = String.valueOf(transactionDate);
    userStockInput[3] = String.valueOf(commission);
    userStockInput[4] = String.valueOf(user.getStockPriceFromDB(tickerNameFromUser,
            transactionDate));
    userStockInput[5] = String.valueOf(false); //indicates shares are bought

    return userStockInput;
  }

  /**
   * Gets date from view.
   * It is a helper method that gets the date from view.
   * It handles invalid date cases.
   * It asks view to display the date format that is expected.
   * If user doesn't enter correct date format, then we ask user to enter correct date again, until
   * user enter correct date.
   *
   * @return the date from view
   */
  private LocalDate getDateFromView() {
    LocalDate valueDate = LocalDate.now();
    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    boolean isDateOkay = false;
    while (!isDateOkay) {
      try {
        view.getDateFromUser();
        valueDate = LocalDate.parse(inputScanner.next(), dateFormat);
        isDateOkay = true;
      } catch (Exception e) {
        view.displayMsgToUser("Invalid date. Please try again!");
        isDateOkay = false;
      }
      if (valueDate.isAfter(LocalDate.now())) {
        view.displayMsgToUser("Future date entered... "
                + "Please enter a date that is not later than today!!! ");
        isDateOkay = false;
      }
    }

    return valueDate;
  }

  /**
   * Validate sell operation boolean.
   * This method validates if the stock details entered by the user is valid and is a legal
   * operation that can be actually performed on the portfolio.
   * It returns true if the stock can be either bought or sold successfully.
   * It returns false if the stock cannot be bought or sold.
   *
   * @param portfolioIndex the portfolio index
   * @param stockDetails   the stock details
   * @return the boolean
   */
  private Boolean validateSellOperation(int portfolioIndex, String[] stockDetails) {
    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDate sellDate = LocalDate.parse(stockDetails[2], dateFormat);
    LocalDate firstDate = LocalDate.now();
    String tickername = stockDetails[0];
    Double numOfStocks = 0.0;
    IFlexiblePortfolio pfToCheck = user.getFlexiblePortfoliosCreatedObjects().get(portfolioIndex
            - 1);

    for (IstockModel s : pfToCheck.getStocksInPortfolio(LocalDate.now())) {
      if (tickername.equals(s.getTickerName()) && s.getBuyOrSell().equals(Operation.BUY)) {
        numOfStocks += s.getNumOfUnits();
        if (firstDate.isAfter(s.getTransactionDate())) {
          firstDate = s.getTransactionDate();
        }
      } else if (tickername.equals(s.getTickerName()) && s.getBuyOrSell().equals(Operation.SELL)) {
        numOfStocks -= s.getNumOfUnits();
      }
    }
    if (numOfStocks < Double.valueOf(stockDetails[1]) || numOfStocks == 0
            || sellDate.isBefore(firstDate)) {
      return false;
    }


    return true;
  }

}
