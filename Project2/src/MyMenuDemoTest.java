import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MyMenuDemoTest {
    @Test
    void TestCommandLineIR() {
        String [] args = {"testing/auditrand2.csv"};
        MyMenuDemo.main(args);
        assertTrue(MyMenuDemo.getCommand());

    }

    @Test
    void TestMainOPL() {
        String [] args = {"testing/SmallELection8.csv"};
        MyMenuDemo.main(args);
        assertTrue(MyMenuDemo.getCommand());

    }
    @Test
    void TestMainNoInput() {
        MyMenuDemo.main(new String[10]);
        assertTrue(MyMenuDemo.getCommand());


    }


    @Test
    void TestInit() {
        MyMenuDemo testDemo = new MyMenuDemo();
        testDemo.init();
        assertTrue(testDemo.flag);
    }

    @Test
    void TestEvent(){
        MyMenuDemo testDemo = new MyMenuDemo();
        assertTrue(testDemo.testEvent());

    }


}