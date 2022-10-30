package View;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import Model.portfolio;
import Model.stock;

public class ViewImpl implements ViewInterface{

  Scanner scanner = new Scanner(System.in);
  /**
   * display options
   * 1. create new portfolio
   * 2. retrieve portfolio
   * 3. check value of a particular portfolio
   *
   * @return the option selected by the user
   */
  @Override
  public int displayMenu() { //TODO: validate the user input in portfolio.
    System.out.println("1. Create a new Portfolio");
    System.out.println("2. Retrieve Portfolio");
    System.out.println("3. Check value of a Portfolio");
    System.out.println("4. Exit the application.");
    System.out.println("Pick one of the options");
    int option = scanner.nextInt();
    return option;
  }

  /**
   * take stock details for a particular portfolio from the user
   * It should take the ticker name and the number of units
   *
   * @return the ticker name and the number of units
   */
  @Override
  public String[] takeStockInput() { //TODO: validate the ticker and the num of units purchased.
    String[] inputStock = new String[2];
    System.out.println("Enter the ticker name:");
    inputStock[0] = scanner.next();
    System.out.println("Enter the number of units purchased");
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
    System.out.println("Do you want to add more stocks to this portfolio? (Press 1 for Yes, 0 for No):");
    int userInput = scanner.nextInt();
    if(userInput == 1)
      return true;
    else
      return false;
  }

  /**
   * displays the list of created portfolios
   */
  @Override
  public void displayListOfPortfolios(List<String> portfolios) { //changing this as model classes shouldn't interact with view.
    int count = 0;
    for(String p: portfolios) {
      count++;
      System.out.println(Integer.toString(count) + p);
    }
  }

  /**
   * displays list of portfolios and asks user to select one
   *
   * @return the selected portfolio
   */
  @Override
  public int getPortfolioName() { //TODO: validate the input from user
    System.out.println("Pick a portfolio");
    return scanner.nextInt();
  }

  /**
   * displays stocks of a particular portfolio
   */
  @Override
  public void displayStocks(List<String[]> listOfStocks){ //changing this as model classes shouldn't interact with view.
    System.out.println("Following stocks are present in the portfolio : ");
    System.out.println("Ticker\t" + "Number of Units\t" + "Date Bought At\n");
    for (String[] stockDetails : listOfStocks) {
      System.out.println(stockDetails[0]+"\t"+stockDetails[1]+"\t"+stockDetails[2]);
    }
  }

  /**
   * To get the date at which the user wants to calculate the value of the portfolio.
   * @return date at which value has to be calculated.
   */
  @Override
  public LocalDate getDateFromUser() throws ParseException {
    System.out.println("Enter the date for which you to check the value of the portfolio:(yyyy-mm-dd)");
    String d = scanner.next();
    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-mm-dd");
    LocalDate date = LocalDate.parse(d, dateFormat);
    return date;

  }

  /**
   * Displaying the value of the portfolio.
   * @param val = value as calculated by model.
   */
  @Override
  public void displayValue(double val) {
    System.out.println("Value of the portfolio is: " + String.format("%.2f",val));
  }

  @Override
  public void displayMsgToUser(String msg) {
    System.out.println(msg);
  }

  @Override
  public String getPortfolioNameFromUser() {
    System.out.println("Please enter a name for this portfolio");
    String portfolioName = scanner.next();
    return portfolioName;
  }
}
