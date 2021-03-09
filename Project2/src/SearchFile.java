import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SearchFile extends JFrame {
	
	public String path;
	public String info;
	private JButton find;
	private JTextField location;

	/**
	 * SearchFile inherits from JFrame. It is window that with a textbox from which the user can input the file argument.
	 * There is a confirm button the user can click when he/she is done entering the argument.
	 */
	
	public SearchFile() {
		
		super("searchFile");
		setSize(370,270);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		FlowLayout flow = new FlowLayout(FlowLayout.RIGHT);
		setLayout(flow);
		JPanel row1 = new JPanel();
		JLabel toLabel = new JLabel("file location: ");
		row1.add(toLabel);
		location = new JTextField(21);
		location.setVisible(true);
		row1.add(location);
		add(row1);
		
		JPanel row2 = new JPanel();
		find = new JButton("Confirm");
		
		row2.add(find);
		add(row2);
		setVisible(true);
		clickEvent();
	}

	/**
	 * This creates a listener for the Confirm button. When the user clicks it, it will extract the file path entered
	 * and call VoteCounter.
	 */
	
	private void clickEvent() {
		this.find.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				path = location.getText();
				VoteCounter voteCounter = new VoteCounter(path);				
				System.out.println(getPath());
				dispose();
			}
		});
	}

	/**
	 * Returns the path name the user entered.
	 * @return path: The file path the user entered.
	 */
	
	public String getPath() {
		return this.path;
	}
	
	public static void main(String args[]) {
		SearchFile s1 = new SearchFile();
		
	}
}
