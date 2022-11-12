package model;

public interface IFlexiblePortfolio extends IFixedPortfolio{
  void addStocks(IstockModel stockToAdd);
  void sellStocks(IstockModel stockToSell);


}
