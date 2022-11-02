package Model;

import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * The type Portfolio test.
 */
public class portfolioTest {

  /**
   * Test value of portfolio.
   */
  @Test
  public void testValueOfPortfolio() {
    List<IstockModel> stockList = new ArrayList<>();
    stock s = new stock("AAPL", 5, LocalDate.of(2022, 10, 26));
    stockList.add(s);
    s = new stock("MSFT", 5, LocalDate.of(2022, 10, 26));
    stockList.add(s);
    portfolioModel portfolio = new portfolio("Portfolio 1", stockList);
    assertEquals("1857.75", String.valueOf(portfolio.valueOfPortfolio(LocalDate.of(2022, 10, 27))));
  }

  /**
   * Test value of empty portfolio.
   */
  @Test
  public void testValueOfEmptyPortfolio() {
    List<IstockModel> stockList = new ArrayList<>();
    portfolioModel portfolio = new portfolio("Portfolio 1", stockList);
    assertEquals("0.0", String.valueOf(portfolio.valueOfPortfolio(LocalDate.of(2022, 10, 27))));
  }

  /**
   * Test get name of port folio.
   */
  @Test
  public void testGetNameOfPortFolio() {
    List<IstockModel> stockList = new ArrayList<>();
    portfolioModel portfolio = new portfolio("Portfolio 1", stockList);
    assertEquals("Portfolio 1", portfolio.getNameOfPortFolio());
  }

  /**
   * Create portfolio without a name throws an exception
   */
  @Test(expected = IllegalArgumentException.class)
  public void testGetNameOfPortFolioEmpty() {
    List<IstockModel> stockList = new ArrayList<>();
    portfolioModel portfolio = new portfolio("", stockList);
  }

  /**
   * Create portfolio without stocks
   */
  @Test(expected = IllegalArgumentException.class)
  public void testCreatePortfolioWithEmptyStocks() {
    List<IstockModel> stockList = new ArrayList<>();
    portfolioModel portfolio = new portfolio("", stockList);
  }

  /**
   * Test to list of string.
   */
  @Test
  public void testToListOfString() {
    List<IstockModel> stockList = new ArrayList<>();
    stock s = new stock("AAPL", 5, LocalDate.of(2022, 10, 26));
    stockList.add(s);
    s = new stock("MSFT", 25, LocalDate.of(2022, 10, 26));
    stockList.add(s);
    portfolioModel portfolio = new portfolio("Portfolio 1", stockList);

    List<String[]> expectedList = new ArrayList<>();
    String[] stock = {"AAPL", "5", "2022-10-26"};
    expectedList.add(stock);
    String[] stock2 = {"MSFT", "25", "2022-10-26"};
    expectedList.add(stock2);

    assertEquals("AAPL", portfolio.toListOfString().get(0)[0]);
    assertEquals("5", portfolio.toListOfString().get(0)[1]);
    assertEquals("2022-10-26", portfolio.toListOfString().get(0)[2]);
    assertEquals("MSFT", portfolio.toListOfString().get(1)[0]);
    assertEquals("25", portfolio.toListOfString().get(1)[1]);
    assertEquals("2022-10-26", portfolio.toListOfString().get(1)[2]);
  }

}