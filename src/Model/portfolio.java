package Model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class portfolio implements portfolioModel{

  public String nameOfPortFolio;
  public List<stock> stocks;

  // private fields for api data fetching and file handling
  private String apiKey = "W0M1JOKC82EZEQA8";
  private URL url = null;
  private String userDirectory = new File("").getAbsolutePath();
  private String folderPath = userDirectory + File.separator + "stockData";


  public portfolio(String nameOfPortFolio) {
    this.nameOfPortFolio = nameOfPortFolio;
    this.stocks = new ArrayList<>();
  }

  @Override
  public void addStocks(stock s) {
    this.stocks.add(s);
  }
  private double getStockValue(String tickerName, LocalDate date) {
    double result = 0;
    FileReader file = null;
    String fileName = folderPath+File.separator+tickerName+".csv";

    try {
      file = new FileReader(fileName);
    } catch (FileNotFoundException e) {
      updateStockFile(tickerName);
    }

    try {
      file = new FileReader(fileName);
    } catch (FileNotFoundException e) {
      throw new RuntimeException("File not found yet !!!!!!!!!!!");
    }

    try(BufferedReader br = new BufferedReader(file)) {
      String line = br.readLine();
      /* check if the date for which the value is to be determined is greater than
          the latest date that exists in our file.
      */
      String[] attributes = line.split(",");
      line = br.readLine();
      attributes = line.split(",");
      String timeStamp = attributes[0];
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
      LocalDate dt = LocalDate.parse(timeStamp,formatter);

      if(dt.compareTo(date) < 0) { // date > latest (first) timestamp in file
        updateStockFile(tickerName);
      }

      while (line != null) {
        attributes = line.split(",");
        timeStamp = attributes[0];
        if(timeStamp.equals(date.toString())) {
          result += Double.parseDouble(attributes[4]);
          return result;
        }
        line = br.readLine();
      }

    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return result;
  }

  private void updateStockFile(String tickerName) {
    try {
      url = new URL("https://www.alphavantage"
              + ".co/query?function=TIME_SERIES_DAILY"
              + "&outputsize=compact"
              + "&symbol"
              + "=" + tickerName + "&apikey="+apiKey+"&datatype=csv");

      String filePath = folderPath+File.separator+tickerName+".csv";
      new FileWriter(filePath, false).close();

      List<String[]> row = new ArrayList<>();

      InputStream in = null;
      StringBuilder output = new StringBuilder();

      in = url.openStream();

      try(BufferedReader br = new BufferedReader(new InputStreamReader(in))) {
        String line = br.readLine();

        while (line != null) {
          String[] attributes = line.split(",");
          row.add(attributes);
          line = br.readLine();
        }
        try (PrintWriter pw = new PrintWriter(filePath)) {
          row.stream()
                  .map(this::convertToCSV)
                  .forEach(pw::println);
        }
      }
      catch (IOException e) {
        throw new RuntimeException(e);
      }

    }
    catch (MalformedURLException e) {
      throw new RuntimeException(e);
    }
    catch (IOException e) {
      throw new RuntimeException(e);
    }
  }


  private String convertToCSV(String[] data) {
    return Stream.of(data)
            .map(this::escapeSpecialCharacters)
            .collect(Collectors.joining(","));
  }


  private String escapeSpecialCharacters(String data) {
    String escapedData = data.replaceAll("\\R", " ");
    if (data.contains(",") || data.contains("\"") || data.contains("'")) {
      data = data.replace("\"", "\"\"");
      escapedData = "\"" + data + "\"";
    }
    return escapedData;
  }


  @Override
  public double valueOfPortfolio(LocalDate date) {
    // get list of stocks from portfolio
    // calculate the value and return

    double answer = 0;

    for(stock curStock: this.stocks) {
      answer = answer + curStock.numOfUnits * getStockValue(curStock.tickerName, date);
    }

    return answer;
  }


  @Override
  public String getNameOfPortFolio() {
    return this.nameOfPortFolio;
  }


  @Override
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