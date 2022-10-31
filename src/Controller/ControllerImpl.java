package Controller;

import java.io.InputStream;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import Model.portfolio;
import Model.stock;
import Model.User;
import View.ViewImpl;
import View.ViewInterface;

public class ControllerImpl {
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
        this.view.displayMsgToUser("Please enter only an integer value!!");
      }
    }
    return userOption;
  }

  public String[] takeStockInputFromView() {
    return view.takeStockInput();
  }

  public void go() {

    while(true) {
        switch(this.showMenuOnView()) {
        // create new portfolio and add stocks to the new portfolio
        case 1:
          view.displayMsgToUser("Creating a new portfolio...");
          String portfolioName = view.getPortfolioNameFromUser();
          portfolio newPortfolio = new portfolio(portfolioName);
          while (view.addMoreStocks()) {
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

        //retrieve portfolio
        case 2:
          view.displayMsgToUser("Following are the portfolios created till now:");
          List<String> portfolioNames = new ArrayList<>();
          List<portfolio> portfolioObjects = user.getPortfoliosCreated();
          for (portfolio p : portfolioObjects) {
            portfolioNames.add(p.nameOfPortFolio);
          }
          view.displayListOfPortfolios(portfolioNames);

          int portfolioIndex = view.getPortfolioName();
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
          int portfolioIndexForVal = view.getPortfolioName();
          portfolio toCalcVal = user.getPortfoliosCreated().get(portfolioIndexForVal - 1);

          LocalDate date = null;
          try {
            date = view.getDateFromUser();
          } catch (ParseException e) {
            throw new RuntimeException(e);
          }
          LocalDate today = LocalDate.now();
          //validation for date
          while (date.isAfter(today)) {
            view.displayMsgToUser("Can't get value for date greater than today");
            try {
              date = view.getDateFromUser();
            } catch (ParseException e) {
              throw new RuntimeException(e);
            }
          }
          if (date.equals(today)) {
            view.displayMsgToUser("Value will be calculated based on yesterday's closing price.");
            date = date.minusDays(1); //modify date to yesterday
          }

          double val = toCalcVal.valueOfPortfolio(date);


          view.displayValue(val);
          break;

        case 4:
          System.exit(0);
      }
    }
  }

}
