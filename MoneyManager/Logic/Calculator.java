package Logic;

import java.math.*;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import Logic.User;
import Logic.MoneyDB;

public class Calculator {
	
	private BigDecimal annualSalary;

	private MoneyDB db;
	
	
	/*
		Income tax bands. Accurate as of Sept 2023.
		
		Personal allowance (0% income tax) up to £12,570 annually.
		
		Basic rate (20% income tax) £12,571 to £50,270 annually.
		
		Higher rate (40% income tax) £50,271 to £125,140 annually.
		
		Additional rate (45% income tax) over £125,140 annually.
	*/
	private BigDecimal personalAllowance = new BigDecimal("0.0");
	private BigDecimal basicRate = new BigDecimal("0.2");
	private BigDecimal higherRate = new BigDecimal("0.4");
	private BigDecimal additionalRate = new BigDecimal("0.45");
	
	//private MathContext mc;
	
	public Calculator(User user){
		db = new MoneyDB();
	}
	
	public BigDecimal calculateSalaryAfterTax(User user){
		BigDecimal salary = user.getSalary();
		BigDecimal taxPaid = calculateTaxPaid(user);
		
		BigDecimal result = salary.subtract(taxPaid);
		
		return result;
	}
	
	public BigDecimal calculateTaxPaid(User user){
		BigDecimal salary = user.getSalary();
		BigDecimal taxRate = calculateTaxRate(user);
		
		BigDecimal tax = salary.multiply(taxRate);
		
		return tax;
	}
	
	public BigDecimal calculateTaxRate(User user){
		annualSalary = user.getSalary();
		BigDecimal tax = new BigDecimal("0.0");
		
		if(annualSalary.compareTo(new BigDecimal("12570.00")) <= 0){
			tax = personalAllowance;
		} if (annualSalary.compareTo(new BigDecimal("12571.00")) >= 0 
			&& annualSalary.compareTo(new BigDecimal("50270.00")) <= 0){
			tax = basicRate;
		} if(annualSalary.compareTo(new BigDecimal("50271.00")) >= 0 
			&& annualSalary.compareTo(new BigDecimal("125140.00")) <= 0){
			tax = higherRate;
		} if(annualSalary.compareTo(new BigDecimal("125141.00")) >= 0){
			tax = additionalRate;
		}
		
		return tax;
		
	}

	public BigDecimal calculateTaxPercentage(User user){
		BigDecimal bracket = calculateTaxRate(user);
		BigDecimal percentage = bracket.multiply(new BigDecimal("100"));

		return percentage;
	}
	
	public void addIncome(BigDecimal input, User user){
		BigDecimal current = user.getBalance();
		BigDecimal newBalance = current.add(input);
				
		db.setBalance(user, newBalance);
	}
	
	public void addExpense(BigDecimal input, User user){
		BigDecimal current = user.getBalance();
		BigDecimal newBalance = current.subtract(input);
		
		db.setBalance(user, newBalance);
	}
	
	
	public BigDecimal calculateHourlyRate(User user){
		BigDecimal salary = user.getSalary();
		
		BigDecimal weekly = calculateWeeklySalary(salary);
		int hoursInt = user.getHours();
		BigDecimal hours = BigDecimal.valueOf(hoursInt);

		BigDecimal result = weekly.divide(hours, 2, RoundingMode.FLOOR); // Scale of 2 = 2 Decimal places. Rounding Mode FLOOR rounds toward negative infinity. CEILING would round toward positive infinity.
		
		return result;
	}
	
	public BigDecimal calculateWeeklySalary(BigDecimal salary){
		BigDecimal weeklyRate = new BigDecimal("0.0");
		
		BigDecimal weeks = new BigDecimal("52.0");
		weeklyRate = salary.divide(weeks, 2, RoundingMode.FLOOR);
		
		return weeklyRate;
	}
	
	public BigDecimal getAnnualSalary(){ return annualSalary; }
	
	public void setAnnualSalary(BigDecimal annualSalary){ this.annualSalary = annualSalary; }

}