package model;

/**
 * The enum Operation indicates wheather the transaction to be performed is buying or selling stock.
 * This operation is set to buy for all fixed portfolio stocks as fixed portfolios doesn't have the
 * option to sell stocks or buy more stocks once the portfolio is already been created.
 */
public enum Operation {
  /**
   * Buy operation.
   */
  BUY,
  /**
   * Sell operation.
   */
  SELL
}
