package gui;

import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.xml.parsers.ParserConfigurationException;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import logic.Constants;
import logic.Core;
import logic.Scanner;

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
	
	DisplayOpen view1;
	
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
			break;
		case Constants.MODE_BUILD:
			c = (CardLayout) screens.getLayout();
			c.show(screens, Constants.MODE_BUILD);
			break;
		case Constants.MODE_MODIFIER:
			c = (CardLayout) screens.getLayout();
			c.show(screens, Constants.MODE_MODIFIER);
			break;
		case Constants.MODE_CALCULATE:
			c = (CardLayout) screens.getLayout();
			c.show(screens, Constants.MODE_CALCULATE);
			break;
		case Constants.MODE_SAVE:
			c = (CardLayout) screens.getLayout();
			c.show(screens, Constants.MODE_SAVE);
			break;
		case Constants.MODE_LOAD:
			c = (CardLayout) screens.getLayout();
			c.show(screens, Constants.MODE_LOAD);
			break;
		}
	}
	/**
	 * Sets up all of the screens to be called at startup. This should only be called once.
	 */
	private void prepareFrames()
	{
		//start
		view1 = new DisplayOpen();
		view1.setup(Constants.WINDOW_X,Constants.WINDOW_Y, new ButtonClickListener(), core);
		screens.add(view1, Constants.MODE_START);
		//main menu
		DisplayCenterMenu view2 = new DisplayCenterMenu();
		view2.setup(Constants.WINDOW_X,Constants.WINDOW_Y, new ButtonClickListener(), core);
		screens.add(view2, Constants.MODE_MAIN);
		//calculator
		DisplayCalculate view3 = new DisplayCalculate();
		view3.setup(Constants.WINDOW_X,Constants.WINDOW_Y, new ButtonClickListener(), core);
		screens.add(view3, Constants.MODE_CALCULATE);
		//build
		DisplayBuild view4 = new DisplayBuild();
		view4.setup(Constants.WINDOW_X,Constants.WINDOW_Y, new ButtonClickListener(), core);
		screens.add(view4, Constants.MODE_BUILD);
		//modifier
		DisplayModifier view5 = new DisplayModifier();
		view5.setup(Constants.WINDOW_X,Constants.WINDOW_Y, new ButtonClickListener(), core);
		screens.add(view5, Constants.MODE_MODIFIER);
		//save
		DisplaySave view6 = new DisplaySave();
		view6.setup(Constants.WINDOW_X,Constants.WINDOW_Y, new ButtonClickListener(), core);
		screens.add(view6, Constants.MODE_SAVE);
		//load
		DisplayLoad view7 = new DisplayLoad();
		view7.setup(Constants.WINDOW_X,Constants.WINDOW_Y, new ButtonClickListener(), core);
		screens.add(view7, Constants.MODE_LOAD);
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
	    		case Constants.GOTO_FIRST_MAIN_BUTTON:
	    			Scanner s = new Scanner();
	    			File f = (File) view1.rulesetSelect.getSelectedItem();
				try {
					//s.LoadRuleset(f);
					core.setRuleset(s.LoadRuleset(f));
				} catch (ParserConfigurationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	    			setup(Constants.MODE_MAIN);
	    			break;
	    		case Constants.GOTO_MAIN_BUTTON:
	    			setup(Constants.MODE_MAIN);
	    			break;
	    		case Constants.GOTO_BUILD_BUTTON:
	    			setup(Constants.MODE_BUILD);
	    			break;
	    		case Constants.GOTO_MODIFIER_BUTTON:
	    			setup(Constants.MODE_MODIFIER);
	    			break;
	    		case Constants.GOTO_CALCULATOR_BUTTON:
	    			setup(Constants.MODE_CALCULATE);
	    			break;
	    		case Constants.GOTO_LOAD_BUTTON:
	    			setup(Constants.MODE_LOAD);
	    			break;
	    		case Constants.GOTO_SAVE_BUTTON:
	    			setup(Constants.MODE_SAVE);
	    			break;
	    	}
	    }
	}  
}
