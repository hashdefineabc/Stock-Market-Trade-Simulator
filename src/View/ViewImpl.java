package View;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

import Model.portfolio;

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
  public int displayMenu() {
    System.out.println("1. Create a new Portfolio");
    System.out.println("2. Retrieve Portfolio");
    System.out.println("3. Check value of a Portfolio");
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
  public String[] takeStockInput() {
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
  public Boolean addMoreStocks() {
    return null;
  }

  /**
   * displays the list of created portfolios
   */
  @Override
  public void displayListOfPortfolios(List<portfolio> portfolios) {

  }

  /**
   * displays list of portfolios and asks user to select one
   *
   * @return the selected portfolio
   */
  @Override
  public int getPortfolioName() {
    System.out.println("Pick a portfolio");
    return scanner.nextInt();
  }

  /**
   * displays stocks of a particular portfolio
   */
  @Override
  public void displayStocks(portfolio toDisplay) {

  }

  @Override
  public Date getDate() {
    return null;
  }

  @Override
  public void displayValue(double val) {

  }
}
