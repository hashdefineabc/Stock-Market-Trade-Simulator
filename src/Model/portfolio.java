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


  public double valueOfPortfolio(Date date) throws IllegalArgumentException {
    if (this.checkDate(date)) {
      throw new IllegalArgumentException("Can't get value for date greater than today");
    }
    // get list of stocks from portfolio
    // calculate the value and return

    return 0;
  }

  private boolean checkDate(Date date) {
    String today = LocalDate.now().toString();
    if (today.compareTo(date.toString()) < 0) {
      return true;
    }
    return false;
  }

}
