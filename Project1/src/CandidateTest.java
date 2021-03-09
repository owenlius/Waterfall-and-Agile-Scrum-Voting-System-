import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by shimouchen on 11/12/18.
 */
class CandidateTest {
    @Test
    void getVotesTest() {
        Candidate c = new Candidate("ABC", 10);
        assertEquals(10,c.getVotes());

    }

    @Test
    void getVotesTestMax() {
        Candidate c = new Candidate("ABC", 9223372036854775807L +1);
        assertTrue(c.getVotes()>0);

    }

    @Test
    void setVotes() {
        Candidate c = new Candidate("ABC", 10);
        c.setVotes(9223372036854775807L +1);
        assertTrue(c.getVotes()>0);

    }

    @Test
    void addVotesNormal() {
        Candidate c = new Candidate("ABC", 10);
        c.addVotes(1);
        assertTrue(c.getVotes()==11);

    }

    @Test
    void addVotesHigh() {
        Candidate c = new Candidate("ABC", 100000);
        c.addVotes(1);
        assertTrue(c.getVotes()==100001);

    }

    @Test
    void addVotesBadInput() {
        Candidate c = new Candidate("ABC",  9223372036854775807L);
        c.addVotes(1);
        assertTrue(c.getVotes()>0);

    }


    @Test
    void reset() {
        Candidate c = new Candidate("ABC",  10);
        c.reset();
        assertTrue(c.getVotes()==0);

    }


    @Test
    void addBallot() {
        Candidate c = new Candidate("ABC",  10);
        int [] b = {1,2,3,4};
        c.addBallot(new Ballot(1,b));
        assertTrue(c.getBallots().size() ==1);

    }

    @Test
    void setBallots() {
        Candidate c = new Candidate("ABC",  0);
        int [] ballotNumbers = {1,0,0,2};
        Ballot ballot = new Ballot(1,ballotNumbers);
        ArrayList<Ballot> ballots = new ArrayList<>();
        ballots.add(ballot);
        c.setBallots(ballots);
        assertTrue(c.getBallots().size()==1);
        assertTrue(c.getVotes()==1);

    }

    @Test
    void getBallots() {
        Candidate c = new Candidate("ABC",  0);
        int [] ballotNumbers = {1,0,0,2};
        int [] ballotNumbers2 = {1,0,3,2};
        Ballot ballot1 = new Ballot(1,ballotNumbers);
        Ballot ballot2 = new Ballot(2,ballotNumbers2);
        c.addBallot(ballot1);
        c.addBallot(ballot2);
        assertTrue(c.getBallots().size()==2);
        assertTrue(c.getVotes()==2);


    }

    @Test
    void toStringTest() {
        Candidate c = new Candidate("ABC",  0);
        System.out.println(c.toString());
        assertTrue(c.toString().contentEquals("[Name: ABC; Votes: 0; status: 1]"));


    }

    @Test
    void getPartyNull() {
        Candidate c = new Candidate("A",0);
        assertTrue(c.getParty().equals(null));

    }

    @Test
    void setParty() {
        Candidate c = new Candidate("A",0);
        Party p = new Party("ABC");
        c.setParty(p);
        assertTrue(c.getParty().equals(p));


    }

    @Test
    void voidNameTest() {
        Candidate c = new Candidate("A",0);
        c.setName(null);
        assertTrue(c.getName()==null);

    }

    @Test
    void voidParty() {
        Candidate c = new Candidate("A",0);
        c.setParty(null);
        assertTrue(c.getParty()==null);

    }
    @Test
    void getId() {
        Candidate c = new Candidate("abc",0);
        Party p = new Party("ABC");
        c.setParty(p);
        assertTrue(c.getId()==0);

    }

    @Test
    void getStatus() {
        Candidate c = new Candidate("abc",0);
        assertTrue(c.getStatus()==1);


    }

    @Test
    void getName() {
        Candidate c = new Candidate("abc",0);
        assertTrue(c.getName().equals("abc"));


    }

    @Test
    void setId() {
        Candidate c = new Candidate("abc",0);
        c.setId(10);
        assertTrue(c.getId()==10);

    }

    @Test
    void setName() {
        Candidate c = new Candidate("abc",0);
        c.setName("Ac");
        assertTrue(c.getName().equals("Ac"));


    }

    @Test
    void setStatus() {
        Candidate c = new Candidate("abc",0);
        c.setStatus(0);
        assertTrue(c.getId()==0);

    }

    @Test
    void compareToEquals() {
        // This should fail occasionally
        Candidate c1 = new Candidate("Chad",20);
        Candidate c2 = new Candidate("Kim", 20);
        assertTrue(c1.compareTo(c2)==-1);

    }

    @Test
    void compareToEqualsBadValue() {
        Candidate c1 = new Candidate("Chad",9223372036854775807L +1);
        Candidate c2 = new Candidate("Kim", 100);
        assertTrue(c1.compareTo(c2)==-1);

    }


}