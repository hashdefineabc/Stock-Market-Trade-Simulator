package Model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class stock {
  String tickerName;
  Integer numOfUnits;
  LocalDate date;

  //todo : builder method to initialize


  public static stockBuilder getBuilder() {
    return new stockBuilder();
  }

  private stock(String tickerName, Integer numOfUnits, LocalDate date) {
    this.tickerName = tickerName;
    this.numOfUnits = numOfUnits;
    this.date = date;
  }

  String getTickerName() {
    return this.tickerName;
  }

  Integer getNumOfUnits() {
    return this.numOfUnits;
  }

  LocalDate getDate() {
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

    public stock build() {
      //use the currently set values to create the stock object
      return new stock(tickerName, numOfUnits, date);
    }

  }


}
