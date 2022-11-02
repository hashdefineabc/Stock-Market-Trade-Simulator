package Model;

import java.time.LocalDate;

public interface IstockModel {
  String getTickerName();
  Integer getNumOfUnits();

  LocalDate getDate();
}
