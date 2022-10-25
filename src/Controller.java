import java.util.Scanner;

public class Controller {

  public static void Main(String args[]) {



  }

  private void run() {
    Scanner scan = new Scanner(System.in);
    int inputOption = 9;
    String menu = "Select one of the below options: \n" +
            "1.Create a new portfolio.\n" +
            "2.Examine a portfolio\n" +
            "3.Check the total value of a portfolio\n" +
            "0.Exit\n";
    System.out.println(menu);
    inputOption = scan.nextInt();
    while (inputOption != 0) {
      switch(inputOption) {
        case 1:
          // function to create a new portfolio
          break;
        case 2:
          //examine a current portfolio
          break;
        case 3:
          //check the total value
          break;
      }
    }
    System.out.println("END");

  }
}
