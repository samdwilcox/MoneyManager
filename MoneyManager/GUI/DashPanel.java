package GUI;

import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.event.*;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;

import java.math.BigDecimal;

import GUI.Toolbar;

import Logic.User;
import Logic.Transaction;
import Logic.Calculator;
import Logic.MoneyDB;

public class DashPanel extends JPanel{
	
	private BigDecimal balance;
	private BigDecimal salary;
	private BigDecimal hourlyRate;
	private BigDecimal taxBracket;
	private String taxBracketString;
	private BigDecimal taxPaid;
	private BigDecimal salaryAfterTax;
	
	private JLabel salaryLabel;
	private JLabel hourlyLabel;
	private JLabel taxLabel;
	private JLabel taxPaidLabel;
	private JLabel remainingSalaryLabel;
	
	private DefaultTableModel model;
	private ListSelectionModel select;
	
	private User user;
	private Alert alert;
	private Calculator calc;
	private MoneyDB db;

	private Toolbar t;
	
	public DashPanel(int x, int y, User user, Toolbar t, JFrame frame){
		this.user = user;

		this.t = t;
		
		this.setLayout(null);
		this.setBackground(Color.white);
		this.setBounds(0, 45, x, y - 45);

		calc = new Calculator(user);
		db = new MoneyDB();
		
		balance = user.getBalance();
		
		salary = user.getSalary();
		if(salary == null) salary = new BigDecimal("0.00");
		
		hourlyRate = user.getHourlyRate();
		if(hourlyRate == null) hourlyRate = new BigDecimal("0.00");
		
		taxBracket = user.getTaxBracket();
		if(taxBracket == null) taxBracket = new BigDecimal("0.00");
		
		taxBracketString = user.getTaxBracketString();
		if(taxBracketString == null) taxBracketString = "";

		taxPaid = user.getTaxPaid();
		if(taxPaid == null) taxPaid = new BigDecimal("0.00");
		
		salaryAfterTax = user.getSalaryAfterTax();
		if(salaryAfterTax == null) salaryAfterTax = new BigDecimal("0.00");
		
		
		int space = 10;
		int buff = 25;
		
		salaryLabel = new JLabel();
		salaryLabel.setText("Salary: " + salary + " GBP");
		salaryLabel.setBounds(10, space, 150, 25);
		salaryLabel.setForeground(Color.black);
		this.add(salaryLabel);
		space += buff;
		
		hourlyLabel = new JLabel();
		hourlyLabel.setText("Hourly rate: " + hourlyRate);
		hourlyLabel.setBounds(10, space, 150, 25);
		hourlyLabel.setForeground(Color.black);
		this.add(hourlyLabel);
		space += buff;
		
		taxLabel = new JLabel();
		taxLabel.setText("Tax bracket: " + taxBracketString + " | " + calc.calculateTaxPercentage(user) + "%");
		taxLabel.setBounds(10, space, 250, 25);
		taxLabel.setForeground(Color.black);
		this.add(taxLabel);
		space += buff;
		
		taxPaidLabel = new JLabel();
		taxPaidLabel.setText("Tax paid: " + taxPaid);
		taxPaidLabel.setBounds(10, space, 150, 25);
		taxPaidLabel.setForeground(Color.black);
		this.add(taxPaidLabel);
		space += buff;
		
		remainingSalaryLabel = new JLabel();
		remainingSalaryLabel.setText("Salary after tax: " + salaryAfterTax);
		remainingSalaryLabel.setBounds(10, space, 150, 25);
		remainingSalaryLabel.setForeground(Color.black);
		this.add(remainingSalaryLabel);
		
		
		model = new DefaultTableModel();
		JTable table = new JTable();
		JScrollPane scroll = new JScrollPane(table);
		
		String columnHeaders[] = {"Index", "Type", "Transaction date", "Transaction amount", "Nature"};
		model.setColumnIdentifiers(columnHeaders);
		model.setRowCount(0);
		
		table.setShowHorizontalLines(true);
		table.setGridColor(Color.black);
		table.setCellSelectionEnabled(true);
		table.setDefaultEditor(Object.class, null);
		
		select = table.getSelectionModel();
		select.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		table.setColumnSelectionAllowed(false);
		table.setRowSelectionAllowed(true);
		table.setModel(model);

		db.findTransactions(user);
		populate(model, user);
		
		JLabel tableLabel = new JLabel();
		tableLabel.setText("Transaction history: ");
		tableLabel.setBounds(10, 165, 150, 25);
		tableLabel.setForeground(Color.black);
		this.add(tableLabel);
		
		scroll.setBounds(10, 200, 550, y - 350);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.getViewport().add(table);
		this.add(scroll);
		scroll.setVisible(true);
		
		select.addListSelectionListener( new ListSelectionListener(){
			@Override
			public void valueChanged(ListSelectionEvent e){
				if(e.getValueIsAdjusting()){
					return;
				}
				ListSelectionModel row = (ListSelectionModel) e.getSource();
				
				if(row.isSelectionEmpty()){
					System.out.println("No selection!");
				} else {
					int selectedRow = row.getMinSelectionIndex();
					String rowIndex = model.getValueAt(selectedRow, 0).toString();
					System.out.println("Selected row: " + rowIndex);
					
					Transaction selectedTransaction = user.transactions.get(selectedRow);
					
					alert = new Alert();
					alert.transactionInfo(frame, selectedRow, selectedTransaction);
					
				}
			}
		});
		
		JButton exportButton = new JButton();
		exportButton.setText("Export");
		exportButton.setBounds(475, y - 130, 80, 25);
		exportButton.setForeground(Color.black);
		exportButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				alert = new Alert();
				alert.exportPrompt(frame);
			}
		});
		this.add(exportButton);
		
	}
	
	public void populate(DefaultTableModel model, User user){
		model.setRowCount(0);
		ArrayList<Transaction> list = user.getTransactions();
		for(int i = 0; i < list.size(); i++){
			Object rowData[] = new Object[5];
			
			rowData[0] = i + 1;
			
			if(list.get(i).type == true){
				rowData[1] = "Income";
			} else {
				rowData[1] = "Expense";
			}
			rowData[2] = list.get(i).date;
			rowData[3] = list.get(i).amount;
			rowData[4] = list.get(i).nature;
			model.addRow(rowData);
		}
	}
	
	public void updateGUI(){
		balance = user.getBalance();
		t.updateGUI(balance);

		salary = user.getSalary();
		if(salary == null) salary = new BigDecimal("0.00");
		String salaryOut = String.valueOf(salary);
		
		salaryLabel.setText("Salary: " + salaryOut + " GBP");
		
		hourlyRate = user.getHourlyRate();
		if(hourlyRate == null) hourlyRate = new BigDecimal("0.00");
		String hourlyOut = String.valueOf(hourlyRate);
		
		hourlyLabel.setText("Hourly rate: " + hourlyRate);
		
		taxBracket = user.getTaxBracket();
		if(taxBracket == null) taxBracket = new BigDecimal("0.00");

		taxBracketString = user.getTaxBracketString();
		if(taxBracketString == null) taxBracketString = "";
		
		taxLabel.setText("Tax bracket: " + taxBracketString + " | " + calc.calculateTaxPercentage(user) + "%");
		
		taxPaid = user.getTaxPaid();
		if(taxPaid == null) taxPaid = new BigDecimal("0.00");
		
		taxPaidLabel.setText("Tax paid: " + taxPaid);
		
		salaryAfterTax = user.getSalaryAfterTax();
		if(salaryAfterTax == null) salaryAfterTax = new BigDecimal("0.00");
		
		remainingSalaryLabel.setText("Salary after tax: " + salaryAfterTax);
		
		db.findTransactions(user);
		populate(model, user);
		
	}
	

}