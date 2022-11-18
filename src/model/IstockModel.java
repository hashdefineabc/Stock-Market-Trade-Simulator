package model;

import java.time.LocalDate;

/**
 * Interface IstockModel describes how a stock would look like.
 * A stock will contain tickerName, the number of units, the date of transaction, the commission
 * value (for flexible portfolios), the transaction price, and whether the stock is bought or sold.
 * In our application we support NASDAQ top 50 stocks. So, only these 50 stocks will be available
 * for purchasing or selling.
 * The number of units of a particular stock is always integral, it cannot be fractional.
 * If user provides fractional stocks, then our application throws an error.
 * The transaction date for buying or selling a stock has to be no later than today's date.
 * Commission value is specific to flexible portfolios.
 * Each buy or sell transaction for a flexible portfolio needs a commission value.
 * We take commission value in dollars.
 * TransactionPrice is the price at which a stock is bought or sold.
 * We get this data from the stock files. We update the stock files if the stock details doesn't
 * exists in our stored file for the transaction date.
 * The operation buyOrSell corresponds to weather the particular transaction is for buying a stock
 * or selling a stock. This is always set to Operation.BUY for fixed portfolios.
 */
public interface IstockModel {
  /**
   * Method to get the NASDAQ ticker name of the stock.
   * We support NASDAQ top 50 stocks in our application.
   * If the entered tickerName is not present in the NASDAQ top 50 list, then we ask user to enter
   * a valid supported tickerName.
   * @return the ticker name of the calling stock object
   */
  String getTickerName();

  /**
   * Method to get the no of units of the stock.
   *
   * @return the number of units of the calling stock object.
   */
  Double getNumOfUnits();

  /**
   * Method to get the date at which this stock was purchased or sold.
   *
   * @return the date of the calling stock object.
   */
  LocalDate getTransactionDate();

  /**
   * This method gets the commission value of a particular transaction.
   * Commission is only used for flexible portfolios.
   *
   * @return the commission
   */

  Double getCommission();

  /**
   * This method gets the transaction price of a particular transaction.
   *
   * @return the transaction price of the calling stock object.
   */

  Double getTransactionPrice();

  /**
   * This method is used to determine whether the stock is bought or sold.
   * It returns Operation.BUY if the stock was bought and Operation.SELL if the stock was sold.
   *
   * @return Operation.Buy or Operation.SELL
   */
  Operation getBuyOrSell();

}
