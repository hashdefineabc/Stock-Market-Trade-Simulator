package Model;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class portfolio {

  List<stock> stocks;

  public portfolio() {
    this.stocks = null;
  }

  public void addStocks(stock s) {
    stocks.add(s);
  }


  public double valueOfPortfolio(LocalDate date) {
    // get list of stocks from portfolio
    // calculate the value and return

    return 0;
  }
  





}
