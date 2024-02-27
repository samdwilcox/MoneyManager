package GUI;

import javax.swing.JButton;

import java.awt.Color;

public class UniversalButton extends JButton{
	
	public UniversalButton(int x, int y){
		this.setText("Back");
		this.setBounds(50, y - 130, 75, 25);
		this.setForeground(Color.black);
	}
	
	public UniversalButton(int x, int y, String text){
		this.setText(text);
		this.setBounds(60, y - 130, 80, 25);
		this.setForeground(Color.black);
	}

}