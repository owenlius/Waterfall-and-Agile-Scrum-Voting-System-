import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class IRVotingTest {

    @org.junit.jupiter.api.Test
    public void testParseInputFileIR() throws Exception {
        IRVoting irVoting = new IRVoting("irTest1.csv");
        irVoting.parseInputFileIR("irTest1.csv");

        // Expected ballot data
        int expectedNumOfCandidates = 4;
        int expectedNumOfBallots = 6;
        List<String> expectedCandidatesName = new ArrayList<String>(Arrays.asList("Rosen", "Kleinberg", "Chou", "Royce"));

        // actual candidates name
        List<String> actualCandidatesName = new ArrayList();
        System.out.println(irVoting.candPoolIR.size());
        for(Candidate cand : irVoting.candPoolIR){
            actualCandidatesName.add(cand.getName());
        }

        assertEquals(expectedNumOfCandidates, irVoting.numOfCandidates);
        assertEquals(expectedNumOfBallots, irVoting.numOfBallots);
        assertEquals(expectedCandidatesName, actualCandidatesName);
    }

    @org.junit.jupiter.api.Test
    public void testRun() throws Exception {
        IRVoting irVoting = new IRVoting("irTest1.csv");
        irVoting.run();
        assertEquals("winner: Rosen", irVoting.screenContent.get(irVoting.screenContent.size()-1));
    }
}