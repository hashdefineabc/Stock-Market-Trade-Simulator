package model;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * This Interface that describes all the operations that can be performed on a fixed portfolio.
 * A fixed portfolio allows a user to create one or more portfolios with shares of one or more
 * stock. Once created, shares cannot be added or removed from the fixed portfolio.
 * We can examine the composition of a portfolio.
 * User are provided an option to view the total value of a portfolio on a certain date.
 * User can persist a portfolio so that it can be saved and loaded
 * User can save and retrieve a portfolio from the files.
 * User can view the cost basis of a portfolio, cost basis is the total amount spent by the user
 * on a particular portfolio.
 * User is also given an option to view the performance of a portfolio over a period of the previous
 * week, previous month and the previous year.
 */
public interface IFixedPortfolio {
  /**
   * Method to calculate the value of a portfolio.
   * It calculate the total value of a portfolio on a specific date.
   * If the market was closed on the date, then we consider the closing price of the previous date
   * for the stock.
   *
   * @param date date at which we need to calculate the value
   * @return the value of the portfolio.
   */
  Double calculateValue(LocalDate date);

  /**
   * Method to get the name of port folio.
   *
   * @return the name of port folio
   */
  String getNameOfPortFolio();

  /**
   * Method to enumerate an object into a list of string arrays.
   *
   * @return List[String[]] list
   */
  List<String[]> toListOfString();

  /**
   * Method that gets the stocks in a portfolio.
   * It returns a list of stocks that are present in the portfolio on a particular date
   *
   * @param date the date at which we want to view the status of stocks in a portfolio
   * @return the list stocks present on a specific date
   */
  List<IstockModel> getStocksInPortfolio(LocalDate date);

  /**
   * Calculate the cost basis of a portfolio on a particular date.
   * For fixed portfolios, the date will be today's date, as the fixed portfolios cannot be modified
   * once created we calculate the cost basis on current date or the date on which the portfolio
   * was created.
   * For flexible portfolio, since it has the ability to modify the portfolio, we calculate the
   * cost basis on a particular date.
   *
   * @param costBasisDate the cost basis date
   * @return the double
   */
  Double calculateCostBasis(LocalDate costBasisDate);

  /**
   * Calculate chart values.
   * Performance of a portfolio can be visualized with the help of a chart.
   * This method calculates the values that needs to be represented in the chart.
   * The parameter option determines whether the user wants to view the performance of a portfolio
   * of the previous week, previous month or the previous year.
   * option 1 would represent previous week
   * option 2 would represent previous month
   * option 3 would represent previous year
   * It returns a map, which contains the date as a key and the string of stars for this date as
   * the value.
   * If the value on a particular date is less than the scale then we would represent it by the
   * symbol <*
   * @param option the option
   * @return the map
   */
  Map<LocalDate, String> calculateChartValues(int option);

  /**
   * Gets the scale of the chart.
   * scale of the chart represents the value that each star would mean.
   * If the value for a particular date is less than the scale, then we would represent it with the
   * symbole <*
   *
   * @return the scale
   */
  Double getScale();
}