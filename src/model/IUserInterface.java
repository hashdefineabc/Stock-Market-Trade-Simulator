package model;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Interface to describe methods for a User of the stock market application.
 * User is the main model of our application.
 * It contains a list of fixed portfolios and flexible portfolios.
 * Each portfolio will contain a list of stocks.
 * This user model is the only model that is interacting with the controller.
 * User model interacts with other models like portfolio model and stock model.
 */
public interface IUserInterface {
  /**
   * Method to load the existing portfolios created by this user.
   *
   * @param portfolioType the portfolio type that is to be loaded.
   */
  void loadExistingPortFolios(PortfolioType portfolioType);

  /**
   * Create a portfolio from file.
   *
   * @param portfolioType the portfolioType of the portfolio that is to be created.
   */
  void createPortFolioFromFile(PortfolioType portfolioType);

  /**
   * Method to get the names of the portfolios created.
   *
   * @param portfolioType the portfolio type
   * @return the list of these portfolio names.
   */
  List<String> getPortfolioNamesCreated(PortfolioType portfolioType);

  /**
   * Method to get the list of fixed portfolio objects created.
   *
   * @return the list of fixed portfolios.
   */
  List<IFixedPortfolio> getFixedPortfoliosCreatedObjects();

  /**
   * Gets flexible portfolios created objects.
   *
   * @return the list flexible portfolios created
   */
  List<IFlexiblePortfolio> getFlexiblePortfoliosCreatedObjects();

  /**
   * Method to check if a particular file exists in the system.
   *
   * @param fileName      the file name to check
   * @param portfolioType the portfolio type
   * @return if file is found or not.
   */
  Boolean checkIfFileExists(String fileName, PortfolioType portfolioType);

  /**
   * Method to save the created portfolio to a csv file.
   *
   * @param dataToWrite   the data to write
   * @param portfolioName the portfolio name
   * @param portfolioType the portfolio type
   */
  void savePortfolioToFile(List<String[]> dataToWrite, String portfolioName,
                           PortfolioType portfolioType);

  /**
   * Method to check if a tickerName is valid or not.
   *
   * @param tickerNameFromUser the ticker name to be checked.
   * @return boolean = if the ticker is valid or not.
   */
  boolean isTickerValid(String tickerNameFromUser);

  /**
   * Method to get the folder path of fixed portfolios where all the fixed portfolio files
   * will be stored.
   *
   * @return string the intended folder path of a fixed portfolio.
   */
  String getFixedPFPath();

  /**
   * Method to get the folder path of flexible portfolios where all the flexible portfolio files
   * will be stored.
   *
   * @return string the intended folder path of a flexible portfolio.
   */
  String getFlexPFPath();

  /**
   * Displays the stocks that are present in a portfolio.
   *
   * @param portfolioIndex     The index of the portfolio that is to be retrieved.
   * @param typeofPortfolio    The type of the portfolio, whether it is fixed or flexible portfolio.
   * @param dateForComposition The date for which the portfolio need to be retrieved.
   * @return The list of stocks that are present in a portfolio on a particular date.
   */
  List<IstockModel> displayStocksOfPortFolio(int portfolioIndex, PortfolioType typeofPortfolio,
                                             LocalDate dateForComposition);

  /**
   * Method to calculate the value of a portfolio.
   *
   * @param portfolioIndex  the portfolio index for which we need to calculate the value
   * @param date            the date for which we need to calculate the value.
   * @param typeofPortfolio the typeof portfolio
   * @return the Double total value of the portfolio.
   */
  Double calculateValueOfPortfolio(int portfolioIndex, LocalDate date,
                                   PortfolioType typeofPortfolio);

  /**
   * Method to create a portfolio manually by taking stock wise input.
   *
   * @param portfolioName   the portfolio name
   * @param stockList       the stocks  in this portfolio.
   * @param typeofPortfolio the typeof portfolio
   * @return boolean = if this portfolio was created or not.
   */
  boolean createNewPortfolio(String portfolioName, List<String[]> stockList,
                             PortfolioType typeofPortfolio);

  /**
   * Calculate cost basis of portfolio double.
   *
   * @param portfolioIndex the portfolio index
   * @param portfolioType  the portfolio type
   * @param costBasisDate  the cost basis date
   * @return the total cost basis value of a portfolio
   */
  Double calculateCostBasisOfPortfolio(int portfolioIndex, PortfolioType portfolioType,
                                       LocalDate costBasisDate);

  /**
   * Gets stock price from the database.
   * If the stock price for a particular date is not found in the database, then we make an api call
   * to update the stock database of the particular stock.
   * If the stock price doesn't exist for a particular date even after making an api call, then
   * it would mean that the market was closed on that date.
   *
   * @param tickerNameFromUser the ticker name for which the stock price needs to be retrieved.
   * @param transactionDate    the date for which the stock price needs to be retrieved.
   * @return the stock price from database.
   */
  Double getStockPriceFromDB(String tickerNameFromUser, LocalDate transactionDate);

  /**
   * Calculate the values that needs to be plotted on the performance chart of a portfolio.
   * We provide user with 3 options to visualize the performance of a portfolio.
   * 1. Performance of the portfolio in the previous week.
   * 2. Performance of the portfolio in the previous month.
   * 3. Performance of the portfolio in the previous year.
   * The number of asterisks on each line is a measure of the value of the portfolio at that
   * timestamp.
   * The value for a date is computed at the end of that day.
   * The value for a month is computed at the last working day of the month.
   * @param option               the option (week, month, year)
   * @param portfolioIndexForVal the index of the portfolio that needs to be visualized
   * @param portfolioType        the type of the portfolio that the user needs to visualize.
   * @return the map of date and string of stars for that particular date.
   */
  Map<LocalDate, String> calculateChart(int option, int portfolioIndexForVal,
                                        PortfolioType portfolioType);

  /**
   * Gets scale of the chart.
   * The scale of the chart indicates how many dollars are represented by each asterisk.
   *
   * @param portfolioIndexForVal the portfolio index for val
   * @param portfolioType        the portfolio type
   * @return the scale
   */
  Double getScale(int portfolioIndexForVal, PortfolioType portfolioType);

  /**
   * gets the name of the portfolio of the given index and the type of the portfolio.
   * @param portfolioIndex index of the portfolio for which the name needs to be returned.
   * @param portfolioType the type of the portfolio
   * @return the name of the portfolio
   */
  String getPortfolioName(int portfolioIndex, PortfolioType portfolioType);

  void updateFlexiblePortFolios(InvestmentType investmentType);

  boolean validateNumUnits(String numUnits);

  boolean validateCommissionValue(String commVal);

  void calculateTxns(LocalDate strategyStart, LocalDate strategyEnd,
                             Integer daysToInvest, HashMap<String,Double> weights, double amount,
                             Double commission, int portfolioIndex, InvestmentType investmentType);

  void acceptStrategyFromUser(int portfolioIndex, Double amount, Double comm,LocalDate startDate,
                              LocalDate endDate, HashMap<String,Double> weights,
                              InvestmentType investmentType);


  void saveInstrToFile(String portfolioName, List<String[]> dataToWrite,
                       InvestmentType investmentType);

}
