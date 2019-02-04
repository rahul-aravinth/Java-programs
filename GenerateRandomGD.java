import java.util.Random;

public class GenerateRandomGD{

    public static void main(String args[]) {

	Random random = new Random();
	// Paste when complete - "Team 1","Team 2","Team 3","Team 4","Team 5","Team 6"
	String[] names = {"Team 3","Team 4","Team 5"};
  /*
    21-Jan-19 - Team 6
    22-Jan-19 - Team 2
    23-Jan-19 - Team 1
  */
  int thisweek = random.nextInt(3);
	System.out.println(names[thisweek]);
}
}
