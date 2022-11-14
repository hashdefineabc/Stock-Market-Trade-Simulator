package model;

import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

/**
 * Class to test the functions of the stock class.
 */
public class StockTest {


  @Test
  public void testGetTickerName() {
    IstockModel stock = new Stock("MSFT", 6.0, commission, transactionPrice, LocalDate.now(), buyOrSell);
    assertEquals("MSFT", stock.getTickerName());
  }

  @Test
  public void testGetNumOfUnits() {
    IstockModel stock = new Stock("MSFT", 6.0, commission, transactionPrice, LocalDate.now(), buyOrSell);
    assertEquals("6", stock.getNumOfUnits().toString());
  }

  @Test
  public void testGetDate() {
    IstockModel stock = new Stock("MSFT", 6.0, commission, transactionPrice, LocalDate.now(), buyOrSell);
    LocalDate curDate = LocalDate.now();
    String s = curDate.toString();
    assertEquals(s, stock.getTransactionDate().toString());
  }
}