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
    this.retrievePortFolios();
  }

  public boolean retrievePortFolios() {
    if (user.getPortfolioNamesCreated().size() == 0) {
      view.displayMsgToUser("No portfolios created till now");
      return false;
    }
    view.displayMsgToUser("Following are the portfolios created till now:");
    view.displayListOfPortfolios(user.getPortfolioNamesCreated());
    int portfolioIndex = this.getSelectedPortFolioFromView();
    List<IstockModel> stocksToDisplay = user.displayStocksOfPortFolio(portfolioIndex,"Fixed");
    view.displayStocks(stocksToDisplay);
    return true;
  }
  public int getSelectedPortFolioFromView() {
    int index = -1;
    while ((index < 0) || (index > user.getPortfolioNamesCreated().size())) {
      view.getSelectedPortfolio();
      index = scanner.nextInt();
    }
    return index;
  }
}
