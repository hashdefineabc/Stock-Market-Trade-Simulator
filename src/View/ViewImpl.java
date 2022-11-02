package View;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

public class ViewImpl implements ViewInterface{

  private PrintStream out;
  public ViewImpl(PrintStream o) {
    this.out = o;
  }

  //Scanner scanner = new Scanner(this.userInput);
  /**
   * display options
   * 1. create new portfolio
   * 2. retrieve portfolio
   * 3. check value of a particular portfolio
   *
   * @return the option selected by the user
   */
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


  /**
   * asks user if they want to add more stocks to the portfolio
   *
   * @return true of they want to add more stocks else return false
   */
  @Override
  public void addMoreStocks() { //TODO: add validation for boolean input
    this.out.println("Do you want to add more stocks to this portfolio? (Press 1 for Yes, 0 for No):");
  }

  /**
   * displays the list of created portfolios
   */
  @Override
  public void displayListOfPortfolios(List<String> portfolios) { //changing this as model classes shouldn't interact with view.
    int count = 0;
    for(String p: portfolios) {
      count++;
      this.out.println(count + " " +p);
    }
  }

  /**
   * displays list of portfolios and asks user to select one
   *
   * @return the selected portfolio
   */
  @Override
  public void getSelectedPortfolio() {
    this.out.println("Pick a portfolio");
  }

  /**
   * displays stocks of a particular portfolio
   */
  @Override
  public void displayStocks(List<String[]> listOfStocks){ //changing this as model classes shouldn't interact with view.
    this.out.println("Following stocks are present in the portfolio : ");
    this.out.println("Ticker\t" + "NumberOfUnits\t" + "DateBoughtAt\n");
    for (String[] stockDetails : listOfStocks) {
      this.out.println(stockDetails[0]+"\t"+stockDetails[1]+"\t\t\t\t"+stockDetails[2]);
    }
  }

  /**
   * To get the date at which the user wants to calculate the value of the portfolio.
   * @return date at which value has to be calculated.
   */
  @Override
  public void getDateFromUser() {
    this.out.println("Enter the date for which you to check the value of the portfolio:(yyyy-mm-dd)");
  }

  /**
   * Displaying the value of the portfolio.
   * @param val = value as calculated by model.
   */
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
    this.out.println("Please enter a name for this portfolio");
  }

  @Override
  public void displayCreatePortFolioOptions() {
    this.out.print("\t1. Enter the stock details manually\n" + "\t2. Upload an existing file\n");
  }

  public void isFileUploaded() {
    this.out.println("Has the file been placed at the above location? 1.Yes 0.No");
  }


}