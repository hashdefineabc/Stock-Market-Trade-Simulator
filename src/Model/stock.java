package model;

import java.time.LocalDate;

/**
 * Class that implements the stock structure and methods.
 */
public class Stock implements IstockModel {
  private String tickerName;
  private Integer numOfUnits; //TODO: make this double
  LocalDate date;

  public static StockBuilder getBuilder() {
    return new StockBuilder();
  }

  /**
   * Constructor to initialize the fields of the stock class.
   * @param tickerName name of the ticker
   * @param numOfUnits number of units for this ticker name
   * @param date date on which the stock was purchased
   */
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

  /**
   * Using builder method to initialize the class.
   */

  public static class StockBuilder {

    private String tickerName;
    private Integer numOfUnits;
    private LocalDate date;

    /**
     * Constructor to initialize the fields of the stockBuilder class.
     */

    public StockBuilder() {
      tickerName = "";
      numOfUnits = 0;
      date = LocalDate.now();
    }

    public StockBuilder tickerName(String tickerName) {
      this.tickerName = tickerName;
      return this;
    }

    public StockBuilder numOfUnits(int units) {
      this.numOfUnits = units;
      return this;
    }

    public StockBuilder date(LocalDate date) {
      this.date = date;
      return this;
    }

    public Stock build() {
      //use the currently set values to create the stock object
      return new Stock(tickerName, numOfUnits, date);
    }

  }


}