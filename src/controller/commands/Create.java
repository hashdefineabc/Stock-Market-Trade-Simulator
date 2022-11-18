package controller.commands;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import model.IUserInterface;
import controller.ICommandController;
import model.Operation;
import model.PortfolioType;
import view.ViewInterface;

/**
 * The class Create implements the command controller interface.
 * It is one of the commands that is supported by our application.
 * Create operation is available for fixed and flexible portfolios.
 * It allows the user create a new portfolio.
 * It allows a user to create one or more portfolios with shares of one or more stock.
 * For fixed portfolios once created, shares cannot be added or removed from the portfolio.
 * For flexible portfolios once created, shares can be added or removed (bought or sold)
 * from the portfolio.
 */
public class Create implements ICommandController {
  private ViewInterface view;
  private IUserInterface user;
  private Scanner inputScanner;

  /**
   * Instantiates a new Create.
   *
   * @param view the view
   * @param user the user
   */
  public Create(ViewInterface view, IUserInterface user, Scanner scanner) {
    this.view = view;
    this.user = user;
    this.inputScanner = scanner;
  }

  @Override
  public void goController() {
    PortfolioType portfolioType = null;
    int fixOrFlex = this.showFixedOrFlexPortfolioOptionsOnView();
    if (fixOrFlex == 1) {
      //create a fixed portfolio
      portfolioType = PortfolioType.fixed;
    } else if (fixOrFlex == 2) {
      //create a flexible portfolio
      portfolioType = PortfolioType.flexible;
    }

    int manualOrFile = this.showCreatePortfolioOptionsOnView();
    if (manualOrFile == 1) {
      view.displayMsgToUser("Creating a new portfolio...");
      String portfolioName = this.getPortFolioNameFromView();
      List<String[]> stockList = new ArrayList<>();
      do {
        String[] s = this.takeStockInputFromView(portfolioType);
        stockList.add(s);
      }
      while (this.addMoreStocksFromView());
      if (user.createNewPortfolio(portfolioName, stockList, portfolioType)) {
        view.displayMsgToUser("Portfolio saved successfully");
      } else {
        view.displayMsgToUser("Portfolio was not saved. Try again");
      }
    } else if (manualOrFile == 2) {
      //upload a file
      this.displayCsvPathToUser(portfolioType);
      //check if file uploaded
      view.isFileUploaded();
      if (inputScanner.nextInt() == 1) {
        user.createPortFolioFromFile(portfolioType);
      }
    }
  }

  /**
   * Show create portfolio options on view int.
   *
   * @return the int
   */
  private int showCreatePortfolioOptionsOnView() {
    int userOption = 0;
    List<Integer> validMenuOptions = Arrays.asList(1, 2);
    do {
      try {
        this.view.displayCreatePortFolioOptions();
        userOption = Integer.parseInt(inputScanner.next());
      } catch (IllegalArgumentException ie) {
        this.view.displayMsgToUser("Please enter only an integer value from the below options!!");
      }
    }
    while (!validMenuOptions.contains(userOption));
    return userOption;
  }

  private int showFixedOrFlexPortfolioOptionsOnView() {
    int userOption = 0;
    List<Integer> validMenuOptions = Arrays.asList(1, 2);
    do {
      try {
        this.view.chooseFixedOrFlexible();
        userOption = Integer.parseInt(inputScanner.next());
      } catch (IllegalArgumentException ie) {
        this.view.displayMsgToUser("Please enter only an integer value from the below options!!");
      }
    }
    while (!validMenuOptions.contains(userOption));
    return userOption;
  }


  private String getPortFolioNameFromView() {
    String portfolioName = null;
    Boolean fileExists = false;
    do {
      try {
        this.view.getPortfolioNameFromUser();
        portfolioName = inputScanner.next();
        if (user.checkIfFileExists(portfolioName, PortfolioType.fixed)
                || user.checkIfFileExists(portfolioName, PortfolioType.flexible)) {
          throw new IllegalArgumentException("A portfolio by this name exists already");
        }
        fileExists = false;
      } catch (IllegalArgumentException e) {
        this.view.displayMsgToUser(e.getMessage());
        fileExists = true;
      }

    }
    while (fileExists);
    return portfolioName;
  }

  private String[] takeStockInputFromView(PortfolioType portfolioType) {
    String[] userStockInput = new String[6];
    String tickerNameFromUser = "";
    Double numUnits = 0.0;
    Double commission = 0.0;
    LocalDate transactionDate = LocalDate.now();
    Boolean isInputValid = false;

    if (portfolioType.equals(PortfolioType.flexible)) {
      do {
        try {
          this.view.takeTickerName();
          tickerNameFromUser = inputScanner.next();
          if (!user.isTickerValid(tickerNameFromUser)) {
            throw new IllegalArgumentException("Invalid ticker name!");
          }
          this.view.displayMsgToUser("Enter the number of units purchased");
          numUnits = inputScanner.nextDouble();
          Long numOfUnitsInt = Math.round(numUnits);
          while (numUnits <= 0.0) {
            view.displayMsgToUser("Number of units purchased cannot be -ve, Please enter a "
                    + "positive number");
            numUnits = inputScanner.nextDouble();
            numOfUnitsInt = Math.round(numUnits);
          }
          while ((double) numOfUnitsInt != numUnits) {
            view.displayMsgToUser("Cannot purchase fractional shares, Please enter a whole number");
            numUnits = inputScanner.nextDouble();
            numOfUnitsInt = Math.round(numUnits);
          }

          this.view.takeCommissionValue();
          commission = inputScanner.nextDouble();
          while (commission <= 0.0) {
            view.displayMsgToUser("Commission cannot be -ve, Please enter a positive value");
            commission = inputScanner.nextDouble();
          }

          transactionDate = this.getDateFromView();

          isInputValid = true;
        } catch (Exception e) {
          this.view.displayMsgToUser(e.getMessage());
          isInputValid = false;
        }
      }
      while (!isInputValid);
    } else if (portfolioType.equals(PortfolioType.fixed)) {
      do {
        try {
          this.view.takeTickerName();
          tickerNameFromUser = inputScanner.next();
          if (!user.isTickerValid(tickerNameFromUser)) {
            throw new IllegalArgumentException("Invalid ticker name!");
          }
          this.view.displayMsgToUser("Enter the number of units purchased");
          numUnits = inputScanner.nextDouble();
          Long numOfUnitsInt = Math.round(numUnits);
          while (numUnits <= 0.0) {
            view.displayMsgToUser("Number of units purchased cannot be -ve, "
                    + "Please enter a postive value");
            numUnits = inputScanner.nextDouble();
            numOfUnitsInt = Math.round(numUnits);
          }
          while ((double) numOfUnitsInt != numUnits) {
            view.displayMsgToUser("Cannot purchase fractional shares, Please enter a whole number");
            numUnits = inputScanner.nextDouble();
            numOfUnitsInt = Math.round(numUnits);
          }

          isInputValid = true;
        } catch (Exception e) {
          this.view.displayMsgToUser(e.getMessage());
          isInputValid = false;
        }
      } while (!isInputValid);
    }
    userStockInput[0] = tickerNameFromUser;
    userStockInput[1] = Double.toString(numUnits);
    userStockInput[2] = String.valueOf(transactionDate);
    userStockInput[3] = String.valueOf(commission);
    userStockInput[2] = String.valueOf(transactionDate);
    Double transactionValue = user.getStockPriceFromDB(tickerNameFromUser, transactionDate);
    LocalTime currentTime = LocalTime.now();
    if (transactionDate.equals(LocalDate.now())
            && currentTime.isBefore(LocalTime.of(16, 0))) {
      view.displayMsgToUser("Market is not closed today yet, "
              + "previous available closing price will be considered as your transaction price...");
      transactionValue = user.getStockPriceFromDB(tickerNameFromUser,
              transactionDate.minusDays(1));
      transactionDate = transactionDate.minusDays(1);
    }
    while (transactionValue == 0.0) {
      view.displayMsgToUser("Market was closed on "
              + transactionDate + "\n Calculating price of previous date");
      transactionValue = user.getStockPriceFromDB(tickerNameFromUser,
              transactionDate.minusDays(1));
      transactionDate = transactionDate.minusDays(1);
    }
    view.displayMsgToUser("Price of the stock considered is of the date " + transactionDate);
    userStockInput[4] = String.valueOf(transactionValue);
    userStockInput[5] = String.valueOf(Operation.BUY); //indicates shares are bought


    return userStockInput;
  }

  private Boolean addMoreStocksFromView() {
    int userInput = 0;
    List<Integer> validOptions = Arrays.asList(0, 1);
    Boolean addMore = false;
    do {
      try {
        this.view.addMoreStocks();
        userInput = inputScanner.nextInt();
        if (userInput == 1) {
          return true;
        }
        else if (userInput == 0) {
          return false;
        }
        if (!validOptions.contains(userInput)) {
          throw new IllegalArgumentException("Please select a valid option!\n");
        }
      } catch (IllegalArgumentException ie) {
        this.view.displayMsgToUser(ie.getMessage());
        addMore = true;
      }
    }
    while (addMore);
    return userInput == 1;
  }

  private void displayCsvPathToUser(PortfolioType portfolioType) {
    if (portfolioType.equals(PortfolioType.fixed)) {
      this.view.displayMsgToUser("Please place the csv at the location:\n"
              + this.user.getFixedPFPath());
    } else if (portfolioType.equals(PortfolioType.flexible)) {
      this.view.displayMsgToUser("Please place the csv at the location:\n"
              + this.user.getFlexPFPath());
    }
  }

  private LocalDate getDateFromView() {
    LocalDate valueDate = LocalDate.now();
    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    boolean isDateOkay = false;
    while (!isDateOkay) {
      try {
        view.displayMsgToUser("Enter the date of transaction : (yyyy-mm-dd)");
        valueDate = LocalDate.parse(inputScanner.next(), dateFormat);
        isDateOkay = true;
      } catch (Exception e) {
        view.displayMsgToUser("Invalid date. Please try again!");
        isDateOkay = false;
      }
      if (valueDate.isAfter(LocalDate.now())) {
        view.displayMsgToUser("Future date entered..."
                + " Please enter a date that is not later than today!!! ");
        isDateOkay = false;
      }
    }
    return valueDate;
  }

}
