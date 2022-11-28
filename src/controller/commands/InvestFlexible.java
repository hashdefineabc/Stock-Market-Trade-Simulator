package controller.commands;

import java.io.File;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import model.IUserInterface;
import model.IstockModel;
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
    LocalDate dateToBuy = this.getDateFromView();
    List<IstockModel> stocksToDisplay = user.displayStocksOfPortFolio(portfolioIndex,
            portfolioType, LocalDate.now());
    HashMap<String,Double> weights = this.getWeightsFromView(stocksToDisplay);
    List<String[]> dataToWrite = this.getDataToWrite(amount,weights,dateToBuy);
    this.saveInstrToFile(user.getPortfolioName(portfolioIndex,portfolioType), dataToWrite);
    view.displayMsgToUser("Instructions saved for this Portfolio! Money will be invested as per "
            + "them");
    user.updateFlexiblePortFolios();
  }

  private void saveInstrToFile(String portfolioName, List<String[]> dataToWrite)
  {
    File csvOutputFile = null;
    csvOutputFile = new File("C:\\Users\\anush\\OneDrive\\Desktop\\CS5010_PDP_Projects\\" +
            "Assignment6\\PortFolioComposition\\InvestmentInstructions" + File.separator
            + portfolioName); //TODO: replace this when file writing is moved to controller
    try {
      PrintWriter pw = new PrintWriter(csvOutputFile);
      dataToWrite.stream().map(this::convertToCSV).forEach(pw::println);
      pw.close();
    } catch (Exception e) {
      System.out.print("Error creating a csv\n");
    }
  }

  private List<String[]> getDataToWrite(Double amount, HashMap<String,Double> weights,
                                        LocalDate dateToBuy) {
    List<String[]> answer = new ArrayList<>();
    String[] amt = new String[2];
    amt[0] = "AMOUNT";
    amt[1] = amount.toString();
    answer.add(amt);
    String[] date = new String[2];
    date[0] = "DATE";
    date[1] = dateToBuy.toString();
    answer.add(date);

    for (Map.Entry<String,Double> element : weights.entrySet()) {
      String[] weight = new String[2];
      weight[0] = element.getKey().toString();
      weight[1] = element.getValue().toString();
      answer.add(weight);
    }
    return answer;
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
  private HashMap<String,Double> getWeightsFromView( List<IstockModel> stocksToDisplay ) {
    HashMap<String,Double> weights = new HashMap<>();
    for (IstockModel stock : stocksToDisplay) {
      boolean okay = false;
      double weight = 0.0;
      do {
         try {
           view.displayMsgToUser("Enter the percentage for the stock:" + stock.getTickerName());
           weight = this.inputScanner.nextDouble();
           if (weight < 0.0) {
             throw new IllegalArgumentException("Percentage cannot be -ve");
           }
           okay = true;
         } catch (IllegalArgumentException ie) {
           this.view.displayMsgToUser(ie.getMessage());
           okay = false;
         }
      } while (!okay);
      weights.put(stock.getTickerName(), weight);
    }
    return weights;
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


}
