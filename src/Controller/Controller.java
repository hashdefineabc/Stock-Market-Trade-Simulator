package Controller;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.Date;

import Model.portfolio;
import Model.portfolioModel;
import Model.portfolioModelImpl;
import Model.stock;
import Model.user;
import View.ViewImpl;
import View.ViewInterface;

public class Controller {

  private static ViewInterface view = new ViewImpl();
  private static portfolioModel model = new portfolioModelImpl();

  private static Model.user user = new user();

  private static boolean checkDateLaterThanToday(LocalDate date) {
    String today = LocalDate.now().toString();
    if (today.compareTo(date.toString()) < 0) {
      return true;
    }
    return false;
  }

  private boolean checkIfDateToday(LocalDate date) {
    String today = LocalDate.now().toString();
    if (today.compareTo(date.toString()) == 0) {
      return true;
    }
    return false;
  }

  public static void Main(String args[]) throws ParseException {

    int option = view.displayMenu(); //TODO: validation for user option

    switch (option) {

      // create new portfolio and add stocks to the new portfolio
      case 1:
        portfolio newPortfolio = new portfolio();
        while(view.addMoreStocks()) {
          String[] s = view.takeStockInput();
//          stock newStock = new stock(s[0], Integer.valueOf(s[1]));

          //using builder method to create stocks
          stock newStock = stock.getBuilder()
                                .tickerName(s[0])
                                .numOfUnits(Integer.valueOf(s[1]))
                                .build();
          newPortfolio.addStocks(newStock);
        }
        user.CreateNewPortfolio(newPortfolio);
        user.savePortfolioToFile(newPortfolio);
        break;

      //retrieve portfolio

      case 2:
        view.displayListOfPortfolios(user.getPortfoliosCreated());
        int portfolioIndex = view.getPortfolioName();
        portfolio toDisplay = user.getPortfoliosCreated().get(portfolioIndex);
        view.displayStocks(toDisplay);
        break;

        // value of a particular portfolio
      case 3:
        view.displayListOfPortfolios(user.getPortfoliosCreated());
        int portfolioIndexForVal = view.getPortfolioName();
        portfolio toCalcVal = user.getPortfoliosCreated().get(portfolioIndexForVal);

        LocalDate date = view.getDate();
        //validation for date
        while (!checkDateLaterThanToday(date)) {
          view.displayMsgToUser("Can't get value for date greater than today");
          date = view.getDate();
        }
        if (checkDateLaterThanToday(date)) {
          view.displayMsgToUser("Value will be calculated based on yesterday's closing price.");
          date = date.minusDays(1); //modify date to yesterday
        }

        double val = toCalcVal.valueOfPortfolio(date);
        view.displayValue(val);
        break;

      case 4: System.exit(0);
    }
  }

}
