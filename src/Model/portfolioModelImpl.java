package Model;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class portfolioModelImpl implements portfolioModel{

  /**
   * shares contain ticker name as key and value contains number of units, date bought
   */
  static HashMap<String, List<String>> shares;
  user u = new user();

  /**
   * Contructor
   * @param
   */

  public portfolioModelImpl(HashMap<String, List<String>> tickers) {
    this.shares = tickers;
  }

  // get high, low, price at open time, price at close time for each ticker on today's date
//  String[][] getPortfolioCompostion() {
//    String[][] ans = new String[2][3];
//
//    for(String ticker : shares.keySet()) {
//      AlphaVantageDemo database = new AlphaVantageDemo(ticker);
//    }
//    return ans;
//  }

  /**
   * Provides the composition of a portfolio.
   *
   * @param portfolioName
   * @return
   */
  @Override
  public List<List<String>> getPortfolio(String portfolioName) {


    return null;
  }

  /**
   * Returns the list of portfolios of a particular user
   *
   * @return
   */
  @Override
  public List<String> showPortfolioNames() {
    this.u.createFolder();



    String[] files = this.u.retrieveFileNames();
    for (int i = 0; i < files.length; i++) {
      System.out.println(files[i]);
    }
    return null;
  }

  /**
   * @param portfolioName
   * @param date
   * @return
   * @throws IllegalArgumentException
   */
  @Override
  public double valueOfPortfolio(String portfolioName, Date date) throws IllegalArgumentException {
    return 0;
  }

  public static void main(String args[]) {
    portfolioModelImpl p = new portfolioModelImpl();
    p.showPortfolioNames();
  }

}