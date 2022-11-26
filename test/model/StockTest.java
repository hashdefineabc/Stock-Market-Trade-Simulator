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
    IstockModel stock = new Stock("MSFT", 6.0, 10.0, 2000.0, LocalDate.now(), Operation.BUY);
    assertEquals("MSFT", stock.getTickerName());
  }

  @Test
  public void testGetNumOfUnits() {
    IstockModel stock = new Stock("MSFT", 6.0, 20.0, 3000.0, LocalDate.now(), Operation.BUY);
    assertEquals("6.0", stock.getNumOfUnits().toString());
  }

  @Test
  public void testGetDate() {
    IstockModel stock = new Stock("MSFT", 6.0, 20.0, 20000.0, LocalDate.now(), Operation.BUY);
    LocalDate curDate = LocalDate.now();
    String s = curDate.toString();
    assertEquals(s, stock.getTransactionDate().toString());
  }

  @Test
  public void testCommission() {
    IstockModel stock = new Stock("MSFT", 6.0, 20.0, 3000.0, LocalDate.now(), Operation.BUY);
    assertEquals("20.0", stock.getCommission().toString());
  }

  @Test
  public void testTransactionPrice() {
    IstockModel stock = new Stock("MSFT", 6.0, 20.0, 3000.0, LocalDate.now(), Operation.BUY);
    assertEquals("3000.0", stock.getTransactionPrice().toString());
  }

  @Test
  public void testOperationBUY() {
    IstockModel stock = new Stock("MSFT", 6.0, 20.0, 3000.0, LocalDate.now(), Operation.BUY);
    assertEquals("BUY", stock.getBuyOrSell().toString());
  }

  @Test
  public void testOperationSELL() {
    IstockModel stock = new Stock("MSFT", 6.0, 20.0, 3000.0, LocalDate.now(), Operation.SELL);
    assertEquals("SELL", stock.getBuyOrSell().toString());
  }
}