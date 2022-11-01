# PDPAssignment4Stocks

ToDo

1. Decide the file format in which we want to store the portfolio and explain in the documentation, stating how we are saving it, and why we choose this file format
2. Save the portfolio everytime the user creates it and let the user know that it is saved.
3. For the case where user can upload a file to load the portfolio, take file-path as user input and process accordingly
4. If the date provided is Sunday.
5. We can use builder design method.


TA meet
1. Users - we are supporting only one user for now, but TA said - make it not tightly coupled so use can modify to support multiple users. (we just need to have another level that contains list of users and that would be our main model that interacts with the controller)
2. api calls - currently we are storing 10 stocks's data and getting value from the locally stored data, but use file management to store data of all stocks and think about how you'll update it on a timely fashion.
