package gui;

import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import javax.xml.parsers.ParserConfigurationException;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import logic.Constants;
import logic.Core;
import logic.Ruleset;
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
	DisplayCalculate view3;
	DisplayModifier view5;
	//the build manager
	DisplayBuild view4;
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
			view5.updateContents();
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
		view3 = new DisplayCalculate();
		view3.setup(Constants.WINDOW_X,Constants.WINDOW_Y, new ButtonClickListener(), core);
		screens.add(view3, Constants.MODE_CALCULATE);
		//build
		view4 = new DisplayBuild();
		view4.setup(Constants.WINDOW_X,Constants.WINDOW_Y, new ButtonClickListener(), core);
		screens.add(view4, Constants.MODE_BUILD);
		//modifier
		view5 = new DisplayModifier();
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
	    			
	    			File f = (File) view1.rulesetSelect.getSelectedItem();
				try {
					//s.LoadRuleset(f);
					core.setRuleset(Scanner.LoadRuleset(f));
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
	    			view4.updateData(core);
	    			setup(Constants.MODE_BUILD);
	    			break;
	    		case Constants.GOTO_MODIFIER_BUTTON:
	    			setup(Constants.MODE_MODIFIER);
	    			break;
	    		case Constants.GOTO_CALCULATOR_BUTTON:
				view3.setCharacterBar();
	    			setup(Constants.MODE_CALCULATE);
	    			break;
	    		case Constants.GOTO_LOAD_BUTTON:
	    			setup(Constants.MODE_LOAD);
	    			break;
	    		case Constants.GOTO_SAVE_BUTTON:
	    			setup(Constants.MODE_SAVE);
	    			break;
	    		case Constants.CHARACTER_BUILD_SAVE_POPUP:
	    			System.out.println("Looking for a file...");
	    			System.out.println("Found: " + chooseFile("BarbarianMathData", true));
	    			break;
			case Constants.CHARACTER_BUILD_LOAD_POPUP:
	    			System.out.println("Looking for a file...");
	    			System.out.println("Found: " + chooseFile("BarbarianMathData", false));
	    			break;
	    		case Constants.SELECTED_CHARACTER_CHANGED:
	    			
	    			break;
	    	}
	    }
	} 
	
	/*private class ItemChangeListener implements ItemListener
	{
	    @Override
	    public void itemStateChanged(ItemEvent event) 
	    {
	       if (event.getStateChange() == ItemEvent.SELECTED)
	       {
	          Object item = event.getItem();
	          // do something with object
	       }
	    }       
	}*/
	
	/** 
	 * Opens a popup that allows the user to choose a file
	 * @param startDir the directory the file chooser window will start in
	 * @param saveOrOpen whether you are saving a file or opening it.<br>  true = save<br> false = open
	 * @return Returns the file the user chose.  Returns null if the user did not choose a file.
	 */
	private File chooseFile(String startDir, boolean saveOrOpen)
	{
		JFileChooser jfc = new JFileChooser(startDir);
		jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Barbarian Math Data Files", "val", "sca", "adv", "char", "ruleset");
		jfc.addChoosableFileFilter(filter);
		jfc.setFileFilter(filter);
		
		int returnValue;
		
		if(saveOrOpen)
		{
			returnValue = jfc.showSaveDialog(null);
		}
		else
		{
			returnValue = jfc.showOpenDialog(null);
		}
		// int returnValue = jfc.showSaveDialog(null);

		if (returnValue == JFileChooser.APPROVE_OPTION) {
			return jfc.getSelectedFile();
		}
		return null;
	}
}
