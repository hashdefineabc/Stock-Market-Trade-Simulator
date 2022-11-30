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
  public Double calculateCostBasisForFuture(LocalDate costBasisDate, InvestmentType investmentType,
                                          String investInstrFile) {

    Double addCostBasis = 0.0;
      Double amount = 0.0;
      Double commission = 0.0;
      Integer daysToInvest = 0;
      LocalDate lastTxnDate, startDate, endDate = null;
      try {
        BufferedReader br = new BufferedReader(new FileReader(investInstrFile));
        amount = Double.parseDouble(br.readLine().split(",")[1]);
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        startDate = LocalDate.parse(br.readLine().split(",")[1],dateFormat);
        endDate = LocalDate.parse(br.readLine().split(",")[1],dateFormat);
        daysToInvest = Integer.parseInt(br.readLine().split(",")[1]);
        commission = Double.parseDouble(br.readLine().split(",")[1]);
        lastTxnDate = LocalDate.parse(br.readLine().split(",")[1],dateFormat);

      } catch (IOException e) {
        throw new RuntimeException(e);
      }
      while((lastTxnDate.plusDays(daysToInvest)).isBefore(costBasisDate) ||
              (lastTxnDate.plusDays(daysToInvest)).isEqual(costBasisDate)) {
        addCostBasis += amount;
        addCostBasis += commission;

        if (investmentType.equals(InvestmentType.InvestByWeights)) {
          daysToInvest = 1;
        }
        lastTxnDate = lastTxnDate.plusDays(daysToInvest);
      }

    return addCostBasis;
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
