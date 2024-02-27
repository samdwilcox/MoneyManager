import GUI.Login;
import Logic.MoneyDB;

import javax.swing.SwingUtilities;

public class Main{
	
	public Main(){
		Login login = new Login();
		MoneyDB db = new MoneyDB();
	}
	
	/*
		Uses Event Dispatch Thread.
	*/
	
	public static void main(String[] args){
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				new Main();
			}
		});
	}

}