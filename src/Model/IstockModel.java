package Model;

import java.time.LocalDate;

/**
 * Interface to describe how a stock would look like.
 */
public interface IstockModel {
  /**
   * Method to get the NASDAQ ticker name of the stock
   * @return the ticker name of the calling stock object
   */
  String getTickerName();

  /**
   * Method to get the no of units purchased of this stock.
   * @return the number of units of the calling stock object.
   */
  Integer getNumOfUnits();

  /**
   * Method to get the date at which this stock was purchased.
   * @return the date f the calling stock object.
   */
  LocalDate getDate();
}
