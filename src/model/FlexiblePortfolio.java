package model;

import java.util.List;

public class FlexiblePortfolio implements IflexiblePortfolio {

  private final String portfolioName;
  private List<IstockModelNew> stocks;

  public FlexiblePortfolio(String portfolioName, List<IstockModelNew> stocks) {
    this.portfolioName = portfolioName;
    this.stocks = stocks;
  }

  @Override
  public String getNameOfPortFolio() {
    return this.portfolioName;
  }

  @Override
  public List<IstockModelNew> getStocksInPortfolio() {
    return this.stocks;
  }

  @Override
  public Double calculateCostBasis() {
    double costbasis = 0.0;
    for (IstockModelNew ns: this.stocks) {
      costbasis += ((ns.getBuyingPrice() * ns.getNumOfUnits()) + ns.getCommission());
    }
    return costbasis;
  }

  @Override
  public Double calculateValue() {
    return null;
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
