package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import logic.Constants;
import logic.Core;
import logic.Scanner;



public class DisplayOpen extends DisplayPanel {
	JComboBox<File> rulesetSelect;
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
		File workingDirectory = new File("BarbarianMathData");
		Scanner scanner = new Scanner();
		
		setSize(y,x);
		
		setLayout(new GridBagLayout());
		GridBagConstraints g = new GridBagConstraints();//used to tell where the item goes
		//establish text
		JLabel text = new JLabel("Barbarian Math");
		//establish button
		JButton startButton = new JButton(Constants.GOTO_MAIN_BUTTON_LABEL);
		//establish dropDown
		File[] localFiles = scanner.ListRulesets(workingDirectory);
		
		rulesetSelect = new JComboBox();
		//rulesetSelect.addActionListener(l);
		
		//action commands are used for identifying what to do. 
		startButton.setActionCommand(Constants.GOTO_FIRST_MAIN_BUTTON);
		//universal listener
		startButton.addActionListener(l);
		//populate dropdown
		Scanner s = new Scanner();
		for(File f : s.ListRulesets(Constants.DATA_FOLDER_NAME))
		{
			rulesetSelect.addItem(f);
		}
		//determine where the text will go in the grid
		g.gridx = 1;//middle
		g.gridy = 1;//middle
		g.ipady = 40;//padding
		g.ipadx = 40;
		g.fill = GridBagConstraints.BOTH;
		add(text, g);
		g.ipady = 0;
		g.ipadx = 0;
		g.gridx = 0;//left
		g.gridy = 2;//bottom
		g.fill = GridBagConstraints.HORIZONTAL;
		add(startButton, g);
		g.gridx = 2;
		add(rulesetSelect, g);
	}

}
