package Controller;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

import Model.portfolioModel;
import Model.portfolioModelImpl;
import View.ViewImpl;
import View.ViewInterface;

public class Controller {

  private static ViewInterface view = new ViewImpl();
  private static portfolioModel model = new portfolioModelImpl();

  public static void Main(String args[]) {

    int option = view.displayMenu();

    switch (option) {
      // create new portfolio
      case 1: model = new portfolioModelImpl();
      while(view.addMoreStocks()) {
        String[] s = view.takeStockInput();
      }
      break;

      //retrieve portfolio

      case 2: view.displayListOfPortfolios(model.showPortfolioNames());
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

  private void run() {
    Scanner scan = new Scanner(System.in);
    int inputOption = 9;
    String menu = "Select one of the below options: \n" +
            "1.Create a new portfolio.\n" +
            "2.Examine a portfolio\n" +
            "3.Check the total value of a portfolio\n" +
            "0.Exit\n";
    System.out.println(menu);
    inputOption = scan.nextInt();
    while (inputOption != 0) {
      switch(inputOption) {
        case 1:
          // function to create a new portfolio
          break;
        case 2:
          //examine a current portfolio
          break;
        case 3:
          //check the total value
          break;
      }
    }
    System.out.println("END");

  }
}
