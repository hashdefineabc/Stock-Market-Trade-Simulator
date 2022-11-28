package controller.commands;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import controller.IController;
import model.IUserInterface;
import model.IstockModel;
import model.PortfolioType;
import view.ViewInterface;

/**
 * The class Composition implements the command controller interface.
 * It is one of the commands that is supported by our application.
 * Composition operation is available for both fixed and flexible portfolios.
 * It allows the user to view the composition of the created portfolios.
 */
public class Composition implements ICommandController {

  private ViewInterface view;
  private IUserInterface user;
  private Scanner inputScanner;

  /**
   * Instantiates a new Composition.
   * It takes view and model (user) and instantiates it.
   * It interacts with view and main model which is user.
   *
   * @param view         the view
   * @param user         the user
   * @param inputScanner the input scanner
   */
  public Composition(ViewInterface view, IUserInterface user, Scanner inputScanner) {
    this.view = view;
    this.user = user;
    this.inputScanner = inputScanner;
  }

  @Override
  public void goController() {
    PortfolioType portfolioType = null;
    int fixOrFlex = this.showFixedOrFlexPortfolioOptionsOnView();
    if (fixOrFlex == 1) {
      //create a fixed portfolio
      portfolioType = PortfolioType.fixed;
    } else if (fixOrFlex == 2) {
      //create a flexible portfolio
      portfolioType = PortfolioType.flexible;
    }

    if (user.getPortfolioNamesCreated(portfolioType).size() == 0) {
      view.displayMsgToUser("No " + portfolioType + " portfolios created till now!!!");
      return;
    }

    this.retrievePortFolios(portfolioType);
  }

  private void retrievePortFolios(PortfolioType portfolioType) {
    view.displayMsgToUser("Following are the " + portfolioType + " portfolios created till now:");
    view.displayListOfPortfolios(user.getPortfolioNamesCreated(portfolioType));
    int portfolioIndex = this.getSelectedPortFolioFromView(portfolioType);
    LocalDate dateForComposition;
    if (portfolioType == PortfolioType.fixed) {
      dateForComposition = LocalDate.now();
    } else {
      dateForComposition = this.getDateFromView();
    }
    List<IstockModel> stocksToDisplay = user.displayStocksOfPortFolio(portfolioIndex,
            portfolioType, dateForComposition);
    if (stocksToDisplay.size() == 0) {
      view.displayMsgToUser("No stocks were present in the portfolio at this date");
    } else {
      view.displayStocks(stocksToDisplay);
    }
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


  private int showFixedOrFlexPortfolioOptionsOnView() {
    int userOption = 0;
    Boolean isOkay = false;
    List<Integer> validMenuOptions = Arrays.asList(1, 2);
    do {
      try {
        this.view.chooseFixedOrFlexible();
        userOption = Integer.parseInt(this.inputScanner.next());
        if (!validMenuOptions.contains(userOption)) {
          throw new IllegalArgumentException("Invalid Option!!");
        }
        isOkay = true;
      } catch (IllegalArgumentException ie) {
        if (!ie.getMessage().equals("Invalid Option!!")) {
          this.view.displayMsgToUser("Please enter an integer value");
        } else {
          this.view.displayMsgToUser(ie.getMessage());
        }

        isOkay = false;
      }
    }
    while (!isOkay);
    return userOption;

  }

  /**
   * Method to get date from user.
   * @return the date enetered by the user.
   */
  public LocalDate getDateFromView() {
    LocalDate valueDate = LocalDate.now();
    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    boolean isDateOkay = false;
    while (!isDateOkay) {
      try {
        view.displayMsgToUser("Please enter the date for which you want to view the composition"
                + "(yyyy-MM-dd)");
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
