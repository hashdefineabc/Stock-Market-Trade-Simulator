package controller;

/**
 * This is the interface for our controller class.
 * It uses command design pattern which promotes delegation
 * The commands supported in our application are as follows:
 * 1. Create a new portfolio
 * 2. Display composition of a portfolio
 * 3. Check value of a portfolio on a particular date
 * 4. Option to buy or sell stocks for flexible portfolios
 * 5. View the cost basis of a portfolio
 * 6. View the performance of a portfolio
 *
 * Details of each command are kept in separate classes,
 * instead of all appearing within the controller.
 */
public interface ICommandController {

  /**
   * This is the driver method of our application.
   * It delegates the commands to their respective classes and methods.
   * It initially asks view to display the list of commands that are supported.
   * Based on the user input , it delegates the commands to respective classes.
   * This method is a driver of our application.
   *    * It delegates the commands to their respective classes and methods.
   *    * It initially asks view to display the list of commands that are supported.
   *    * Based on the user input, it delegates the commands to respective classes.
   */
  void go();
}
