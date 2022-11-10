package model;

import java.time.LocalDate;

/**
 * Class that implements the stock structure and methods.
 */
public class Stock implements IstockModel {
  private String tickerName;
  private Integer numOfUnits;
  LocalDate date;

  public static stockBuilder getBuilder() {
    return new stockBuilder();
  }

  public Stock(String tickerName, Integer numOfUnits, LocalDate date) {
    this.tickerName = tickerName;
    this.numOfUnits = numOfUnits;
    this.date = date;
  }

  @Override
  public String getTickerName() {
    return this.tickerName;
  }

  @Override
  public Integer getNumOfUnits() {
    return this.numOfUnits;
  }

  @Override
  public LocalDate getDate() {
    return this.date;
  }

  public static class stockBuilder {

    private String tickerName;
    private Integer numOfUnits;
    private LocalDate date;

    public stockBuilder() {
      tickerName = "";
      numOfUnits = 0;
      date = LocalDate.now();
    }

    public stockBuilder tickerName(String tickerName) {
      this.tickerName = tickerName;
      return this;
    }

    public stockBuilder numOfUnits(int units) {
      this.numOfUnits = units;
      return this;
    }

    public stockBuilder date(LocalDate date) {
      this.date = date;
      return this;
    }

    public Stock build() {
      //use the currently set values to create the stock object
      return new Stock(tickerName, numOfUnits, date);
    }

  }


}