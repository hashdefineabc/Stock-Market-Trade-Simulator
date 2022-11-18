package controller.commands;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import controller.ICommandController;
import model.IUserInterface;
import model.PortfolioType;
import view.ViewInterface;

/**
 * The class CostBasis implements the command controller interface.
 * It is one of the commands that is supported by our application.
 * CostBasis operation is available for flexible portfolios only.
 * It allows the user to view the total cost spent on a portfolio.
 * It displays the total amount of money invested in a portfolio by a specific date.
 * This includes all the purchases made in that portfolio till that date.
 */
public class CostBasis implements ICommandController {

  private ViewInterface view;
  private IUserInterface user;
  private Scanner inputScanner;

  /**
   * Instantiates a new Cost basis.
   * It takes view and model (user) and instantiates it.
   * It interacts with view and main model which is user.
   * @param view the view
   * @param user the user
   */
  public CostBasis(ViewInterface view, IUserInterface user, Scanner scanner) {
    this.view = view;
    this.user = user;
    this.inputScanner = scanner;
  }

  @Override
  public void goController() {
    PortfolioType portfolioType = PortfolioType.flexible;
    view.displayMsgToUser("Cost Basis can be calculated only for flexible portfolios.");
    if (user.getPortfolioNamesCreated(portfolioType).size() == 0) {
      view.displayMsgToUser("No " + portfolioType + " portfolios created till now!!!");
      return;
    }

    this.calculateCostBasis(portfolioType);

  }

  private void calculateCostBasis(PortfolioType portfolioType) {
    view.displayMsgToUser("Following are the " + portfolioType + " portfolios created till now:");
    view.displayListOfPortfolios(user.getPortfolioNamesCreated(portfolioType));
    int portfolioIndex = this.getSelectedPortFolioFromView(portfolioType);
    LocalDate costBasisDate = this.getDateFromView();
    //calculate cost basis n display to user
    Double costBasis = user.calculateCostBasisOfPortfolio(portfolioIndex,
            portfolioType, costBasisDate);
    view.displayCostBasis(costBasis, costBasisDate);

  }

  private int getSelectedPortFolioFromView(PortfolioType portfolioType) {
    int index = -1;
    boolean okay = false;
    do {
      try {
        view.getSelectedPortfolio();
        index = this.inputScanner.nextInt();
        if ((index < 0) || (index > user.getPortfolioNamesCreated(portfolioType).size())) {
          throw new IllegalArgumentException("Invalid Index");
        }
        okay = true;
      } catch (IllegalArgumentException ie) {
        this.view.displayMsgToUser(ie.getMessage());
        okay = false;
      }
    }
    while (!okay);
    return index;
  }

  private LocalDate getDateFromView() {
    LocalDate valueDate = LocalDate.now();
    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    boolean isDateOkay = false;
    while (!isDateOkay) {
      try {
        view.getDateFromUser();
        valueDate = LocalDate.parse(this.inputScanner.next(), dateFormat);
        isDateOkay = true;
      } catch (Exception e) {
        view.displayMsgToUser("Invalid date. Please try again!");
        isDateOkay = false;
      }
    }
    return valueDate;
  }
}
