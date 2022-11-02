package Model;

import java.util.List;

public interface IUserInterface {
  void loadExistingPortFolios();

  void createPortFolioFromFile();

  /**
    creates a new portfolio
   */
  void CreateNewPortfolio(portfolioModel newPortfolio);

  List<portfolioModel> getPortfoliosCreated();

  Boolean checkIfFileExists(String fileName);

  void savePortfolioToFile(portfolioModel newPortfolio);

  List<portfolioModel> getportfoliosList();

  boolean isTickerValid(String tickerNameFromUser);

  String getFolderPath();
}
