import java.io.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by shimouchen on 11/27/18.
 */
class VoteCounterTest {
    @Test
    void TestMainIR() {
        VoteCounter test_vote_counter = new VoteCounter("testing/auditrand2.csv");
        test_vote_counter.run();
        File f = new File("testing/invalidated_Sat Dec 08 20:04:31 CST 2018.txt");
        assertTrue(f.exists());
    }

    @Test
    void TestMainOPL() {
        VoteCounter test_vote_counter = new VoteCounter("testing/SmallElection8.csv");
        test_vote_counter.run();
        File f = new File("testing/Election_Sat Dec 08 20:11:35 CST 2018_audit.txt");
        assertTrue(f.exists());

    }

}