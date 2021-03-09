import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by shimouchen on 11/12/18.
 */
class PartyTest {
    @Test
    void setTotalCounts() {
        Party p = new Party("R",100,0);
        p.setTotalCounts(9223372036854775807L+1);
        assertTrue(p.getTotalCounts()>0);

    }

    @Test
    void getTotalCounts() {
        Party p = new Party("R",100,100);
        assertEquals(p.getTotalCounts(),100);

    }

    @Test
    void setSeatCounts() {
        Party p = new Party("R",100,0);
        p.setSeatCounts(9223372036854775807L+1);
        assertTrue(p.getSeatCounts()>0);

    }

    @Test
    void getSeatCounts() {
        Party p = new Party("R",100,100);
        assertEquals(p.getSeatCounts(),100);

    }


    @Test

    void compareToEquals() {
        // this should fail occasionally
        Party p1 = new Party("R",100,0);
        Party p2 = new Party("D", 100, 0);
        assertTrue(p1.compareTo(p2)==-1);


    }


    @Test
    void compareToEqualsBadValue() {
        Party p1 = new Party("R", 9223372036854775807L + 1, 10);
        Party p2 = new Party("D", 100, 10);
        assertTrue(p1.compareTo(p2) == -1);
    }


    @Test
    void addSeats() {
        Party p1 = new Party("R", 10, 9223372036854775807L);
        p1.addSeats(1);
        assertTrue(p1.getSeatCounts()>0);

    }

    @Test
    void addTotalCounts() {
        Party p1 = new Party("A", 9223372036854775807L,1);
        p1.addTotalCounts(1);
        assertTrue(p1.getTotalCounts()>0);

    }

    @Test
    void setCandidates() {
        Party p1 = new Party("A", 10,10);
        p1.setCandidates(null);
        assertTrue(p1.getCandidates() != null);

    }

    @Test
    void getCandidates() {
        Party p1 = new Party("A", 10,10);
        p1.addCandidate(new Candidate("Smith",p1));
        assertTrue(p1.getCandidates().get(0)!=null);

    }

    @Test
    void addCandidate() {
        Party p1 = new Party("A", 10,10);
        p1.addCandidate(null);
        assertTrue(p1.getCandidates().get(0)!=null);

    }

    @Test
    void toStringTest() {
        Party p1 = new Party("A", 10,10);
        assertFalse(p1.toString().equals(""));

    }

    @Test
    void getName() {
        Party p1 = new Party("A", 10,10);
        assertTrue(p1.getName().equals("A"));

    }

    @Test
    void setName() {
        Party p1 = new Party("AVV", 10,10);
        p1.setName(null);

        assertTrue(p1.getName().equals(null));

    }

}