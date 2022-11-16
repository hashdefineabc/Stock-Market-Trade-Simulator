package view;

import java.time.LocalDate;
import java.util.List;

import model.IstockModel;


/**
 * Interface defining methods of that would decide how the text user interface
 * of this stockMarket application would look like.
 */
public interface ViewInterface {

  /**
   * Method to display the below options to the user.
   * 1. create new portfolio
   * 2. retrieve portfolio
   * 3. check value of a particular portfolio
   * 4. Exit the application
   */
  void displayMenu();

  /**
   * Method to tell the user to input a ticker name.
   */
  void takeTickerName();

  /**
   * Method to tell the user to input the num of units bought for that particular share.
   */
  void takeNumOfUnits();

  /**
   * Method that asks the user if they want to add more stocks to the portfolio.
   *
   */
  void addMoreStocks();

  /**
   * Method that displays the list of created portfolios.
   * @param portfolios = list of portfolios as given by the controller.
   */
  void displayListOfPortfolios(List<String> portfolios);

  /**
   * Method that displays list of portfolios and asks user to select one.
   */
  void getSelectedPortfolio();

  /**
   * Method that displays stocks of a particular portfolio.
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
   */
  void displayValue(double val, LocalDate valueDate);

  /**
   * Method to display any arbitrary msg to the user.
   * @param msg = the msg passed by the controller.
   */
  void displayMsgToUser(String msg);

  /**
   * Method to get the portfolio name from the user.
   */
  void getPortfolioNameFromUser();

  /**
   * Method to display the following two options of creating a portfolio:
   * 1. Enter the stock details manually
   * 2. Upload an existing file
   */
  void displayCreatePortFolioOptions();

  /**
   * Method to check if the file to create a portfolio has been uploaded by the user.
   */
  void isFileUploaded();

  void chooseFixedOrFlexible();

  void takeCommissionValue();

  void displayCostBasis(double costBasis, LocalDate costBasisDate);

  void askAddOrSell();
}
