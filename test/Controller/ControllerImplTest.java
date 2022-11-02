package Controller;

import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import Model.IUserInterface;
import Model.IstockModel;
import Model.User;
import Model.portfolio;
import Model.portfolioModel;
import Model.stock;

import static org.junit.Assert.assertEquals;

public class ControllerImplTest {

  class MockModel implements IUserInterface {

    private StringBuilder log;
    public MockModel(StringBuilder log) {
      this.log = log;
    }

    @Override
    public void loadExistingPortFolios() {

    }

    @Override
    public void createPortFolioFromFile() {

    }

    /**
     * creates a new portfolio
     *
     * @param newPortfolio
     */
    @Override
    public void CreateNewPortfolio(portfolioModel newPortfolio) {

    }

    @Override
    public List<String> getPortfolioNamesCreated() {
      return null;
    }

    @Override
    public List<portfolioModel> getPortfoliosCreated_Objects() {
      return null;
    }


    @Override
    public Boolean checkIfFileExists(String fileName) {
      log.append("fileName provided is : " + fileName);
      return null;
    }

    @Override
    public void savePortfolioToFile(portfolioModel newPortfolio) {

    }

    @Override
    public boolean isTickerValid(String tickerNameFromUser) {
      log.append("tickerName provided = "+tickerNameFromUser);
      return false;
    }

    @Override
    public String getFolderPath() {
      return null;
    }

    @Override
    public List<String[]> displayStocksOfPortFolio(int portfolioIndex) {
      return null;
    }

    @Override
    public double calculateValueOfPortfolio(int portfolioIndex, LocalDate date) {
      return 0;
    }

    @Override
    public boolean createPortfolioManually(String portfolioName, List<String[]> stockList) {
      return false;
    }
  }

  @Test
  public void testControllerUserModelConnection() {

    String fileName = "p1";

    StringBuilder log = new StringBuilder();
    IUserInterface user = new MockModel(log);
    String expectedResult = "fileName provided is : " + fileName;
    user.checkIfFileExists(fileName);
    assertEquals(expectedResult, log.toString());
  }

  @Test
  public void testTickerNameProvided() {
    String tickerName = "AAPL";

    StringBuilder log = new StringBuilder();
    IUserInterface user = new MockModel(log);
    String expectedResult = "tickerName provided = " + tickerName;
    user.isTickerValid(tickerName);
    assertEquals(expectedResult, log.toString());
  }
}
