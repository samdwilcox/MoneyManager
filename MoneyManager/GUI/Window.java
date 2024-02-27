package GUI;

import javax.swing.*;
import javax.swing.JFrame;

import java.awt.*;
import java.awt.event.*;

/*
	Parent class for all different GUI windows.
*/
public abstract class Window{
	
	public JFrame frame;
	public int x = 765;
	public int y = 640;
	
	public void loadFrame(String title){
		frame = new JFrame();
		frame.setTitle(title);
		frame.setMaximumSize(new Dimension(x, y));
		frame.setMinimumSize(new Dimension(x, y));
		frame.setPreferredSize(new Dimension(x, y));
		frame.setResizable(false);								//Frame size cannot be changed.
		frame.setLocationRelativeTo(null);						//Frame will appear in the center of the screen.
		frame.getContentPane().setLayout(null);					//No layout.
		frame.setDefaultCloseOperation(frame.DO_NOTHING_ON_CLOSE);	
		
		/*
			displays a yes/no prompt when attempting to exit application using x button
		*/
		frame.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent windowEvent){
				Alert alert = new Alert();
				alert.exitAlert(frame);
			}
		});
		frame.setVisible(true);
	}

	public int getX(){ return x; }
	
	public void setX(int x){ this.x = x; }
	
	public int getY(){ return y; }
	
	public void setY(int y){ this.y = y;}
}