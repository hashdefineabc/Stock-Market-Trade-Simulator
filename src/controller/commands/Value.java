package controller.commands;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import controller.ICommandController;
import model.IUserInterface;
import view.ViewInterface;

public class Value implements ICommandController {
  private ViewInterface view;
  private IUserInterface user;
  private Scanner scanner;

  public Value(ViewInterface view, IUserInterface user) {
    this.view = view;
    this.user = user;
    scanner = new Scanner(System.in);
  }
  @Override
  public void go() {


    if (user.getPortfolioNamesCreated().size() == 0) {
      view.displayMsgToUser("No portfolios created till now, can't calculate value");
      return;
    }
    view.displayListOfPortfolios(user.getPortfolioNamesCreated());
    int portfolioIndexForVal = this.getSelectedPortFolioFromView();

    LocalDate date = this.validateDateForValue();
    double val = user.calculateValueOfPortfolio(portfolioIndexForVal, date, "Fixed");

    if (val == 0) {
      view.displayMsgToUser("Market was closed on " + date);
    } else if (val == -1) {
      view.displayMsgToUser(
              "Value cannot be calculated for a date prior to portfolio creation");
    } else {
      view.displayValue(val);
    }
  }

  public int getSelectedPortFolioFromView() {
    int index = -1;
    while ((index < 0) || (index > user.getPortfolioNamesCreated().size())) {
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

  public LocalDate getDateFromView() {
    LocalDate valueDate = LocalDate.now();
    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    boolean isDateOkay = false;
    while (!isDateOkay) {
      try {
        view.getDateFromUser();
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
