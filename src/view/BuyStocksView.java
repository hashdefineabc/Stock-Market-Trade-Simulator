package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

public class BuyStocksView extends JFrame {

  private JTextField tickerNameTextField;
  private JTextField numUnitsTextField;
  private JTextField commissionTextField;
  private JComboBox monthComboBox;
  private JComboBox dateComboBox;
  private JComboBox yearComboBox;
  private JLabel status;
  private JButton buyStocks;
  private JButton createPortfolio;
  private JLabel popUpMsg;
  JPanel popUpPanel;


  public BuyStocksView(String title) {
    super(title);
    popUpMsg = new JLabel("");
    popUpPanel = new JPanel();
    popUpPanel.add(popUpMsg);

    JLabel portfolioNameLabel;
    JLabel tickerNameLabel;
    JLabel numUnitsLabel;
    JLabel commissionLabel;
    JLabel dateOfTransactionLabel;



    //ticker Name panel

    JPanel tickerNamePanel = new JPanel();
    tickerNameLabel = new JLabel("Ticker Name:");
    tickerNameTextField = new JTextField(15);
    tickerNamePanel.add(tickerNameLabel);
    tickerNamePanel.add(tickerNameTextField);

    // num of units panel

    JPanel numUnitsPanel = new JPanel();
    numUnitsLabel = new JLabel("Number of Units:");
    numUnitsTextField = new JTextField(5);
    numUnitsPanel.add(numUnitsLabel);
    numUnitsPanel.add(numUnitsTextField);

    // commission value panel

    JPanel commissionPanel = new JPanel();
    commissionLabel = new JLabel("Commission Value (USD):");
    commissionTextField = new JTextField(5);
    commissionPanel.add(commissionLabel);
    commissionPanel.add(commissionTextField);

    //date of transaction panel

    JPanel datePanel = new JPanel();
    dateOfTransactionLabel = new JLabel("Date of Transaction: ");
    JLabel yearLabel = new JLabel("Year:");
    JLabel monthLabel = new JLabel("Month:");
    JLabel dateLabel = new JLabel("Date:");

//    ActionListener listener = new ActionListener() {
//      @Override
//      public void actionPerformed(ActionEvent e) {
//        SetDay(yearComboBox, monthComboBox, dateComboBox);
//      }
//    };

    //handle month
    String[] months = {"01", "02", "03", "04", "05", "06", "07", "08",
            "09", "10", "11", "12"};
    monthComboBox = new JComboBox(months);

    //handle date
    String[] dates = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13"
            , "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27"
            , "28", "29", "30", "31"};
    dateComboBox = new JComboBox(dates);

    //handle year
    int currYear = LocalDateTime.now().getYear();
    String[] years = new String[currYear + 20 - 2000];
    int j = 0;
    for (int i = 2000; i < currYear + 20; i=i+1) {
      years[j] = Integer.toString(i);
      j = j+1;
    }
    yearComboBox = new JComboBox(years);

    datePanel.add(dateOfTransactionLabel);
    datePanel.add(monthLabel);
    datePanel.add(monthComboBox);
    datePanel.add(dateLabel);
    datePanel.add(dateComboBox);
    datePanel.add(yearLabel);
    datePanel.add(yearComboBox);


    JPanel statusPanel = new JPanel();
    status = new JLabel("");
    statusPanel.add(status);

    JPanel buttonPanel = new JPanel();
    buyStocks = new JButton("Buy More stocks");
    buyStocks.setActionCommand("buyStocks");

    createPortfolio = new JButton("Create Portfolio");
    createPortfolio.setActionCommand("createPortfolio");

    buttonPanel.add(buyStocks);
    buttonPanel.add(createPortfolio);

    JPanel buyStocksWholePanel = new JPanel();
    buyStocksWholePanel.setLayout(new GridLayout(4, 1));
    buyStocksWholePanel.add(tickerNamePanel);
    buyStocksWholePanel.add(numUnitsPanel);
    buyStocksWholePanel.add(commissionPanel);
    buyStocksWholePanel.add(datePanel);

    this.add(buyStocksWholePanel, BorderLayout.PAGE_START);
    this.add(statusPanel, BorderLayout.CENTER);
    this.add(buttonPanel, BorderLayout.PAGE_END);

    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
//    this.setPreferredSize(new Dimension(450, 500));
    this.setVisible(true);
    this.pack();
  }

  public String[] getInput() {
    String[] input = new String[4];
//    LocalDate dateSelected;
    String date = yearComboBox.getSelectedItem().toString() + "-" +
            monthComboBox.getSelectedItem().toString() + "-"+
            dateComboBox.getSelectedItem().toString();
//    dateSelected = LocalDate.parse(date);
    input[0] = tickerNameTextField.getText();

    input[1] = numUnitsTextField.getText();
    input[2] = date;
    input[3] = commissionTextField.getText();
    return input;
  }

  public void addActionListener(ActionListener listener) {
    buyStocks.addActionListener(listener);
    createPortfolio.addActionListener(listener);
  }

  public void setPopUp(String message) {
    popUpMsg.setText(message);
  }


  public void setStatus(String message) {
    status.setText(message);
  }
}
