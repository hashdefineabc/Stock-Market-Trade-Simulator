package Controller;

import org.junit.Test;

import java.util.List;

import Model.IUserInterface;
import Model.portfolioModel;

public class ControllerImplTest {

  class MockModel implements IUserInterface {

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
    public List<portfolioModel> getPortfoliosCreated() {
      return null;
    }

    @Override
    public Boolean checkIfFileExists(String fileName) {
      return null;
    }

    @Override
    public void savePortfolioToFile(portfolioModel newPortfolio) {

    }

    @Override
    public List<portfolioModel> getportfoliosList() {
      return null;
    }

    @Override
    public boolean isTickerValid(String tickerNameFromUser) {
      return false;
    }

    @Override
    public String getFolderPath() {
      return null;
    }
  }

  @Test
  public void testController() {
    IUserInterface user = new MockModel();
  }


}
