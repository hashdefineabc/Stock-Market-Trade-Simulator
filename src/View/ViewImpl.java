package View;

import java.util.Scanner;

public class ViewImpl implements ViewInterface{

  Scanner scan = new Scanner(System.in);
  @Override
  public int displayMenu() {
    return 0;
  }

  @Override
  public String[] takeStockInput() {
    System.out.println("Please enter");

    return new String[0];
  }

  @Override
  public Boolean addMoreStocks() {

    return null;
  }

  @Override
  public void displayListOfPortfolios() {

  }

  @Override
  public String getPortfolioName() {
    return null;
  }

  @Override
  public void displayStocks() {

  }
}
