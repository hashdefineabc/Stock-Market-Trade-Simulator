package View;

import java.io.PrintStream;
import java.util.List;

/**
 * Class that implements the text user interface of the stock market application.
 */
public class ViewImpl implements ViewInterface{

  private PrintStream out;
  public ViewImpl(PrintStream o) {
    this.out = o;
  }

  @Override
  public void displayMenu() {
    this.out.print("***********************************\n");
    this.out.print("\t1. Create a new Portfolio\n");
    this.out.print("\t2. Retrieve Portfolio\n");
    this.out.print("\t3. Check value of a Portfolio\n");
    this.out.print("\t4. Exit the application.\n");
    this.out.print("Pick one of the options\n");

  }

  @Override
  public void takeTickerName() {
    this.out.print("Enter the ticker name:\n");
  }

  @Override
  public void takeNumOfUnits() {
    this.out.print("Enter the number of units purchased:\n");
  }

  @Override
  public void addMoreStocks() { 
    this.out.print("Do you want to add more stocks to this portfolio? (Press 1 for Yes, 0 for No):\n");
  }

  @Override
  public void displayListOfPortfolios(List<String> portfolios) { //changing this as model classes shouldn't interact with view.
    int count = 0;
    for(String p: portfolios) {
      count++;
      this.out.println(count + " " +p);
    }
  }

  @Override
  public void getSelectedPortfolio() {
    this.out.print("Pick a portfolio\n");
  }


  @Override
  public void displayStocks(List<String[]> listOfStocks){ //changing this as model classes shouldn't interact with view.
    this.out.println("Following stocks are present in the portfolio : ");
    this.out.println("Ticker\t" + "NumberOfUnits\t" + "DateBoughtAt\n");
    for (String[] stockDetails : listOfStocks) {
      this.out.println(stockDetails[0]+"\t"+stockDetails[1]+"\t\t\t\t"+stockDetails[2]);
    }
  }

  @Override
  public void getDateFromUser() {
    this.out.print("Enter the date for which you to check the value of the portfolio:(yyyy-mm-dd)\n");
  }


  @Override
  public void displayValue(double val) {
    this.out.println("Value of the portfolio is: " + String.format("%.2f",val));
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


}