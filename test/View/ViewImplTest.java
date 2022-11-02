package View;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import Controller.ControllerImpl;
import Model.User;

import static org.junit.Assert.*;

public class ViewImplTest {

  OutputStream out;
  ViewInterface view;

  @Before
  public void setup() {
    out = new ByteArrayOutputStream();
    view = new ViewImpl(new PrintStream(out));
  }

  @Test
  public void testDisplayMenu() {
    view.displayMenu();
    String expectedMenu = "***********************************\n" + "\t1. Create a new Portfolio\n" + "\t2. Retrieve Portfolio\n" + "\t3. Check value of a Portfolio\n" + "\t4. Exit the application.\n" + "Pick one of the options\n";
    assertEquals(expectedMenu, out.toString());
  }

  @Test
  public void testTickerName() {
    view.takeTickerName();
    String expectedMenu = "Enter the ticker name:\n";
    assertEquals(expectedMenu, out.toString());
  }

  @Test
  public void testTakeNumOfUnits() {
    view.takeNumOfUnits();
    String expectedMenu = "Enter the number of units purchased:\n";
    assertEquals(expectedMenu, out.toString());
  }

  @Test
  public void testAddMoreStocks() {
    view.addMoreStocks();
    String expectedMenu = "Do you want to add more stocks to this portfolio? (Press 1 for Yes, 0 for No):\n";
    assertEquals(expectedMenu, out.toString());
  }

  @Test
  public void testGetSelectedPortfolio() {
    view.getSelectedPortfolio();
    String expectedMenu = "Pick a portfolio\n";
    assertEquals(expectedMenu, out.toString());
  }

  @Test
  public void testGetDateFromUser() {
    view.getDateFromUser();
    String expectedMenu = "Enter the date for which you to check the value of the portfolio:(yyyy-mm-dd)\n";
    assertEquals(expectedMenu, out.toString());
  }

  @Test
  public void testGetPortfolioNameFromUser(){
    view.getPortfolioNameFromUser();
    String expectedMenu = "\tPlease enter a name for this portfolio\n";
    assertEquals(expectedMenu, out.toString());
  }

  @Test
  public void testDisplayCreatePortFolioOptions(){
    view.displayCreatePortFolioOptions();
    String expectedMenu = "\t1. Enter the stock details manually\n" + "\t2. Upload an existing file\n";
    assertEquals(expectedMenu, out.toString());
  }

  @Test
  public void testIsFileUploaded(){
    view.isFileUploaded();
    String expectedMenu = "\tHas the file been placed at the above location? 1.Yes 0.No\n";
    assertEquals(expectedMenu, out.toString());
  }

}