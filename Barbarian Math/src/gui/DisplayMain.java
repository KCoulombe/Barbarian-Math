package gui;

import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;



import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import logic.Constants;
import logic.Core;


public class DisplayMain {
	Core core;
	private JFrame mainFrame;
	private JPanel screens;
	String lastMode = Constants.MODE_MAIN;
	
	public DisplayMain() {
		core = new Core();
		mainFrame = new JFrame("Barbarian Math");
		mainFrame.setSize(Constants.WINDOW_Y,Constants.WINDOW_X);
		mainFrame.setLayout(new FlowLayout());
		screens = new JPanel(new CardLayout());
		
		mainFrame.addWindowListener(new WindowAdapter() {
		    	public void windowClosing(WindowEvent windowEvent){
		            System.exit(0);
		       }        
		    });    
		////build windows
		prepareFrames();
		mainFrame.add(screens);
		//setup(Command.MODE_MAIN);
		//mainFrame.setVisible(true);
	}
	public void show()
	{
		//mainFrame.pack();
		setup(Constants.MODE_START);
		mainFrame.setVisible(true);
	}
	private void setup(String mode)
	{
		CardLayout c;
		switch(mode)
		{
		case Constants.MODE_START:
			c = (CardLayout) screens.getLayout();
			c.show(screens, Constants.MODE_START);
		case Constants.MODE_MAIN: 
			c = (CardLayout) screens.getLayout();
			c.show(screens, Constants.MODE_MAIN);
			break;/*
		case Constants.MODE_IMPORT:
			c = (CardLayout) screens.getLayout();
			c.show(screens, Constants.MODE_IMPORT);
			break;
		case Constants.MODE_LIST:
			c = (CardLayout) screens.getLayout();
			c.show(screens, Command.MODE_LIST);*/
		}
	}
	private void prepareFrames()
	{
		//start
		DisplayOpen view1 = new DisplayOpen();
		view1.setup(Constants.WINDOW_X,Constants.WINDOW_Y, new ButtonClickListener());
		screens.add(view1, Constants.MODE_MAIN);
		//import
		//DisplayImport view2 = new DisplayImport(brie);
		//view2.setup(Command.WINDOW_X,Command.WINDOW_Y, new ButtonClickListener());
		//screens.add(view2, Command.MODE_IMPORT);
		//list
		//view3 = new DisplayList(brie);
		//view3.setup(Command.WINDOW_X, Command.WINDOW_Y, new ButtonClickListener());
		//screens.add(view3, Command.MODE_LIST);
		
		//item
		
		//attribute
		
		//export
	}
	private class ButtonClickListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e) 
	    {
			String a = e.getActionCommand();
	    	switch(a)
	    	{
	    		case Constants.START_BUTTON:
	    			setup(Constants.START_BUTTON);
	    			break;
	    		
	    		
	    	}
	    }
	}  
}
