package controller.commands;

import java.io.File;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import model.IUserInterface;
import model.InvestmentType;
import model.PortfolioType;
import view.ViewInterface;

public class DCA implements ICommandController {

  private ViewInterface view;
  private IUserInterface user;
  private Scanner inputScanner;

  public DCA(ViewInterface view, IUserInterface user, Scanner inputScanner) {
    this.view = view;
    this.user = user;
    this.inputScanner = inputScanner;
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
    LocalDate strategyStart = this.getDateFromView("investment start");
    LocalDate strategyEnd = this.getDateFromView("investment end");
    Integer daysToInvest = this.getDaysFromView();

    HashMap<String, Double> weights = this.getWeightsFromView();
    LocalDate lastTxnDate = user.calculateTxns(strategyStart, strategyEnd, daysToInvest, weights,
            amount, commission, portfolioIndex, InvestmentType.DCA);
    user.acceptStrategyFromUser(portfolioIndex, amount, commission, strategyStart, strategyEnd, weights,
            InvestmentType.DCA, daysToInvest, lastTxnDate);
    view.displayMsgToUser("Instructions saved for this Portfolio! Money will be invested as per "
            + "them");

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

  private LocalDate getDateFromView(String dateType) {

    LocalDate valueDate = LocalDate.now();
    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    boolean isDateOkay = false;
    while (!isDateOkay) {
      try {
        view.displayMsgToUser("Enter the" + dateType + " date (yyyy-MM-dd):");
        valueDate = LocalDate.parse(this.inputScanner.next(), dateFormat);
        isDateOkay = true;
      } catch (Exception e) {
        view.displayMsgToUser("Invalid date. Please try again!");
        isDateOkay = false;
      }
    }
    return valueDate;
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
    }
    while (!isInputValid);

    return new String[]{tickerNameFromUser, Double.toString(percentage)};
  }

  private List<String[]> getDataToWrite(Double amount, HashMap<String, Double> weights,
                                        LocalDate strategyStart, LocalDate strategyEnd,
                                        Integer daysToInvest, Double commission,
                                        List<String> txnDates) {
    List<String[]> answer = new ArrayList<>();
    String[] amt = new String[2];
    amt[0] = "AMOUNT";
    amt[1] = amount.toString();
    answer.add(amt);
    String[] comm = new String[2];
    comm[0] = "COMMISSION";
    comm[1] = commission.toString();
    answer.add(comm);
    String[] st_date = new String[2];
    st_date[0] = "START DATE";
    st_date[1] = strategyStart.toString();
    answer.add(st_date);
    String[] end_date = new String[2];
    end_date[0] = "END DATE";
    end_date[1] = strategyEnd.toString();
    answer.add(end_date);
    String[] days = new String[2];
    days[0] = "DAYS_TO_INVEST";
    days[1] = daysToInvest.toString();
    answer.add(days);

    for (Map.Entry<String, Double> element : weights.entrySet()) {
      String[] weight = new String[2];
      weight[0] = element.getKey();
      weight[1] = element.getValue().toString();
      answer.add(weight);
    }
    String[] date = null;
    for (String txnDate : txnDates) {
      date = new String[2];
      date[0] = "TXN_DATE";
      date[1] = txnDate;
      answer.add(date);
    }

    String[] lastTxn = new String[2];
    lastTxn[0] = "LAST_TXN";
    lastTxn[1] = txnDates.get(txnDates.size() - 1);

    return answer;
  }

  private Integer getDaysFromView() {
    int days = 0;
    boolean okay = false;
    do {
      try {
        view.displayMsgToUser("Enter the period after which investment should be done (in days):");
        days = this.inputScanner.nextInt();
        if (days < 0) {
          throw new IllegalArgumentException("Period to invest cannot be -ve!");
        }
        okay = true;
      } catch (IllegalArgumentException ie) {
        this.view.displayMsgToUser(ie.getMessage());
        okay = false;
      }
    }
    while (!okay);
    return days;
  }

  private void saveInstrToFile(String portfolioName, List<String[]> dataToWrite) {
    File csvOutputFile = null;
    csvOutputFile = new File("C:\\Users\\anush\\OneDrive\\Desktop\\CS5010_PDP_Projects\\" +
            "Assignment6\\PortFolioComposition\\DCAInstructions" + File.separator
            + portfolioName); //TODO: replace this when file writing is moved to controller
    try {
      PrintWriter pw = new PrintWriter(csvOutputFile);
      dataToWrite.stream().map(this::convertToCSV).forEach(pw::println);
      pw.close();
    } catch (Exception e) {
      System.out.print("Error creating a csv\n");
    }
  }

  private String escapeSpecialCharacters(String data) {
    String escapedData = data.replaceAll("\\R", " ");
    if (data.contains(",") || data.contains("\"") || data.contains("'")) {
      data = data.replace("\"", "\"\"");
      escapedData = "\"" + data + "\"";
    }
    return escapedData;
  }

  private String convertToCSV(String[] data) {
    return Stream.of(data)
            .map(this::escapeSpecialCharacters)
            .collect(Collectors.joining(","));
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
