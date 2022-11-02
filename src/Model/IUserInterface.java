package Model;

import java.time.LocalDate;
import java.util.List;

public interface IUserInterface {
  void loadExistingPortFolios();

  void createPortFolioFromFile();

  /**
    creates a new portfolio
   */
  void CreateNewPortfolio(portfolioModel newPortfolio);

  List<String> getPortfolioNamesCreated();

  List<portfolioModel> getPortfoliosCreated_Objects();

  Boolean checkIfFileExists(String fileName);

  void savePortfolioToFile(portfolioModel newPortfolio);

  boolean isTickerValid(String tickerNameFromUser);

  String getFolderPath();

  List<String[]> displayStocksOfPortFolio(int portfolioIndex);

  double calculateValueOfPortfolio(int portfolioIndex, LocalDate date);

  boolean createPortfolioManually(String portfolioName, List<String[]> stockList);
}
