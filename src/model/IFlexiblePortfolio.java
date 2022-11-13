package model;

public interface IFlexiblePortfolio extends IFixedPortfolio{
  void addOrSellStocks(IstockModel stockToAdd);

}
