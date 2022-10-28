package Model;

import java.util.Date;
import java.util.List;

public interface portfolioModel {
  /**
   * Provides the composition of a portfolio.
   * @return
   */
  public List<List<String>> getPortfolio(String portfolioName);


  // ToDo: Make it private
  /**
   * Returns the list of portfolios of a particular user
   * @return
   */
  public List<String> showPortfolioNames();

  /**
   *
   * @param portfolioName
   * @param date
   * @return
   * @throws IllegalArgumentException
   */

  public double valueOfPortfolio(String portfolioName, Date date) throws IllegalArgumentException;
}