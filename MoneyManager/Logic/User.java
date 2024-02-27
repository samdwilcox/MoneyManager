package Logic;

import java.util.*;
import java.math.BigDecimal;

public class User{
	
	private int user_id;
	private String username;
	private String password;
	
	private BigDecimal balance;
	private BigDecimal salary;
	private int hours;
	private BigDecimal hourlyRate;
	private BigDecimal taxBracket;
	private String taxBracketString;
	private BigDecimal taxPaid;
	private BigDecimal salaryAfterTax;
	
	public ArrayList<Transaction> transactions;
	
	public User(){
		transactions = new ArrayList<>();
		
	}
	
	public int getUser_id(){ return user_id; }

	public void setUser_id(int user_id){ this.user_id = user_id; }

	
	public String getUsername(){ return username; }
	
	public void setUsername(String username){ this.username = username; }
	
	
	public String getPassword(){ return password; }
	
	public void setPassword(String password){ this.password = password; }
	
	
	public BigDecimal getBalance(){ return balance; }
	
	public void setBalance(BigDecimal balance){ this.balance = balance; }
	
	
	public BigDecimal getSalary(){ return salary; }
	
	public void setSalary(BigDecimal salary){ this.salary = salary; }
	
	
	public int getHours(){ return hours; }
	
	public void setHours(int hours){ this.hours = hours; }
	
	
	public BigDecimal getHourlyRate(){ return hourlyRate; }
	
	public void setHourlyRate(BigDecimal hourlyRate){ this.hourlyRate = hourlyRate; }
	
	
	public BigDecimal getTaxBracket(){ return taxBracket; }
	
	public void setTaxBracket(BigDecimal taxBracket){ this.taxBracket = taxBracket; }


	public String getTaxBracketString(){
		
		if(taxBracket != null){
			if(taxBracket.equals(new BigDecimal("0.0"))){
				taxBracketString = "Personal allowance";
			} if(taxBracket.equals(new BigDecimal("0.2"))){
				taxBracketString = "Basic rate";
			} if(taxBracket.equals(new BigDecimal("0.4"))){
				taxBracketString = "Higher rate";
			} if(taxBracket.equals(new BigDecimal("0.45"))){
				taxBracketString = "Additional rate";
			}
		}

		return taxBracketString;
	}
	
	
	public BigDecimal getTaxPaid(){ return taxPaid; }
	
	public void setTaxPaid(BigDecimal taxPaid){ this.taxPaid = taxPaid; }
	
	
	public BigDecimal getSalaryAfterTax(){ return salaryAfterTax; }
	
	public void setSalaryAfterTax(BigDecimal salaryAfterTax){ this.salaryAfterTax = salaryAfterTax; }
	
	
	public ArrayList<Transaction> getTransactions(){ return transactions; }
}