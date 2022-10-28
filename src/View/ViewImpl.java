package View;

public class ViewImpl implements ViewInterface{
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

    return 0;
  }

  /**
   * take stock details for a particular portfolio from the user
   * It should take the ticker name and the number of units
   *
   * @return the ticker name and the number of units
   */
  @Override
  public String[] takeStockInput() {
    return new String[0];
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
  public void displayListOfPortfolios() {

  }

  /**
   * displays list of portfolios and asks user to select one
   *
   * @return the selected portfolio
   */
  @Override
  public String getPortfolioName() {
    return null;
  }

  /**
   * displays stocks of a particular portfolio
   */
  @Override
  public void displayStocks() {

  }
}
