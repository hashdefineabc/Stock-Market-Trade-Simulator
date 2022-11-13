package controller.commands;

import java.util.List;
import java.util.Scanner;

import controller.ICommandController;
import model.IUserInterface;
import model.IstockModel;
import model.User;
import view.ViewInterface;

public class Composition implements ICommandController {

  private ViewInterface view;
  private IUserInterface user;
  private Scanner scanner;

  public Composition(ViewInterface view, IUserInterface user) {
    this.view = view;
    this.user = user;
    scanner = new Scanner(System.in);
  }
  @Override
  public void go() {

    view.chooseFixedOrFlexible();
    int userInput = scanner.nextInt();
    String portfolioType = "";
    if(userInput == 1) { //fixed portfolio
      portfolioType = "fixed";
      if (user.getPortfolioNamesCreated("fixed").size() == 0) {
        view.displayMsgToUser("No fixed portfolios created till now!!!");
        return;
      }
    }
    else if(userInput == 2) { //flexible portfolio
      portfolioType = "flexible";
      if (user.getPortfolioNamesCreated("flexible").size() == 0) {
        view.displayMsgToUser("No flexible portfolios created till now!!!");
        return;
      }
    }
    else {
      view.displayMsgToUser("Invalid input!!!!");
    }

    this.retrievePortFolios(portfolioType);
  }

  public Boolean retrievePortFolios(String portfolioType) {
    if (user.getPortfolioNamesCreated(portfolioType).size() == 0) {
      view.displayMsgToUser("No "+portfolioType+" portfolios created till now");
      return false;
    }
    view.displayMsgToUser("Following are the "+portfolioType+" portfolios created till now:");

    view.displayListOfPortfolios(user.getPortfolioNamesCreated(portfolioType));
    int portfolioIndex = this.getSelectedPortFolioFromView(portfolioType);
    List<IstockModel> stocksToDisplay = user.displayStocksOfPortFolio(portfolioIndex,portfolioType);
    view.displayStocks(stocksToDisplay);
    return true;
  }

  public int getSelectedPortFolioFromView(String portfolioType) {
    int index = -1;
    while ((index < 0) || (index > user.getPortfolioNamesCreated(portfolioType).size())) {
      view.getSelectedPortfolio();
      index = scanner.nextInt();
    }
    return index;
  }
}
