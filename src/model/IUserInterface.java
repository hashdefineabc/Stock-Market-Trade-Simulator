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
  void loadExistingPortFolios(String portfolioType);

  void createPortFolioFromFile();

  /**
   * Method to create port folio using a file from the user.
   */
  void createPortFolioFromFile(String typeofPortfolio);


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
  void savePortfolioToFile(PortfolioModel newPortfolio);

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

  abstract List<IstockModel> displayStocksOfPortFolio(int portfolioIndex, String typeofPortfolio);

  /**
   * Method to calculate the value of a portfolio.
   * @param portfolioIndex the portfolio index for which we need to calculate the value
   * @param date the date for which we need to calculate the value.
   * @return the double total value of the portfolio.
   */
  double calculateValueOfPortfolio(int portfolioIndex, LocalDate date, String typeofPortfolio);

  /**
   * Method to create a portfolio manually by taking stock wise input.
   *
   * @param portfolioName the portfolio name
   * @param stockList the stocks  in this portfolio.
   * @return boolean = if this portfolio was created or not.
   */
  boolean createNewPortfolio(String portfolioName, List<String[]> stockList,
                                  String typeofPortfolio);

  double calculateCostBasisOfPortfolio(int portfolioIndex);

  void addStocksToAPortfolio(int portfolioIndex);

  void sellStocksFromAPortfolio(int portfolioIndex);

  //todo
  void displayChart();
}
