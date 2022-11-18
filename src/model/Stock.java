package model;

import java.time.LocalDate;

/**
 * Stock class implements IstockModel interface.
 * It contains all the data related a stock.
 * Each stock will contain tickerName, the number of units, the date of transaction, the commission
 * value (for flexible portfolios), the transaction price, and whether the stock is bought or sold.
 * In our application we support NASDAQ top 50 stocks. So, only these 50 stocks will be available
 * for purchasing or selling.
 * The number of units of a particular stock is always integral, it cannot be fractional.
 * If user provides fractional stocks, then our application throws an error.
 * The transaction date for buying or selling a stock has to be no later than today's date.
 * Commission value is specific to flexible portfolios.
 * Each buy or sell transaction for a flexible portfolio needs a commission value.
 * We take commission value in dollars.
 * TransactionPrice is the price at which a stock is bought or sold.
 * We get this data from the stock files. We update the stock files if the stock details doesn't
 * exists in our stored file for the transaction date.
 * The operation buyOrSell corresponds to weather the particular transaction is for buying a stock
 * or selling a stock. This is always set to Operation.BUY for fixed portfolios.
 */
public class Stock implements IstockModel {

  private final String tickerName;
  private final Double numOfUnits;
  private final Double commission;
  private final Double transactionPrice;
  private final LocalDate transactionDate;
  private final Operation buyOrSell;

  /**
   * <p>
   * We use the builder method.
   * It maintains copies of all the fields that would be required to build the stock object.
   * It offers methods to edit each field individually,
   * but maintains default values for all of them.
   * A user would have to choose to call as few or as many of these methods to
   * set the values of these fields.
   * In the end the user can call a method in the builder that would take the set fields,
   * create the stock object and return it.
   *</p>
   * <p>
   * This method is particularly helpful in our application, because we have two types of stocks,
   * one for fixed portfolios and the other for flexible portfolios.
   * We don't require the fields like commission, buyOrSell for fixed portfolios, hence
   * we set the default values for these fields for fixed portfolios using this builder method.
   *</p>
   * @return the builder
   */
  public static StockBuilder getBuilder() {
    return new StockBuilder();
  }

  /**
   * Instantiates a new Stock.
   * It takes in tickerName, num of units, commission value, the transaction price, the transaction
   * date and whether the stock was bought or sold.
   * The commission value is always set to 0 for fixed portfolios.
   * The buyOrSell is set to Operation.BUY for fixed portfolios as we don't support selling of a
   * stock for fixed portfolios.
   *
   * @param tickerName       the ticker name
   * @param numOfUnits       the num of units
   * @param commission       the commission
   * @param transactionPrice the transaction price
   * @param transactionDate  the transaction date
   * @param buyOrSell        the buy or sell
   */
  public Stock(String tickerName, Double numOfUnits, Double commission, Double transactionPrice,
                  LocalDate transactionDate, Operation buyOrSell) {
    this.tickerName = tickerName;
    this.numOfUnits = numOfUnits;
    this.commission = commission;
    this.transactionPrice = transactionPrice;
    this.transactionDate = transactionDate;
    this.buyOrSell = buyOrSell;
  }

  @Override
  public String getTickerName() {
    return this.tickerName;
  }

  @Override
  public Double getNumOfUnits() {
    return this.numOfUnits;
  }

  @Override
  public LocalDate getTransactionDate() {
    return this.transactionDate;
  }

  @Override
  public Double getCommission() {
    return this.commission;
  }

  @Override
  public Double getTransactionPrice() {
    return this.transactionPrice;
  }

  @Override
  public Operation getBuyOrSell() {
    return this.buyOrSell;
  }

  /**
   * <p>
   * We use the builder method.
   * It maintains copies of all the fields that would be required to build the stock object.
   * It offers methods to edit each field individually,
   * but maintains default values for all of them.
   * A user would have to choose to call as few or as many of these methods to
   * set the values of these fields.
   * In the end the user can call a method in the builder that would take the set fields,
   * create the stock object and return it.
   *</p>
   * <p>
   * This method is particularly helpful in our application, because we have two types of stocks,
   * one for fixed portfolios and the other for flexible portfolios.
   * We don't require the fields like commission, buyOrSell for fixed portfolios, hence
   * we set the default values for these fields for fixed portfolios using this builder method.
   * </p>
   */
  public static class StockBuilder {

    private String tickerName;
    private Double numOfUnits;
    private LocalDate transactionDate;
    private Double commission;
    private Double transactionPrice;
    private Operation buyOrSell;



    private StockBuilder() {
      tickerName = "";
      numOfUnits = 0.0;
      transactionDate = LocalDate.now();
      commission = 0.0;
      transactionPrice = 0.0;
      buyOrSell = Operation.BUY;
    }

    /**
     * Sets the Ticker name of the stock.
     *
     * @param tickerName the ticker name
     * @return the stock builder
     */
    public StockBuilder tickerName(String tickerName) {
      this.tickerName = tickerName;
      return this;
    }

    /**
     * Num of units stock builder.
     *
     * @param units the units
     * @return the stock builder
     */
    public StockBuilder numOfUnits(Double units) {
      this.numOfUnits = units;
      return this;
    }

    /**
     * Transaction date stock builder.
     *
     * @param date the date
     * @return the stock builder
     */
    public StockBuilder transactionDate(LocalDate date) {
      this.transactionDate = date;
      return this;
    }

    /**
     * Transaction price stock builder.
     *
     * @param buyingPrice the buying price
     * @return the stock builder
     */
    public StockBuilder transactionPrice(Double buyingPrice) {
      this.transactionPrice = buyingPrice;
      return this;
    }

    /**
     * Commission stock builder.
     *
     * @param commission the commission
     * @return the stock builder
     */
    public StockBuilder commission(Double commission) {
      this.commission = commission;
      return this;
    }

    /**
     * Buy or sell stock builder.
     *
     * @param buyOrSell the buy or sell
     * @return the stock builder
     */
    public StockBuilder buyOrSell(Operation buyOrSell) {
      this.buyOrSell = buyOrSell;
      return this;
    }


    /**
     * Build stock.
     *
     * @return the stock
     */
    public Stock build() {
      //use the currently set values to create the stock object
      return new Stock(tickerName, numOfUnits, commission, transactionPrice,
              transactionDate, buyOrSell);
    }

  }
}