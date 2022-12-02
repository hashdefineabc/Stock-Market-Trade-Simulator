package view;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import model.IstockModel;


/**
 * Interface for the view class of this application.
 * It defines all the methods necessary to implement the text-user interface for this application.
 * The user gets all the necessary instructions about entering the data via these methods.
 */
public interface ViewInterface {

  /**
   * Method to display the below options to the user.
   * 1. Create a new portfolio
   * 2. Display composition of a portfolio
   * 3. Check value of a portfolio on a particular date
   * 4. Option to buy or sell stocks for flexible portfolios
   * 5. View the cost basis of a portfolio
   * 6. View the performance of a portfolio
   * 7. Exit the application.
   */
  void displayMenu();

  /**
   * Method to tell the user to input a ticker name.
   * Ticker name is  the name of the stock as listed on NASDAQ.
   */
  void takeTickerName();

  /**
   * Method to tell the user to input the total num of units bought/sold for that particular share.
   */
  void takeNumOfUnits();

  /**
   * Method that asks the user if they want to add more stocks to the portfolio.
   */
  void addMoreStocks();

  /**
   * Method that displays the list of created portfolios.
   * @param portfolios = list of portfolios as given by the controller.
   */
  void displayListOfPortfolios(List<String> portfolios);

  /**
   * Method that displays list of portfolios and asks user to select one.
   * This portfolio will then be used for further operations.
   */
  void getSelectedPortfolio();

  /**
   * Method that displays stocks of a particular portfolio on the text user interface.
   @param listOfStocks = list of stocks.
   */
  void displayStocks(List<IstockModel> listOfStocks);

  /**
   * Method that tells the user to input the date for which he/she wishes to see the value of the
   * portfolio.
   */

  void getDateFromUser();

  /**
   * Method to display the calculated value of the portfolio.
   * @param val = value as calculated by model.
   * @param valueDate = date at which the value is calculated.
   */
  void displayValue(double val, LocalDate valueDate);

  /**
   * Method to display any arbitrary msg to the user.
   * @param msg = the msg passed by the controller.
   */
  void displayMsgToUser(String msg);

  /**
   * Method to get the portfolio name from the user.
   * This name is then used to create a file in the system for that portfolio.
   */
  void getPortfolioNameFromUser();

  /**
   * Method to display the following two options of creating a portfolio:
   * 1. Enter the stock details manually
   * 2. Upload an existing file.
   */
  void displayCreatePortFolioOptions();

  /**
   * Method to check if the file to create a portfolio has been uploaded by the user.
   */
  void isFileUploaded();

  /**
   * This method asks the user to choose between the below 2 options:
   * 1.Fixed Portfolio
   * 2.Flexible Portfolio
   * This choice is the used to perform further operations.
   */
  void chooseFixedOrFlexible();

  /**
   * Method to get the commission value for that transaction from the user.
   * Commission value is used only for flexible portfolios.
   */

  void takeCommissionValue();

  /**
   * Method to display the cost-basis i.e. the total money invested in the portfolio.
   * @param costBasis = costbasis as calculated by the model
   * @param costBasisDate = the date till which the costbasis is calculated.
   */

  void displayCostBasis(double costBasis, LocalDate costBasisDate);

  /**
   * Method that asks the user to choose between the below two options:
   * 1. Add stocks to a portfolio
   * 2. Sell stocks from a portfolio.
   */
  void askAddOrSell();

  /**
   * Method that shows the user the following three options for viewing a bar graph
   * 1. Display chart for previous week
   * 2. Display chart for previous month
   * 3. Display chart for previous year
   */

  void displayOptionsForChart();

  /**
   * Method to display the investments made in a portfolio in the form of a graph, week wise.
   * @param chart = the bar graph as calculated by the model.
   */

  void displayChartWeek(Map<LocalDate, String> chart);

  /**
   * Method to display the investments made in a portfolio in the form of a graph, month wise.
   * @param chart = the bar graph as calculated by the model.
   */

  void displayChartMonth(Map<LocalDate, String> chart);

  void askUserAmountToInvest();
}
