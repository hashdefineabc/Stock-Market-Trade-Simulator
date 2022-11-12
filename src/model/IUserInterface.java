package model;

import java.time.LocalDate;
import java.util.List;

/**
 * Interface to describe methods for a User of the stock market application.
 */
public interface IUserInterface {
  /**
   * Method to load the existing portfolios created by this user.
   */
  void loadExistingPortFolios();

  /**
   * Method to create port folio using a file from the user.
   */
  void createPortFolioFromFile();

  /**
   * Method to create a new portfolio.
   * @param newPortfolio the new portfolio
   */
  void CreateNewPortfolio(IFixedPortfolio newPortfolio);

  /**
   * Method to get the names of the portfolios created.
   * @return the list of these portfolionames.
   */
  List<String> getPortfolioNamesCreated();

  /**
   * Method to get the list of portfolio objects created.
   * @return the list of portfolios.
   */
  List<IFixedPortfolio> getPortfoliosCreatedObjects();

  /**
   * Method to check if a particular file exists in the system.
   * @param fileName the file name to check
   * @return if file is found or not.
   */
  Boolean checkIfFileExists(String fileName);

  /**
   * Method to save the created portfolio to a csv file.
   * @param newPortfolio the portfolio to be saved.
   */
  void savePortfolioToFile(IFixedPortfolio newPortfolio);

  /**
   * Method to check if a tickerName is valid or not.
   * @param tickerNameFromUser the ticker name to be checked.
   * @return boolean = if the ticker is valid or not.
   */
  boolean isTickerValid(String tickerNameFromUser);

  /**
   * Method to get the folder path where all the files will be stored.
   * @return string = the intended folder path.
   */
  String getFolderPath();

  /**
   * Method to display the stocks of a portfolio list.
   * @param portfolioIndex the portfolio index = indicates the portfolio for which we need to
   *                       display the stocks.
   * @return the list = list of stocks
   */
  List<String[]> displayStocksOfPortFolio(int portfolioIndex);

  /**
   * Method to calculate the value of a portfolio.
   * @param portfolioIndex the portfolio index for which we need to calculate the value
   * @param date the date for which we need to calculate the value.
   * @return the double total value of the portfolio.
   */
  double calculateValueOfPortfolio(int portfolioIndex, LocalDate date);

  /**
   * Method to create a portfolio manually by taking stock wise input.
   *
   * @param portfolioName the portfolio name
   * @param stockList the stocks  in this portfolio.
   * @return boolean = if this portfolio was created or not.
   */
  boolean createPortfolioManually(String portfolioName, List<String[]> stockList);

  //todo
  void buySell();

  //todo
  void calculateCostBasis();

  //todo
  void displayChart();
}
