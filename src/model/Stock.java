package model;

import java.time.LocalDate;

public class Stock implements IstockModel {

  private final String tickerName;
  private final Double numOfUnits;
  private final Double commission;
  private final Double transactionPrice;
  private final LocalDate transactionDate;
  private final Operation buyOrSell;

  public static StockBuilder getBuilder() {
    return new StockBuilder();
  }

  public Stock(String tickerName, Double numOfUnits, Double commission, Double transactionPrice,
                  LocalDate transactionDate, Operation buyOrSell) {
    this.tickerName = tickerName;
    this.numOfUnits = numOfUnits;
    this.commission = commission;
    this.transactionPrice = transactionPrice;
    this.transactionDate = transactionDate;
    this.buyOrSell = buyOrSell;
  }

  @Override
  public String getTickerName() {
    return this.tickerName;
  }

  @Override
  public Double getNumOfUnits() {
    return this.numOfUnits;
  }

  @Override
  public LocalDate getTransactionDate() {
    return this.transactionDate;
  }

  @Override
  public Double getCommission() {
    return this.commission;
  }

  @Override
  public Double getTransactionPrice() {
    return this.transactionPrice;
  }

  @Override
  public Operation getBuyOrSell() {
    return this.buyOrSell;
  }

  public static class StockBuilder {

    private String tickerName;
    private Double numOfUnits;
    private LocalDate transactionDate;
    private Double commission;
    private Double transactionPrice;
    private Operation buyOrSell;



    public StockBuilder() {
      tickerName = "";
      numOfUnits = 0.0;
      transactionDate = LocalDate.now();
      commission = 0.0;
      transactionPrice = 0.0;
      buyOrSell = Operation.BUY;
    }

    public StockBuilder tickerName(String tickerName) {
      this.tickerName = tickerName;
      return this;
    }

    public StockBuilder numOfUnits(Double units) {
      this.numOfUnits = units;
      return this;
    }

    public StockBuilder transactionDate(LocalDate date) {
      this.transactionDate = date;
      return this;
    }

    public StockBuilder transactionPrice(Double buyingPrice) {
      this.transactionPrice = buyingPrice;
      return this;
    }

    public StockBuilder commission(Double commission) {
      this.commission = commission;
      return this;
    }

    public StockBuilder buyOrSell(Operation buyOrSell) {
      this.buyOrSell = buyOrSell;
      return this;
    }


    public Stock build() {
      //use the currently set values to create the stock object
      return new Stock(tickerName, numOfUnits, commission, transactionPrice, transactionDate, buyOrSell);
    }

  }
}