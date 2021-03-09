How to run this project

To run just the project (and not the tests) from the terminal

    1. first run git clone https://github.umn.edu/umn-csci-5801-F18/repo-Team3
    2. navigate to the /Project2/src folder
    3. run "javac Util.java LogUtil.java Ballot.java Party.java IRVoting.java OPLVoting.java VoteCounter.java MyMenuDemo.java SearchFile.java"
    4. then run "java MyMenuDemo" for the VoteCounter UI
    4.b. Or run java MyMenuDemo filename.csv
         filepaths are relative (you do not need entire filepath)

Unfortunately, we relied on Junit5 and we couldn’t figure out the right classpath for the jar file because we were using intellij.

How to run the tests and code from intellij. We apologize for the inconvenience.

    1. Open Intellij
    2. One the project in Intellij by Importing it
    3. Make sure the SDK is 10
    4. Finish
    5. Navigate to File at the top
    6. Click on Project Structure
    7. Click on Libraries
    8. Then click on the dependencies tab
    9. Click on the +
    10. Select From Maven
    11. In the text box type org.junit.jupiter:junit-jupiter-api:RELEASE
    12. OK
    13. Not click on build on the top and select Build Project
    14. To run any of the test right click on the XXTest.java files and select run

