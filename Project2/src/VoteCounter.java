import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Logger;

//import com.sun.corba.se.spi.orbutil.fsm.Input;
//import com.sun.glass.ui.TouchInputSupport;

/**
 * Vote Counter takes the input accepted from MyMenuDemo and will hand off the filename to the correct voting method.
 * It prompts the user for a filename of an election file and proceeds to
 * run the correct election algorithm.
 */
public class VoteCounter {
    private static Logger logger;
    private static MyMenuDemo myMenuDemo;
    private String fileLocation;

    /**
     * This is the lone constructor which will take the file input and call run() to initiate the election
     * calculation process.
     *
     * @param fileLocation the file name of the election file which is in .csv format.
     */
    
    public VoteCounter(String fileLocation) {
    	System.out.println(fileLocation + "---get!-------------------");
    	this.fileLocation = fileLocation;
    	run();
    	System.out.println("finish===============");
    	
    }

    /**
     * This function is responsible for processing the first line of an election file and passing the file to the
     * correct voting method, which is either OPLVoting or IRVoting.
     */
    
    public void run() {
    	if(fileLocation != null) {
    		try {
              Util.getFirstLine(fileLocation);
              if (Util.METHOD_NAME.equals("OPL")) {
                  System.out.println("!!!");
                  OPLVoting opl = new OPLVoting(fileLocation);
                  // TODO:
                  opl.run();
              } else if (Util.METHOD_NAME.equals("IR")) {
                  IRVoting ir = new IRVoting(fileLocation);
                  ir.run();
              } else {
//              logger.log("Method not allowed");
              }
          } catch (IOException e) {
              e.printStackTrace();
          }
    	}
    }

    /**
     * This is now defunct.
     * @param args
     */
    public static void main(String args[]) {
//    	myMenuDemo = new MyMenuDemo();
//    	while(fileLocation == null) {
//    		System.out.println("@");
//    		
//    	}
//    	System.out.println("!!!!!!!!!! " +  fileLocation);
    	
    	
    	
    }
//        String input = null;
//
//        if (args.length > 0)  {
//          System.out.println("got to here.");
//            input = args[0];
//        } else {
//            Scanner console = new Scanner(System.in);
//            System.out.println("Please Enter the File Name.");
//            input = console.nextLine();
//        }
//            // TODO: Any voting methods determination goes here
//            try {
//                Util.getFirstLine(input);
//                if (Util.METHOD_NAME.equals("OPL")) {
//                    System.out.println("!!!");
//                    OPLVoting opl = new OPLVoting(input);
//                    // TODO:
//                    opl.run();
//                } else if (Util.METHOD_NAME.equals("IR")) {
//                    IRVoting ir = new IRVoting(input);
//                    ir.run();
//                } else {
////                logger.log("Method not allowed");
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            // display(input);
//        }
}
