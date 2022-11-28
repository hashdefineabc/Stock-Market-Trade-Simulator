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
 * Details of each command are kept in separate classes,
 * instead of all appearing within the controller.
 */
public interface IController {
  void go();
}
