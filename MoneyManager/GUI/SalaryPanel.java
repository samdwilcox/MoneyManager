package GUI;

import javax.swing.*;
import javax.swing.JButton;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.math.BigDecimal;

import Logic.User;
import Logic.Calculator;
import Logic.MoneyDB;

public class SalaryPanel extends JPanel{
	
	private Calculator calc;

	private MoneyDB db;

	public SalaryPanel(int  x, int y, User user){
		this.setLayout(null);
		this.setBackground(Color.white);
		this.setBounds(0, 45, x, y - 45);

		db = new MoneyDB();
		
		JLabel salaryLabel = new JLabel();
		salaryLabel.setText("Annual salary: ");
		salaryLabel.setBounds(25, 50, 150, 25);
		salaryLabel.setForeground(Color.black);
		this.add(salaryLabel);
		
		JTextField salaryInput = new JTextField();
		salaryInput.setBounds(200, 50, 200, 25);
		this.add(salaryInput);
		
		JLabel hoursLabel =  new JLabel();
		hoursLabel.setText("Weekly hours:  ");
		hoursLabel.setBounds(25, 85, 150, 25);
		hoursLabel.setForeground(Color.black);
		this.add(hoursLabel);
		
		JTextField hoursInput = new JTextField();
		hoursInput.setBounds(200, 85, 200, 25);
		this.add(hoursInput);
		
		JButton submit = new JButton();
		submit.setText("Submit");
		submit.setBounds(200, 120, 75, 25);
		submit.setForeground(Color.black);
		submit.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				String input = salaryInput.getText();
				calc = new Calculator(user);
				BigDecimal d = new BigDecimal("0.0");
				if(!input.equals("")){
					d = BigDecimal.valueOf(Double.parseDouble(input));
					
					db.setSalary(user, d);
					
					System.out.println(user.getSalary());
				}
				
				String hrInput = hoursInput.getText();
				int hrs = 0;
				if(!hrInput.equals("")){
					hrs = Integer.valueOf(hrInput);
					
					db.setHours(user, hrs);
					
					BigDecimal hourlyRate = calc.calculateHourlyRate(user);
					db.setHourlyRate(user, hourlyRate);
					
					System.out.println(user.getHours());
				}
				
				BigDecimal taxBracket = calc.calculateTaxRate(user);
				db.setTaxBracket(user, taxBracket);
				
				BigDecimal taxPaid = calc.calculateTaxPaid(user);
				db.setTaxPaid(user, taxPaid);
				
				BigDecimal salaryAfterTax = calc.calculateSalaryAfterTax(user);
				db.setSalaryAfterTax(user, salaryAfterTax);
				
				salaryInput.setText("");
				hoursInput.setText("");
			}
		});
		this.add(submit);
	}

}