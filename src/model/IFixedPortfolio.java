package model;

import java.util.List;

/**
 *Interface that describes all the operations that can be performed on a portfolio.
 */
public interface IFixedPortfolio {

  String getNameOfPortFolio();

  List<IstockModel> getStocksInPortfolio();

  Double calculateCostBasis();

  Double calculateValue();

}