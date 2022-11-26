package controller.commands;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

import controller.ICommandController;
import model.IUserInterface;
import model.PortfolioType;
import view.ViewInterface;

/**
 * The class DisplayChart implements the command controller interface.
 * It is one of the commands that is supported by our application.
 * DisplayChart operation is available for fixed and flexible portfolios.
 * It allows the user view the performance of a portfolio.
 * It facilitates seeing the performance of a portfolio "at a glance" offering a better way.
 * We provide users with 3 options
 * 1. To view the performance of a portfolio for the previous week
 * 2. To view the performance of a portfolio for the previous month
 * 3. To view the performance of a portfolio for the previous year
 * We visualize the performance by using a bar or line chart.
 */
public class DisplayChart implements ICommandController {
  private ViewInterface view;
  private IUserInterface user;
  private Scanner inputScanner;
  private Map<LocalDate, String> chart;

  /**
   * Instantiates a new Display chart.
   *
   * @param view the view
   * @param user the user
   */
  public DisplayChart(ViewInterface view, IUserInterface user, Scanner scanner) {
    this.view = view;
    this.user = user;
    this.chart = new LinkedHashMap<>();
    inputScanner = scanner;
  }

  @Override
  public void goController() {
    boolean isValid = false;

    int userInput;
    PortfolioType portfolioType = null;
    do {
      view.chooseFixedOrFlexible();
      userInput = inputScanner.nextInt();
      if (userInput == 1) { //fixed portfolio
        portfolioType = PortfolioType.fixed;
        if (user.getPortfolioNamesCreated(portfolioType).size() == 0) {
          view.displayMsgToUser("No fixed portfolios created till now, can't display chart");
          return;
        }
      } else if (userInput == 2) { //flexible portfolio
        portfolioType = PortfolioType.flexible;
        if (user.getPortfolioNamesCreated(portfolioType).size() == 0) {
          view.displayMsgToUser("No flexible portfolios created till now, can't display chart");
          return;
        }
      } else {
        view.displayMsgToUser("Invalid input!!!!");
      }
    }
    while (userInput > 2 || userInput <= 0);

    view.displayMsgToUser("Following are the fixed portfolios created till now:");
    view.displayListOfPortfolios(user.getPortfolioNamesCreated(portfolioType));
    int portfolioIndexForVal = this.getSelectedPortFolioFromView(portfolioType);
    view.displayMsgToUser("Pick a portfolio");

    int option = 1;

    while (!isValid) {
      view.displayOptionsForChart();
      option = inputScanner.nextInt();

      if (option == 1 || option == 2 || option == 3) {
        isValid = true;
      } else {
        view.displayMsgToUser("Please select a valid option");
      }
    }

    this.chart = user.calculateChart(option, portfolioIndexForVal, portfolioType);
    if (option == 1) {
      view.displayMsgToUser("Performance of the portfolio " +
              user.getPortfolioName(portfolioIndexForVal, portfolioType) +
              " from " + LocalDate.now() + " to " + LocalDate.now().minusDays(7));
      view.displayChartWeek(chart);
    } else if (option == 2) {
      view.displayMsgToUser("Performance of the portfolio " +
              user.getPortfolioName(portfolioIndexForVal, portfolioType) +
              " from " + LocalDate.now() + " to " + LocalDate.now().minusDays(30));
      view.displayChartWeek(chart);
    } else {
      view.displayMsgToUser("Performance of the portfolio " +
              user.getPortfolioName(portfolioIndexForVal, portfolioType) +
              " from " + LocalDate.now().getMonth() + " " + LocalDate.now().getYear() +
              " to " + LocalDate.now().minusMonths(11).getMonth() + " " +
              LocalDate.now().minusMonths(11).getYear());
      view.displayChartMonth(chart);
    }

    view.displayMsgToUser("<* means the value of the portfolio for a " +
            "particular timestamp is less than the scale");
    view.displayMsgToUser("Scale: * = $" + user.getScale(portfolioIndexForVal, portfolioType));

  }

  private int getSelectedPortFolioFromView(PortfolioType portfolioType) {
    int index = -1;
    while ((index < 0) || (index > user.getPortfolioNamesCreated(portfolioType).size())) {
      index = inputScanner.nextInt();
    }
    return index;
  }
}
