import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by shimouchen on 11/12/18.
 */
class OPLVotingTest {
    String filename = "auditrand0.csv";
    String filename1 = "opl2.csv";
    OPLVoting o = new OPLVoting(filename);
    OPLVoting o1 = new OPLVoting(filename1);
    @Test
    void findWinner() {
        o1.findWinner();
        assertTrue(true); // rest of this test has to be done manually


    }

    @Test
    void makeLog() {
        o.makeLog(10);
        assertTrue(true); // the test has to be done manually

    }

    @Test
    void run() {
        o.run();
        assertTrue(o.candPoolOPL.size() == 6);
         // the test has to be done manually

    }

}