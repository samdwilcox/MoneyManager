package Logic;

import java.math.BigDecimal;

public class Transaction{
	
	public int transaction_id;
	public boolean type;
	public BigDecimal amount;
	public String nature;
	public String note;
	public java.sql.Date date;
	public int user_id;
	
	public Transaction(){
		date = new java.sql.Date(System.currentTimeMillis());
		
	}

	public int getTransaction_id(){ return transaction_id; }

	public void setTransaction_id(int transaction_id){ this.transaction_id = transaction_id; }
	
	public boolean getType(){ return type; }
	
	public void setType(boolean type){ this.type = type; }
	
	
	public BigDecimal getAmount(){ return amount; }
	
	public void setAmount(BigDecimal amount){ this.amount = amount; }
	
	
	public String getNature(){ return nature; }
	
	public void setNature(String nature){ this.nature = nature; }
	
	
	public String getNote(){ return note; }
	
	public void setNote(String note){ this.note = note; }
	
	
	public java.sql.Date getDate(){ return date; }
	
	public void setDate(java.sql.Date date){ this.date = date; }


	public int getUser_id(){ return user_id; }

	public void setUser_id(int user_id){ this.user_id = user_id; }


}