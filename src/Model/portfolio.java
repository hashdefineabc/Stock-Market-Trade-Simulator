package Model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class portfolio implements portfolioModel{

  public String nameOfPortFolio;
  String apiKey = "W0M1JOKC82EZEQA8";

  public List<stock> stocks;

  public portfolio(String nameOfPortFolio) {
    this.nameOfPortFolio = nameOfPortFolio;
    this.stocks = new ArrayList<>();
  }

  public void addStocks(stock s) {
    this.stocks.add(s);
  }

  double getStockValue(String tickerName, LocalDate date) {
    double result = 0;
    FileReader file = null;
    try {
      String fileName = ""+tickerName+".csv";
      file = new FileReader(fileName);
    } catch (FileNotFoundException e) {
      throw new RuntimeException("File not found!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    }

    try(BufferedReader br = new BufferedReader(file)) {

      String line = br.readLine();
      while (line != null) {
        String[] attributes = line.split(",");
        String timeStamp = attributes[0];
        if(timeStamp.equals(date.toString()))
          result += Double.parseDouble(attributes[4]);
        line = br.readLine();
      }

    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return result;
  }


  public double valueOfPortfolio(LocalDate date) {
    // get list of stocks from portfolio
    // calculate the value and return


    double answer = 0;

    for(stock curStock: this.stocks) {
      answer = answer + curStock.numOfUnits * getStockValue(curStock.tickerName, date);
    }

    return answer;
  }


  public String getNameOfPortFolio() {
    return this.nameOfPortFolio;
  }


  public List<String[]> toListOfString() {
    List<String[]> answer = new ArrayList<>();

    for (Model.stock stock : this.stocks) {
      String[] stocksDetails = new String[3];
      stocksDetails[0] = String.valueOf(stock.getTickerName());
      stocksDetails[1] = String.valueOf(stock.getNumOfUnits());
      stocksDetails[2] = String.valueOf(stock.getDate());
      answer.add(stocksDetails);
    }
    return answer;
  }


}
