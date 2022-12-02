package view;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.time.LocalDate;
import java.util.Map;

import javax.swing.JFrame;

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
