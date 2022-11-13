package controller.commands;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import controller.ICommandController;
import model.IUserInterface;
import model.IstockModel;
import model.User;
import view.ViewInterface;

public class CostBasis implements ICommandController {

  private ViewInterface view;
  private IUserInterface user;
  private Scanner scanner;

  public CostBasis(ViewInterface view, IUserInterface user) {
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

    if (user.getPortfolioNamesCreated(portfolioType).size() == 0) {
      view.displayMsgToUser("No " + portfolioType + " portfolios created till now!!!");
      return;
    }

    this.calculateCostBasis(portfolioType);

  }

  public void calculateCostBasis(String portfolioType) {
    view.displayMsgToUser("Following are the "+portfolioType+" portfolios created till now:");
    view.displayListOfPortfolios(user.getPortfolioNamesCreated(portfolioType));
    int portfolioIndex = this.getSelectedPortFolioFromView(portfolioType);
    //calculate cost basis n display to user
    Double costBasis = user.calculateCostBasisOfPortfolio(portfolioIndex,portfolioType);
    view.displayCostBasis(costBasis);
    
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

  public int getSelectedPortFolioFromView(String portfolioType) {
    int index = -1;
    boolean okay = false;
    do {
      try {
        view.getSelectedPortfolio();
        index = scanner.nextInt();
        if ((index < 0) || (index > user.getPortfolioNamesCreated(portfolioType).size())) {
          throw new IllegalArgumentException("Invalid Index");
        }
        okay = true;
      } catch (IllegalArgumentException ie) {
        this.view.displayMsgToUser(ie.getMessage());
        okay = false;
      }
    } while (!okay);

    return index;
  }

}
