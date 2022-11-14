package view;

import java.io.PrintStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import model.IstockModel;
import model.Operation;

/**
 * Class that implements the text user interface of the stock market application.
 */
public class ViewImpl implements ViewInterface {

  private PrintStream out;

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
    this.out.print("\t7. Close Application\n");
    this.out.print("Pick one of the options\n");

  }

  @Override
  public void takeTickerName() {
    this.out.println("Enter the ticker name:");
  }

  @Override
  public void takeNumOfUnits() {
    this.out.print("Enter the number of units purchased:\n");
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
      this.out.println(count + " " + p);
    }
  }

  @Override
  public void getSelectedPortfolio() {
    this.out.print("Pick a portfolio\n");
  }


  @Override
  public void displayStocks(List<IstockModel> listOfStocks) {
    this.out.println("Following stocks are present in the portfolio : ");
    this.out.println("TickerName\t" + "NumberOfUnits\t" + "TransactionDate\t" + "Commission\t"
                    + "Price\t" + "BUY/SELL");

    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    for (IstockModel stock : listOfStocks) {
      this.out.println(stock.getTickerName() + "\t\t" + Double.toString(stock.getNumOfUnits())
                      + "\t\t\t" +stock.getBuyDate().format(dateFormat) + "\t\t"
              + Double.toString(stock.getCommission()) + "\t\t\t"
              + Double.toString(stock.getTransactionPrice()) + "\t\t\t"
              + stock.getBuyOrSell().toString());
    }

  }

  @Override
  public void getDateFromUser() {
    this.out.println("Enter the date :(yyyy-mm-dd)");
  }


  @Override
  public void displayValue(double val) {
    this.out.println("Value of the portfolio is: " + String.format("%.2f", val));
  }

  @Override
  public void displayMsgToUser(String msg) {
    this.out.println(msg);
  }

  @Override
  public void getPortfolioNameFromUser() {
    this.out.print("\tPlease enter a name for this portfolio\n");
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
    this.out.println("Please pick one ");
    this.out.println("1. Fixed Portfolio");
    this.out.println("2. Flexible Portfolio");
  }

  @Override
  public void takeCommissionValue() {
    this.out.println("Enter the commission value for this transaction");
  }

  @Override
  public void takeDateOfTransaction() {
    this.out.println("Please enter the date of this transaction (yyyy-mm-dd):");
  }

  @Override
  public void displayCostBasis(double costBasis) {
    this.out.println("Value of the portfolio is: " + String.format("%.2f", costBasis));
  }

  @Override
  public void askAddOrSell() {
    this.out.println("Please pick an operation\n " +
            "1. Add Stock to the portfolio\n" +
            "2. Sell Stock from the portfolio");
  }

}