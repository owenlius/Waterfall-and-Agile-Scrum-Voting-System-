import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IRVotingTest {

    @Test
    public void testMakeLog() throws Exception {

    }

    @Test
    public void testParseInputFileIR() throws Exception {
        IRVoting irVoting = new IRVoting("irTest1.csv");
        irVoting.parseInputFileIR("irTest1.csv");

        // Expected ballot data
        int expectedNumOfCandidates = 4;
        int expectedNumOfBallots = 6;
        List<String> expectedCandidatesName = new ArrayList<String>(Arrays.asList("Rosen", "Kleinberg", "Chou", "Royce"));

        // actual candidates name
        List<String> actualCandidatesName = new ArrayList();
        // System.out.println(irVoting.candPoolIR.size());
        for(Candidate cand : irVoting.candPoolIR){
            actualCandidatesName.add(cand.getName());
        }

        assertEquals(expectedNumOfCandidates, irVoting.numOfCandidates);
        assertEquals(expectedNumOfBallots, irVoting.numOfBallots);
        assertEquals(expectedCandidatesName, actualCandidatesName);
    }

    @Test
    public void testRun() throws Exception {
        IRVoting irVoting = new IRVoting("irTest1.csv");
        irVoting.run();
        assertEquals("winner: Rosen", irVoting.screenContent.get(irVoting.screenContent.size()-1));
    }

    @Test
    public void testGetAllRecords() throws Exception{
        IRVoting irVoting = new IRVoting("irTest1.csv");
        irVoting.run();

        List<int[]> updateOfEachRound = new ArrayList ();
        List<int[]> totalOfEachRound = new ArrayList ();

        updateOfEachRound.add(new int[] {1, 2, -7, 2});
        totalOfEachRound.add(new int[] {2, 4, 7, 3});
        totalOfEachRound.add(new int[] {3, 6, 0, 5});

        ArrayList<String> expected = new ArrayList<String>();
        expected.add("");
        expected.add("----------------update of votes belonging to all candidates in each round----------------");
        expected.add("               Rosen          Kleinberg      Chou           Royce          ");
        expected.add("round1:        2              4              7              3              ");
        expected.add("");
        expected.add("round2:update  1              2              -7             2              ");
        expected.add("       total   3              6              0              5              ");

        StringBuilder expectedSB = new StringBuilder();
        for(String str : expected){
            expectedSB.append(str);
        }

        ArrayList<String> actual = irVoting.getAllRecords(updateOfEachRound, totalOfEachRound);
        StringBuilder actualSB = new StringBuilder();
        for(String str : actual){
            actualSB.append(str);
        }

        assertEquals(expectedSB.toString(), actualSB.toString());
    }
}