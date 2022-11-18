#Design changes

1. Redesign portfolio model
- We have redesigned the portfolio model of our application to incorporate new features.
- We have divided the portfolio model into two separate models - fixed portfolio model and flexible portfolio model.
- Both fixed and flexible portfolio models have interface and implementation.
- AbstractFixedPortfolio class contains the implementation of the features that are common to both fixed and flexible portfolios.
- FixedPortfolio class extends the AbstractFixedPortfolio class.
- FlexiblePortfolio class also extends the AbstractFixedPortfolio class. It also contains implementations of the features that are only specific to flexible portfolios.

We made this design change to implement the concept of code reuse through which we didn't have to reimplement the common features for the new flexible portfolios.

2. Redesign controller
- As the number of features increased, our controller started expanding by the number of switch cases.
- To handle this increasing switch cases and the length of code inside each switch case in our controller, we used the command design pattern.
- It takes one word commands, like Create, BuySell, Composition, CostBasis, DisplayChart, Value, and calls the appropriate class based on the command.
- Details of each command are kept in separate classes, instead of all appearing within the controller.



#Design of the application

-This application has been built using the MVC(Model View Controller) design pattern.

1) Model: The User Class here is the main model for this application.
          It has two other helper models
          - Portfolio
          - Stocks
          A User can have multiple portfolios which in turn can have multiple stocks.
          All the major operations such as createPortFolio, retrievePortfolio have been implemented
          in the User class, since its the only model class that interacts with the controller.

2) Controller: The controller delegates the tasks between model and view.
               It will take the input from the user via text interface, validate it and then pass
               the control to model as per the operation to be done.

3) View: The user interface of this application acts as the view.
         It displays the messages/output to the user as per the controller's delegation.