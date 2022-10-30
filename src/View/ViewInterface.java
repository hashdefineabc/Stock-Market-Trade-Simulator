package View;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import Model.portfolio;

public interface ViewInterface {

  /**
   * display options
   * 1. create new portfolio
   * 2. retrieve portfolio
   * 3. check value of a particular portfolio
   * @return the option selected by the user
   */
  public int displayMenu();

  /**
   * take stock details for a particular portfolio from the user
   * It should take the ticker name and the number of units
   * @return the ticker name and the number of units
   */
  public String[] takeStockInput();

  /**
   * asks user if they want to add more stocks to the portfolio
   * @return true of they want to add more stocks else return false
   */
  public Boolean addMoreStocks();

  /**
   * displays the list of created portfolios
   */
  public void displayListOfPortfolios(List<String> portfolios); //changing this as model classes shouldn't interact with view.

  /**
   * displays list of portfolios and asks user to select one
   * @return the selected portfolio
   */
  public int getPortfolioName();

  /**
   * displays stocks of a particular portfolio
   */

  public void displayStocks(List<String[]> listOfStocks);

  public LocalDate getDateFromUser() throws ParseException;

  void displayValue(double val);

  void displayMsgToUser(String msg);

  String getPortfolioNameFromUser();
}
