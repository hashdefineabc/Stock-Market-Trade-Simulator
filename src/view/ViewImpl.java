package view;

import java.io.PrintStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import model.IstockModel;

/**
 * The ViewImpl class implements the View Interface of this application.
 * It defines all the methods necessary for the program to interact with the user.
 */
public class ViewImpl implements ViewInterface {

  private PrintStream out;

  /**
   * Constructor for the view class. It initializes all the objects that are being used in the
   * class.
   * @param o = printstream object that is used for printing in this class.
   */

  public ViewImpl(PrintStream o) {
    this.out = o;
  }

  @Override
  public void displayMenu() {
    this.out.print("\n***********************************\n");
    this.out.print("\t1. Create a new Portfolio\n");
    this.out.print("\t2. View Composition of a Portfolio\n");
    this.out.print("\t3. Check value of a Portfolio\n");
    this.out.print("\t4. Buy or Sell Shares in a Portfolio\n");
    this.out.print("\t5. View Cost Basis of a Portfolio\n");
    this.out.print("\t6. Display bar chart of a Portfolio\n");
    this.out.print("\t7. Close the Application\n");
    this.out.print("Pick one of the options\n");

  }

  @Override
  public void takeTickerName() {
    this.out.print("Enter the ticker name:\n");
  }

  @Override
  public void takeNumOfUnits() {
    this.out.print("Enter the number of units purchased/sold:\n");
  }

  @Override
  public void addMoreStocks() {
    this.out.print(
            "Do you want to add more stocks to this portfolio? (Press 1 for Yes, 0 for No):\n");
  }

  @Override
  public void displayListOfPortfolios(List<String> portfolios) {
    int count = 0;
    for (String p : portfolios) {
      count++;
      this.out.print(count + " " + p + "\n");
    }
  }

  @Override
  public void getSelectedPortfolio() {
    this.out.print("Pick a portfolio\n");
  }


  @Override
  public void displayStocks(List<IstockModel> listOfStocks) {
    this.out.print("Following stocks are present in the portfolio : \n");
    this.out.print("TickerName\t" + "NumberOfUnits\t" + "TransactionDate\t" + "Commission(USD)\t"
                    + "Price(USD)\t" + "BUY/SELL\n");

    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    for (IstockModel stock : listOfStocks) {
      this.out.print(stock.getTickerName() + "\t\t" + Double.toString(stock.getNumOfUnits())
              + "\t\t\t" + stock.getTransactionDate().format(dateFormat) + "\t\t"
              + Double.toString(stock.getCommission()) + "\t\t\t"
              + Double.toString(stock.getTransactionPrice()) + "\t\t\t"
              + stock.getBuyOrSell().toString() + "\n");
    }

  }

  @Override
  public void getDateFromUser() {
    this.out.print("Enter the date :(yyyy-mm-dd)\n");
  }


  @Override
  public void displayValue(double val, LocalDate valueDate) {
    this.out.print("Value of the portfolio as of date " + valueDate.toString()
            + " is: " + String.format("%.2f", val) + " USD\n");
  }

  @Override
  public void displayMsgToUser(String msg) {
    this.out.print(msg + "\n");
  }

  @Override
  public void getPortfolioNameFromUser() {
    this.out.print("\tPlease enter a name for this portfolio: "
            + "(No spaces or special characters allowed in the name)\n");
  }

  @Override
  public void displayCreatePortFolioOptions() {
    this.out.print("\t1. Enter the stock details manually\n" + "\t2. Upload an existing file\n");
  }

  @Override
  public void isFileUploaded() {
    this.out.print("\tHas the file been placed at the above location? 1.Yes 0.No\n");
  }

  @Override
  public void chooseFixedOrFlexible() {
    this.out.print("Please pick one \n");
    this.out.print("1. Fixed Portfolio\n");
    this.out.print("2. Flexible Portfolio\n");
  }

  @Override
  public void takeCommissionValue() {
    this.out.print("Enter the commission value (USD) for this transaction: \n");
  }

  @Override
  public void displayCostBasis(double costBasis, LocalDate costBasisDate) {
    this.out.print("CostBasis of the portfolio as of date " + costBasisDate.toString()
            + " is: " + String.format("%.2f", costBasis) + " USD\n");
  }

  @Override
  public void askAddOrSell() {
    this.out.print("Please pick an operation\n"
            + "1. Add Stock to the portfolio\n"
            + "2. Sell Stock from the portfolio\n");
  }

  @Override
  public void displayOptionsForChart() {
    this.out.print("Please pick an option\n"
            + "1. Display chart for previous week\n"
            + "2. Display chart for previous month\n"
            + "3. Display chart for previous year\n");
  }

  @Override
  public void displayChartWeek(Map<LocalDate, String> chart) {
    for (Map.Entry<LocalDate,String> entry : chart.entrySet()) {
      this.out.print(entry.getKey() + ":" + entry.getValue() + "\n");

    }
  }

  @Override
  public void displayChartMonth(Map<LocalDate, String> chart) {
    for (Map.Entry<LocalDate,String> entry : chart.entrySet()) {
      this.out.print(entry.getKey().getMonth().toString().substring(0, 3)
              + " " + entry.getKey().getYear() +
              " : " + entry.getValue() + "\n");
    }
  }

}