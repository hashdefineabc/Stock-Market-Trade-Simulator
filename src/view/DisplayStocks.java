package view;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;

import model.IstockModel;

public class DisplayStocks extends JFrame {
  private JButton okButton;

  public DisplayStocks(List<IstockModel> stocksToDisplay) {
    super("Stocks");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLayout(new BorderLayout());


    int size = stocksToDisplay.size();
    String[] columnNames = {"Ticker Name",
              "Number of Units",
              "Commission",
              "Date of Transaction",
              "Price",
              "Buy/Sell"};

    String[][] data = new String[size][6];
    int i = 0;

    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    for (IstockModel stock : stocksToDisplay) {
      String[] singleStock = new String[6];
      singleStock[0] = stock.getTickerName();
      singleStock[1] = Double.toString(stock.getNumOfUnits());
      singleStock[2] = Double.toString(stock.getCommission());
      singleStock[3] = stock.getTransactionDate().format(dateFormat);
      singleStock[4] = String.valueOf(stock.getTransactionPrice());
      singleStock[5] = stock.getBuyOrSell().toString();

      data[i++] = singleStock;

    }

    JTable table = new JTable(data, columnNames);

    this.add(table.getTableHeader(), BorderLayout.PAGE_START);
    this.add(table, BorderLayout.CENTER);

    //ok button

    okButton = new JButton("Ok");
    okButton.setActionCommand("okFromDisplayStocks");

    JPanel buttonsPanel = new JPanel();
    buttonsPanel.add(okButton);
    this.add(buttonsPanel, BorderLayout.PAGE_END);

    this.setVisible(true);
    this.pack();
  }

  public void addActionListener(ActionListener listener) {
    okButton.addActionListener(listener);
  }
}
