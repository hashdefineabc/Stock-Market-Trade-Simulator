package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JCheckBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

/**
 *The DCAGuiView represents the invest using DCA feature of the application.
 * DCA will take inputs weightage for all the stocks.
 * It extends the JFrame class in order to design the buttons for the create operation.
 *This lets the user create a new portfolio by entering the details of it.
 * All the buttons and textfields necessary to take input from the user are implemented here.
 */

public class DCAGuiView extends JFrame {
  private JComboBox portfolioNameComboBox;
  int selectedPortfolioIndex;
  private JTextField amountTextField;
  private JTextField commissionTextField;
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
  private JCheckBox infiniteDate;
  private Boolean infiniteDateEnabled = false;
  TickerWeightPanel tw1;
  TickerWeightPanel tw2;
  TickerWeightPanel tw3;
  TickerWeightPanel tw4;
  TickerWeightPanel tw5;

  /**
   * Creates a new  instance of 'DCAGuiView' class
   * @param title = the title we want this frame to display
   */

  public DCAGuiView(String title) {
    super(title);
    this.setLayout(new BorderLayout());

    // pick portfolio panel
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


    // amount panel

    amountPanel = new JPanel();
    JLabel amountLabel = new JLabel("Enter the amount to invest in USD");
    amountTextField = new JTextField(15);
    amountPanel.add(amountLabel);
    amountPanel.add(amountTextField);

    // commission panel

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

    //start date panel

    JPanel startDatePanel = new JPanel();
    JLabel dateOfTransactionLabel = new JLabel("Investment Start Date: ");
    JLabel yearLabel = new JLabel("Year:");
    JLabel monthLabel = new JLabel("Month:");
    JLabel dateLabel = new JLabel("Date:");

    //handle month
    String[] months = {"01", "02", "03", "04", "05", "06", "07", "08",
                      "09", "10", "11", "12"};
    monthComboBox = new JComboBox(months);
    monthComboBox.addActionListener(listener);

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
    endMonthComboBox.addActionListener(listener);

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

    //infinite date checkbox

    infiniteDate = new JCheckBox("No End Date / ", false);
    infiniteDate.addItemListener(new ItemListener() {
      public void itemStateChanged(ItemEvent e) {
        e.getStateChange();
        if(e.getStateChange()==1) { //checked
          endMonthComboBox.setEnabled(false);
          endDateComboBox.setEnabled(false);
          endYearComboBox.setEnabled(false);
          infiniteDateEnabled = true;
        }
        else {
          endMonthComboBox.setEnabled(true);
          endDateComboBox.setEnabled(true);
          endYearComboBox.setEnabled(true);
          infiniteDateEnabled = false;
        }
      }
    });

    endYearComboBox = new JComboBox(endYears);

    endDatePanel.add(endDateOfTransactionLabel);
    endDatePanel.add(infiniteDate);
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

  /**
   * updating the existing portfolios as per the strategies.
   * @param existingPortfolios = list of existing portfolios.
   */
  public void updateExistingPortfoliosList(List<String> existingPortfolios) {
    DefaultComboBoxModel tempComboBox = new DefaultComboBoxModel();
    for(String portfolio: existingPortfolios) {
      tempComboBox.addElement(portfolio);
    }

    this.portfolioNameComboBox.setModel(tempComboBox);
  }

  /**
   * gettting the user input for the selected portfolio.
   * @return = user choice
   */
  public int getSelectedPortfolioIndex() {
    return selectedPortfolioIndex;
  }

  /**
   * getting the input from the user for the stratgey.
   * @return the user strategy array.
   */
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

    if(infiniteDateEnabled) {
      endDate = null;
    }

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

  /**
   * letting the user know that theere was some error in the strategy
   * @param message = msg to be displayed to the user.
   */
  public void setWarning(String message) {
    JOptionPane.showMessageDialog(DCAGuiView.this, message, "Warning", JOptionPane.WARNING_MESSAGE);
  }

  /**
   * letting the user know that the strategy was saved sucessfully.
   * @param s = msg to be displayed to the user.
   */
  public void setSuccessMsg(String s) {
    JOptionPane.showMessageDialog(DCAGuiView.this, s, "Success", JOptionPane.INFORMATION_MESSAGE);
  }

  /**
   * clearing all the fields after the user has input the strategy.
   */
  public void clear() {
    amountTextField.setText("");
    commissionTextField.setText("");
    frequencyTextField.setText("");
    tw1.clear();
    tw2.clear();
    tw3.clear();
    tw4.clear();
    tw5.clear();
  }
}
