package view;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class CalculateDate {

  public static void setDate(JComboBox month, JComboBox date) {

    String monthNumStr = month.getSelectedItem().toString();
    int monthNum = Integer.parseInt(monthNumStr);
    DefaultComboBoxModel model = (DefaultComboBoxModel) date.getModel();
    if (monthNum == 1 || monthNum == 3 || monthNum == 5 || monthNum == 7 || monthNum == 8
            || monthNum == 10 || monthNum == 12) {
      String[] oddDates = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12",
              "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26",
              "27", "28", "29", "30", "31"};
      model.removeAllElements();
      for (int i = 0; i < 31; i++) {
        model.addElement(oddDates[i]);
      }
    } else if (monthNum == 4 || monthNum == 6 || monthNum == 9 || monthNum == 11) {
      String[] evenDates = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12",
              "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26",
              "27", "28", "29", "30"};
      model.removeAllElements();
      for (int i = 0; i < 30; i++) {
        model.addElement(evenDates[i]);
      }
    } else {
      String[] febDates = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12",
              "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26",
              "27", "28"};
      model.removeAllElements();
      for (int i = 0; i < 28; i++) {
        model.addElement(febDates[i]);
      }
    }
  }
}
