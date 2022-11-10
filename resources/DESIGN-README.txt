#Design of the application
-This application has been built using the MVC(Model View Controller) design pattern.
1) Model: The User Class here is the main model for this application.
          It has two other helper classes
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