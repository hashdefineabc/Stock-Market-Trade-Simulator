package model;

import java.time.LocalDate;

public class Stock implements IstockModel {

  private final String tickerName;
  private final double numOfUnits;
  private double commission;
  private double buyingPrice;
  private final LocalDate buyDate;

  public static Stock.stockBuilder getBuilder() {
    return new Stock.stockBuilder();
  }

  public Stock(String tickerName, Double numOfUnits, Double commission, Double buyingPrice,
                  LocalDate buyDate) {
    this.tickerName = tickerName;
    this.numOfUnits = numOfUnits;
    this.commission = commission;
    this.buyingPrice = buyingPrice;
    this.buyDate = buyDate;
  }

  public Stock(String tickerName, Double numOfUnits, LocalDate buyDate) {
    this.tickerName = tickerName;
    this.numOfUnits = numOfUnits;
    this.buyDate = buyDate;
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
    return this.buyDate;
  }

  @Override
  public Double getCommission() {
    return this.commission;
  }

  @Override
  public Double getBuyingPrice() {
    return this.buyingPrice;
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