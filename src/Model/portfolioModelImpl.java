package Model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static java.time.LocalDate.now;

public class portfolioModelImpl implements portfolioModel{

  /**
   * shares contain ticker name as key and value contains number of units, date bought
   */
  static HashMap<String, List<String>> shares;
  List<String> portfolios;
  //user u = new user();

  /**
   * Contructor
   * @param
   */

  /*public portfolioModelImpl(HashMap<String, List<String>> tickers) {
    this.shares = tickers;
  }*/

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

  private List<String> showPortfolioNames(user u) {
    String[] files = u.retrieveFileNames();
    for (int i = 0; i < files.length; i++) {
      System.out.println(files[i]);
      this.portfolios.add(files[i]);
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
    if (!this.findPortfolio(portfolioName)) {
      throw new IllegalArgumentException("Portfolio doesn't exist!");
    }
    if (this.checkDate(date)) {
      throw new IllegalArgumentException("Can't get value for date greater than today");
    }
    // get list of stocks from portfolio


    return 0;
  }

  private boolean findPortfolio(String portfolioName) {
    if (!this.portfolios.contains(portfolioName)) {
      return false;
    }
    return true;
  }

  private boolean checkDate(Date date) {
    String today = LocalDate.now().toString();
    if (today.compareTo(date.toString()) < 0) {
      return true;
    }
    return false;
  }

  private List<String> getStocksFromPortfolio(String portfolioName) {
    return null;

  }

  public static void main(String args[]) {
    portfolioModelImpl p = new portfolioModelImpl();
    System.out.println(now());
    user u = new user();
    p.showPortfolioNames(u);
  }

}