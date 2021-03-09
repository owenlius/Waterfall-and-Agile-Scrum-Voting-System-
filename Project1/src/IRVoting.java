//import sun.jvm.hotspot.asm.LoadInstruction;
//import sun.rmi.runtime.Log;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.Calendar;


/**
 * Created by Dong on 10/19/18.
 * The name of the file is: IRVoting.java
 * This is a class to run the IR election method with the run function. Inherits the LogUtil for logging purposes.
*/
public class IRVoting implements LogUtil{
    /**
     * The Candidates involved in the election stored in a list.
     */
    public List<Candidate> candPoolIR = new ArrayList<Candidate>();

    /**
     * The lines of the audit file to be written stored in a list.
     */
    public ArrayList<String> auditContent;

    /**
     * The lines of the output file to be written stored in a list.
     */
    public ArrayList<String> screenContent;

    List<Ballot> ballots = new ArrayList<Ballot>();
    List<Ballot> notAssignedBallots = new ArrayList<Ballot>();
    int numOfCandidates;
    int numOfBallots;
    String winner;
    int file_counter;

    private String input = "NOT DEFINED";

    public IRVoting(String input) {
        this.input = input;
        auditContent = new ArrayList<String>();
        screenContent = new ArrayList<String>();
        winner = "NOT DEFINED";
    }

    public void makeLog(int caseNum) {
        // add some code of your choice here
        // Moving to the logging part now
        // Identify a specific log with caseNum
        if (caseNum == 1) {
            logger.log(Level.INFO, "Log Message Test");
        }
    }

    /**
     * Parses the file specified at the filename and creates the Ballot and Candidate classes from the file.
     * Does preliminary distribution of the Ballots. Reads each line of the file and sets the member variables to
     * specified values and turns the ballot lines into actual Ballot instances.
     *
     * @param fileLocation Location of the file.
     * @throws IOException
     */
    public void parseInputFileIR(String fileLocation) throws IOException {
        FileInputStream inputStream = new FileInputStream(fileLocation);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        int lineNum = 0;
        String content = null;
        while((content = bufferedReader.readLine()) != null) {
            // TODO: Implement any comprehensive logic here(like , split functionality)
            lineNum++;

            // System.out.println("#Line: " + lineNum + " is " + content);

            if(lineNum == 1) {
                auditContent.add("Method: IR");
                screenContent.add("Method: IR");
            }else if(lineNum == 2) {
                numOfCandidates = Integer.parseInt(content);
                auditContent.add("Number of Candidates: " + numOfCandidates);
                screenContent.add("Number of Candidates: " + numOfCandidates);
            }else if(lineNum == 3){
                String[] candidates = content.split(", ");
                StringBuilder sb = new StringBuilder("All candidates: ");
                for(String cand : candidates){
                    // should we remove the party info in cand name, such as "Rosen (D)" or just "Rosen"
                    String candName = cand.contains(" (") ? cand.substring(0, cand.indexOf(" (")) : cand;
                    candPoolIR.add(new Candidate(candName, 0));
                    sb.append(candName).append(" ");
                }
                auditContent.add(sb.toString());
                screenContent.add(sb.toString());
            }else if(lineNum == 4){
                numOfBallots = Integer.parseInt(content);
                auditContent.add("Number of total ballots: " + numOfBallots);
                screenContent.add("Number of total ballots: " + numOfBallots);
            }else{
                // ballot id starts from 0
                Ballot ballot = new Ballot(lineNum-5, content.split(","));

                // add the ballot to ranking 1st candidate
                for(int i = 0; i < ballot.getVotes().length; i++) {
                    if (ballot.getVotes()[i] == 1) {
                        candPoolIR.get(i).addBallot(ballot);
                        // auditContent.add(candPoolIR.get(i).getName() + " gets 1 vote.");
                    }
                }

                ballots.add(ballot);
            }
        }

        // calculate the ballots number each candidate has
        auditContent.add("The ballot number each candidate has:");
        for(Candidate cand : candPoolIR){
            auditContent.add(cand.getName() + " gets " + cand.getBallots().size() + " votes.");
        }

        //close inputStream and bufferedReader
        inputStream.close();
        bufferedReader.close();
    }

    /**
     * Executes an IR election with the filename stored at input. It parses the file and finds the winner of the
     * election. Then it writes the lines to an audit and summary file and prints a summary to the screen.
     */
    public void run() throws IOException {
        parseInputFileIR(input);

        auditContent.add("-----------------------------------find winner process-----------------------------------");

        Random rand = new Random();
        int curMaxBallotNum = 0;
        int curMinBallotNum = Integer.MAX_VALUE;

        int round = 1;
        List<Candidate> leastVotedCands = new ArrayList<Candidate>();
        List<Candidate> mostVotedCands = new ArrayList<Candidate>();
        List<Candidate> active = new ArrayList<Candidate>();

        for(Candidate c : candPoolIR){
            if(c.getStatus() == 1)
                active.add(c);

        }

        // start voting
        while(true){
            // System.out.println("*******round: " + round);
            auditContent.add("*******round: " + round);

            active.clear();
            for(Candidate c : candPoolIR) {
                if (c.getStatus() == 1)
                    active.add(c);
            }

            // output all votes of every candidate
            for(Candidate c : candPoolIR){
                if(c.getStatus() == 0)
                    continue;

                StringBuilder sb = new StringBuilder(c.getName() + " gets " + c.getBallots().size() + " votes, ballots are :");
                for(Ballot b : c.getBallots()){
                    sb.append(b.getId() + " ");
                }

                auditContent.add(sb.toString());

            }
            curMaxBallotNum = 0;
            curMinBallotNum = Integer.MAX_VALUE;

            for(Candidate cand : candPoolIR){
                if(cand.getStatus() == 1){
                    // update maximum votes
                    if(curMaxBallotNum < cand.getBallots().size()){
                        curMaxBallotNum = cand.getBallots().size();
                        mostVotedCands.clear();
                        mostVotedCands.add(cand);
                    }else if(curMaxBallotNum == cand.getBallots().size()){
                        mostVotedCands.add(cand);
                    }

                    //update minimum votes
                    if(curMinBallotNum > cand.getBallots().size()){
                        curMinBallotNum = cand.getBallots().size();
                        leastVotedCands.clear();
                        leastVotedCands.add(cand);
                    }else if(curMinBallotNum == cand.getBallots().size()){
                        leastVotedCands.add(cand);
                    }

                }
            }

            // all candidates have the same number of votes
//            if(curMaxBallotNum == curMinBallotNum){
//                winner = active.get(rand.nextInt(active.size())).getName();
//                // System.out.println("winner: " + winner);
//                break;
//            }

            // System.out.println("max and min: " + curMaxBallotNum + " " + curMinBallotNum);

            // majority
            if(curMaxBallotNum > numOfBallots/2){
                winner = mostVotedCands.get(0).getName();
                // System.out.println("winner: " + winner);
                break;
            }

            // only two candidates left, elect the person with more votes
            if(active.size() == 2){
                if(active.get(0).getVotes() == active.get(1).getVotes()){
                    winner = active.get(rand.nextInt(2)).getName();
                    // System.out.println("winner: " + winner);
                    break;
                }else{
                    int more = active.get(0).getVotes() > active.get(1).getVotes() ? 0 : 1;
                    winner = active.get(more).getName();
                    // System.out.println("winner: " + winner);
                    break;
                }
            }
            // System.out.println("active.size: " + active.size());

            // go to next round
            round++;

            // redistribute the ballots of the least voted candidate
            int leastCandId = rand.nextInt(leastVotedCands.size());
            auditContent.add(leastVotedCands.get(leastCandId).getName() + " has the least votes, his/her ballots go to the next candidate if he/she is still active.");
            List<Ballot> temp = new ArrayList<Ballot>(leastVotedCands.get(leastCandId).getBallots());

            for(Ballot ballot : temp){
                for(int i = 0; i<ballot.getVotes().length; i++) {
                    if (ballot.getVotes()[i] == ballot.getCurRound() + 1 && candPoolIR.get(i).getStatus() == 1) {
                        ballot.setCurRound(ballot.getCurRound() + 1);
                        candPoolIR.get(i).addBallot(ballot);
                        leastVotedCands.get(leastCandId).getBallots().remove(ballot);
                        auditContent.add("ballot id = " + ballot.getId() + " is redistributed to candidate " + candPoolIR.get(i).getName());
                        break;
                    }else if(ballot.getVotes()[i] == ballot.getCurRound() + 1 && candPoolIR.get(i).getStatus() == 0){
                        // the next candidate of this ballet is eliminated
                        i = -1;
                        ballot.setCurRound(ballot.getCurRound() + 1);
                    }
                }
            }

            // eliminate this candidate
            leastVotedCands.get(leastCandId).setStatus(0);
        }

        auditContent.add("-----------------------------------final winner -----------------------------------");
        auditContent.add("winner: " + winner);
        screenContent.add("-----------------------------------final winner -----------------------------------");
        screenContent.add("winner: " + winner);

        String auditFileName = Calendar.getInstance().getTime().toString()+"--[ " + input+ " Audit].txt";
        String summaryFileName = Calendar.getInstance().getTime().toString()+"--[ " + input+ " Summary].txt";
        Util.writeAuditFile(auditFileName, auditContent);
        Util.writeAuditFile(summaryFileName,screenContent);
        Util.printScreen(screenContent);
    }

    // only use for test for IR logs
    public static void main(String args[]) throws IOException {

        IRVoting irVoting = new IRVoting("irTest4.csv");
        irVoting.run();

        irVoting.makeLog(1);

    }
}