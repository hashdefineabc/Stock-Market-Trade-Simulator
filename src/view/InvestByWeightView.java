package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.*;

public class InvestByWeightView extends JFrame {

  private JLabel portfolioNameLabel;
  private JComboBox portfolioNameComboBox;
  int selectedPortfolioIndex;
  private JLabel amountLabel;
  private JTextField amountTextField;
  private JLabel commissionLabel;
  private JTextField commissionTextField;
  private JLabel dateOfTransactionLabel;
  private JComboBox monthComboBox;
  private JComboBox dateComboBox;
  private JComboBox yearComboBox;
  private JButton cancel;

  private JButton done;
  private JPanel buttonPanel;
  private JPanel amountPanel;
  TickerWeightPanel tw1;
  TickerWeightPanel tw2;
  TickerWeightPanel tw3;
  TickerWeightPanel tw4;
  TickerWeightPanel tw5;

  public InvestByWeightView(String title) {
    super(title);
    this.setLayout(new BorderLayout());

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

    buttonPanel = new JPanel();

    tw1 = new TickerWeightPanel();
    tw2 = new TickerWeightPanel();
    tw3 = new TickerWeightPanel();
    tw4 = new TickerWeightPanel();
    tw5 = new TickerWeightPanel();


    JPanel investWeightsWholePanel = new JPanel();
    investWeightsWholePanel.setLayout(new GridLayout(9,1));
    investWeightsWholePanel.add(portfolioNamePanel);
    investWeightsWholePanel.add(amountPanel);
    investWeightsWholePanel.add(commissionPanel);
    investWeightsWholePanel.add(datePanel);
    investWeightsWholePanel.add(tw1);
    investWeightsWholePanel.add(tw2);
    investWeightsWholePanel.add(tw3);
    investWeightsWholePanel.add(tw4);
    investWeightsWholePanel.add(tw5);

    done = new JButton("Done");
    done.setActionCommand("doneFromInvestWeights");

    cancel = new JButton("Cancel");
    cancel.setActionCommand("cancelFromInvestWeights");

    buttonPanel.add(done);
    buttonPanel.add(cancel);


    this.add(investWeightsWholePanel, BorderLayout.CENTER);
    this.add(buttonPanel, BorderLayout.PAGE_END);

    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    this.setVisible(true);
    this.pack();
  }

  public void addActionListener(ActionListener listener) {
    cancel.addActionListener(listener);
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
    String[] input = new String[13];

    input[0] = amountTextField.getText();
    input[1] = commissionTextField.getText();

    String date = yearComboBox.getSelectedItem().toString() + "-" +
            monthComboBox.getSelectedItem().toString() + "-"+
            dateComboBox.getSelectedItem().toString();

    input[2] = date;
    input[3] = tw1.getTickerNameTextField();
    input[4] = tw1.getWeightTextField();


    input[5] = tw2.getTickerNameTextField();
    input[6] = tw2.getWeightTextField();

    input[7] = tw3.getTickerNameTextField();
    input[8] = tw3.getWeightTextField();


    input[9] = tw4.getTickerNameTextField();
    input[10] = tw4.getWeightTextField();


    input[11] = tw5.getTickerNameTextField();
    input[12] = tw5.getWeightTextField();

    return input;
  }

  public void clear() {
    amountTextField.setText("");
    commissionTextField.setText("");
    tw1.clear();
    tw2.clear();
    tw3.clear();
    tw4.clear();
    tw5.clear();
  }

  public void setWarning(String message) {
    JOptionPane.showMessageDialog(InvestByWeightView.this, message, "Warning", JOptionPane.WARNING_MESSAGE);
  }

  public void setSuccessMsg(String s) {
    JOptionPane.showMessageDialog(InvestByWeightView.this, s, "Success", JOptionPane.INFORMATION_MESSAGE);
  }
}
