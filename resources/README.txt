# Stock Market Application

#About
Java application for managing stocks easily.
This application has a text user interface. User will be provided with suitable options for managing their stock market profile.

Two types of portfolios are supported in our application
1. Fixed portfolio
2. Flexible portfolio

1. Fixed portfolio is a basic portfolio that has operations like
    a) Creating a portfolio
    b) Examining the composition of the portfolio
    c) Determining the value of the portfolio
    d) Persisting the portfolio so that it can be saved and retrieved from the files.

   * User can create one or more portfolios with shares of one or more stock.
   * Once created, shares cannot be added or removed from the fixed portfolio.
   * The num of units to be purchased by a portfolio is always a positive whole number, it cannot be negative or fractional.

2. Flexible portfolio supports all the operations that are supported by fixed portfolios, it has additional features like
   * User can purchase a specific number of shares of a specific stock on a specified date, and add them to the portfolio
   * User can sell a specific number of shares of a specific stock on a specified date from a given portfolio
   * User can determine the cost basis that is the total amount of money invested in a portfolio by a specific date. This will include all the purchases made in that portfolio till that date.
   * User can determine the value of a portfolio on a specific date. The value for a portfolio before the date of its first purchase will be 0.
   * Each buy or sell transaction will have a commission fee.



#Operations
Following are the operations supported by this application:

1) Creating  a portfolio:
- User will be given an option to create either a fixed portfolio or a flexible portfolio.
- The user of this application can create a portfolio consisting of different stocks purchased by them.
- No restriction on the number of portfolios created by a user.
- These portfolios will be saved by the application in the folder 'PortFolioComposition' in the user's system.
- Currently this application supports 2 ways of creating a portfolio:
		1.1) Creating a portfolio by entering the details of every stock
			- User can input the ticker names and the num of units they purchased for this stock.
			- All these stocks will be added to a portfolio named by the user.
			- Ticker names should be the ones supported by this application and num of units need to be positive whole numbers.
			- This application supports NASDAQ 23 ticker names - they are as follows
			AAPL, MSFT, GOOG, GOOGL, AMZN, TSLA, UNH, XOM, JNJ, V, WMT, JPM, CVX, LLY, NVDA, TSM, PG, MA, HD, BAC, PFE, ABBV, KO

		1.2) Uploading a file of stocks
			- User needs to upload a .csv file containing the stockDetails [tickerName, num of Units, Date at which purchased].
			- The application will now keep track of this file as a new portfolio added by the user.

2) Displaying all the portfolios:
- User will be given an option view the composition of fixed or flexible portfolio
- The application will display a list of all the portfolios (either fixed or flexible as requested by the user) created till now.
- User can select the index of the portfolio they wish to view.
- All the stocks listed in that portfolio will be displayed on the UI.

3)Calculating the value of a portfolio:
- User will be given an option to view the total value of a particular fixed or flexible portfolio.
- The application can calculate the value of a portfolio. The user needs to specify the date for which this value needs to be calculated.
- Based on the stock's closing price for that day and the num of units of that stock the user holds, the value will be calculated.

4) CostBasis of a portfolio:
- User can view the cost basis, that is the total amount spent on a flexible portfolio by a specific date.
- User can view cost basis of flexible portfolios only.
- The cost basis includes the commission value of every transaction that is made until the specified date.
- Cost basis will include all the purchases made in that portfolio till that date.

5) BuySell
- This operation is available for flexible portfolios only.
- User can buy a specific number of shares of a specific stock on a specified date, and add them to the portfolio
- User can sell a specific number of shares of a specific stock on a specified date from a given portfolio
- User cannot sell a share before it is bought, and they cannot sell the stocks that don't exist.

6) View performance of a portfolio
- This operation is available for both fixed and flexible portfolios.
- User can visualize the performance of a portfolio over a period of the previous week, previous month and the previous year.
- The title of the chart specifies the name of the portfolio, and the time range.
- Each line of the chart starts with a timestamp followed by asterisks.
- The number of asterisks on each line is a measure of the value of the portfolio at that timestamp.
- If the timestamps are dates then the value is computed at the end of that day. If the timestamps are months then the value is computed at the last working day of that month.
- The end of the bar chart shows the scale in terms of how many dollars are represented by each asterisk.
- The scale of the horizontal axis is decided by the maximum value of the portfolios in the given time range.
- There are a maximum of 30 asterisks on each line.



#Things to remember while using this application
1)Currently only the top 25 stocks listed on NASDAQ are supported as valid ticker names. In the future, all the NASDAQ listed stocks will be supported.
2)If the user wants to calculate the value of a portfolio for a holiday/day when stock exchange was closed, the value returned in such cases will be 0.
3)Number of stocks purchased need to be positive whole numbers.