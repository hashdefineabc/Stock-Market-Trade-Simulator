package model;

import java.util.List;

public class AbstractFixedPortfolio implements IFixedPortfolio {

  protected final String portfolioName;

  protected List<IstockModel> stocks;

  public AbstractFixedPortfolio(String portfolioName, List<IstockModel> stocks) {
    this.portfolioName = portfolioName;
    this.stocks = stocks;
  }


  @Override
  public String getNameOfPortFolio() {
    return this.portfolioName;
  }

  @Override
  public List<IstockModel> getStocksInPortfolio() {
    return this.stocks;
  }

  @Override
  public Double calculateCostBasis() {
    double costBasis = 0.0;
    for (IstockModel ns: this.stocks) {
      costBasis += ((ns.getBuyingPrice() * ns.getNumOfUnits()) + ns.getCommission());
    }
    return costBasis;
  }

  @Override
  public Double calculateValue() {
    double value = 0.0;
    for (IstockModel ns: this.stocks) {
      double closingPrice = 0.0; //TODO: get this from API
      value += closingPrice * ns.getNumOfUnits();
    }
    return value;
  }

}
