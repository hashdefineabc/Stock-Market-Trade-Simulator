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
<<<<<<< HEAD:test/model/stockTest.java
    IstockModel stock = new Stock("MSFT", 6 , LocalDate.now());
=======
    IstockModel stock = new Stock("MSFT", 6, LocalDate.now());
>>>>>>> af18596172db03b6f2b5df49441c9f3cad7b7c47:test/Model/stockTest.java
    assertEquals("MSFT", stock.getTickerName());
  }

  @Test
  public void testGetNumOfUnits() {
<<<<<<< HEAD:test/model/stockTest.java
    IstockModel stock = new Stock("MSFT", 6 , LocalDate.now());
=======
    IstockModel stock = new Stock("MSFT", 6, LocalDate.now());
>>>>>>> af18596172db03b6f2b5df49441c9f3cad7b7c47:test/Model/stockTest.java
    assertEquals("6", stock.getNumOfUnits().toString());
  }

  @Test
  public void testGetDate() {
<<<<<<< HEAD:test/model/stockTest.java
    IstockModel stock = new Stock("MSFT", 6 , LocalDate.now());
=======
    IstockModel stock = new Stock("MSFT", 6, LocalDate.now());
>>>>>>> af18596172db03b6f2b5df49441c9f3cad7b7c47:test/Model/stockTest.java
    LocalDate curDate = LocalDate.now();
    String s = curDate.toString();
    assertEquals(s, stock.getDate().toString());
  }
}