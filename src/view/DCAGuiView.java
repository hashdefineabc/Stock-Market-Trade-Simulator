package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.*;

public class DCAGuiView extends JFrame {
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
  private JComboBox endMonthComboBox;
  private JComboBox endDateComboBox;
  private JComboBox endYearComboBox;
  private JTextField frequencyTextField;
  TickerWeightPanel tw1;
  TickerWeightPanel tw2;
  TickerWeightPanel tw3;
  TickerWeightPanel tw4;
  TickerWeightPanel tw5;

  public DCAGuiView(String title) {
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

    //start date panel

    JPanel startDatePanel = new JPanel();
    dateOfTransactionLabel = new JLabel("Investment Start Date: ");
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

    startDatePanel.add(dateOfTransactionLabel);
    startDatePanel.add(monthLabel);
    startDatePanel.add(monthComboBox);
    startDatePanel.add(dateLabel);
    startDatePanel.add(dateComboBox);
    startDatePanel.add(yearLabel);
    startDatePanel.add(yearComboBox);

    //end date panel

    JPanel endDatePanel = new JPanel();
    JLabel endDateOfTransactionLabel = new JLabel("Investment End Date: ");
    JLabel endYearLabel = new JLabel("Year:");
    JLabel endMonthLabel = new JLabel("Month:");
    JLabel endDateLabel = new JLabel("Date:");

    //handle month
    String[] endMonths = {"01", "02", "03", "04", "05", "06", "07", "08",
            "09", "10", "11", "12"};
    endMonthComboBox = new JComboBox(endMonths);

    //handle date
    String[] endDates = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13"
            , "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27"
            , "28", "29", "30", "31"};
    endDateComboBox = new JComboBox(endDates);

    //handle year
    String[] endYears = new String[30];
    int k = 0;
    for (int i = 2022; i > 2006; i=i-1) {
      endYears[k] = Integer.toString(i);
      k = k+1;
    }
    endYearComboBox = new JComboBox(endYears);

    endDatePanel.add(endDateOfTransactionLabel);
    endDatePanel.add(endMonthLabel);
    endDatePanel.add(endMonthComboBox);
    endDatePanel.add(endDateLabel);
    endDatePanel.add(endDateComboBox);
    endDatePanel.add(endYearLabel);
    endDatePanel.add(endYearComboBox);

    // frequency panel

    JPanel frequencyPanel = new JPanel();
    JLabel frequencyLabel = new JLabel("Frequency of investment (in Days)");
    frequencyTextField = new JTextField(5);
    frequencyPanel.add(frequencyLabel);
    frequencyPanel.add(frequencyTextField);

    buttonPanel = new JPanel();

    tw1 = new TickerWeightPanel();
    tw2 = new TickerWeightPanel();
    tw3 = new TickerWeightPanel();
    tw4 = new TickerWeightPanel();
    tw5 = new TickerWeightPanel();


    JPanel investWeightsWholePanel = new JPanel();
    investWeightsWholePanel.setLayout(new GridLayout(11,1));
    investWeightsWholePanel.add(portfolioNamePanel);
    investWeightsWholePanel.add(amountPanel);
    investWeightsWholePanel.add(commissionPanel);
    investWeightsWholePanel.add(startDatePanel);
    investWeightsWholePanel.add(endDatePanel);
    investWeightsWholePanel.add(frequencyPanel);
    investWeightsWholePanel.add(tw1);
    investWeightsWholePanel.add(tw2);
    investWeightsWholePanel.add(tw3);
    investWeightsWholePanel.add(tw4);
    investWeightsWholePanel.add(tw5);

    done = new JButton("Done");
    done.setActionCommand("doneFromDCA");

    cancel = new JButton("Cancel");
    cancel.setActionCommand("cancelFromDCA");

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
    String[] input = new String[15];

    input[0] = amountTextField.getText();
    input[1] = commissionTextField.getText();

    String startDate = yearComboBox.getSelectedItem().toString() + "-" +
            monthComboBox.getSelectedItem().toString() + "-"+
            dateComboBox.getSelectedItem().toString();

    input[2] = startDate;


    String endDate = endYearComboBox.getSelectedItem().toString() + "-" +
            endMonthComboBox.getSelectedItem().toString() + "-"+
            endDateComboBox.getSelectedItem().toString();

    input[3] = endDate;
    input[4] = frequencyTextField.getText();
    input[5] = tw1.getTickerNameTextField();
    input[6] = tw1.getWeightTextField();

    input[7] = tw2.getTickerNameTextField();
    input[8] = tw2.getWeightTextField();

    input[9] = tw3.getTickerNameTextField();
    input[10] = tw3.getWeightTextField();

    input[11] = tw4.getTickerNameTextField();
    input[12] = tw4.getWeightTextField();

    input[13] = tw5.getTickerNameTextField();
    input[14] = tw5.getWeightTextField();

    return input;
  }

  public void setWarning(String message) {
    JOptionPane.showMessageDialog(DCAGuiView.this, message, "Warning", JOptionPane.WARNING_MESSAGE);
  }

  public void setSuccessMsg(String s) {
    JOptionPane.showMessageDialog(DCAGuiView.this, s, "Success", JOptionPane.INFORMATION_MESSAGE);
  }
}
