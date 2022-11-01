package Controller;

import java.io.InputStream;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import Model.portfolio;
import Model.stock;
import Model.User;
import View.ViewImpl;
import View.ViewInterface;

public class ControllerImpl implements Controller{
  private static ViewInterface view;
  private static User user;

  public ControllerImpl(User user, ViewInterface view) {
    this.view = view;
    this.user = user;
  }

  public int showMenuOnView() {
    int userOption = 5;
    List<Integer> validMenuOptions = Arrays.asList(1,2,3,4);
    while (!validMenuOptions.contains(userOption)){
      try {
        userOption = Integer.parseInt( this.view.displayMenu());
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
        userOption = Integer.parseInt( this.view.displayCreatePortFolioOptions());
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
      userStockInput = this.view.takeStockInput();
      numUnits = Integer.parseInt(userStockInput[1]);
      tickerNameFromUser = userStockInput[0];
    }

    return userStockInput;
  }

  public boolean addMoreStocksFromView() {
    return view.addMoreStocks();
  }

  public String getPortFolioNameFromView() {
    String portfolioName = this.view.getPortfolioNameFromUser();;
    while(user.checkIfFileExists(portfolioName)) {
      this.view.displayMsgToUser("This portfolio already exists in the system!!");
      portfolioName = this.view.getPortfolioNameFromUser();
    }
    return portfolioName;
  }

  public int getSelectedPortFolioFromView() {
    int index = -1;
    while ((index < 0) || (index > user.getPortfoliosCreated().size())) {
      index = view.getSelectedPortfolio();
    }
    return index;
  }

  public LocalDate getDateFromView() {
    LocalDate valueDate = LocalDate.now();
    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    boolean isDateOkay = false;
    while (isDateOkay == false) {
      try {
        valueDate =  LocalDate.parse(view.getDateFromUser(), dateFormat);
        isDateOkay = true;
      } catch (Exception e) {
        view.displayMsgToUser("Invalid date. Please try again!");
        isDateOkay = false;
      }
    }
    return valueDate;
  }

  private boolean isPortFolioEmpty(portfolio p) {
    if (p.stocks.size() == 0) {
      this.view.displayMsgToUser("Portfolio is empty right now...");
      return true;
    }
    return false;
  }

  public void displayCsvPathToUser() {
    this.view.displayMsgToUser("Please place the csv at the location:\n" + this.user.folderPath);
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
              portfolio newPortfolio = new portfolio(portfolioName);
              while (this.addMoreStocksFromView() || this.isPortFolioEmpty(newPortfolio)) {
                String[] s = this.takeStockInputFromView();
                //stock newStock = new stock(s[0], Integer.valueOf(s[1]));
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
              if (view.isFileUploaded()) { //TODO: complete this fn
                user.createPortFolioFromFile();
              }
              break;
          }
          break;

        //retrieve portfolio
        case 2:
          view.displayMsgToUser("Following are the portfolios created till now:");
          List<String> portfolioNames = new ArrayList<>();
          List<portfolio> portfolioObjects = user.getPortfoliosCreated();
          for (portfolio p : portfolioObjects) {
            portfolioNames.add(p.nameOfPortFolio);
          }
          view.displayListOfPortfolios(portfolioNames);

          int portfolioIndex = this.getSelectedPortFolioFromView();
          portfolio toDisplay = user.getPortfoliosCreated().get(portfolioIndex - 1);
          List<String[]> stocksToDisplay = toDisplay.toListOfString();
          view.displayStocks(stocksToDisplay);
          break;

        // value of a particular portfolio
        case 3:
          List<String> stocksNamesToDisplay = new ArrayList<>();
          for (portfolio p : user.getPortfoliosCreated()) {
            stocksNamesToDisplay.add(p.getNameOfPortFolio());
          }
          view.displayListOfPortfolios(stocksNamesToDisplay);
          int portfolioIndexForVal = view.getSelectedPortfolio();
          portfolio toCalcVal = user.getPortfoliosCreated().get(portfolioIndexForVal - 1);

          LocalDate date = this.getDateFromView();
          LocalDate today = LocalDate.now();
          //validation for date
          while (date.isAfter(today)) {
            view.displayMsgToUser("Can't get value for date greater than today");
            date = this.getDateFromView();
          }
          if (date.equals(today)) {
            view.displayMsgToUser("Value will be calculated based on yesterday's closing price.");
            date = date.minusDays(1); //modify date to yesterday
          }

          double val = toCalcVal.valueOfPortfolio(date);
          view.displayValue(val);
          break;

        case 4:
          view.displayMsgToUser("Closing the application");
          System.exit(0);
          break;
      }
    }
  }

}
