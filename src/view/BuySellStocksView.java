package view;

import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.DefaultComboBoxModel;

/**
 *The BuySellStocksView shows the user the way to buy/sell stocks from  a portfolio.
 * It extends the JFrame class in order to design the buttons for the create operation.
 * All the buttons and textfields necessary to take input from the user are implemented here.
 */

public class BuySellStocksView extends JFrame {

  private JTextField tickerNameTextField;
  private JTextField numUnitsTextField;
  private JTextField commissionTextField;
  private JComboBox portfolioNameComboBox;
  private JComboBox monthComboBox;
  private JComboBox dateComboBox;
  private JComboBox yearComboBox;
  private JButton save;
  private JButton cancel;
  private JLabel popUpMsg;
  private int selectedPortfolioIndex;

  private Boolean buyOrSell;


  public BuySellStocksView(String title) {
    super(title);
    popUpMsg = new JLabel("");
    JPanel popUpPanel = new JPanel();
    popUpPanel.add(popUpMsg);

    //Portfolio name panel
    JPanel portfolioNamePanel = new JPanel();
    JLabel portfolioNameLabel = new JLabel("Portfolio Name: ");

    portfolioNameComboBox = new JComboBox();
    portfolioNamePanel.add(portfolioNameLabel);
    portfolioNamePanel.add(portfolioNameComboBox);

    ActionListener actionListener = new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        selectedPortfolioIndex = portfolioNameComboBox.getSelectedIndex();
      }
    };
    portfolioNameComboBox.addActionListener(actionListener);

    //ticker Name panel

    JPanel tickerNamePanel = new JPanel();
    JLabel tickerNameLabel = new JLabel("Ticker Name: ");
    tickerNameTextField = new JTextField(15);
    tickerNamePanel.add(tickerNameLabel);
    tickerNamePanel.add(tickerNameTextField);

    // num of units panel

    JPanel numUnitsPanel = new JPanel();
    JLabel numUnitsLabel = new JLabel("Number of Units: ");
    numUnitsTextField = new JTextField(5);
    numUnitsPanel.add(numUnitsLabel);
    numUnitsPanel.add(numUnitsTextField);

    // commission value panel

    JPanel commissionPanel = new JPanel();
    JLabel commissionLabel = new JLabel("Commission Value (USD):");
    commissionTextField = new JTextField(5);
    commissionPanel.add(commissionLabel);
    commissionPanel.add(commissionTextField);


    ActionListener listener = new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        CalculateDate.setDate(monthComboBox, dateComboBox);
      }
    };

    //date of transaction panel

    JPanel datePanel = new JPanel();
    JLabel dateOfTransactionLabel = new JLabel("Date of Transaction: ");
    JLabel yearLabel = new JLabel("Year:");
    JLabel monthLabel = new JLabel("Month:");
    JLabel dateLabel = new JLabel("Date:");

    String[] dates = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13"
            , "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27"
            , "28", "29", "30", "31"};


    //handle month
    String[] months = {"01", "02", "03", "04", "05", "06", "07", "08",
                      "09", "10", "11", "12"};
    monthComboBox = new JComboBox(months);
    monthComboBox.addActionListener(listener);

    //handle date
    dateComboBox = new JComboBox(dates);

    //handle year
    String[] years = new String[30];
    int j = 0;
    for (int i = 2022; i > 2006; i = i - 1) {
      years[j] = Integer.toString(i);
      j = j + 1;
    }
    yearComboBox = new JComboBox(years);

    datePanel.add(dateOfTransactionLabel);
    datePanel.add(monthLabel);
    datePanel.add(monthComboBox);
    datePanel.add(dateLabel);
    datePanel.add(dateComboBox);
    datePanel.add(yearLabel);
    datePanel.add(yearComboBox);

    JPanel buttonPanel = new JPanel();
    save = new JButton("Save");
    save.setActionCommand("saveStock");

    cancel = new JButton("Cancel");
    cancel.setActionCommand("cancelFromBuy");

    buttonPanel.add(save);
    buttonPanel.add(cancel);

    JPanel buyStocksWholePanel = new JPanel();
    buyStocksWholePanel.setLayout(new GridLayout(5, 1));
    buyStocksWholePanel.add(portfolioNamePanel);
    buyStocksWholePanel.add(tickerNamePanel);
    buyStocksWholePanel.add(numUnitsPanel);
    buyStocksWholePanel.add(commissionPanel);
    buyStocksWholePanel.add(datePanel);

    this.add(buyStocksWholePanel, BorderLayout.PAGE_START);
    this.add(buttonPanel, BorderLayout.PAGE_END);

    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    this.setVisible(true);
    this.pack();
  }

  public String[] getInput() {
    String[] input = new String[4];
    input[0] = tickerNameTextField.getText();
    input[1] = numUnitsTextField.getText();
    input[3] = commissionTextField.getText();

    String date = yearComboBox.getSelectedItem().toString() + "-" +
            monthComboBox.getSelectedItem().toString() + "-" +
            dateComboBox.getSelectedItem().toString();

    input[2] = date;

    return input;
  }

  public void addActionListener(ActionListener listener) {
    save.addActionListener(listener);
    cancel.addActionListener(listener);
  }

  public void setPopUp(String message) {
    JOptionPane.showMessageDialog(BuySellStocksView.this, message, "Warning",
            JOptionPane.WARNING_MESSAGE);
    popUpMsg.setText(message);
  }

  public void displaySuccess(String message) {
    JOptionPane.showMessageDialog(BuySellStocksView.this, message, "Yayyyy",
            JOptionPane.INFORMATION_MESSAGE);
  }

  public void updateExistingPortfoliosList(List<String> existingPortfolios) {
    DefaultComboBoxModel tempComboBox = new DefaultComboBoxModel();
    for (String portfolio : existingPortfolios) {
      tempComboBox.addElement(portfolio);
    }

    this.portfolioNameComboBox.setModel(tempComboBox);

  }

  public void clear() {
    tickerNameTextField.setText("");
    numUnitsTextField.setText("");
    commissionTextField.setText("");
  }

  public int getSelectedPortfolioIndex() {
    return selectedPortfolioIndex;
  }

  public Boolean getBuyOrSell() {
    return buyOrSell;
  }

  public void setBuyOrSell(Boolean buyOrSell) {
    this.buyOrSell = buyOrSell;
  }
}
