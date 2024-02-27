package GUI;

import javax.swing.*;
import javax.swing.JFrame;
import static javax.swing.JOptionPane.*;

import Logic.Transaction;
import Logic.Export;
import Logic.User;

public class Alert{
	
	public Alert(){
		
	}
	
	/*
		Alert displayed upon successful login.
	*/
	public void loginSuccess(JFrame frame){
		String title = "Login successful";
		String message = "Logged in successfully.";
		showMessageDialog(frame, message, title, PLAIN_MESSAGE);
	}
	
	/*
		alert displayed when wrong login credentials are entered in login. 
	*/
	public void loginFailed(JFrame frame){
		String title = "Login failed";
		String message = "Incorrect username/password. Please try again.";
		showMessageDialog(frame, message, title, WARNING_MESSAGE);
	}
	
	
	/* 
		displayed when logout button is pressed to confirm logout.
	*/
	public void logoutAlert(JFrame frame, User user){
		String title = "Logout";
		String message = "Are you sure you want to logout?";
		int answer;
		answer = showConfirmDialog(frame, message, title, YES_NO_OPTION);
		if(answer == JOptionPane.YES_OPTION){
			frame.dispose();
			user = null; 
			System.gc(); //Garbage collection.
			Login login = new Login();
		}
	}
	
	/*
		Alert displayed when x is pressed to confirm application exit.
	*/
	public void exitAlert(JFrame frame){
		String title = "Exit";
		String message = "Are you sure you want to exit?";
		int answer;
		answer = showConfirmDialog(frame, message, title, YES_NO_OPTION);
		if(answer == JOptionPane.YES_OPTION){
			System.exit(0);
		}
	}
	
	/*
		Displayes information on selected transaction in transaction history table.
	*/
	public void transactionInfo(JFrame frame, int selectedRow, Transaction transaction){
		String title = "Transaction details";
		String message = "Transaction date: " + transaction.getDate() 
						+ "\n" + "\n" + "Transaction amount: " + transaction.getAmount()
						+ "\n" + "\n" + "Nature of transaction: " + transaction.getNature()
						+ "\n" + "\n" + "Transaction note: " + transaction.getNote();
		showMessageDialog(frame, message, title, PLAIN_MESSAGE);
	}
	
	public void exportPrompt(JFrame frame){
		String title = "Export transactions";
		String[] options = {".csv", ".xlsx"};
		String message = "Choose type: ";
		int answer;
		answer = JOptionPane.showOptionDialog(frame, message, title, 0, 2, null, options, options[0]);
		
		Export export;
		
		if(answer == 0){
			JOptionPane.showMessageDialog(frame, "Exporting transactions to .csv...");
			export = new Export();
		} if(answer == 1){
			JOptionPane.showMessageDialog(frame, "Exporting transactions to .xlsx...");
			export = new Export("xlsx");
		}
		
	}

}