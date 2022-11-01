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
  String displayMenu();

  /**
   * take stock details for a particular portfolio from the user
   * It should take the ticker name and the number of units
   * @return the ticker name and the number of units
   */
  String[] takeStockInput();

  /**
   * asks user if they want to add more stocks to the portfolio
   * @return true of they want to add more stocks else return false
   */
  Boolean addMoreStocks();

  /**
   * displays the list of created portfolios
   */
  void displayListOfPortfolios(List<String> portfolios); //changing this as model classes shouldn't interact with view.

  /**
   * displays list of portfolios and asks user to select one
   * @return the selected portfolio
   */
  int getSelectedPortfolio();

  /**
   * displays stocks of a particular portfolio
   */

  void displayStocks(List<String[]> listOfStocks);

  String getDateFromUser();

  void displayValue(double val);

  void displayMsgToUser(String msg);

  String getPortfolioNameFromUser();
}
