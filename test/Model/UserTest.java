package Model;


import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class UserTest {


  @Test
  public void testStocks() {
    IstockModel stock = new stock("AAPL", 5, LocalDate.now());
//    assertEquals();
  }

  @Test
  public void testIsTickerValid() {
    IUserInterface user = new User("new user");
    List<IstockModel> stockList = new ArrayList<>();
    stock s = new stock("AAPL", 5, LocalDate.of(2022, 10, 26));
    stockList.add(s);
    s = new stock("MSFT", 5, LocalDate.of(2022, 10, 26));
    stockList.add(s);
    portfolioModel portfolio = new portfolio("Portfolio 1", stockList);

    user.CreateNewPortfolio(portfolio);
    assertEquals("true", user.isTickerValid("AAPL"));
  }

  public void testGetFolderPath() {
  }

  public void testLoadExistingPortFolios() {
  }

  public void testCreatePortFolioFromFile() {
  }

  public void testCreateNewPortfolio() {
  }

  public void testGetPortfoliosCreated() {
  }

  public void testCheckIfFileExists() {
  }

  public void testSavePortfolioToFile() {
  }

  public void testGetportfoliosList() {
  }
}