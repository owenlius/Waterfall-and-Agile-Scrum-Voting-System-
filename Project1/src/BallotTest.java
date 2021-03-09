import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by shimouchen on 11/11/18.
 */
class BallotTest {
    @org.junit.jupiter.api.Test
    void getId() {
        int t[] = new int[5];
        Ballot b = new Ballot(132, t);
        assertEquals(132, b.getId());

    }

    @org.junit.jupiter.api.Test
    void getVotesSmallArrayFrontEdge() {
        int t[] = new int[5];
        t[0] = 1;
        Ballot b = new Ballot(132, t);
        assertEquals(1, b.getVotes()[0]);

    }
    @org.junit.jupiter.api.Test
    void getVotesSmallArrayBackEdge() {
        int t[] = new int[5];
        t[4] = 1;
        Ballot b = new Ballot(132, t);
        assertEquals(1, b.getVotes()[4]);

    }

    @org.junit.jupiter.api.Test

    void getVotesSmallArrayMiddle() {
        int t[] = new int[5];
        t[2] = 1;
        Ballot b = new Ballot(132, t);
        assertEquals(1, b.getVotes()[2]);

    }

    @org.junit.jupiter.api.Test
    void getVotesBigArrayMiddle() {
        int t[] = new int[100];
        t[50] = 1;
        Ballot b = new Ballot(132, t);
        assertEquals(1, b.getVotes()[50]);

    }

    @org.junit.jupiter.api.Test
    void getVotesBigArrayFront() {
        int t[] = new int[100];
        t[0] = 1;
        Ballot b = new Ballot(132, t);
        assertEquals(1, b.getVotes()[0]);

    }

    @org.junit.jupiter.api.Test
    void getVotesBigArrayBack() {
        int t[] = new int[100];
        t[99] = 1;
        Ballot b = new Ballot(132, t);
        assertEquals(1, b.getVotes()[99]);

    }
    @org.junit.jupiter.api.Test
    void getCurRound() {
        int t[] = new int[100];
        Ballot b = new Ballot(132, t);
        assertEquals(1, b.getCurRound());


    }

    @org.junit.jupiter.api.Test
    void setCurRound() {
        int t[] = new int[100];
        Ballot b = new Ballot(132, t);
        b.setCurRound(10);
        assertEquals(10, b.getCurRound());

    }


}