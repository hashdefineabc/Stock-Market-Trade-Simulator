package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.*;
import javax.swing.plaf.basic.BasicSplitPaneUI;

import controller.GUIController;

public class InvestByWeightView extends JFrame {

  private JLabel portfolioNameLabel;
  private JComboBox portfolioNameComboBox;
  int selectedPortfolioIndex;
  private JLabel amountLabel;
  private JTextField amountTextField;
  private JLabel commissionLabel;
  private JTextField commissionTextField;
  private JLabel tickerNameLabel;
  private JTextField tickerNameTextField;
  private JLabel weightLabel;
  private JTextField weightTextField;
  private JLabel dateOfTransactionLabel;
  private JComboBox monthComboBox;
  private JComboBox dateComboBox;
  private JComboBox yearComboBox;
  private JButton addMoreWeightsButton;
  private JButton done;
  private JPanel tickerNamePanel;
  private JPanel weightPanel;
  private JPanel tickerWeightPanel;
  private JPanel buttonPanel;
  private JPanel amountPanel;

  public InvestByWeightView(String title) {
    super(title);

    // pick portfolio panel
    JPanel portfolioNamePanel = new JPanel();
    portfolioNameLabel = new JLabel("Portfolio Name: ");

    portfolioNameComboBox = new JComboBox();
    portfolioNamePanel.add(portfolioNameLabel);
    portfolioNamePanel.add(portfolioNameComboBox);

    ActionListener actionListener = new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
//        getSelectedPortfolio();
        selectedPortfolioIndex = portfolioNameComboBox.getSelectedIndex();
      }
    };
    portfolioNameComboBox.addActionListener(actionListener);


    // amount panel

    amountPanel = new JPanel();
    amountLabel = new JLabel("Enter the amount to invest in USD");
    amountTextField = new JTextField(15);
    amountPanel.add(amountLabel);
    amountPanel.add(amountTextField);

    // commission panel

    JPanel commissionPanel = new JPanel();
    commissionLabel = new JLabel("Commission Value (USD):");
    commissionTextField = new JTextField(5);
    commissionPanel.add(commissionLabel);
    commissionPanel.add(commissionTextField);

    //date panel

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
    String[] years = new String[30];
    int j = 0;
    for (int i = 2022; i > 2006; i=i-1) {
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

    //ticker Name panel

    tickerNamePanel = new JPanel();
    tickerNameLabel = new JLabel("Ticker Name: ");
    tickerNameTextField = new JTextField(15);
    tickerNamePanel.add(tickerNameLabel);
    tickerNamePanel.add(tickerNameTextField);

    //Weight for stock panel

    weightPanel = new JPanel();
    weightLabel = new JLabel("Weight for this stock (%) ");
    weightTextField = new JTextField(15);
    weightPanel.add(weightLabel);
    weightPanel.add(weightTextField);

    buttonPanel = new JPanel();
    addMoreWeightsButton = new JButton("Add More Weights");
    addMoreWeightsButton.setActionCommand("addWeights");

    //ticker-weight panel
    JPanel tickerWeightPanel = new JPanel();
    tickerWeightPanel.setLayout(new FlowLayout());
    tickerWeightPanel.add(tickerNamePanel);
    tickerWeightPanel.add(weightPanel);

    //ticker-weight panel
    JPanel tickerWeightPanel1 = new JPanel();
    tickerWeightPanel1.setLayout(new FlowLayout());
    tickerWeightPanel1.add(tickerNamePanel);
    tickerWeightPanel1.add(weightPanel);

    JPanel tickerWeightPanel2 = new JPanel();
    tickerWeightPanel2.setLayout(new FlowLayout());
    tickerWeightPanel2.add(tickerNamePanel);
    tickerWeightPanel2.add(weightPanel);

    JPanel tickerWeightPanel3 = new JPanel();
    tickerWeightPanel3.setLayout(new FlowLayout());
    tickerWeightPanel3.add(tickerNamePanel);
    tickerWeightPanel3.add(weightPanel);

    JPanel tickerWeightPanel4 = new JPanel();
    tickerWeightPanel4.setLayout(new FlowLayout());
    tickerWeightPanel4.add(tickerNamePanel);
    tickerWeightPanel4.add(weightPanel);

    JPanel tickerWeightPanel5 = new JPanel();
    tickerWeightPanel5.setLayout(new FlowLayout());
    tickerWeightPanel5.add(tickerNamePanel);
    tickerWeightPanel5.add(weightPanel);


    JPanel investWeightsWholePanel = new JPanel();
    investWeightsWholePanel.setLayout(new GridLayout(10,1));
    investWeightsWholePanel.add(portfolioNamePanel);
    investWeightsWholePanel.add(amountPanel);
    investWeightsWholePanel.add(commissionPanel);
    investWeightsWholePanel.add(datePanel);
//    investWeightsWholePanel.add(tickerWeightPanel);
    investWeightsWholePanel.add(tickerWeightPanel1);
    investWeightsWholePanel.add(tickerWeightPanel2);
    investWeightsWholePanel.add(tickerWeightPanel3);
    investWeightsWholePanel.add(tickerWeightPanel4);
    investWeightsWholePanel.add(tickerWeightPanel5);


    done = new JButton("Done");
    done.setActionCommand("doneFromInvestWeights");

    buttonPanel.add(done);


    this.add(investWeightsWholePanel, BorderLayout.PAGE_START);
    this.add(tickerWeightPanel, BorderLayout.CENTER);
    this.add(buttonPanel, BorderLayout.PAGE_END);

    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    this.setVisible(true);
    this.pack();
  }

  public void addActionListener(ActionListener listener) {
    addMoreWeightsButton.addActionListener(listener);
    done.addActionListener(listener);
  }

  public void updateExistingPortfoliosList(List<String> existingPortfolios) {
    DefaultComboBoxModel tempComboBox = new DefaultComboBoxModel();
    for(String portfolio: existingPortfolios) {
      tempComboBox.addElement(portfolio);
    }

    this.portfolioNameComboBox.setModel(tempComboBox);
  }

  public int getSelectedPortfolioIndex() {
    return selectedPortfolioIndex;
  }

  public String[] getInput() {
    String[] input = new String[4];

    input[0] = amountTextField.getText();
    input[1] = commissionTextField.getText();

    String date = yearComboBox.getSelectedItem().toString() + "-" +
            monthComboBox.getSelectedItem().toString() + "-"+
            dateComboBox.getSelectedItem().toString();

    input[2] = date;
    input[3] = tickerNameTextField.getText();
    input[4] = weightTextField.getText();

    return input;
  }
}
