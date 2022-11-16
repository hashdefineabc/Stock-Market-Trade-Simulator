package controller.commands;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

import controller.ICommandController;
import model.IUserInterface;
import model.PortfolioType;
import view.ViewInterface;

public class DisplayChart implements ICommandController {
  private ViewInterface view;
  private IUserInterface user;
  private Scanner scanner;
  private Map<LocalDate, String> chart;

  public DisplayChart(ViewInterface view, IUserInterface user) {
    this.view = view;
    this.user = user;
    this.chart = new LinkedHashMap<>();
    scanner = new Scanner(System.in);
  }
  @Override
  public void go() {
    boolean isValid = false;

    int userInput;
    PortfolioType portfolioType = null;
    do {
      view.chooseFixedOrFlexible();
      userInput = scanner.nextInt();
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


    view.displayListOfPortfolios(user.getPortfolioNamesCreated(portfolioType));
    int portfolioIndexForVal = this.getSelectedPortFolioFromView(portfolioType);

    int option = 1;

    while(!isValid) {
      view.displayOptionsForChart();
      option = scanner.nextInt();

      if (option == 1 || option == 2 || option == 3) {
        isValid = true;
      }
      else {
        view.displayMsgToUser("Please select a valid option");
      }
    }

      this.chart = user.CalculateChart(option, portfolioIndexForVal, portfolioType);
      if(option == 1 || option == 2)
        view.displayChartWeek(chart);
      else
        view.displayChartMonth(chart);

      view.displayMsgToUser("<* means the value of the portfolio for a particular timestamp is less than the scale");
      view.displayMsgToUser("Scale: * = $" + user.getScale(portfolioIndexForVal, portfolioType));

  }

  public int getSelectedPortFolioFromView(PortfolioType portfolioType) {
    int index = -1;
    while ((index < 0) || (index > user.getPortfolioNamesCreated(portfolioType).size())) {
      view.getSelectedPortfolio();
      index = scanner.nextInt();
    }
    return index;
  }
}
