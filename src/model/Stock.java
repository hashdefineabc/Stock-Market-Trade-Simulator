package model;

import java.time.LocalDate;

public class Stock implements IstockModel {

  private final String tickerName;
  private final double numOfUnits;
  private double commission;
  private double transactionPrice;
  private final LocalDate transactionDate;
  private Boolean buyOrSell;

  public static Stock.stockBuilder getBuilder() {
    return new Stock.stockBuilder();
  }

  public Stock(String tickerName, Double numOfUnits, Double commission, Double transactionPrice,
                  LocalDate transactionDate, Boolean buyOrSell) {
    this.tickerName = tickerName;
    this.numOfUnits = numOfUnits;
    this.commission = commission;
    this.transactionPrice = transactionPrice;
    this.transactionDate = transactionDate;
    this.buyOrSell = buyOrSell;
  }

  public Stock(String tickerName, Double numOfUnits, LocalDate transactionDate) {
    this.tickerName = tickerName;
    this.numOfUnits = numOfUnits;
    this.transactionDate = transactionDate;
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
  public LocalDate getBuyDate() {
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

  public static class stockBuilder {

    private String tickerName;
    private double numOfUnits;
    private LocalDate buyDate;
    private double commission;
    private double buyingPrice;



    public stockBuilder() {
      tickerName = "";
      numOfUnits = 0.0;
      buyDate = LocalDate.now();
      commission = 0.0;
      buyingPrice = 0.0;
    }

    public Stock.stockBuilder tickerName(String tickerName) {
      this.tickerName = tickerName;
      return this;
    }

    public Stock.stockBuilder numOfUnits(double units) {
      this.numOfUnits = units;
      return this;
    }

    public Stock.stockBuilder buyDate(LocalDate date) {
      this.buyDate = date;
      return this;
    }

    public Stock.stockBuilder buyingPrice(double buyingPrice) {
      this.buyingPrice = buyingPrice;
      return this;
    }

    public Stock.stockBuilder commission(double commission) {
      this.commission = commission;
      return this;
    }


    public Stock build() {
      //use the currently set values to create the stock object
      return new Stock(tickerName, numOfUnits, commission, buyingPrice, buyDate);
    }

  }
}