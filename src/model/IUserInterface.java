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

  /**
   * Updates the Flexible portfolios as per the investment strategies decided for them
   * @param investmentType = Type of investment strategy for a portfolio, can be investing by
   *                       weights or DCA.
   */
  void updateFlexiblePortFolios(InvestmentType investmentType);

  /**
   * Checks if the num of shares bought/sold for a ticker are whole numbers and non-negative.
   * @param numUnits = the num of shares
   * @return if the num of units are valid.
   */
  boolean validateNumUnits(String numUnits);

  /**
   * Check if the commission value is non-negative.
   * @param commVal = commission value as entered by the user.
   * @return if the commission is valid or not.
   */
  boolean validateCommissionValue(String commVal);

  /**
   * This function will calculate all the txns that must be done as per an investment strategy.
   * @param strategyStart = start Date of the strategy.
   * @param strategyEnd = end Date of the strategy.
   * @param daysToInvest = daysToInvest periodically.
   * @param weights = weightage to distribute the amount among the stocks.
   * @param amount = total amount to invest.
   * @param commission = commission that will be charged for the txns.
   * @param portfolioIndex = portfolio on which the strategy would be applied.
   * @param investmentType = Investment by weight or DCA.
   * @return the last txn date.
   */

  LocalDate calculateTxns(LocalDate strategyStart, LocalDate strategyEnd,
                          Integer daysToInvest, HashMap<String,Double> weights, double amount,
                          Double commission, int portfolioIndex, InvestmentType investmentType);

  /**
   * Accept the information from the user for the investment strategy.
   * @param portfolioIndex =portfolio on which the strategy would be applied.
   * @param amount = total amount to invest.
   * @param comm = commission that will be charged for the txns.
   * @param startDate = start Date of the strategy.
   * @param endDate = end Date of the strategy.
   * @param weights = weightage to distribute the amount among the stocks.
   * @param investmentType = Investment by weight or DCA.
   * @param daysToInvest = daysToInvest periodically.
   * @param lastTxnDate = last date when the investment was made.
   */

  void acceptStrategyFromUser(int portfolioIndex, Double amount, Double comm,LocalDate startDate,
                              LocalDate endDate, HashMap<String,Double> weights,
                              InvestmentType investmentType, Integer daysToInvest,
                              LocalDate lastTxnDate);

  /**
   * Saving the investment strategy to file for future use.
   * @param portfolioName = portfolio on which the strategy would be applied.
   * @param dataToWrite = the strategy details.
   * @param investmentType = Investment by weight or DCA.
   */

  void saveInstrToFile(String portfolioName, List<String[]> dataToWrite,
                       InvestmentType investmentType);

  /**
   * Check if the weightage for investment adds up to 100%.
   * @param weights = weightages for different stocks in the investment strategy.
   * @return if the sum of all weights is valid or not.
   */

  Boolean validateWeightsForInvestment(double[] weights);

  boolean isDoubleValid(String s);
}
