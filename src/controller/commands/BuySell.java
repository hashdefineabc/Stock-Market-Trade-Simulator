package controller.commands;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import controller.ICommandController;
import model.IFlexiblePortfolio;
import model.IUserInterface;
import model.IstockModel;
import model.Operation;
import model.PortfolioType;
import model.Stock;
import model.User;
import view.ViewInterface;

public class BuySell implements ICommandController {

  private ViewInterface view;
  private IUserInterface user;
  private Scanner scanner;

  public BuySell(ViewInterface view, IUserInterface user) {
    this.view = view;
    this.user = user;
    scanner = new Scanner(System.in);
  }


  @Override
  public void go() {
    /**
     * display flexible pf
     * ask what has to be done
     */
    if (user.getPortfolioNamesCreated(PortfolioType.flexible).size() == 0) {
      view.displayMsgToUser("No " + PortfolioType.flexible.toString() + " portfolios created till now!!!"
                          + "\n Hence cannot add/sell stocks");
      return;
    }
    this.buySell(PortfolioType.flexible);



  }

  public void buySell(PortfolioType portfolioType) {
    List<String[]> dataToWrite = null;

    this.view.displayMsgToUser("Following is the list of portfolios available for adding/selling"
            + "stocks");
    view.displayListOfPortfolios(user.getPortfolioNamesCreated(portfolioType));
    int portfolioIndex = this.getSelectedPortFolioFromView(portfolioType);
    int buyOrSell = this.getBuyOrSellFromView();
    String[] stockDetails = this.takeStockInputFromView();

    if (buyOrSell == 1) {
      stockDetails[5] = Boolean.toString(false);
    }
    else if (buyOrSell == 2) {
      stockDetails[5] = Boolean.toString(true);
    }
    Stock newStock = Stock.getBuilder()
              .tickerName(stockDetails[0])
              .numOfUnits(Double.valueOf(stockDetails[1]))
              .transactionDate(LocalDate.parse((stockDetails[2])))
              .commission(Double.valueOf(stockDetails[3]))
              .transactionPrice(Double.valueOf(stockDetails[4]))
              .buyOrSell(Operation.valueOf(stockDetails[5]))
              .build();


    IFlexiblePortfolio pf = user.getFlexiblePortfoliosCreatedObjects().get(portfolioIndex - 1);
    pf.addOrSellStocks(newStock);
    dataToWrite = pf.toListOfString();
    user.savePortfolioToFile(dataToWrite, pf.getNameOfPortFolio().strip().split(".csv")[0],
            portfolioType);
  }

  public int getSelectedPortFolioFromView(PortfolioType portfolioType) {
    int index = -1;
    boolean okay = false;
    do {
      try {
        view.getSelectedPortfolio();
        index = scanner.nextInt();
        if ((index < 0) || (index > user.getPortfolioNamesCreated(portfolioType).size())) {
          throw new IllegalArgumentException("Invalid Index");
        }
        okay = true;
      } catch (IllegalArgumentException ie) {
        this.view.displayMsgToUser(ie.getMessage());
        okay = false;
      }
    } while (!okay);

    return index;
  }

  public int getBuyOrSellFromView() {
    int userOption = 0;
    List<Integer> validMenuOptions = Arrays.asList(1, 2);
    do {
      try {
        this.view.askAddOrSell();
        userOption = Integer.parseInt(scanner.next());
      } catch (IllegalArgumentException ie) {
        this.view.displayMsgToUser("Please enter only an integer value from the below options!!");
      }
    } while (!validMenuOptions.contains(userOption));
    return userOption;
  }

  public String[] takeStockInputFromView() {
    String[] userStockInput = new String[6];
    String tickerNameFromUser = "";
    Double numUnits = 0.0;
    Double commission = 0.0;
    LocalDate transactionDate = LocalDate.now();
    Boolean isInputValid = false;
    do {
      try{
        this.view.takeTickerName();
        tickerNameFromUser = scanner.next();
        if (!user.isTickerValid(tickerNameFromUser)) {
          throw new IllegalArgumentException("Invalid ticker name!");
        }
        this.view.takeNumOfUnits();
        numUnits = scanner.nextDouble();
        Long numOfUnitsInt = Math.round(numUnits);
        if (numUnits <= 0.0) {
          throw new IllegalArgumentException("Number of units purchased cannot be -ve");
        }
        else if ((double)numOfUnitsInt != numUnits) {
          throw new IllegalArgumentException("Cannot purchase fractional shares");
        }

        transactionDate = this.getDateFromView();

        this.view.takeCommissionValue();
        commission = scanner.nextDouble();
        if (commission <= 0.0) {
          throw new IllegalArgumentException("Commission cannot be -ve");
        }

        isInputValid = true;
      } catch (Exception e) {
        this.view.displayMsgToUser(e.getMessage());
        isInputValid = false;
      }
    } while (!isInputValid);

    userStockInput[0] = tickerNameFromUser;
    userStockInput[1] = Double.toString(numUnits);
    userStockInput[2] = String.valueOf(transactionDate);
    userStockInput[3] = String.valueOf(commission);
    userStockInput[4] = String.valueOf(0.0); //TODO: replace with price at which it was bought/sold
    userStockInput[5] = String.valueOf(false); //indicates shares are bought

    return userStockInput;
  }

  public LocalDate getDateFromView() {
    LocalDate valueDate = LocalDate.now();
    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    boolean isDateOkay = false;
    while (!isDateOkay) {
      try {
        view.getDateFromUser();
        valueDate = LocalDate.parse(scanner.next(), dateFormat);
        isDateOkay = true;
      } catch (Exception e) {
        view.displayMsgToUser("Invalid date. Please try again!");
        isDateOkay = false;
      }
    }
    return valueDate;
  }

  public Boolean validateSellOperation(int portfolioIndex, int buyOrSell, String[] stockDetails) {
    HashMap<String, Integer> stockMap = new HashMap<String, Integer>();
    IFlexiblePortfolio pfToCheck = user.getFlexiblePortfoliosCreatedObjects().get(portfolioIndex - 1);
    for (IstockModel s : pfToCheck.getStocksInPortfolio(LocalDate.now())) {
      if (stockMap.containsKey(s.getTickerName())) {
        stockMap.get(s.getTickerName());
      }
    }

    return true;
  }


}
