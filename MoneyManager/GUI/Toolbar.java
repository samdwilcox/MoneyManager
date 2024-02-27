package GUI;

import javax.swing.*;

import java.awt.Color;
import java.math.BigDecimal;

import Logic.User;

public class Toolbar extends JPanel{

	private BigDecimal balance;
	
	private java.sql.Date date = new java.sql.Date(System.currentTimeMillis());

	private JLabel balanceLabel;

	/*
	 * 	Blank constructor so can be used without creating a whole new toolbar.
	 */
	public Toolbar(){}
	
	/*
		Params x & y are the x and y values of the frame.
	*/
	public Toolbar(int x, int y, User user){
		String username = user.getUsername();

		balance = user.getBalance();
		
		this.setLayout(null);
		this.setBackground(Color.gray);
		this.setBounds(0, 0, x, 45);

		int space = 25;
		int buff = 200;
		
		JLabel userLabel = new JLabel();
		userLabel.setText("User: " + username);
		userLabel.setBounds(space, 10, 100, 25);
		userLabel.setForeground(Color.black);
		this.add(userLabel);
		space += buff;

		balanceLabel = new JLabel();
		balanceLabel.setText("Balance: " + balance + " GBP");
		balanceLabel.setBounds(space, 10, 150, 25);
		balanceLabel.setForeground(Color.black);
		this.add(balanceLabel);
		
		JLabel dateLabel = new JLabel();
		dateLabel.setText(String.valueOf(date));
		dateLabel.setBounds(x - 150, 10, 100, 25);
		dateLabel.setForeground(Color.black);
		this.add(dateLabel);
	}

	public void updateGUI(BigDecimal balance){
		String balanceOut = String.valueOf(balance);
		
		balanceLabel.setText("Balance: " + balanceOut + " GBP");
	}

}