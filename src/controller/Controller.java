package controller;

import java.time.LocalDate;

/**
 * Interface for the controller of the stockmarket application.
 */
public interface Controller {

  void goController();

//  /**
//   * Method to show the menu on the view. Accepts input from the user for the selected menu option.
//   * Also validates this user input.
//   *@return int = option selected by the user.
//   */
//  int showMenuOnView();
//
//  /**
//   * Takes the ticker name and num of units bought as the input from the user and validates it.
//   * @return string[] = [tickername, numOfUnits]
//   */
//  String[] takeStockInputFromView();
//
//  /**
//   * Checks if the user wants to add more stocks.
//   * @return boolean = user's choice (yes/no) for adding the stocks.
//   */
//  boolean addMoreStocksFromView();
//
//  /**
//   * Asks the user to input a name for the portfolio.
//   * Validates the name against already existing portfolios.
//   * @return user input
//   */
//  String getPortFolioNameFromView();
//
//  /**
//   * Whenever there is a list of portfolios displayed, this method will return the portfolio which
//   * the user selects.
//   * @return the selected port folio by user.
//   */
//  int getSelectedPortFolioFromView();
//
//  /**
//   * Method to get the date input from user and validate it.
//   * @return the date input.
//   */
//  LocalDate getDateFromView();
//
//  /**
//   * Main driver method of the controller.
//   */
//  void goController();
//
//  /**
//   * Main controller method to display all the portfolios.
//   * @return
//   */
//  boolean retrievePortFolios();

}
