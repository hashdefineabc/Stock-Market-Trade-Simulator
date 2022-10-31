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

  // TODO: 10/30/22 remove this main method
  public static void main(String[]args) throws IOException {

    FileReader file = new FileReader("daily_goog.csv");

    try(BufferedReader br = new BufferedReader(file)) {

      String line = br.readLine();
      while (line != null) {
        String[] attributes = line.split(",");
        LocalDate today = LocalDate.now();
        LocalDate yes = today.minusDays(2);
        System.out.println(yes);
        String timeStamp = attributes[0];
        if(timeStamp.equals(yes.toString()))
          System.out.println("28th October");
        System.out.println("date: "+attributes[0]+" close_value: "+attributes[4]);
        line = br.readLine();
      }

    } catch (IOException e) {
      throw new RuntimeException(e);
    }

  }

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
