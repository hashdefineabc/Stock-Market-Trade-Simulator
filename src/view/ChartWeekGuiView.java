package view;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.time.LocalDate;
import java.util.Map;

import javax.swing.JFrame;

/**
 *The ChartWeekGuiView shows the performance of a portfolio on a weekly basis.
 * It extends the JFrame class in order to design the buttons for the create operation.
 *This lets the user view the  performance of a portfolio on a weekly basis.
 * All the buttons and textfields necessary to take input from the user are implemented here.
 */
public class ChartWeekGuiView extends JFrame {

  public ChartWeekGuiView(Map<LocalDate, String> chart) {
    this.setSize(1050, 1000);
    this.getContentPane().add(new BarChart(chart));

    WindowListener winListener = new WindowAdapter() {
      public void windowClosing(WindowEvent event) {
        System.exit(0);
      }
    };
    this.addWindowListener(winListener);

    this.setVisible(true);
  }
}
