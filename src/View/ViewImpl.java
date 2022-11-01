package View;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class ViewImpl implements ViewInterface{

  private InputStream  userInput;
  private PrintStream out;
  Scanner scanner;
  public ViewImpl(InputStream in, PrintStream o) {
    this.userInput = in;
    this.out = o;
    scanner = new Scanner(this.userInput);
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
  public String displayMenu() {
    int userInput = 5;
    this.out.print("\t1. Create a new Portfolio\n");
    this.out.print("\t2. Retrieve Portfolio\n");
    this.out.print("\t3. Check value of a Portfolio\n");
    this.out.print("\t4. Exit the application.\n");
    this.out.print("\tPick one of the options\n");
    return scanner.next();
  }

  /**
   * take stock details for a particular portfolio from the user
   * It should take the ticker name and the number of units
   *
   * @return the ticker name and the number of units
   */
  @Override
  public String[] takeStockInput() { //TODO: validate the ticker.
    String[] inputStock = new String[2];
    this.out.print("Enter the ticker name:\n");
    inputStock[0] = scanner.next();
    this.out.print("Enter the number of units purchased:\n");
    inputStock[1] = Integer.toString(scanner.nextInt());
    return inputStock;
  }

  /**
   * asks user if they want to add more stocks to the portfolio
   *
   * @return true of they want to add more stocks else return false
   */
  @Override
  public Boolean addMoreStocks() { //TODO: add validation for boolean input
    this.out.println("Do you want to add more stocks to this portfolio? (Press 1 for Yes, 0 for No):");
    int userInput = scanner.nextInt();
    return userInput == 1;
  }

  /**
   * displays the list of created portfolios
   */
  @Override
  public void displayListOfPortfolios(List<String> portfolios) { //changing this as model classes shouldn't interact with view.
    int count = 0;
    for(String p: portfolios) {
      count++;
      this.out.println(count + p);
    }
  }

  /**
   * displays list of portfolios and asks user to select one
   *
   * @return the selected portfolio
   */
  @Override
  public int getPortfolioName() { //TODO: validate the input from user
    this.out.println("Pick a portfolio");
    return scanner.nextInt();
  }

  /**
   * displays stocks of a particular portfolio
   */
  @Override
  public void displayStocks(List<String[]> listOfStocks){ //changing this as model classes shouldn't interact with view.
    this.out.println("Following stocks are present in the portfolio : ");
    this.out.println("Ticker\t" + "Number of Units\t" + "Date Bought At\n");
    for (String[] stockDetails : listOfStocks) {
      this.out.println(stockDetails[0]+"\t"+stockDetails[1]+"\t"+stockDetails[2]);
    }
  }

  /**
   * To get the date at which the user wants to calculate the value of the portfolio.
   * @return date at which value has to be calculated.
   */
  @Override
  public LocalDate getDateFromUser() {
    this.out.println("Enter the date for which you to check the value of the portfolio:(yyyy-mm-dd)");
    String d = scanner.next();
    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    return LocalDate.parse(d, dateFormat);

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
  public String getPortfolioNameFromUser() {
    this.out.println("Please enter a name for this portfolio");
    return scanner.next();
  }
}
