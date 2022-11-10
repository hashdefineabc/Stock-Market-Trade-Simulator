package model;

import java.util.List;

public interface IflexiblePortfolio {

  String getNameOfPortFolio();

  List<IstockModelNew> getStocksInPortfolio();

  Double calculateCostBasis();

  Double calculateValue();

  void addStocks(IstockModelNew stockToAdd);

  void sellStocks(IstockModelNew stockToSell);


}
