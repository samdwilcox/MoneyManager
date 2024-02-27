package GUI;

import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import GUI.Alert;

import Logic.User;
import Logic.MoneyDB;

public class Login extends Window{
	
	private JTextField usernameField;
	private JPasswordField passwordField;
	
	private Alert alert;
	
	private User user;
	private MoneyDB db;
	
	public Login(){

		db = new MoneyDB();
		
		loadFrame("MoneyManager - Login");
		
		JLabel usernameLabel = new JLabel();
		usernameLabel.setText("Username: ");
		usernameLabel.setBounds(x/2 - 200, y/2 - 100, 100, 25);
		frame.add(usernameLabel);
		
		usernameField = new JTextField();
		usernameField.setBounds(x/2 - 110, y/2 - 100, 220, 25);
		frame.add(usernameField);
		
		JLabel passwordLabel = new JLabel();
		passwordLabel.setText("Password: ");
		passwordLabel.setBounds(x/2 - 200, y/2 - 60, 100, 25);
		frame.add(passwordLabel);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(x/2 - 110, y/2 - 60, 220, 25);
		frame.add(passwordField);
		
		JButton submitButton = new JButton();
		submitButton.setText("Enter");
		submitButton.setBounds(x/2 - 50, y/2, 100,  25);
		submitButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				enter();
			}
		});
		frame.add(submitButton);
		frame.getRootPane().setDefaultButton(submitButton); //Sets default button to listen to enter.
		
		JButton exitButton = new JButton();
		exitButton.setText("Exit");
		exitButton.setBounds(x - 150, y - 100, 100, 25);
		exitButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				alert = new Alert();
				alert.exitAlert(frame);
			}
		});
		frame.add(exitButton);
	}
	
	public void enter(){
		String usernameVal = usernameField.getText();
		String passwordVal = String.valueOf(passwordField.getPassword());
		
		
		
		boolean match = db.verifyUser(usernameVal, passwordVal);

		if(match == true){
			user = new User();
			db.getUserInfo(user);
			alert = new Alert();
			alert.loginSuccess(frame);
			close(user);
		} else {
			alert = new Alert();
			alert.loginFailed(frame);
			passwordField.setText("");
		}
		
	}
	
	public void close(User user){
		frame.dispose();
		Dashboard dash = new Dashboard(user);
	}
}