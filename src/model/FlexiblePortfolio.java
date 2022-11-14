package model;

import java.util.List;

public class FlexiblePortfolio extends AbstractFixedPortfolio implements IFlexiblePortfolio {
  public FlexiblePortfolio(String portfolioName, List<IstockModel> stocks) {
    super(portfolioName, stocks);
    this.portfolioType = "flexible";
  }

  @Override
  public void addOrSellStocks(IstockModel stockToAdd) {
    this.stocks.add(stockToAdd);

  }

}
