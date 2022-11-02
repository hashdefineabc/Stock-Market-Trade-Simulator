package Controller;

import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Model.IUserInterface;
import Model.portfolio;
import Model.portfolioModel;
import Model.stock;
import Model.User;
import View.ViewInterface;
import java.util.Scanner;

public class ControllerImpl implements Controller{
  private static ViewInterface view;
  private static IUserInterface user;
  private InputStream userInput;
  Scanner scanner;

  public ControllerImpl(User user, ViewInterface view, InputStream in) {
    this.view = view;
    this.user = user;
    this.userInput = in;
    scanner = new Scanner(this.userInput);
  }

  public int showMenuOnView() {
    int userOption = 5;
    List<Integer> validMenuOptions = Arrays.asList(1,2,3,4);
    while (!validMenuOptions.contains(userOption)){
      try {
        this.view.displayMenu();
        userOption = Integer.parseInt(scanner.next());
      } catch (IllegalArgumentException ie) {
        this.view.displayMsgToUser("Please enter only an integer value from the below options!!");
      }
    }
    return userOption;
  }

  public int showCreatePortfolioOptionsOnView() {
    int userOption = 3;
    List<Integer> validMenuOptions = Arrays.asList(1,2);
    while (!validMenuOptions.contains(userOption)){
      try {
        this.view.displayCreatePortFolioOptions();
        userOption = Integer.parseInt(scanner.next());
      } catch (IllegalArgumentException ie) {
        this.view.displayMsgToUser("Please enter only an integer value from the below options!!");
      }
    }
    return userOption;
  }

  public String[] takeStockInputFromView() {
    String[] userStockInput = new String[2];
    String tickerNameFromUser = "";
    int numUnits = -1;
    while (numUnits <= 0 || !user.isTickerValid(tickerNameFromUser)) {
      this.view.takeTickerName();
      tickerNameFromUser = scanner.next();
      userStockInput[0] = tickerNameFromUser;
      this.view.takeNumOfUnits();
      numUnits = scanner.nextInt();
      userStockInput[1] = Integer.toString(numUnits);
    }

    return userStockInput;
  }

  public boolean addMoreStocksFromView() {
    int userInput = 3;
    List<Integer> validOptions = Arrays.asList(0,1);

    while (!validOptions.contains(userInput)){
      this.view.addMoreStocks();
      userInput = scanner.nextInt();
    }
    return userInput == 1;
  }

  public String getPortFolioNameFromView() {
    this.view.getPortfolioNameFromUser();
    String portfolioName = scanner.next();
    while(user.checkIfFileExists(portfolioName)) {
      this.view.displayMsgToUser("This portfolio already exists in the system!!");
      this.view.getPortfolioNameFromUser();
      portfolioName = scanner.next();
    }
    return portfolioName;
  }

  public int getSelectedPortFolioFromView() {
    int index = -1;
    while ((index < 0) || (index > user.getPortfoliosCreated().size())) {
      view.getSelectedPortfolio();
      index = scanner.nextInt();
    }
    return index;
  }

  public LocalDate getDateFromView() {
    LocalDate valueDate = LocalDate.now();
    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    boolean isDateOkay = false;
    while (isDateOkay == false) {
      try {
        view.getDateFromUser();
        valueDate =  LocalDate.parse(scanner.next(), dateFormat);
        isDateOkay = true;
      } catch (Exception e) {
        view.displayMsgToUser("Invalid date. Please try again!");
        isDateOkay = false;
      }
    }
    return valueDate;
  }

  private boolean isPortFolioEmpty(portfolioModel p) {
    if (p.getStocks().size() == 0) {
      this.view.displayMsgToUser("Portfolio is empty right now...");
      return true;
    }
    return false;
  }

  public void displayCsvPathToUser() {
    this.view.displayMsgToUser("Please place the csv at the location:\n" + this.user.getFolderPath());
  }



  public void go() {

    while(true) {
      switch(this.showMenuOnView()) {
        // create new portfolio and add stocks to the new portfolio
        case 1:
          switch(this.showCreatePortfolioOptionsOnView()) {
            case 1:
              view.displayMsgToUser("Creating a new portfolio...");
              String portfolioName = this.getPortFolioNameFromView();
              portfolioModel newPortfolio = new portfolio(portfolioName);
              while (this.addMoreStocksFromView() || this.isPortFolioEmpty(newPortfolio)) {
                String[] s = this.takeStockInputFromView();
                //using builder method to create stocks
                stock newStock = stock.getBuilder()
                        .tickerName(s[0])
                        .numOfUnits(Integer.valueOf(s[1]))
                        .build();
                newPortfolio.addStocks(newStock);
              }
              user.CreateNewPortfolio(newPortfolio);
              user.savePortfolioToFile(newPortfolio);
              if (user.checkIfFileExists(portfolioName)) {
                view.displayMsgToUser("Portfolio saved successfully");
              } else {
                view.displayMsgToUser("Portfolio was not saved. Try again");
              }
              break;

            case 2: //upload a file
              this.displayCsvPathToUser();
              //check if file uploaded
              view.isFileUploaded();
              if (scanner.nextInt() == 1) { //TODO: complete this fn
                user.createPortFolioFromFile();
              }
              break;
          }
          break;

        //retrieve portfolio
        case 2:
          if (this.user.getportfoliosList().size() == 0) {
            view.displayMsgToUser("No portfolios created till now");
            continue;
          }
          view.displayMsgToUser("Following are the portfolios created till now:");
          List<String> portfolioNames = new ArrayList<>();
          List<portfolioModel> portfolioObjects = user.getPortfoliosCreated();
          for (portfolioModel p : portfolioObjects) {
            portfolioNames.add(p.getNameOfPortFolio());
          }
          view.displayListOfPortfolios(portfolioNames);

          int portfolioIndex = this.getSelectedPortFolioFromView();
          portfolioModel toDisplay = user.getPortfoliosCreated().get(portfolioIndex - 1);
          List<String[]> stocksToDisplay = toDisplay.toListOfString();
          view.displayStocks(stocksToDisplay);
          break;

        // value of a particular portfolio
        case 3:
          List<String> stocksNamesToDisplay = new ArrayList<>();
          for (portfolioModel p : user.getPortfoliosCreated()) {
            stocksNamesToDisplay.add(p.getNameOfPortFolio());
          }
          view.displayListOfPortfolios(stocksNamesToDisplay);
          int portfolioIndexForVal = this.getSelectedPortFolioFromView();
          portfolioModel toCalcVal = user.getPortfoliosCreated().get(portfolioIndexForVal - 1);

          LocalDate date = this.getDateFromView();
          LocalDate today = LocalDate.now();
          //validation for date
          while (date.isAfter(today)) {
            view.displayMsgToUser("Can't get value for date greater than today");
            date = this.getDateFromView();
          }
          LocalTime fourPM = LocalTime.of(16, 00, 00, 342123342);
          if (date.equals(today) && java.time.LocalTime.now().compareTo(fourPM) < 0) {
            view.displayMsgToUser("Value will be calculated based on yesterday's closing price.");
            date = date.minusDays(1); //modify date to yesterday
          }

          double val = toCalcVal.valueOfPortfolio(date);
          view.displayValue(val);
          if(val == 0) {
            view.displayMsgToUser("Market was closed on "+date);
          }
          break;

        case 4:
          view.displayMsgToUser("Closing the application");
          System.exit(0);
          break;

        default:
          System.exit(0);
      }
    }
  }

}