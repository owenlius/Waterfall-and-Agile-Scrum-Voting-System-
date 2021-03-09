import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 * This is the entry point of the system. It prompts the user for a filename of an election file and proceeds to
 * run the correct election algorithm.
 */
public class VoteCounter {
    private static Logger logger;


    /**
     * This is the entry point of the system. It prompts the user for a filename of an election file and proceeds to
     * run the correct election algorithm.
     * @param args Potential command line inputs
     */
    public static void main(String args[]) {
        Scanner console = new Scanner(System.in);
        String input = null;
        System.out.println("Please Enter the File Name.");
        input = console.nextLine();
        // TODO: Any voting methods determination goes here
        try {
            Util.getFirstLine(input);
            if (Util.METHOD_NAME.equals("OPL")) {
                System.out.println("!!!");
                OPLVoting opl = new OPLVoting(input);
                // TODO:
                opl.run();
            } else if (Util.METHOD_NAME.equals("IR")) {
                IRVoting ir = new IRVoting(input);
                ir.run();
            } else {
//                logger.log("Method not allowed");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // display(input);
    }
}
