package GUI;

import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Logic.User;

public class Dashboard extends Window{
	
	private Toolbar toolbar;
	private DashPanel dash;
	private IncomePanel income;
	private SalaryPanel salary;
	private MenuBar menubar;
	
	private JButton signOut;

	public Dashboard(User user){
		
		loadFrame("MoneyManager");
		
		panels(user);
		
		buttons(user);
	}
	
	public void panels(User user){
		toolbar = new Toolbar(x, y, user);
		frame.getContentPane().add(toolbar);
		
		menubar = new MenuBar(x, y);
		frame.getContentPane().add(menubar);

		dash = new DashPanel(x, y, user, toolbar,  frame);
		frame.getContentPane().add(dash);
		
		income = new IncomePanel(x, y, user, toolbar);
		frame.getContentPane().add(income);
		
		salary = new SalaryPanel(x, y, user);
		frame.getContentPane().add(salary);
		
		defaultSetup();
	}
		
		
	public void buttons(User user){
		/*
		openMenu = new JButton();
		openMenu.setText("Menu");
		openMenu.setBounds(x - 140, 10, 80, 25);
		openMenu.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				if(menubar.isVisible()){
					menubar.setVisible(false);
				} else {
					menubar.setVisible(true);
				}
			}
		});
		toolbar.add(openMenu);
		*/
		
		// Values for easier spacing of menu JButtons.
		int space = 25;
		int buff = 50;
		
		JButton dashboardButton = new JButton();
		dashboardButton.setText("Dashboard");
		dashboardButton.setBounds(10, space, 175, 25);
		dashboardButton.setForeground(Color.black);
		dashboardButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				dash.updateGUI();
				dash.setVisible(true);
				income.setVisible(false);
				salary.setVisible(false);
				}
		});
		menubar.add(dashboardButton);
		space += buff;
		
		
		JButton balanceButton = new JButton();
		balanceButton.setText("Add income/expense");
		balanceButton.setBounds(10, space, 175, 25);
		balanceButton.setForeground(Color.black);
		balanceButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				income.updateGUI();
				dash.setVisible(false);
				income.setVisible(true);
				salary.setVisible(false);
			}
		});
		menubar.add(balanceButton);
		space += buff;
		
		JButton salaryButton = new JButton();
		salaryButton.setText("Enter salary");
		salaryButton.setBounds(10, space, 175, 25);
		salaryButton.setForeground(Color.black);
		salaryButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				salary.revalidate();
				dash.setVisible(false);
				income.setVisible(false);
				salary.setVisible(true);
				
			}
		});
		menubar.add(salaryButton);
		space += buff;
		
		JButton budgetButton = new JButton();
		budgetButton.setText("Budget");
		budgetButton.setBounds(10, space, 175, 25);
		budgetButton.setForeground(Color.black);
		budgetButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				
			}
		});
		menubar.add(budgetButton);
		space += buff;
		
		JButton reportButton = new JButton();
		reportButton.setText("Generate report");
		reportButton.setBounds(10, space, 175, 25);
		reportButton.setForeground(Color.black);
		reportButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				
			}
		});
		menubar.add(reportButton);
		space += buff;
		
		signOut = new UniversalButton(x, y, "Logout");
		signOut.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				Alert alert = new Alert();
				alert.logoutAlert(frame, user);
			}
		});
		menubar.add(signOut);
	}
	
	public void defaultSetup(){
		toolbar.setVisible(true);
		menubar.setVisible(true);
		dash.setVisible(true);
		income.setVisible(false);
		salary.setVisible(false);
	}
		
}