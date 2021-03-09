/**
 * Ballot is a class that holds a class that holds an election ballot information. A ballot is held in an array. Each
 * element is a rank of a candidate in the IR election.
 */
public class Ballot {
    /**
     * Identifies a ballot uniquely.
     */
    private int id;
    /**
     * The array containing ranking/voting information of a ballot.
     */
    private int[] votes;

    /**
     * Record current round of the election for this Ballot
     */
    private int curRound;

    /**
     * Constructor for a ballot which we don't know the ballot information right away.
     * @param id: The unique identifier for the Ballot
     * @param leng The size of the array to store the ballot
     */

    public Ballot(int id, int leng) {
        this.id = id;
        this.votes = new int[leng];
        this.curRound =1;
    }

    /**
     * Ballot constructor.
     * @param id The unique identifier for the Ballot
     * @param votes The size of the array to store the ballot
     */

    public Ballot(int id, int [] votes) {
        this.id = id;
        this.votes = votes;
        this.curRound = 1;
    }

    /**
     * Since the ballot information is in String format after processing, this constructor was added for conversion
     * convenience to convert a string ballot to an int ballot.
     * @param id The unique identifier for the Ballot
     * @param votes_string The array of information in string format.
     */

    public Ballot(int id, String [] votes_string) {
        this.id = id;
        this.votes = new int[votes_string.length];
        for (int i = 0; i<votes_string.length; i++) {
            if (votes_string[i].equals("")) {
                this.votes[i] = 0;
            } else {
                this.votes[i] = Integer.parseInt(votes_string[i]);
            }
        }
        this.curRound = 1; // added by Qing
    }

    /**
     *
     * @return id The id of the Ballot
     */

    public int getId() {
        return this.id;
    }

    /**
     *
     * @return votes: The integer array holding the Ballot information/rank information.
     */

    public int[] getVotes() {
        return this.votes;
    }

    /**
     *
     * @return curRound: The current round of the IR runoff election.
     */



    public int getCurRound() {
        return curRound;
    }

    /**
     * Updates the Ballot to the current round of the instant runoff election.
     * @param curRound The new round of the runoff election.
     */

    public void setCurRound(int curRound) {
        this.curRound = curRound;
    }

}