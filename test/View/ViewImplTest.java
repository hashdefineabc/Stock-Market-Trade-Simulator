//package View;
//
//import org.junit.Before;
//import org.junit.Test;
//
//import java.io.ByteArrayInputStream;
//import java.io.ByteArrayOutputStream;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.io.PrintStream;
//import java.util.List;
//
//import Controller.ControllerImpl;
//import Model.User;
//
//import static org.junit.Assert.*;
//
//public class ViewImplTest {
//
//  /*class MockView implements ViewInterface {
//
//    //create a flag
//    boolean checkDisplayMenu = false;
//    //compare this flag later to check if this fn was called or not
//
//
//    @Override
//    public String displayMenu() {
//      checkDisplayMenu = true;
//      return null;
//    }
//
//    @Override
//    public String[] takeStockInput() {
//      return new String[0];
//    }
//
//    @Override
//    public Boolean addMoreStocks() {
//      return null;
//    }
//
//    @Override
//    public void displayListOfPortfolios(List<String> portfolios) {
//
//    }
//
//    @Override
//    public int getSelectedPortfolio() {
//      return 0;
//    }
//
//    @Override
//    public void displayStocks(List<String[]> listOfStocks) {
//
//    }
//
//    @Override
//    public String getDateFromUser() {
//      return null;
//    }
//
//    @Override
//    public void displayValue(double val) {
//
//    }
//
//    @Override
//    public void displayMsgToUser(String msg) {
//
//    }
//
//    @Override
//    public String getPortfolioNameFromUser() {
//      return null;
//    }
//
//    @Override
//    public String displayCreatePortFolioOptions() {
//      return null;
//    }
//
//    @Override
//    public boolean isFileUploaded() {
//      return false;
//    }
//  }*/
//
//  @Test
//  public void testDisplayMenu() {
//    InputStream input = null;
//    String inputForOption1 = "1";
//    input = new ByteArrayInputStream(inputForOption1.getBytes());
//    OutputStream out = new ByteArrayOutputStream();
//    User u = new User();
//    ViewInterface view = new ViewImpl(input, new PrintStream(out)); //this input stream can be used by view as well as junit tests.
//    ControllerImpl controller = new ControllerImpl(u,view);
//    controller.showMenuOnView();
//    String expectedMenu = "\t1. Create a new Portfolio\n" + "\t2. Retrieve Portfolio\n"
//            + "\t3. Check value of a Portfolio\n" +"\t4. Exit the application.\n" + "\tPick one of the options\n";
//
//    assertEquals(expectedMenu, out.toString());
//  }
//
//  @Test
//  public void testTakeStockInput() throws IOException {
//    String inputForTest = "GOOG\n5\n";
//    InputStream input = new ByteArrayInputStream(inputForTest.getBytes());
//    OutputStream out = new ByteArrayOutputStream();
//    User u = new User();
//    ViewInterface view = new ViewImpl(input, new PrintStream(out));
//    ControllerImpl controller = new ControllerImpl(u, view);
//    controller.takeStockInputFromView();
//    String expectedOutput = "Enter the ticker name:\n" + "Enter the number of units purchased:\n";
//    assertEquals(expectedOutput, out.toString());
//  }
//  @Test
//  public void testAddMoreStocks() {
//    String inputForTest = "1";
//    InputStream input = new ByteArrayInputStream(inputForTest.getBytes());
//    OutputStream out = new ByteArrayOutputStream();
//    User u = new User();
//    ViewInterface view = new ViewImpl(input, new PrintStream(out));
//    ControllerImpl controller = new ControllerImpl(u, view);
//    controller.addMoreStocksFromView();
//    String expectedOutput = "Do you want to add more stocks to this portfolio? (Press 1 for Yes, 0 for No):\n";
//    assertEquals(expectedOutput, out.toString());
//  }
//
//}