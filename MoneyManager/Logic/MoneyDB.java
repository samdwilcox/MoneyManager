package Logic;

import java.math.BigDecimal;
import java.sql.Connection;  
import java.sql.DriverManager;  
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/*
 * 	This class handles all database operations and queries.
 */

public class MoneyDB{

	Connection conn = null;

	private int id;

	public MoneyDB(){}

	public Connection connect(){
		final String url = "jdbc:sqlite:C:/Coding/PROJECTS/2023/MoneyManager/Data/Database/db.db";
		try{
			conn = DriverManager.getConnection(url);
		} catch(SQLException e){
			e.printStackTrace();
		}
		return conn;
	}

	/*
	 * 	Generic db connect and execute prepared statement.
	 * 	Used for updating user data.
	 */
	public void update(String sql){
		try(Connection conn = this.connect();
			PreparedStatement pstmt = conn.prepareStatement(sql)){
				pstmt.execute();

			} catch(SQLException e){
				e.printStackTrace();
			}
	}

	/*
	 * 	Adds attributes of transaction object to transactions_table in db.
	 * 	Used in IncomePanel.
	 */
	public void addTransaction(User user, Transaction transaction){
			int user_id = user.getUser_id();
			final String sql = "INSERT INTO transactions_table (type, amount, nature, note, date, user_id) VALUES (?, ?, ?, ?, ?, ?);";

			boolean type = transaction.getType();
			String transaction_type = "Expense";
			if(type == true){ transaction_type = "Income"; }

			try(Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)){
				pstmt.setString(1, transaction_type);
				pstmt.setString(2, transaction.getAmount().toString());
				pstmt.setString(3, transaction.getNature());
				pstmt.setString(4, transaction.getNote());
				pstmt.setString(5, transaction.getDate().toString());
				pstmt.setInt(6, user_id);

				pstmt.executeUpdate();
			} catch(SQLException e){
				e.printStackTrace();
			} finally{
				try{
					conn.close();
				} catch(SQLException e){
					e.printStackTrace();
				}
			}	
	}

	/*
	 * 	Selects all records from transactions_table and adds to the user's transactions arraylist.
	 * 	Used to display transactions in the JTable in DashPanel.
	 * 	Called in DashPanel constructor and update();
	 */
	public void findTransactions(User user){
		int user_id = user.getUser_id();

		user.transactions.clear();

		final String sql = "SELECT * FROM transactions_table WHERE user_id = " + user_id;

		try(Connection conn = this.connect();
			PreparedStatement pstmt = conn.prepareStatement(sql)){

				ResultSet rs = pstmt.executeQuery();

				while(rs.next()){
					Transaction transaction = new Transaction();

					if(rs.getString("type").equals("Income")){
						transaction.setType(true);
					} else {
						transaction.setType(false);
					}

					transaction.setAmount(new BigDecimal(rs.getString("amount")));

					transaction.setNature(rs.getString("nature"));

					transaction.setNote(rs.getString("note"));

					transaction.setDate(java.sql.Date.valueOf(rs.getString("date")));

					user.transactions.add(transaction);
				}
				System.out.println(user.transactions.toString());

			} catch(SQLException e){
				e.printStackTrace();
			}
	}
	
	/*
	 * 	Looks for matching username and password to those entered by user in Login page.
	 */
	public boolean verifyUser(String username, String password){
			boolean found = false;
			String tempUsername = "";
			String tempPassword = "";
			final String sql = "SELECT user_id, username, password FROM users";

			try(Connection conn = this.connect(); 
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)){

				while(rs.next() && !found){
					tempUsername = rs.getString("username");
					tempPassword = rs.getString("password");

					if(tempUsername.equals(username) && tempPassword.equals(password)){
						found = true;
						id = rs.getInt("user_id");
					}
				}
			} catch(SQLException e){
				e.printStackTrace();
			} finally{
				try{
					conn.close();
				} catch(SQLException e){
					e.printStackTrace();
				}
			}

			return found;
	}

	/*
	 * 	Queries database for all information on selected user.
	 * 	Used to load user data on login.
	 */
	public void getUserInfo(User user){
		user.setUser_id(id);

		final String sql = 	"SELECT username, balance, salary, hours, hourlyRate," + 
		 				"taxBracket, taxPaid, salaryAfterTax " + 
						"FROM users WHERE user_id =" + id;

		try(Connection conn = this.connect();
			PreparedStatement pstmt = conn.prepareStatement(sql)){
				
				ResultSet rs = pstmt.executeQuery();

				while(rs.next()){
					user.setUsername(rs.getString("username"));

					String balance = rs.getString("balance");
					if(balance == null) balance = "0.0";
					user.setBalance(new BigDecimal(balance));

					String salary = rs.getString("salary");
					if(salary == null) salary = "0.0";
					user.setSalary(new BigDecimal(salary));
					
					int hours = rs.getInt("hours");
					user.setHours(hours);
					
					String hourlyRate = rs.getString("hourlyRate");
					if(hourlyRate == null) hourlyRate = "0.0";
					user.setHourlyRate(new BigDecimal(hourlyRate));

					String taxBracket = rs.getString("taxBracket");
					if(taxBracket == null) taxBracket = "0.0";
					user.setTaxBracket(new BigDecimal(taxBracket));

					String taxPaid = rs.getString("taxPaid");
					if(taxPaid == null) taxPaid = "0.0";
					user.setTaxPaid(new BigDecimal(taxPaid));

					String salaryAfterTax = rs.getString("salaryAfterTax");
					if(salaryAfterTax == null) salaryAfterTax = "0.0";
					user.setSalaryAfterTax(new BigDecimal(salaryAfterTax));
				}


			} catch(SQLException e){
				e.printStackTrace();
			}	
	}

	public void setBalance(User user, BigDecimal balance){
		user.setBalance(balance);
		int user_id = user.getUser_id();
		
		final String sql = "UPDATE users SET balance = " + input(balance) + " WHERE user_id = " + user_id;

		update(sql);
	}

	public void setSalary(User user, BigDecimal salary){
		user.setSalary(salary);
		int user_id = user.getUser_id();

		final String sql = "UPDATE users SET salary = " + input(salary) + " WHERE user_id = " + user_id;

		update(sql);
	}

	public void setHours(User user, int hours){
		user.setHours(hours);
		int user_id = user.getUser_id();

		final String sql = "UPDATE users SET hours = " + hours + " WHERE user_id = " + user_id;

		update(sql);

	}

	public void setHourlyRate(User user, BigDecimal hourlyRate){
		user.setHourlyRate(hourlyRate);
		int user_id = user.getUser_id();

		final String sql = "UPDATE users SET hourlyRate = " + input(hourlyRate) + " WHERE user_id = " + user_id;

		update(sql);
	}

	public void setTaxBracket(User user, BigDecimal taxBracket){
		user.setTaxBracket(taxBracket);
		int user_id = user.getUser_id();

		final String sql = "UPDATE users SET taxBracket = " + input(taxBracket) + " WHERE user_id = " + user_id;

		update(sql);
	}

	public void setTaxPaid(User user, BigDecimal taxPaid){
		user.setTaxPaid(taxPaid);
		int user_id = user.getUser_id();

		final String sql = "UPDATE users SET taxPaid = " + input(taxPaid) + " WHERE user_id = " + user_id;

		update(sql);
	}

	public void setSalaryAfterTax(User user, BigDecimal salaryAfterTax){
		user.setSalaryAfterTax(salaryAfterTax);
		int user_id = user.getUser_id();

		final String sql = "UPDATE users SET salaryAfterTax = " + input(salaryAfterTax) + " WHERE user_id = " + user_id;

		update(sql);
	}


	/*
	 * 	Generic method to convert BigDecimal to String.
	 * 	Used for SQL queries.
	 */
	public String input(BigDecimal input){
		String result = String.valueOf(input);

		return result;
	}

}