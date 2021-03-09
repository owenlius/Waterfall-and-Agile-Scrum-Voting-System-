import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.*;
import javax.swing.*;

//import com.sun.java.swing.plaf.windows.resources.windows;

//import javafx.scene.control.Tab;


/**
 * This class is the entry point for the system. If this class is ran with a command line file name argument,
 * it will skip the graphical user interface and proceed directly to the election process. If there was no command line
 * argument, this class with provide a simple awt graphical interface. The user can either enter the file name through
 * popup or open a file explorer.
 */
class MyMenuDemo {

	public static boolean flag = false;
	public static String filePath = null; // the file path if the user uses the search option
	private static boolean command = false; // tracks whether user entered command line arguments. Used for testing.

	private Frame f;
	private MenuBar bar;
	private Menu fileMenu, subMenu;
	//	private MenuItem saveItem,subItem1,subItem2;
	private MenuItem openItem, closeItem, searchItem;
	private String displayInfo = null;
	private SearchFile searchFile;

	private FileDialog openDia, saveDia;

	private TextArea ta;

	private File file;

	MyMenuDemo() {
		init();
	}

	/**
	 * This generates the initial gui window. creates a Frame that is shown to the user.
	 * Additionally, it also creates menu items that allows users to select whether to input filename or search for it
	 * with a file finder.
	 */

	public void init() {
		f = new Frame("my window"); // generate start window frame.
		f.setBounds(300, 100, 500, 600);
		//f.setLayout(new FlowLayout());

		bar = new MenuBar();

		ta = new TextArea();
		ta.setEditable(false);
		ta.setText("Please click on File in the menu bar to run vote counting system!");
		ta.setForeground(new Color(255, 0, 0));

		fileMenu = new Menu("File");


		openItem = new MenuItem("Open from file manager");  // give menu options for user.
		searchItem = new MenuItem("Input open file");
		closeItem = new MenuItem("quit");

//		subMenu.add(subItem1);
//		subMenu.add(subItem2);


		fileMenu.add(openItem);
		fileMenu.add(searchItem);

//		fileMenu.add(subMenu);
		fileMenu.add(closeItem);
		bar.add(fileMenu);

		f.setMenuBar(bar);

		openDia = new FileDialog(f, "my open", FileDialog.LOAD);

		f.add(ta);

		myEvent();
		flag = true;
		f.setVisible(true);

	}

	/**
	 * This is a test function to experiment with displays.
	 */

	private void myDisplay() {
		// System.out.println(displayInfo);
		if (displayInfo != null) {
			ta.setBackground(new Color(0, 255, 0));
			ta.setText(displayInfo);
		}
	}

	/**
	 * This is a private function. It creates a SearchFile instance that is used to as a file explorer for the user
	 * to find files on the disk/directory.
	 */

	private void myEvent() {
		searchItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				searchFile = new SearchFile(); // create a window for the user to input file name.

			}
		});

		openItem.addActionListener(new ActionListener() {


			// open file from manager
			public void actionPerformed(ActionEvent e) {
				openDia.setVisible(true);
				String dirPath = openDia.getDirectory();//获取文件路径, get the directory path
				filePath = dirPath;
				// System.out.println(dirPath);
				String fileName = openDia.getFile();//获取文件名称, get the file name that the user had entered.
				//System.out.println(dirPath +"++"+ fileName);

				//如果打开路径 或 目录为空 则返回空
				if (dirPath == null || fileName == null) {
					return;
				}


				//System.out.println(fileName);
				VoteCounter voteCounter = new VoteCounter(dirPath+fileName); // this is questionable.

				System.exit(0);

//				file = new File(dirPath,fileName);
//				
//				try {
//						BufferedReader bufr = new BufferedReader(new FileReader(file));
//						
//						String line = null;
// 
//						while( (line = bufr.readLine())!= null) {
//							ta.append(line +"\r\n");
//						}
//						bufr.close();
//				}
//				catch (IOException ex) {
//					throw new RuntimeException("文件读取失败！");
//				}

			}
		});

		closeItem.addActionListener(new ActionListener() {
			/* (non-Javadoc)
			 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
			 */
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});


		f.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		}); // listen for window closing.
	}

	/**
	 *
	 * @return command is a boolean for testing to see if there was command line argument.
	 */
	public static boolean getCommand() {
		return command;
	}

	/**
	 * This is a wrapper test function to test myEvent.
	 * @return boolean value indicating the file finder was opened.
	 */

	public boolean testEvent() {
		myEvent();
		return openDia.isEnabled();

	}

	/**
	 * This is the entry function for the system. It will launch a GUI if there is no input from args. If args
	 * is not empty, it will take the first entry of args and attempt to read that filename.
	 *
	 * @param args Possible file name argument. Only args[0] is checked so the command line input must be precise.
	 */

	public static void main(String[] args) {
		if (args.length > 0) {
			command = true;
			VoteCounter voteCounter = new VoteCounter(args[0]);
			System.exit(0);
			// System.out.println("got to here.");

		} else {
			command=false;
			new MyMenuDemo();
		}
	}
}
