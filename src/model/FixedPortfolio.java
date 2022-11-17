package model;

import java.util.List;

/**
 * Fixed portfolio class inherits the abstract fixed portfolio.
 * This class is specific to fixed portfolio.
 * AbstractFixedPortfolio class contains the implementations of the methods that are common to both
 * fixed and flexible portfolios.
 */
public class FixedPortfolio extends AbstractFixedPortfolio {
  /**
   * Instantiates a new Fixed portfolio.
   *
   * @param portfolioName the portfolio name
   * @param stocks        the list of stocks present in the portfolio
   */
  public FixedPortfolio(String portfolioName, List<IstockModel> stocks) {
    super(portfolioName, stocks);

  }
}