package model;

import java.util.List;

public class FlexiblePortfolio extends AbstractFixedPortfolio implements IFlexiblePortfolio {
  public FlexiblePortfolio(String portfolioName, List<IstockModel> stocks) {
    super(portfolioName, stocks);
  }

  @Override
  public void addStocks(IstockModel stockToAdd) {
    this.stocks.add(stockToAdd);

  }

  @Override
  public void sellStocks(IstockModel stockToSell) {
    this.stocks.remove(stockToSell);
  }
}
