package model;

import java.time.LocalDate;

/**
 * The interface for Flexible portfolio.
 * It extends the interface for fixed portfolios as there are several common operation to be
 * performed on both fixed and flexible portfolios.
 * A flexible portfolio has the ability to be modified after it has been created.
 * Stocks can be purchased or sold from a portfolio after the creation of the portfolio.
 */
public interface IFlexiblePortfolio extends IFixedPortfolio {
  /**
   * Add or sell stocks after the creation of portfolio.
   * It takes in the details of the stocks that are to be bought or sold.
   *
   * @param stockDetails the details of the stock that is to be purchased or sold.
   */
  void addOrSellStocks(IstockModel stockDetails);

  /**
   * Used to calculate how much money will be invested in a portfolio at a future date as part of
   * the investment strategy.
   * @param costBasisDate = date at which the cost basis is to be calculated.
   * @param investmentType = investment strategies that are applied to the portfolio.
   * @param investInstrFile = file which stores the investment strategies for the portfolio.
   * @return calculated cost basis.
   */

  Double calculateCostBasisForFuture(LocalDate costBasisDate, InvestmentType investmentType,
                                     String investInstrFile);

}
