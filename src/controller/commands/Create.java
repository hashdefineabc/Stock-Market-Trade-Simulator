package controller.commands;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import model.IUserInterface;
import model.User;
import controller.ICommandController;
import view.ViewInterface;

public class Create implements ICommandController {
  private ViewInterface view;
  private IUserInterface user;
  private Scanner scanner;
  public Create(ViewInterface view, IUserInterface user) {
    this.view = view;
    this.user = user;
    scanner = new Scanner(System.in);
  }

  @Override
  public void go() {
    String portfolioType = null;
    if (this.showFixedOrFlexPortfolioOptionsOnView() == 1) {
      //create a fixed portfolio
      portfolioType = "fixed";
    }
    else if (this.showFixedOrFlexPortfolioOptionsOnView() == 2) {
      //create a flexible portfolio
      portfolioType = "flexible";
    }

    if (this.showCreatePortfolioOptionsOnView() == 1) {
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
    } else if (this.showCreatePortfolioOptionsOnView() == 2) {
      //upload a file
      this.displayCsvPathToUser(portfolioType);
      //check if file uploaded
      view.isFileUploaded(); //TODO: put in a controller fn
      if (scanner.nextInt() == 1) {
        user.createPortFolioFromFile(portfolioType);
      }
    }
  }

  /**
   * Show create portfolio options on view int.
   *
   * @return the int
   */
  public int showCreatePortfolioOptionsOnView() {
    int userOption = 0;
    List<Integer> validMenuOptions = Arrays.asList(1, 2);
    do {
      try {
        this.view.displayCreatePortFolioOptions();
        userOption = Integer.parseInt(scanner.next());
      } catch (IllegalArgumentException ie) {
        this.view.displayMsgToUser("Please enter only an integer value from the below options!!");
      }
    } while (!validMenuOptions.contains(userOption));
    return userOption;
  }

  public int showFixedOrFlexPortfolioOptionsOnView() {
    int userOption = 0;
    List<Integer> validMenuOptions = Arrays.asList(1, 2);
    do {
      try {
        this.view.chooseFixedOrFlexible();
        userOption = Integer.parseInt(scanner.next());
      } catch (IllegalArgumentException ie) {
        this.view.displayMsgToUser("Please enter only an integer value from the below options!!");
      }
    } while (!validMenuOptions.contains(userOption));
    return userOption;
  }


  public String getPortFolioNameFromView() {
    String portfolioName = null;
    Boolean fileExists = false;
    do {
      try{
        this.view.getPortfolioNameFromUser();
        portfolioName = scanner.next();
        if (user.checkIfFileExists(portfolioName,"fixed")
                || user.checkIfFileExists(portfolioName, "flexible")) {
          throw new IllegalArgumentException("A portfolio by this name exists already");
        }
        fileExists = false;
      } catch (IllegalArgumentException e) {
        this.view.displayMsgToUser(e.getMessage());
        fileExists = true;
      }

    } while (fileExists);
    return portfolioName;
  }

  public String[] takeStockInputFromView(String portfolioType) {
    String[] userStockInput = new String[5];
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
        if (numUnits <= 0.0) {
          throw new IllegalArgumentException("Number of units purchased cannot be -ve");
        }
        else if (numUnits != Integer.parseInt(String.valueOf(numUnits))) {
          throw new IllegalArgumentException("Cannot purchase fractional shares");
        }
        isInputValid = true;
      } catch (Exception e) {
        this.view.displayMsgToUser(e.getMessage());
        isInputValid = false;
      }
    } while (!isInputValid);


    if(portfolioType.equals("flexible")) {
      this.view.takeDateOfTransaction();
      transactionDate = LocalDate.parse(scanner.next());

      this.view.takeCommissionValue();
      commission = scanner.nextDouble();

    }

    userStockInput[0] = tickerNameFromUser;
    userStockInput[1] = Double.toString(numUnits);
    userStockInput[2] = String.valueOf(transactionDate);
    userStockInput[3] = String.valueOf(commission);
    userStockInput[4] = String.valueOf(0.0); //replace with price at which it was bought/sold

    return userStockInput;
  }

  public Boolean addMoreStocksFromView() {
    int userInput = 0;
    List<Integer> validOptions = Arrays.asList(0, 1);
    Boolean addMore = false;

    do {
      try {
        this.view.addMoreStocks();
        userInput = scanner.nextInt();
        if (!validOptions.contains(userInput)) {
          throw new IllegalArgumentException("Please select a valid option!");
        }
      } catch (IllegalArgumentException ie) {
        this.view.displayMsgToUser(ie.getMessage());
        addMore = true;
      }
    } while (!addMore);
    return userInput == 1;
  }

  public void displayCsvPathToUser(String portfolioType) {
    if (portfolioType.equals("fixed")) {
      this.view.displayMsgToUser("Please place the csv at the location:\n"
              + this.user.getFixedPFPath());
    }
    else if (portfolioType.equals("flexible")) {
      this.view.displayMsgToUser("Please place the csv at the location:\n"
              + this.user.getFlexPFPath());
    }
  }

}
