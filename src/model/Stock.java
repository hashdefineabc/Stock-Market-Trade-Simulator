package model;

import java.time.LocalDate;

public class Stock implements IstockModel {

  private final String tickerName;
  private final Double numOfUnits;
  private final Double commission;
  private final Double transactionPrice;
  private final LocalDate transactionDate;
  private final Boolean buyOrSell;

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

  @Override
  public Boolean getBuyOrSell() {
    return this.buyOrSell;
  }

  public static class stockBuilder {

    private String tickerName;
    private double numOfUnits;
    private LocalDate buyDate;
    private double commission;
    private double buyingPrice;

    private boolean buyOrSell;



    public stockBuilder() {
      tickerName = "";
      numOfUnits = 0.0;
      buyDate = LocalDate.now();
      commission = 0.0;
      buyingPrice = 0.0;
      buyOrSell = false;
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

    public Stock.stockBuilder buyOrSell(boolean buyOrSell) {
      this.buyOrSell = buyOrSell;
      return this;
    }


    public Stock build() {
      //use the currently set values to create the stock object
      return new Stock(tickerName, numOfUnits, commission, buyingPrice, buyDate, buyOrSell);
    }

  }
}