import java.util.ArrayList;
import java.util.Random;

/**
 * Party class to encapsulate a Party in an election. Has information on its name, its Candidates, and its seat counts.
 * Implements Comparable to allow sorting on the number of votes won.
 */
public class Party implements Comparable<Party> {

    /**
     * The name of the Party.
     */

    private String name;

    /**
     * The list holding the Candidates of the Party
     */

    private ArrayList<Candidate> candidates = new ArrayList<Candidate>();

    /**
     * The number of votes won by the Party
     */

    private long totalCounts;

    /**
     *  The number of seats won by the Party
     */

    private long seatCounts;

    /**
     * Constructor for Party
     * @param name The name of the Party
     * @param totalCounts The number of votes won by the Party
     * @param seatCounts The number of seats won by the Party
     */

    public Party(String name, long totalCounts, long seatCounts) {
        this.name = name;
        this.totalCounts = totalCounts;
        this.seatCounts = seatCounts;

    }

    /**
     * Constructor that sets the Party counts to 0.
     * @param name The name of the Party
     */

    public Party(String name) {
        this.name = name;
        this.totalCounts = 0;
        this.seatCounts = 0;
    }

    /**
     * Set totalCounts to a new value.
     * @param num The new value to set totalCounts to.
     */

    public void setTotalCounts(long num) {
        this.totalCounts = num;
    }

    /**
     * Retrieve the number of votes won.
     * @return The number of votes won.
     */

    public long getTotalCounts() {
        return this.totalCounts;
    }


    /**
     * Set seatCounts to a new value.
     * @param num The new value to set seatCounts to.
     */

    public void setSeatCounts(long num) {
        this.seatCounts = num;
    }

    /**
     * Retrieve the number of seats won.
     * @return The number of seats won.
     */

    public long getSeatCounts() {
        return this.seatCounts;
    }

    /**
     * Implementing the compareTo method of the Comparable class. Ties are broken randomly using Random.
     * @param anotherParty The other Party used in the comparison.
     * @return 1 if this Party had less votes, -1 if this Party had more votes. Ties are broken randomly.
     */


    public int compareTo(Party anotherParty) {
        if (this.totalCounts > anotherParty.getTotalCounts()) {
            return -1;
        } else if (this.totalCounts < anotherParty.getTotalCounts()) {
            return 1;
        } else {
            // Handled tie cases
            Random random = new Random();
            return random.nextBoolean() ? 1 : -1;
        }
    }

    /**
     * Add an increment of seats to the Party.
     * @param increment The number of additional seats won.
     */

    public void addSeats(long increment) {
        this.seatCounts += increment;
    }

    /**
     * Add an increment of votes to the Party.
     * @param increment The number of additional votes won.
     */

    public void addTotalCounts(long increment) {
        this.totalCounts += increment;
    }

    /**
     * Reset the list of Candidates associated to this Party.
     * @param cand The new list of associated Candidates.
     */

    public void setCandidates(ArrayList cand) {
        this.candidates = cand;
    }

    /**
     * Retrieve the list of associated Candidate.
     * @return The Candidates list.
     */

    public ArrayList<Candidate> getCandidates() {
        return this.candidates;
    }

    /**
     * Add a Candidate to list of Candidates associated with this Party.
     * @param c The new associated Candidate.
     */

    public void addCandidate(Candidate c) {
        candidates.add(c);
    }

    /**
     * Lists the votes, seats, and Candidates of the Party.
     * @return A String representation of the Party.
     */

    public String toString() {
        String ret = "{ "+ name+ " ";
        for (int i = 0; i < candidates.size(); i++) {
            ret += "cand[" + i + "]: " + candidates.get(i).getName() + "\n";
        }
        ret += "total counts: " + this.totalCounts + "\n";
        ret += "seat counts: " + this.seatCounts + "\n }";
        return ret;
    }

    /**
     * Retrieve the name of the Party.
     * @return The name of the Party.
     */

    public String getName() {
        return name;
    }

    /**
     * Reset the name of the Party.
     * @param name The new name of the Party.
     */

    public void setName(String name) {
        this.name = name;
    }
}