package Model;


import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

public class stockTest{


  @Test
  public void testGetTickerName() {
    IstockModel stock = new stock("MSFT", 6 , LocalDate.now());
    assertEquals("MSFT", stock.getTickerName());
  }

  @Test
  public void testGetNumOfUnits() {
    IstockModel stock = new stock("MSFT", 6 , LocalDate.now());
    assertEquals("6", stock.getNumOfUnits().toString());
  }

  @Test
  public void testGetDate() {
  }
}