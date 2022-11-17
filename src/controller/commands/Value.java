package controller.commands;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import controller.ICommandController;
import model.IUserInterface;
import model.PortfolioType;
import view.ViewInterface;

/**
 * The class Value implements the command controller interface.
 * It is one of the commands that is supported by our application.
 * Value operation is available for both fixed and flexible portfolios.
 * It allows the user view the total value of a portfolio at a specific date.
 * It provides the value of a portfolio on a specific date (to be exact, the end of that day).
 * The value for a portfolio before the date of its first purchase is 0
 */
public class Value implements ICommandController {
  private ViewInterface view;
  private IUserInterface user;
  private Scanner scanner;

  /**
   * Instantiates a new Value.
   *
   * @param view the view
   * @param user the user
   */
  public Value(ViewInterface view, IUserInterface user) {
    this.view = view;
    this.user = user;
    scanner = new Scanner(System.in);
  }
  @Override
  public void go() {

    int userInput;
    PortfolioType portfolioType = null;
    do {
      view.chooseFixedOrFlexible();
      userInput = scanner.nextInt();
      if (userInput == 1) { //fixed portfolio
        portfolioType = PortfolioType.fixed;
        if (user.getPortfolioNamesCreated(portfolioType).size() == 0) {
          view.displayMsgToUser("No fixed portfolios created till now, can't calculate value");
          return;
        }
      } else if (userInput == 2) { //flexible portfolio
        portfolioType = PortfolioType.flexible;
        if (user.getPortfolioNamesCreated(portfolioType).size() == 0) {
          view.displayMsgToUser("No flexible portfolios created till now, can't calculate value");
          return;
        }
      } else {
        view.displayMsgToUser("Invalid input!!!!");
      }
    }
    while (userInput > 2 || userInput <= 0);


    view.displayListOfPortfolios(user.getPortfolioNamesCreated(portfolioType));
    int portfolioIndexForVal = this.getSelectedPortFolioFromView(portfolioType);

    LocalDate date = this.validateDateForValue();

    double val = user.calculateValueOfPortfolio(portfolioIndexForVal, date, portfolioType);

    if (val == -1) {
      view.displayMsgToUser(
              "Value cannot be calculated for a date prior to portfolio creation");
    } else {
      view.displayValue(val, date);
    }
  }

  private int getSelectedPortFolioFromView(PortfolioType portfolioType) {
    int index = -1;
    while ((index < 0) || (index > user.getPortfolioNamesCreated(portfolioType).size())) {
      view.getSelectedPortfolio();
      index = scanner.nextInt();
    }
    return index;
  }

  private LocalDate validateDateForValue() {
    LocalDate date = this.getDateFromView();
    LocalDate today = LocalDate.now();
    //validation for date
    while (date.isAfter(today)) {
      view.displayMsgToUser("Can't get value for date greater than today");
      date = this.getDateFromView();
    }
    LocalTime fourPM = LocalTime.of(16, 00, 00, 342123342);
    if (date.equals(today) && LocalTime.now().compareTo(fourPM) < 0) {
      view.displayMsgToUser("Value will be calculated based on yesterday's closing price.");
      date = date.minusDays(1); //modify date to yesterday
    }
    return date;
  }

  private LocalDate getDateFromView() {
    LocalDate valueDate = LocalDate.now();
    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    boolean isDateOkay = false;
    while (!isDateOkay) {
      try {
        view.displayMsgToUser("Enter Date for which you want to check the value : (yyyy-mm-dd)");
        valueDate = LocalDate.parse(scanner.next(), dateFormat);
        isDateOkay = true;
      } catch (Exception e) {
        view.displayMsgToUser("Invalid date. Please try again!");
        isDateOkay = false;
      }
    }
    return valueDate;
  }
}
