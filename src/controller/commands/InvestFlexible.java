package controller.commands;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import model.IUserInterface;
import model.InvestmentType;
import model.PortfolioType;
import view.ViewInterface;

public class InvestFlexible implements ICommandController {

  private ViewInterface view;
  private IUserInterface user;
  private Scanner inputScanner;

  /**
   * Instantiates a new Cost basis.
   * It takes view and model (user) and instantiates it.
   * It interacts with view and main model which is user.
   *
   * @param view the view
   * @param user the user
   */
  public InvestFlexible(ViewInterface view, IUserInterface user, Scanner scanner) {
    this.view = view;
    this.user = user;
    this.inputScanner = scanner;
  }

  @Override
  public void goController() {
    PortfolioType portfolioType = PortfolioType.flexible;

    if (user.getPortfolioNamesCreated(portfolioType).size() == 0) {
      view.displayMsgToUser("No " + portfolioType + " portfolios created till now!!!");
      return;
    }
    view.displayMsgToUser("Following are the " + portfolioType + " portfolios created till now:");
    view.displayListOfPortfolios(user.getPortfolioNamesCreated(portfolioType));
    int portfolioIndex = this.getSelectedPortFolioFromView(portfolioType);
    double amount = this.getInvestAmountFromView();
    this.view.takeCommissionValue();
    Double commission = inputScanner.nextDouble();
    while (commission <= 0.0) {
      view.displayMsgToUser("Commission cannot be -ve, Please enter a positive value");
      commission = inputScanner.nextDouble();
    }
    view.displayMsgToUser("Total amount the user will spend would be:" + (amount + commission));
    LocalDate dateToBuy = this.getDateFromView();
    HashMap<String, Double> weights = this.getWeightsFromView();
    LocalDate lastTxnDate = user.calculateTxns(dateToBuy, dateToBuy, 0,
            weights, amount, commission, portfolioIndex, InvestmentType.InvestByWeights);
    user.acceptStrategyFromUser(portfolioIndex, amount, commission, dateToBuy, dateToBuy, weights,
            InvestmentType.InvestByWeights, 0, lastTxnDate);
    view.displayMsgToUser("Instructions saved for this Portfolio! Money will be invested as per "
            + "them");
  }


  private HashMap<String, Double> getWeightsFromView() {
    HashMap<String, Double> weights = new HashMap<>();
    do {
      String[] w = this.getWeightFromView();
      weights.put(w[0], Double.parseDouble(w[1]));
    }
    while (this.addMoreWeightsFromView());
    return weights;
  }

  private String[] getWeightFromView() {
    Boolean isInputValid = false;
    String tickerNameFromUser = "";
    Double percentage = 0.0;
    do {
      try {
        this.view.takeTickerName();
        tickerNameFromUser = inputScanner.next();
        if (!user.isTickerValid(tickerNameFromUser)) {
          throw new IllegalArgumentException("Invalid ticker name!");
        }
        this.view.displayMsgToUser("Enter the weight for this stock (%):");
        percentage = this.inputScanner.nextDouble();
        if (percentage < 0.0) {
          throw new IllegalArgumentException("Percentage cannot be -ve");
        }
        isInputValid = true;

      } catch (Exception e) {
        this.view.displayMsgToUser(e.getMessage());
        isInputValid = false;
      }
    } while (!isInputValid);

    return new String[]{tickerNameFromUser, Double.toString(percentage)};
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

  private double getInvestAmountFromView() {
    double amount = -1;
    boolean okay = false;
    do {
      try {
        view.askUserAmountToInvest();
        amount = this.inputScanner.nextDouble();
        if (amount < 0.0) {
          throw new IllegalArgumentException("Amount to invest cannot be -ve!");
        }
        okay = true;
      } catch (IllegalArgumentException ie) {
        this.view.displayMsgToUser(ie.getMessage());
        okay = false;
      }
    }
    while (!okay);
    return amount;
  }

  private LocalDate getDateFromView() {
    LocalDate valueDate = LocalDate.now();
    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    boolean isDateOkay = false;
    while (!isDateOkay) {
      try {
        view.displayMsgToUser("Enter the date to invest this amount (yyyy-MM-dd):");
        valueDate = LocalDate.parse(this.inputScanner.next(), dateFormat);
        isDateOkay = true;
      } catch (Exception e) {
        view.displayMsgToUser("Invalid date. Please try again!");
        isDateOkay = false;
      }
    }
    return valueDate;
  }

  private Boolean addMoreWeightsFromView() {
    int userInput = 0;
    List<Integer> validOptions = Arrays.asList(0, 1);
    Boolean addMore = false;
    do {
      try {
        this.view.displayMsgToUser("Do you want to add more weights? 1.Yes 0.No");
        userInput = inputScanner.nextInt();
        if (userInput == 1) {
          return true;
        } else if (userInput == 0) {
          return false;
        }
        if (!validOptions.contains(userInput)) {
          throw new IllegalArgumentException("Please select a valid option!\n");
        }
      } catch (IllegalArgumentException ie) {
        this.view.displayMsgToUser(ie.getMessage());
        addMore = true;
      }
    }
    while (addMore);
    return userInput == 1;
  }


}
