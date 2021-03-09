import java.util.ArrayList;
import java.util.Random;

/**
 * Candidate class encapsulates an election Candidate. It tracks what party he/she belongs to, his/her name, his/her
 * votes, etc. It holds an association to the Party of the Candidate and his/her Ballots. Inherits the Comparable class
 * to sort on the Candidate's vote count.
 */
public class Candidate implements Comparable<Candidate> {

    /**
     * Name of the candidate.
     */

    private String name;

    /**
     * The Party of the Candidate.
     */
    private Party party;

    /**
     * The number of votes received by the Candidate.
     */

    private long votes;

    /**
     * The unique identifier of the Candidate.
     */

    private int id;

    /**
     * The election status of the Candidate. 0: eliminated; 1: alive.
     */

    private int status;

    /**
     * The Ballots won by the Candidate.
     */

    private ArrayList<Ballot> ballots = new ArrayList<Ballot>(); // changed this to an array list.

    /**
     * Constructor for Candidate class.
     * @param name The name of the Candidate
     * @param votes The number of votes won by the Candidate
     */
    public Candidate(String name, long votes) {
        this.name = name;
        this.votes = votes;
        this.status = 1;
    }

    /**
     * Constructor for Candidate class.
     * @param name The name of the Candidate
     * @param party The Party instance the Candidate belongs to.
     */


    public Candidate(String name, Party party) {
        this.party = party;
        this.name = name;
        this.votes = 0;
        this.status = 1;
    }

    /**
     * Getter for the vote count.
     * @return The votes: The number of votes.
     */

    public long getVotes() {
        return this.votes;
    }

    /**
     * Set the vote count to a new value.
     * @param value The new vote count.
     */

    public void setVotes(long value) {
        this.votes = value;
    }

    /**
     * Add to the vote count for the Candidate.
     * @param value The value to increment the vote count by.
     */

    public void addVotes(long value) {
        this.setVotes(this.votes + value);
    }

    /**
     * Reset the Candidate's information to start of the election.
     */

    public void reset() {
        this.votes = 0;
        this.status = 0;
        this.ballots.clear();
    }


    /**
     * Add a Ballot to list of Ballots won by the Candidate.
     * @param b The new Ballot.
     */

    public void addBallot(Ballot b) {
        ballots.add(b);
        votes++;

    }

    /**
     * Reset the list of Ballots to a new one.
     * @param ballots The new list of Ballots won.
     */


    public void setBallots(ArrayList<Ballot> ballots) {
        this.ballots = ballots;
    }

    /**
     * Retrieve the Ballots won the Candidate.
     * @return The Ballots won.
     */
    public ArrayList<Ballot> getBallots() {
        return this.ballots;
    }


    /**
     * String representation of the Candidate information
     * @return A string with the Candidate information such as name, the number votes, etc.
     */

    public String toString() {
        return "[Name: " + this.name + "; Votes: " + this.votes + "; status: " + this.status
                + "]";
    }

    /**
     * Retrieve the associated Party.
     * @return The Party of the Candidate.
     */

    public Party getParty() {
        return party;
    }

    /**
     * Change party allegiance of a Candidate.
     * @param party the new Party allegiance.
     */


    public void setParty(Party party) {
        this.party = party;
    }

    /**
     * Retrieve identifier
     * @return the id of the Candidate
     */


    public int getId() {
        return id;
    }

    /**
     * Retrieve the election status of the Candidate
     * @return The election status of the Candidate
     */


    public int getStatus() {
        return status;
    }

    /**
     * Retrieve the name of the Candidate
     * @return The name of the Candidate
     */

    public String getName() {
        return name;
    }

    /**
     * Reset the id of the Candidate.
     * @param id The new id #.
     */

    public void setId(int id) {
        this.id = id;
    }

    /**
     * Reset the name of the Candidate.
     * @param name The new name.
     */

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Reset the id of the Candidate.
     * @param status The new status of the Candidate.
     */

    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * Implementing the compareTo method of the Comparable class. Ties are broken randomly using Random.
     * @param anotherCand The other Candidate used in the comparison.
     * @return 1 if this Candidate had less votes, -1 if this Candidate had more votes. Ties are broken randomly.
     */

    public int compareTo(Candidate anotherCand) {
        if (this.votes > anotherCand.getVotes()) {
            return -1;
        } else if (this.votes < anotherCand.getVotes()) {
            return 1;
        } else {
            // Handled tie cases
            Random random = new Random();
            return random.nextBoolean() ? 1 : -1;
        }
    }



    public static void main(String args[]) {


    }
}