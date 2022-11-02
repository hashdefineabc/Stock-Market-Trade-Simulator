package Model;

import java.util.List;

public interface IUserInterface {
  void loadExistingPortFolios();

  void createPortFolioFromFile();

  /**
    creates a new portfolio
   */
  void CreateNewPortfolio(portfolio newPortfolio);

  List<portfolio> getPortfoliosCreated();

  Boolean checkIfFileExists(String fileName);

  void savePortfolioToFile(portfolio newPortfolio);
}
