package Model;

import java.time.LocalDate;
import java.util.List;

public interface portfolioModel {
  double valueOfPortfolio(LocalDate date);

  String getNameOfPortFolio();

  List<String[]> toListOfString();

  List<IstockModel> getStocks();

}