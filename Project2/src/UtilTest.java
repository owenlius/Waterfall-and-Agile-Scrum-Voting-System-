import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by shimouchen on 11/12/18.
 */
class UtilTest {
    @Test
    void getFirstLine() {
        try {
            Util.getFirstLine("auditrand3.csv");
            assertTrue(Util.METHOD_NAME.equals("unknown"));
        } catch (Exception e) {
            // System.out.println("Error reading file");
        }


    }

    @Test

    void getFirstLineNormal() {
        try {
            Util.getFirstLine("auditrand2.csv");
            assertTrue(Util.METHOD_NAME.equals("IR"));
        } catch (Exception e) {
            // System.out.println("Error reading file");
        }


    }
    /**
     * printScreen and writeAudit file are done manually.
     */

}