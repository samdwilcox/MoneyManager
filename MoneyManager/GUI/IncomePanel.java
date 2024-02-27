package GUI;

import javax.swing.*;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JComboBox;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.math.BigDecimal;

import Logic.User;
import Logic.Calculator;
import Logic.Transaction;
import Logic.MoneyDB;

public class IncomePanel extends JPanel{
	
	private JLabel changeConfirm;
	private JLabel noInput;
	
	private String natures[] = {"", "Work", "Service", "friends/family", "Goods", "Other"}; //Empty index for default setting.
	
	private Calculator calc;
	private Transaction transaction;

	private MoneyDB db;

	public IncomePanel(int x, int y, User user, Toolbar t){
		calc = new Calculator(user);
		db = new MoneyDB();

		this.setLayout(null);
		this.setBackground(Color.white);
		this.setBounds(0, 45, x, y - 45);
		
		JLabel incomeLabel = new JLabel();
		incomeLabel.setText("Add income: ");
		incomeLabel.setBounds(25, 50, 150, 25);
		incomeLabel.setForeground(Color.black);
		this.add(incomeLabel);
		
		JTextField incomeInput = new JTextField();
		incomeInput.setBounds(200, 50, 200, 25);
		this.add(incomeInput);
		
		JLabel incomeNatureLabel = new JLabel();
		incomeNatureLabel.setText("Nature of Transaction: ");
		incomeNatureLabel.setBounds(25, 85, 150, 25);
		incomeNatureLabel.setForeground(Color.black);
		this.add(incomeNatureLabel);
		
		JComboBox incomeNature = new JComboBox(natures);
		incomeNature.setBounds(200, 85, 150, 25);
		incomeNature.setSelectedIndex(0);
		incomeNature.setForeground(Color.black);
		this.add(incomeNature);
		
		JLabel incomeNoteLabel = new JLabel();
		incomeNoteLabel.setText("Notes:");
		incomeNoteLabel.setBounds(25, 120, 150, 25);
		incomeNoteLabel.setForeground(Color.black);
		this.add(incomeNoteLabel);
		
		JTextField incomeNote = new JTextField();
		incomeNote.setBounds(200, 120, 200, 25);
		this.add(incomeNote);
		
		
		JLabel expenseLabel = new JLabel();
		expenseLabel.setText("Add expense: ");
		expenseLabel.setBounds(25, 210, 110, 25);
		expenseLabel.setForeground(Color.black);
		this.add(expenseLabel);
		
		JTextField expenseInput = new JTextField();
		expenseInput.setBounds(200, 210, 200, 25);
		this.add(expenseInput);
		
		JLabel expenseNatureLabel = new JLabel();
		expenseNatureLabel.setText("Nature of Transaction: ");
		expenseNatureLabel.setBounds(25, 245, 150, 25);
		expenseNatureLabel.setForeground(Color.black);
		this.add(expenseNatureLabel);
		
		JComboBox expenseNature = new JComboBox(natures);
		expenseNature.setBounds(200, 245, 150, 25);
		expenseNature.setSelectedIndex(0);
		expenseNature.setForeground(Color.black);
		this.add(expenseNature);

		JLabel expenseNoteLabel = new JLabel();
		expenseNoteLabel.setText("Notes: ");
		expenseNoteLabel.setBounds(25, 280, 150, 25);
		expenseNoteLabel.setForeground(Color.black);
		this.add(expenseNoteLabel);
		
		JTextField expenseNote = new JTextField();
		expenseNote.setBounds(200, 280, 200, 25);
		this.add(expenseNote);
		
		
		
		changeConfirm = new JLabel();
		changeConfirm.setText("Income/Expense added.");
		changeConfirm.setBounds(350, 445, 200, 25);
		changeConfirm.setForeground(Color.green);
		this.add(changeConfirm);
		
		noInput = new JLabel();
		noInput.setText("Please and an income/expense.");
		noInput.setBounds(350, 445, 200, 25);
		noInput.setForeground(Color.red);
		this.add(noInput);
		
		JButton enterButton = new JButton();
		enterButton.setText("Submit");
		enterButton.setBounds(200, 445, 75, 25);
		enterButton.setForeground(Color.black);
		enterButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				//Input for income
				String input = incomeInput.getText();
				BigDecimal d = new BigDecimal("0.0");
				if(!input.equals("")){
					d = BigDecimal.valueOf(Double.valueOf(input));
					
					transaction = new Transaction();
					transaction.setType(true); //True for income, false for expense.
					transaction.setAmount(d);
					transaction.setNature(String.valueOf(incomeNature.getSelectedItem()));
					transaction.setNote(incomeNote.getText());
					transaction.setUser_id(user.getUser_id());
					
					user.transactions.add(transaction);

					db.addTransaction(user, transaction);
					
					System.out.println(String.valueOf(transaction.getAmount()));
					System.out.println(transaction.getNature());
					System.out.println(transaction.getNote());
					
					calc.addIncome(d, user);
				}
				
				//Input for expenses
				String exInput = expenseInput.getText();
				BigDecimal b = new BigDecimal("0.0");
				if(!exInput.equals("")){
					b = BigDecimal.valueOf(Double.parseDouble(exInput));
					
					transaction = new Transaction();
					transaction.setType(false); //True for income, false for expense;
					transaction.setAmount(b);
					transaction.setNature(String.valueOf(expenseNature.getSelectedItem()));
					transaction.setNote(expenseNote.getText());
					transaction.setUser_id(user.getUser_id());
					
					
					user.transactions.add(transaction);
					db.addTransaction(user, transaction);
					
					System.out.println(String.valueOf(transaction.getAmount()));
					System.out.println(transaction.getNature());
					System.out.println(transaction.getNote());
					
					calc.addExpense(b, user);
				}
				
				System.out.println(user.getBalance());
				
				//Resets all input text fields and combo boxes.
				incomeInput.setText("");
				incomeNature.setSelectedIndex(0);
				incomeNote.setText("");
				
				expenseInput.setText("");
				expenseNature.setSelectedIndex(0);
				expenseNote.setText("");
				
				if(input.equals("") && exInput.equals("")){
					changeConfirm.setVisible(false);
					noInput.setVisible(true);
				} else {
					noInput.setVisible(false);
					changeConfirm.setVisible(true);
				}

				t.updateGUI(user.getBalance());
			}
		});
		this.add(enterButton);
	
	}
	
	public void updateGUI(){
		System.out.println("Update");
		
		changeConfirm.setVisible(false);
		noInput.setVisible(false);
	}

}