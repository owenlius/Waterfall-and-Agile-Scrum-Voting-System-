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
    /**
     * The lines of the invalid ballots to be written stored in a list.
     */
    public ArrayList<String> invalidatedBallot;
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
        invalidatedBallot = new ArrayList<String>();
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
                // test if the ballot is invalid
                // the ballots need to have at least half of the candidates ranked for the ballot to be valid
                String[] ballotTest = content.split(",");
                int validBallotCount = content.length() - numOfCandidates + 1;

                if (validBallotCount < numOfCandidates/2.0) {
                    invalidatedBallot.add("invalid " + content);

                    continue;
                }
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

        // a list of updating process in all rounds
        List<int[]> updateOfEachRound = new ArrayList ();

        // a list of voting result in all rounds
        List<int[]> totalOfEachRound = new ArrayList ();

        // start voting
        while(true){
            // System.out.println("*******round: " + round);
            auditContent.add("*******round: " + round);

            // record the number of changing votes
            int[] changeOfVotesOfAllCand = new int[numOfCandidates];

            // record the total number of votes after updating of each round
            int[] totalVotesOfAllCand = new int[numOfCandidates];
            int idx = 0;

            active.clear();
            for(Candidate c : candPoolIR) {
                if (c.getStatus() == 1)
                    active.add(c);
            }

            // output all votes of every candidate
            for(Candidate c : candPoolIR){
                idx++;
                if(c.getStatus() == 0)
                    continue;

                StringBuilder sb = new StringBuilder(c.getName() + " gets " + c.getBallots().size() + " votes, ballots are :");
                for(Ballot b : c.getBallots()){
                    sb.append(b.getId() + " ");
                }
                totalVotesOfAllCand[idx-1] = c.getBallots().size();

                auditContent.add(sb.toString());
            }

            totalOfEachRound.add(totalVotesOfAllCand);

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

            // update the subtraction number of votes belonging to the candidate with least votes
            for(int i = 0; i<changeOfVotesOfAllCand.length; i++){
                if(candPoolIR.get(i).getName().equals(leastVotedCands.get(leastCandId).getName())){
                    changeOfVotesOfAllCand[i] = -leastVotedCands.get(leastCandId).getBallots().size();
                }
            }

            for(Ballot ballot : temp){
                for(int i = 0; i<ballot.getVotes().length; i++) {
                    if (ballot.getVotes()[i] == ballot.getCurRound() + 1 && candPoolIR.get(i).getStatus() == 1) {
                        ballot.setCurRound(ballot.getCurRound() + 1);
                        candPoolIR.get(i).addBallot(ballot);
                        leastVotedCands.get(leastCandId).getBallots().remove(ballot);
                        auditContent.add("ballot id = " + ballot.getId() + " is redistributed to candidate " + candPoolIR.get(i).getName());

                        // update the addition number of votes from the candidate with least votes
                        changeOfVotesOfAllCand[i]++;

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

            updateOfEachRound.add(changeOfVotesOfAllCand);
        }

        auditContent.add("-----------------------------------final winner -----------------------------------");
        auditContent.add("winner: " + winner);
        screenContent.add("-----------------------------------final winner -----------------------------------");
        screenContent.add("winner: " + winner);

        String auditFileName = "Election_"+Calendar.getInstance().getTime().toString()+"_audit.txt";
        String summaryFileName ="Election_"+ Calendar.getInstance().getTime().toString()+"_Summary.txt";
        String invalidatedBallotsName = "invalidated_"+Calendar.getInstance().getTime().toString()+".txt";
        Util.writeAuditFile(invalidatedBallotsName, invalidatedBallot);
        Util.writeAuditFile(auditFileName, auditContent);
        Util.writeAuditFile(summaryFileName,screenContent);
        Util.printScreen(screenContent);

        // print voting process to screen
        Util.printScreen(getAllRecords(updateOfEachRound, totalOfEachRound));

    }

    public ArrayList<String> getAllRecords(List<int[]> updateOfEachRound, List<int[]> totalOfEachRound){
        ArrayList<String> eachRoundInfo = new ArrayList ();

        int colWidth = 15;
        eachRoundInfo.add("");
        eachRoundInfo.add("----------------update of votes belonging to all candidates in each round----------------");
        eachRoundInfo.add("");

        // the names of all candidates
        StringBuilder sb = new StringBuilder();
        sb.append(getSpace(colWidth));
        for(Candidate cand : candPoolIR){
            sb.append(cand.getName() + getSpace(colWidth - cand.getName().length()));
        }
        eachRoundInfo.add(sb.toString());

        // initial voting statistics of all candidates
        if(totalOfEachRound.size() > 0){
            sb.setLength(0);
            sb.append("round1:" + getSpace(colWidth - 7));
            for(int i = 0; i<numOfCandidates; i++){
                sb.append(totalOfEachRound.get(0)[i] + getSpace(colWidth - String.valueOf(totalOfEachRound.get(0)[i]).length()));
            }
            eachRoundInfo.add(sb.toString());
        }

        // update total votes of each candidate by adding/subtracting
        for(int i = 0; i<updateOfEachRound.size(); i++){
            // add an empty line to split each round
            eachRoundInfo.add("");

            sb.setLength(0);
            String roundLabelUpdate = "round" + (i+2) + ":update";
            String roundLabelTotal =  getSpace(roundLabelUpdate.length() - 6) + "total";

            // the update process of this round, including addition and subtraction of any candidate
            sb.append(roundLabelUpdate + getSpace(colWidth - roundLabelUpdate.length()));
            for(int j = 0; j<numOfCandidates; j++){
                sb.append(updateOfEachRound.get(i)[j] + getSpace(colWidth - String.valueOf(updateOfEachRound.get(i)[j]).length()));
            }
            eachRoundInfo.add(sb.toString());

            // the total voting result after this round
            sb.setLength(0);
            sb.append(roundLabelTotal + getSpace(colWidth - roundLabelTotal.length()));
            for(int j = 0; j<numOfCandidates; j++){
                sb.append(totalOfEachRound.get(i + 1)[j] + getSpace(colWidth - String.valueOf(totalOfEachRound.get(i + 1)[j]).length()));
            }
            eachRoundInfo.add(sb.toString());
        }

        return eachRoundInfo;
    }

    // make table more structured
    // return a string filled with all space, the length of which is "width"
    public String getSpace(int width){
        switch (width){
            case 2: return "  ";
            case 3: return "   ";
            case 4: return "    ";
            case 5: return "     ";
            case 6: return "      ";
            case 7: return "       ";
            case 8: return "        ";
            case 9: return "         ";
            case 10: return "          ";
            case 11: return "           ";
            case 12: return "            ";
            case 13: return "             ";
            case 14: return "              ";
            case 15: return "               ";
            default: return " ";
        }
    }

    // only use for test for IR logs
    public static void main(String args[]) throws IOException {

        IRVoting irVoting = new IRVoting("irTest4.csv");
        irVoting.run();

        irVoting.makeLog(1);
      }

}
