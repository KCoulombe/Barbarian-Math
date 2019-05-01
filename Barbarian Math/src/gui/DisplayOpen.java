package gui;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import logic.Constants;
import logic.Core;
import logic.Scanner;



public class DisplayOpen extends DisplayPanel {
	JComboBox<RulesetComboItem> rulesetSelect;
	JComboBox<File> rulesetDropdown;
	JLabel titleText;
	JButton openRulesetButton;
	
	public DisplayOpen() {
	}

	public DisplayOpen(LayoutManager layout) {
		super(layout);

	}

	public DisplayOpen(boolean isDoubleBuffered) {
		super(isDoubleBuffered);

	}

	public DisplayOpen(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);

	}
	
	public void setup(int x, int y, ActionListener l, Core c)
	{
		
		
		
		setSize(y,x);
		
		setLayout(new GridBagLayout());
		GridBagConstraints g = new GridBagConstraints();//used to tell where the item goes
		g.anchor = GridBagConstraints.NORTH;
		
		setupTitleText(x,y,l,c,g);
		try {
			setupRulesetDropdown(x,y,l,c,g);
		} catch (ParserConfigurationException | SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setupOpenRulesetButton(x,y,l,c,g);
	}
	
	private void setupTitleText(int x, int y, ActionListener l, Core c, GridBagConstraints g) 
	{
		titleText = new JLabel("Barbarian Math");
		titleText.setFont(new Font(titleText.getName(), Font.BOLD, 50));
		
		//determine where the text will go in the grid
		g.gridx = 0;//middle
		g.gridy = 0;//middle
		g.ipady = 20;//padding
		g.ipadx = 20;
		g.gridwidth = 2;
		g.fill = GridBagConstraints.BOTH;
		add(titleText, g);
		
		JLabel openText = new JLabel("Please Select a Ruleset");
		openText.setFont(new Font(titleText.getName(), Font.PLAIN, 15));
		
		//determine where the text will go in the grid
		g.gridx = 0;//middle
		g.gridy = 1;//middle
		g.ipady = 20;//padding
		g.ipadx = 20;
		g.fill = GridBagConstraints.BOTH;
		add(openText, g);
	}
	
	private void setupRulesetDropdown(int x, int y, ActionListener l, Core c, GridBagConstraints g) throws ParserConfigurationException, SAXException, IOException 
	{
		File workingDirectory = new File("BarbarianMathData");
		
		List<File> rulesetFiles = Arrays.asList(Scanner.ListRulesets(workingDirectory));
		List<String> rulesetNames = Scanner.ListRulesetNames(workingDirectory);
		
		rulesetSelect = new JComboBox();
		//rulesetSelect.addActionListener(l);
		
		
		
		for(int i = 0; i < rulesetNames.size(); i++)
		{
			rulesetSelect.addItem(new RulesetComboItem(rulesetFiles.get(i), rulesetNames.get(i)));
		}
		
		g.ipady = 0;
		g.ipadx = 0;
		g.gridx = 0;//left
		g.gridy = 2;//bottom
		//g.fill = GridBagConstraints.HORIZONTAL;
		add(rulesetSelect, g);
	}
	
	private void setupOpenRulesetButton(int x, int y, ActionListener l, Core c, GridBagConstraints g) 
	{
		openRulesetButton = new JButton(Constants.GOTO_MAIN_BUTTON_LABEL);
		
		//action commands are used for identifying what to do. 
		openRulesetButton.setActionCommand(Constants.GOTO_FIRST_MAIN_BUTTON);
		//universal listener
		openRulesetButton.addActionListener(l);
		//populate dropdown
		
		g.ipady = 0;
		g.ipadx = 0;
		g.gridx = 2;//left
		g.gridy = 2;//bottom
		g.ipadx = 2;
		//g.fill = GridBagConstraints.HORIZONTAL;
		add(openRulesetButton, g);
	}
	
	public class RulesetComboItem {
	    private File value;
	    private String label;

	    public RulesetComboItem(File value, String label) {
	        this.value = value;
	        this.label = label;
	    }

	    public File getValue() {
	        return this.value;
	    }

	    public String getLabel() {
	        return this.label;
	    }

	    @Override
	    public String toString() {
	        return label;
	    }
	}
	
	public File getSelectedRuleset()
	{
		return ((RulesetComboItem) rulesetSelect.getSelectedItem()).getValue();
	}
}











