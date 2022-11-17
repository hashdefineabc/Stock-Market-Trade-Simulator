package model;

import java.time.LocalDate;
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
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * AbstractFixedPortfolio class implements the IFixedPortfolio interface.
 * It contains implementations of all the operations for a fixed portfolio.
 * It essentially has the method definitions of the methods that are common to both fixed and
 * flexible portfolio.
 *
 * This class is inherited by FixedPortfolio class and FlexiblePortfolio class.
 */
abstract class AbstractFixedPortfolio implements IFixedPortfolio {
  private String nameOfPortFolio;
  private LocalDate dateOfCreation;
  /**
   * The list of stocks present in a portfolio.
   */
  final List<IstockModel> stocks;
  private Double scale = 0.0;
  private String apiKey = "RWI9HAQXNXJQQSJI";
  private String userDirectory = new File("").getAbsolutePath();
  private String folderPath = userDirectory + File.separator + "stockData";
  /**
   * The type of the portfolio. It can be either fixed or flexible
   */
  String portfolioType;


  /**
   * Constructor to initialize the fields of the portfolio class.
   * It throws an exception if no stocks are provided while creating the portfolio
   *
   * @param nameOfPortFolio name of the portfolio
   * @param stocks          list of stocks in the portfolio
   * @throws IllegalArgumentException when we try to create portfolio with empty stocks
   */
  public AbstractFixedPortfolio(String nameOfPortFolio, List<IstockModel> stocks)
          throws IllegalArgumentException {
    if (Objects.equals(nameOfPortFolio, "")) {
      throw new IllegalArgumentException("Please provide a name for your portfolio");
    }
    if (stocks.isEmpty()) {
      throw new IllegalArgumentException(
              "Creating a portfolio requires atleast one stock, please add a stock!!!!!");
    }
    this.nameOfPortFolio = nameOfPortFolio;
    this.stocks = stocks;
    this.dateOfCreation = LocalDate.now();
    this.portfolioType = "fixed";
  }

  private Double getStockValue(String tickerName, LocalDate date) {
    Double result = 0.0;
    FileReader file = null;
    String fileName = "./resources/stockData/" + File.separator + tickerName + ".csv";

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

    try (BufferedReader br = new BufferedReader(file)) {
      String line = br.readLine();
      /* check if the date for which the value is to be determined is greater than
          the latest date that exists in our file.
      */
      String[] attributes = line.split(",");
      line = br.readLine();
      attributes = line.split(",");
      String timeStamp = attributes[0];
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
      try {
        LocalDate dt = LocalDate.parse(timeStamp, formatter);
        if (dt.compareTo(date) < 0) { // date > latest (first) timestamp in file
          updateStockFile(tickerName);
        }

        while (line != null) {
          attributes = line.split(",");
          timeStamp = attributes[0];
          if (timeStamp.equals(date.toString())) {
            result += Double.parseDouble(attributes[4]);
            return result;
          }
          line = br.readLine();
        }
      } catch (Exception e) {
        // do nothing
      }

    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    if(result == 0.0) {
      User user = new User(null);
      result = user.getValueOnDate(tickerName, date);
    }
    return result;
  }

  private void updateStockFile(String tickerName) {
    try {
      URL url = new URL("https://www.alphavantage"
              + ".co/query?function=TIME_SERIES_DAILY"
              + "&outputsize=compact"
              + "&symbol"
              + "=" + tickerName + "&apikey=" + apiKey + "&datatype=csv");

      String filePath = "./resources/stockData/" + File.separator + tickerName + ".csv";
      new FileWriter(filePath, false).close();

      List<String[]> row = new ArrayList<>();

      InputStream in = null;

      in = url.openStream();

      try (BufferedReader br = new BufferedReader(new InputStreamReader(in))) {
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
      } catch (IOException e) {
        throw new RuntimeException(e);
      }

    } catch (MalformedURLException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
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
  public Double calculateValue(LocalDate date) {
    // get list of stocks from portfolio
    // calculate the value and return

    Double answer = 0.0;

    for (IstockModel curStock : this.stocks) {
      Integer addOrSub = 1;
      if(curStock.getBuyOrSell().equals(Operation.SELL)){
        addOrSub = -1;
      }
      if(curStock.getTransactionDate().isAfter(date)) {
        continue;
      }
      answer = answer + (curStock.getNumOfUnits() * getStockValue(curStock.getTickerName(), date) * addOrSub);
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

    for (IstockModel stock : this.stocks) {
      String[] stocksDetails = new String[6];
      stocksDetails[0] = String.valueOf(stock.getTickerName());
      stocksDetails[1] = String.valueOf(stock.getNumOfUnits());
      stocksDetails[2] = String.valueOf(stock.getTransactionDate());
      stocksDetails[3] = String.valueOf(stock.getCommission());
      stocksDetails[4] = String.valueOf(stock.getTransactionPrice());
      stocksDetails[5] = String.valueOf((stock.getBuyOrSell()));
      answer.add(stocksDetails);
    }
    return answer;
  }

  @Override
  public List<IstockModel> getStocksInPortfolio(LocalDate date) {
    List<IstockModel> stocksBeforeDate = new ArrayList<>();
    for(IstockModel stock : this.stocks) {
      if(stock.getTransactionDate().equals(date) || stock.getTransactionDate().isBefore(date)) {
        stocksBeforeDate.add(stock);
      }
    }
    return stocksBeforeDate;
  }
  @Override
  public Double calculateCostBasis(LocalDate costBasisDate) {
    double costBasis = 0.0;
    for (IstockModel ns: this.stocks) {
      if (ns.getTransactionDate().compareTo(costBasisDate) <= 0){
        if (ns.getBuyOrSell() == Operation.BUY) {
          costBasis += ((ns.getTransactionPrice() * ns.getNumOfUnits()) + ns.getCommission());
        }
        else if (ns.getBuyOrSell() == Operation.SELL) {
          costBasis += ns.getCommission();
        }
      }
    }
    return costBasis;
  }

  @Override
  public Map<LocalDate, String> calculateChartValues(int option) {

    Map<LocalDate, String> chart = new LinkedHashMap<>();
    List<Double> values = new ArrayList<>();

    LocalDate startDate = LocalDate.now();
    LocalDate endDate;

    if(option == 1) { // previous week
      endDate = startDate.minusDays(7);
      for(LocalDate date = startDate; date.isAfter(endDate); date = date.minusDays(1)) {
        values.add(this.calculateValue(date));
      }
    } else if (option == 2) { // previous month
      endDate = startDate.minusDays(30);
      for(LocalDate date = startDate; date.isAfter(endDate); date = date.minusDays(1)) {
        values.add(this.calculateValue(date));
      }
    }
    else {
      endDate = startDate.minusMonths(12);
      endDate = endDate.with(TemporalAdjusters.lastDayOfMonth());
      for(LocalDate date = startDate; date.isAfter(endDate); date = date.minusMonths(1)) {
        values.add(this.calculateValue(date));
        date = date.with(TemporalAdjusters.lastDayOfMonth());
      }
    }


    Double maxValue = Collections.max(values);
    this.scale = maxValue / 30;
    LocalDate date = LocalDate.now();
    for(int i=0; i<values.size(); i++) {
      int numStars = (int) (values.get(i) / scale);
      String stars = "";
      if(numStars == 0)
        stars = "<*";
      else {
        for(int j=0; j<numStars; j++)
          stars = stars+"*";
      }
      chart.put(date, stars);
      if(option == 3){
        date = date.minusMonths(1);
      }
      else {
        date = date.minusDays(1);
      }
    }

    return chart;

  }

  @Override
  public Double getScale() {
    return this.scale;
  }

}
