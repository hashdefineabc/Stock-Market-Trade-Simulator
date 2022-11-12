package model;

import java.util.List;

public class FlexiblePortfolio extends AbstractFixedPortfolio implements IFlexiblePortfolio {


  public FlexiblePortfolio(String portfolioName, List<IstockModelNew> stocks) {
    super(portfolioName, stocks);
  }

  @Override
  public void addStocks(IstockModelNew stockToAdd) {
    this.stocks.add(stockToAdd);

  }

  @Override
  public void sellStocks(IstockModelNew stockToSell) {
    this.stocks.remove(stockToSell);
  }
}
