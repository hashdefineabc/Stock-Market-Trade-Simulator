package Model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class portfolio implements portfolioModel{

  public String nameOfPortFolio;

  public List<stock> stocks;

  public portfolio(String nameOfPortFolio) {
    this.nameOfPortFolio = nameOfPortFolio;
    this.stocks = new ArrayList<>();
  }

  public void addStocks(stock s) {
    this.stocks.add(s);
  }


  public double valueOfPortfolio(LocalDate date) {
    // get list of stocks from portfolio
    // calculate the value and return


    return 0;
  }

  public String getNameOfPortFolio() {
    return this.nameOfPortFolio;
  }


  public List<String[]> toListOfString() {
    List<String[]> answer = new ArrayList<>();

    for(int i=0; i<this.stocks.size(); i++) {
      String[] stocksDetails = new String[3];
      stocksDetails[0] = String.valueOf(this.stocks.get(i).getTickerName());
      stocksDetails[1] = String.valueOf(this.stocks.get(i).getNumOfUnits());
      stocksDetails[2] = String.valueOf(this.stocks.get(i).getDate());
      answer.add(stocksDetails);
    }
    return answer;
  }


}
