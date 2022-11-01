package Controller;

import java.time.LocalDate;

public interface Controller {

  int showMenuOnView();

  String[] takeStockInputFromView();

  boolean addMoreStocksFromView();

  String getPortFolioNameFromView();

  int getSelectedPortFolioFromView();

  LocalDate getDateFromView();

}
