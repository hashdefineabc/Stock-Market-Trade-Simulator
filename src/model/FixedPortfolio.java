package model;

import java.util.List;

public class FixedPortfolio extends AbstractFixedPortfolio {
  public FixedPortfolio(String portfolioName, List<IstockModel> stocks) {
    super(portfolioName, stocks);
  }
}
