import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This is a class to run the OPL election method with the run function. Inherits the LogUtil for logging purposes.
 */

public class OPLVoting implements LogUtil {
    /**
     * The Candidates involved in the election stored in a list.
     */

    public static List<Candidate> candPoolOPL = new ArrayList<Candidate>();

    /**
     * The parties involved in the election stored in a list.
     */

    public static List<Party> partyPoolOPL = new ArrayList<Party>();

    /**
     * The lines of the audit file to be written stored in a list.
     */


    private static ArrayList<String> audit_file = new ArrayList<String>();

    /**
     * The lines of the output to be written stored in a list.
     */

    private static ArrayList<String> std_output = new ArrayList<String>();


    /**
     * The Parties involved stored additionally in a HashMap to make accessing it easier.
     */

    public static HashMap<String, Party> partyMapOPL = new HashMap<>();

    private long numOfCand = 0;
    private String input = "NOT DEFINED";
    private long totalSeats = 0;
    private long totalBallots = 0;


    /**
     * Constructor to run the OPL election
     * @param input The filename storing the election file.
     */

    public OPLVoting(String input) {
        this.input = input;
    }

    /**
     * Parses the file specified at the filename and creates the Ballot and Candidate and Party classes from the file.
     * Does preliminary distribution of the Ballots. Reads each line of the file and sets the member variables to
     * specified values and turns the ballot lines into actual Ballot instances.
     *
     * @param fileLocation Location of the file.
     * @throws IOException
     */
    private void parseInputFileOPL(String fileLocation) throws IOException {
        FileInputStream inputStream = new FileInputStream(fileLocation);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        int lineNum = 0;
        String content = null;
        while((content = bufferedReader.readLine()) != null) {
            // TODO: Implement any comprehensive logic here(like , split functionality)
            lineNum++;

            if (lineNum == 1) {

                //TODO: record  OPL
                audit_file.add("Method: OPL");
                std_output.add("Method: OPL");

            } else if (lineNum == 2) {

                this.numOfCand = Long.parseLong(content);
                audit_file.add("Number of Candidates: " + content);
                std_output.add("Number of Candidates: " + content);

            } else if (lineNum == 3) {

                String candInfo = content;

                // pattern matching using java reg exp i.e. [name1, party1], [name2, party2] ...
                Pattern pattern = Pattern.compile("\\[(.+?)\\]");
                Matcher matcher = pattern.matcher(content);

                for (int i = 0; i < numOfCand; i++) {
                    // pattern matching using java reg exp
                    if (matcher.find()) {
                        String info = matcher.group(1);
                        String name = info.split(",")[0].trim();
                        String party = info.split(",")[1].trim();
                        // if we don't have this in the hash map yet, we build a new K-V pair.
                        if (!partyMapOPL.containsKey(party)) {
                            // instantiate a new party obj for the hash map
                            Party newParty = new Party(party);

                            // update the party map and the party list
                            partyMapOPL.put(party, newParty);
                            partyPoolOPL.add(newParty);

                            // create a new candidate with name and its party
                            Candidate temp = new Candidate(name, newParty);
                            candPoolOPL.add(temp);

                            // add the candidate to the party
                            newParty.addCandidate(temp);


                        } else {

                            // if the party has already exist, find it from the map
                            Candidate temp = new Candidate(name, partyMapOPL.get(party));
                            candPoolOPL.add(temp);

                            // add the candidate to the party
                            partyMapOPL.get(party).addCandidate(temp);

                        }
                    }
                }
//                audit_file.add("Candidates: " + candInfo);


            } else if (lineNum == 4) {

                totalSeats = Long.parseLong(content);
                audit_file.add("Number of total seats: " + content);
                std_output.add("Number of total seats: " + content);

            } else if (lineNum == 5) {

                totalBallots = Long.parseLong(content);
                audit_file.add("Number of total ballots: " + content);
                std_output.add("Number of total ballots: " + content);

            } else {
                String[] info = content.split(",");
                int i = 0;

                // traverse the info[] to find the winner, i is the index of the winner
                while (i < info.length) {
                    if (info[i].equals("1")) {
                        break;
                    }
                    i++;
                }
                Candidate targetCand = candPoolOPL.get(i);
                targetCand.addVotes(1);
                targetCand.getParty().addTotalCounts(1);

                String addvotesInfo = "";
                addvotesInfo += targetCand.getName() + " and party " + targetCand.getParty().getName() + " gets 1 vote.";
                audit_file.add(addvotesInfo);

            }
        }
        //close inputStream and bufferedReader
        audit_file.add("-----------------------------------find winner process-----------------------------------");


        inputStream.close();
        bufferedReader.close();
    }

    /**
     * Handles post file parsing election steps. That includes calculating the quota, distributing the seats to the
     * parties and then handling the remaining Ballots and Seats at the end.
     */

    public void findWinner() {
        long quota = (long) (totalBallots / totalSeats);

        audit_file.add("Get Quota: " + totalBallots + " / " + totalSeats +  " = " + quota);

        double seatsLeft = totalSeats;

        Iterator it = partyMapOPL.entrySet().iterator();
        int counter = 0;

        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            Party temp =  (Party) pair.getValue();
            String pName = (String) pair.getKey();
            // for the first iteration, set obtained seats for each party/
            long giveOut =  (temp.getTotalCounts() / quota);

            audit_file.add(pName + " gets " + giveOut + " seats.");
            seatsLeft -= giveOut;


            temp.addSeats(giveOut);

            // update the remainder to the party's current
            long remainder = temp.getTotalCounts() % quota;
            temp.setTotalCounts(remainder);

            audit_file.add(pName + " has " + remainder + " ballots remaining,");
        }

        // iteration #2
        System.out.println("___________________________________________________________________");

        Collections.sort(partyPoolOPL);

        for (int i = 0; i < seatsLeft; i++) {
            partyPoolOPL.get(i).addSeats(1);
            audit_file.add(partyPoolOPL.get(i).getName() + " gets " + "1 seat in remainder stage.");
        }

        int leng = partyPoolOPL.size();
//        System.out.println("LENG: " + leng);
        // TODO: how many seats each party has.
        for (int i = 0; i < leng; i++) {

            String winnerInfo = "\n";

            double obtainedSeats = partyPoolOPL.get(i).getSeatCounts();

//                System.out.println("~~~~~~~~~~~" + obtainedSeats);

            winnerInfo += "Party: " + partyPoolOPL.get(i).getName() + " ------ Winners: ";
            System.out.print("Party: " + partyPoolOPL.get(i).getName() + " ------ Winners: ");

            Collections.sort(partyPoolOPL.get(i).getCandidates());
            Party tempParty = partyPoolOPL.get(i);


            // print the winners info
            for (int j = 0; j < Math.min(obtainedSeats, tempParty.getCandidates().size()); j++) {
                Candidate temp = partyPoolOPL.get(i).getCandidates().get(j);
                winnerInfo += " [" + temp.getName() + ", " + temp.getVotes() + "] ";
                System.out.print(" [" + temp.getName() + ", " + temp.getVotes() + "] ");
            }

            System.out.println();
            audit_file.add(winnerInfo);
            std_output.add(winnerInfo);
        }

        String summaryInfo = "-----------------------------------Candidates summary-----------------------------------\n";
        for (int i = 0; i < candPoolOPL.size(); i++) {
            Candidate temp = candPoolOPL.get(i);
            summaryInfo += "Candidates " + temp.getName() + " has " + (long)temp.getVotes() + " votes.";
            summaryInfo += "\n";
        }
        audit_file.add(summaryInfo);
        std_output.add(summaryInfo);
    }



    public void makeLog(int caseNum) {
        // Identify a specific log with caseNum
        if (caseNum == 1) {
            logger.log(Level.INFO, "Log Message Test");
        } else {
            logger.log(Level.INFO, "Log Test INFO");
        }
    }

    /**
     * Executes an OPL election with the filename stored at input. It parses the file and finds the winners of the
     * election. Then it writes the lines to an audit and summary file and prints a summary to the screen.
     */

    public void run(){
        try {

            parseInputFileOPL(this.input);

            findWinner();

            String auditFileName = Calendar.getInstance().getTime().toString()+"--[ " + input+ " Audit].txt";
            String summaryFileName = Calendar.getInstance().getTime().toString()+"--[ " + input+ " Summary].txt";
            Util.writeAuditFile(auditFileName, audit_file);
            Util.writeAuditFile(summaryFileName, std_output);
            Util.printScreen(std_output);

        } catch (Exception e) {

            e.printStackTrace();

        }
    }

    //TODO: any OPL logic goes here

    // only test for OPL logs here
    public static void main(String[] args) {
        OPLVoting oplVoting = new OPLVoting("SmallElection3.csv");
        try {
            oplVoting.parseInputFileOPL("SmallElection3.csv");
            oplVoting.run();
        } catch (IOException e) {
            System.out.println("Error reading file");
        }

    }
}
