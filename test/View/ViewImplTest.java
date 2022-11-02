package View;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;

public class ViewImplTest {

  @Test
  public void testViewDisplayMenu() {
    OutputStream out = new ByteArrayOutputStream();
    ViewInterface view = new ViewImpl(new PrintStream(out));
    view.displayMenu();
    String expectedOutput = "***********************************\n" + "\t1. Create a new Portfolio\n" + "\t2. Retrieve Portfolio\n"
            + "\t3. Check value of a Portfolio\n" +"\t4. Exit the application.\n" + "\tPick one of the options\n";
    assertEquals(expectedOutput, out.toString());
  }
}