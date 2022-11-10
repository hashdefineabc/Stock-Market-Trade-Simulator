package model;


import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Class to check the user functionalities.
 */
public class UserTest {

  IUserInterface testUser;

  @Before
  public void setUp() {
    testUser = new User();
  }


  @Test
  public void testIsTickerValid() {
    assertEquals(true, testUser.isTickerValid("AAPL"));
  }

  @Test
  public void testIsTickerInvalid() {
    assertEquals(false, testUser.isTickerValid("zzz"));
  }

  @Test
  public void testGetPortfoliosCreated() {
    String expected = "[TestPortFolio1.csv, TestPortfolio2.csv]";
    assertEquals(expected, testUser.getPortfolioNamesCreated().toString());
  }

  @Test
  public void testCreatePortfolio() {
    List<String[]> stockList = new ArrayList<>();
    String[] s = new String[2];
    s[0] = "AAPL";
    s[1] = "5";
    s[2] = "2022-10-26";
    stockList.add(s);
    int previous = testUser.getPortfolioNamesCreated().size();
    testUser.createPortfolioManually("P1", stockList);
    assertEquals(previous + 1, testUser.getPortfolioNamesCreated().size());
  }

  @Test
  public void testSavePortFolio() {
    List<String[]> stockList = new ArrayList<>();
    String[] s = new String[2];
    s[0] = "GOOG";
    s[1] = "5";
    s[2] = "2022-11-02";
    stockList.add(s);
    testUser.createPortfolioManually("P2", stockList);
    assertTrue("true", testUser.checkIfFileExists("P2"));
  }


}