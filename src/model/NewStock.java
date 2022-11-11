package model;

import java.time.LocalDate;

public class NewStock implements  IstockModelNew{

  private final String tickerName;
  private final double numOfUnits;
  private final double commission;
  private final double buyingPrice;
  private final LocalDate buyDate;

  public static NewStock.stockBuilder getBuilder() {
    return new NewStock.stockBuilder();
  }

  public NewStock(String tickerName, Double numOfUnits, Double commission, Double buyingPrice,
                  LocalDate buyDate) {
    this.tickerName = tickerName;
    this.numOfUnits = numOfUnits;
    this.commission = commission;
    this.buyingPrice = buyingPrice;
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

    public NewStock.stockBuilder tickerName(String tickerName) {
      this.tickerName = tickerName;
      return this;
    }

    public NewStock.stockBuilder numOfUnits(double units) {
      this.numOfUnits = units;
      return this;
    }

    public NewStock.stockBuilder buyDate(LocalDate date) {
      this.buyDate = date;
      return this;
    }

    public NewStock.stockBuilder buyingPrice(double buyingPrice) {
      this.buyingPrice = buyingPrice;
      return this;
    }

    public NewStock.stockBuilder commission(double commission) {
      this.commission = commission;
      return this;
    }


    public NewStock build() {
      //use the currently set values to create the stock object
      return new NewStock(tickerName, numOfUnits, commission, buyingPrice, buyDate);
    }

  }
}