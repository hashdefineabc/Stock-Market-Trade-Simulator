package model;

/**
 * The enum InvestmentType indicates whether the investment has to be made by using the weights 
 * assigned to each portfolio or using dollar cost averaging.
 * This InvestmentType is set everytime an investment strategy is accepted from the user.
 */
public enum InvestmentType {
  /**
   * Invest in a portfolio using the weightage assigned to each stock.
   */
  InvestByWeights,
  /**
   * Invest in a portfolio using dollar cost averaging.
   */
  DCA,
}
