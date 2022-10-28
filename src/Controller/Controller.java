package Controller;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

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

  public static void Main(String args[]) {

    int option = view.displayMenu();

    switch (option) {

      // create new portfolio and add stocks to the new portfolio
      case 1:
        portfolio newPortfolio = new portfolio();
        while(view.addMoreStocks()) {
          String[] s = view.takeStockInput();
          stock newStock = new stock(s[0], Integer.valueOf(s[1]));
          newPortfolio.addStocks(newStock);
        }
        break;

      //retrieve portfolio

      case 2:
        view.displayListOfPortfolios(model.showPortfolioNames());
        String portfolioName = view.getPortfolioName();
        List<List<String>> completePortfolio = model.getPortfolio(portfolioName);
        view.displayStocks(completePortfolio);
        break;

      case 3: view.displayListOfPortfolios(model.showPortfolioNames());
        String portfolio = view.getPortfolioName();
        Date date = view.getDate();
        double val = model.valueOfPortfolio(portfolio, date);
        view.displayValue(val);
        break;

      case 4: System.exit(0);
    }
  }

}
