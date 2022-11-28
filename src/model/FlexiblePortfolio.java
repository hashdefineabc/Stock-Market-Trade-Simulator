package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The class for flexible portfolios implements the flexible portfolio interface and extends the
 * abstractFixedPortfolio class.
 * Some operations that are to be performed on flexible portfolios are already implemented for
 * fixed portfolios, hence we use the concept of code reuse to efficiently reuse the implementations
 * of the fixed portfolios.
 */
public class FlexiblePortfolio extends AbstractFixedPortfolio implements IFlexiblePortfolio {
  /**
   * Instantiates a new Flexible portfolio.
   *
   * @param portfolioName the portfolio name
   * @param stocks        the list stocks that are present in this flexible portfolio
   */
  public FlexiblePortfolio(String portfolioName, List<IstockModel> stocks) {
    super(portfolioName, stocks);
    this.portfolioType = "flexible";
  }

  @Override
  public void addOrSellStocks(IstockModel stockToAdd) {
    this.stocks.add(stockToAdd);

  }

  @Override
  public void executeInstructions(String instrFile){
    //read the file
    try{
      BufferedReader br = new BufferedReader(new FileReader(instrFile));
      Double amount = Double.parseDouble(br.readLine());
      DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
      LocalDate buyDate = LocalDate.parse(br.readLine(),dateFormat);
      HashMap<String, Double> weights = new HashMap<>();
      String line = "";
      String splitBy = ",";
      if (buyDate.isBefore(LocalDate.now()) || buyDate.isEqual(LocalDate.now())) {
        while ((line = br.readLine()) != null) {
         weights.put(line.split(splitBy)[0], Double.parseDouble(line.split(splitBy)[1]));
        }
      // buy stocks
        for(Map.Entry<String,Double> stockWeight: weights.entrySet()) {
          String stockName = stockWeight.getKey();
          Double moneyToInvest = amount / stockWeight.getValue();
          Double priceOfSingleShare = this.getStockValue(stockName,buyDate);
          Integer numSharesBought = (int) (moneyToInvest/priceOfSingleShare);
          Double numShares = Double.valueOf(numSharesBought);

          Stock newStock = Stock.getBuilder().tickerName(stockName)
                  .numOfUnits(numShares)
                  .transactionDate(buyDate)
                  .commission(10.0) //TODO:replace with commission value
                  .transactionPrice(priceOfSingleShare)
                  .buyOrSell(Operation.BUY).build();
          this.addOrSellStocks(newStock);
        }

        //rename the file to 'executed'
        File oldFile = new File(instrFile);
        File newFile = new File(instrFile.replace(".csv","executed.csv"));
        oldFile.renameTo(newFile);
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

  }

  private List<String[]> readCSVFromSystem(String filePath) {

    List<String[]> listOfStocks = new ArrayList<String[]>();
    String line = "";
    String splitBy = ",";

    try {
      BufferedReader br = new BufferedReader(new FileReader(filePath));
      while ((line = br.readLine()) != null) {
        listOfStocks.add(line.split(splitBy));
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return listOfStocks;
  }

}
