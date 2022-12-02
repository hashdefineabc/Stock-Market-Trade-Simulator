package view;

import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;

public class DCAFromHome extends JFrame {

  JButton create;
  JButton dca;
  private JButton cancel;


  public DCAFromHome() {
    super("Dollar cost averaging");
    setVisible(true);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    JPanel panel = new JPanel();
    panel.setLayout(new GridLayout(2, 1));

    // Create a new Portfolio
    create = new JButton("Create a new Portfolio");
    create.setActionCommand("create");
    panel.add(create);

    //set dollar cost averaging for a portfolio
    dca = new JButton("Add dollar cost averaging to an existing portfolio");
    dca.setActionCommand("dcaButtonHome");
    panel.add(dca);

    JPanel buttonPanel = new JPanel();
    cancel = new JButton("Cancel");
    cancel.setActionCommand("cancelFromDCAHome");
    buttonPanel.add(cancel);


    this.add(buttonPanel, BorderLayout.PAGE_END);

    this.getContentPane().add(panel, BorderLayout.PAGE_START);
    this.setVisible(true);
    this.pack();
  }

  public void addActionListener(ActionListener listener) {
    create.addActionListener(listener);
    dca.addActionListener(listener);
    cancel.addActionListener(listener);
  }
}
