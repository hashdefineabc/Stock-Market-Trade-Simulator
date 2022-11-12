package model;

import java.util.List;

public interface IFlexiblePortfolio {

  void addStocks(IstockModelNew stockToAdd);

  void sellStocks(IstockModelNew stockToSell);


}
