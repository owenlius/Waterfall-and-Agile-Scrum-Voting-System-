import java.io.*;
import java.util.ArrayList;

/**
 * Created by Dong on 10/19/18.
 */
public class Util {

    public static String METHOD_NAME = "unkown";



    // Get the first line

    /**
     * Reads the first line of an election file so the system can determine which function to run.
     * @param fileLocation The filename/location of the file being read.
     * @throws IOException Throws an IOException from opening and closing BufferedReader.
     */
    public static void getFirstLine(String fileLocation) throws IOException {
        FileInputStream inputStream = new FileInputStream(fileLocation);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        int lineNum = 0;
        String content = null;
        while((content = bufferedReader.readLine()) != null) {
            lineNum++;
            if (lineNum == 1) {
                METHOD_NAME = content;
                break;
            }
        }
        //close inputStream and bufferedReader
        inputStream.close();
        bufferedReader.close();
    }

    /**
     * Uses BufferedWriter to write a list of Strings to newlines in a file.
     * @param filename The name of the output file
     * @param content The list of Strings for each line
     * @throws IOException Throws an IOException from opening and closing BufferedWriter.
     */

    public static void writeAuditFile(String filename, ArrayList<String> content) throws IOException {

        BufferedWriter output = null;
        FileWriter fw = null;

        try {
            File file = new File(filename);
            fw = new FileWriter(file);
            output = new BufferedWriter(fw);
            for (int i = 0; i < content.size(); i++) {
                output.write(content.get(i) + "\n");
                fw.flush();
            }
        } catch ( IOException e ) {
            e.printStackTrace();
        } finally {
            if ( output != null ) {
//                fw.close();
                output.close();
            }
        }
    }

    /**
     * Print a list of lines to the screen.
     * @param stdout The list of Strings to be displayed.
     */
    public static void printScreen(ArrayList<String> stdout) {
        for (int i = 0; i < stdout.size(); i++) {
            System.out.println(stdout.get(i));
        }
    }

    public static void main(String args[]) {
    }
}
