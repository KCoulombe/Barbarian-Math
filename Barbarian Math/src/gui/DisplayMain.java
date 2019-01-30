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

/**
 * Holds and controls the operation of the GUI
 * @author Tim
 *
 */
public class DisplayMain {
	Core core;// holds the actual game data
	private JFrame mainFrame;//the top level of the gui
	private JPanel screens;//where each screen is held
	String lastMode = Constants.MODE_START;//used for nested menus
	
	public DisplayMain() {
		core = new Core();
		//generate basic association of GUI
		mainFrame = new JFrame("Barbarian Math");
		mainFrame.setSize(Constants.WINDOW_Y,Constants.WINDOW_X);
		mainFrame.setLayout(new FlowLayout());
		screens = new JPanel(new CardLayout());
		/**
		 * Actions for when program is closed
		 */
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
	/**
	 * Launch the window from other class.
	 */
	public void show()
	{
		//mainFrame.pack();
		setup(Constants.MODE_START);
		mainFrame.setVisible(true);
	}
	/**
	 * This will switch between screens.
	 * @param mode A mode is used to declare what screen is to be used.
	 */
	private void setup(String mode)
	{
		CardLayout c;
		switch(mode)
		{
		case Constants.MODE_START:
			c = (CardLayout) screens.getLayout();
			c.show(screens, Constants.MODE_START);
			break;
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
	/**
	 * Sets up all of the screens to be called at startup. This should only be called once.
	 */
	private void prepareFrames()
	{
		//start
		DisplayOpen view1 = new DisplayOpen();
		view1.setup(Constants.WINDOW_X,Constants.WINDOW_Y, new ButtonClickListener());
		screens.add(view1, Constants.MODE_START);
		//main menu
		DisplayCenterMenu view2 = new DisplayCenterMenu();
		view2.setup(Constants.WINDOW_X,Constants.WINDOW_Y, new ButtonClickListener());
		screens.add(view2, Constants.MODE_MAIN);
		//list
		//view3 = new DisplayList(brie);
		//view3.setup(Command.WINDOW_X, Command.WINDOW_Y, new ButtonClickListener());
		//screens.add(view3, Command.MODE_LIST);
		
		//item
		
		//attribute
		
		//export
	}
	/**
	 * Button control lives here. This listener should be used universally.
	 * @author Tim
	 *
	 */
	private class ButtonClickListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e) 
	    {
			String a = e.getActionCommand();
	    	switch(a)
	    	{
	    		case Constants.GOTO_MAIN_BUTTON:
	    			setup(Constants.MODE_MAIN);
	    			break;
	    		
	    		
	    		
	    	}
	    }
	}  
}
