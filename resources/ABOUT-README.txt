# Stock Market Application

#About
Java application for managing stocks easily.
This application has a text user interface. User will be provided with suitable options for managing their stock market profile.

#Operations
Following are the operations supported by this application:

1) Creating  a portfolio: 
- The user of this application can create a portfolio consisting of different stocks purchased by them. 
- No restriction on the number of portfolios created by a user. 
- These portfolios will be saved by the application in the folder 'PortFolioComposition' in the user's system.
- Currently this application supports 2 ways of creating a portfolio:
		1.1) Creating a portfolio by entering the details of every stock 
			- User can input the ticker names and the num of units they purchased for this stock.
			- All these stocks will be added to a portfolio named by the user.
			- Ticker names should be the ones supported by this application and num of units need to be positive whole numbers.
		
		1.2) Uploading a file of stocks
			- User needs to upload a .csv file containing the stockDetails [tickerName, num of Units, Date at which purchased].
			- The application will now keep track of this file as a new portfolio added by the user.

2) Displaying all the portfolios:
- The application will display a list of all the portfolios created till now.
- User can select the index of the portfolio they wish to see.
- All the stocks listed in that portfolio will be displayed on the UI.

3)Calculating the value of a portfolio:
- The application can calculate the value of a portfolio. The user needs to specify the date for which this value needs to be calculated. 
- Based on the stock's closing price for that day and the num of units of that stock the user holds, the value will be calculated.


#Things to remember while using this application
1)Currently only the top 25 stocks listed on NASDAQ are supported as valid ticker names. In the future, all the NASDAQ listed stocks will be supported.
2)If the user wants to calculate the value of a portfolio for a holiday/day when stock exchange was closed, the value returned in such cases will be 0.
3)Number of stocks purchased need to be positive whole numbers.
4)Data for all the stocks can be fetched by this application only until 6/13/2022.