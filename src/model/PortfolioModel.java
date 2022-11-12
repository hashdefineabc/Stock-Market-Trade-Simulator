package model;

import java.time.LocalDate;
import java.util.List;

/**
 *Interface that describes all the operations that can be performed on a portfolio.
 */
public interface PortfolioModel {
  /**
   * Method to calculate the value of a portfolio.
   * @param date  =  date at which we need to calculate the value
   * @return the value of the portfolio.
   */
  double valueOfPortfolio(LocalDate date);

  /**
   * Method to get the name of port folio.
   * @return the name of port folio
   */
  String getNameOfPortFolio();

  /**
   * Method to enumerate an object into a list of string arrays.
   * @return List[String[]]
   */
  List<String[]> toListOfString();

  /**
   * Method that gets the stocks in a portfolio.
   * @return the stocks
   */
  List<IstockModel> getStocks();

}