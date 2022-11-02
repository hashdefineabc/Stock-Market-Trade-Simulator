package Model;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface portfolioModel {

  void addStocks(stock s);

  double valueOfPortfolio(LocalDate date);

  String getNameOfPortFolio();

  List<String[]> toListOfString();

  List<stock> getStocks();

}