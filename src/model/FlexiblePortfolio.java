package model;

import java.util.List;

/**
 * The class for flexible portfolios implements the flexible portfolio interface and extends the
 * abstractFixedPortfolio class.
 * Some operations that are to be performed on flexible portfolios are already implemented for
 * fixed portfolios, hence we use the concept of code reuse to efficiently reuse the implementations
 * of the fixed portfolios.
 */
public class FlexiblePortfolio extends AbstractFixedPortfolio implements IFlexiblePortfolio {
  /**
   * Instantiates a new Flexible portfolio.
   *
   * @param portfolioName the portfolio name
   * @param stocks        the list stocks that are present in this flexible portfolio
   */
  public FlexiblePortfolio(String portfolioName, List<IstockModel> stocks) {
    super(portfolioName, stocks);
    this.portfolioType = "flexible";
  }

  @Override
  public void addOrSellStocks(IstockModel stockToAdd) {
    this.stocks.add(stockToAdd);

  }

}
