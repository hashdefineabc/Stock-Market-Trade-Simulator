package model;

/**
 * The enum Portfolio type indicated if the portfolio is of type fixed or flexible.
 * Fixed portfolios do not have the operation to buy or sell stocks once it has been created.
 * Flexible portfolios have the option to buy or sell stocks even after the portfolio has been
 * created.
 */
public enum PortfolioType {
  /**
   * Fixed portfolio type.
   */
  fixed,
  /**
   * Flexible portfolio type.
   */
  flexible
}
