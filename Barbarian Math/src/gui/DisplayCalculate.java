package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import logic.CharacterBuild;
import logic.Constants;
import logic.Core;
import logic.Record;

public class DisplayCalculate extends DisplayPanel {

	JScrollPane buildPane;
	JScrollPane resultPane;
	JPanel buildBox;
	JPanel optionsBox;
	JPanel resultBox;
	JTable resultTable;
	JPanel buttonBox;
	HashMap<CharacterBuild, JCheckBox> buildMap;
	HashMap<String, JComponent> optionsMap;
	HashMap<String, JButton> buttonMap;
	
	public DisplayCalculate() {
	}

	public DisplayCalculate(LayoutManager layout) {
		super(layout);

	}

	public DisplayCalculate(boolean isDoubleBuffered) {
		super(isDoubleBuffered);

	}

	public DisplayCalculate(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);

	}

	@Override
	public void setup(int x, int y, ActionListener l, Core c) {
		//initial
		setSize(x,y);
		setLayout(new GridBagLayout());
		GridBagConstraints g = new GridBagConstraints();
		
		/**BuildBox******************/
		buildBox = new JPanel();
		buildBox.setLayout(new BoxLayout(buildBox, BoxLayout.Y_AXIS));
		//populate map
		buildMap = new HashMap<>();
		for(CharacterBuild b : c.loadedCharacterBuilds)
		{
			JCheckBox check = new JCheckBox();
			check.setText(b.name);
			buildMap.put(b, check);
			buildBox.add(check);
		}
		buildPane = new JScrollPane(buildBox);
		g.gridx = 0;
		g.gridy = 0;
		g.fill = GridBagConstraints.BOTH;
		add(buildPane, g);
		/**OptionBox*****************/
		optionsBox = new JPanel();
		optionsBox.setLayout(new BoxLayout(optionsBox, BoxLayout.Y_AXIS));
		optionsMap = new HashMap<>();
		optionsMap.put(Constants.LABEL_LEVEL_RANGE_FIELD, new JTextField(Constants.LABEL_LEVEL_RANGE_FIELD));
		optionsMap.put(Constants.LABEL_CALCULATE_AVERAGE_CHECK, new JCheckBox(Constants.LABEL_CALCULATE_AVERAGE_CHECK));
		optionsMap.put(Constants.LABEL_CALCULATE_MAX_CHECK, new JCheckBox(Constants.LABEL_CALCULATE_MAX_CHECK));
		optionsMap.put(Constants.LABEL_CALCULATE_MIN_CHECK, new JCheckBox(Constants.LABEL_CALCULATE_MIN_CHECK));
		for (JComponent j : optionsMap.values())
		{
			optionsBox.add(j);
		}
		g.gridy = 2;
		add(optionsBox, g);
		/**OutputBox****************/
		resultTable = new JTable();
		setResults();
		g.gridy = 0;
		g.gridx = 2;
		add(resultTable, g);
		/**ButtonBox****************/
		//initialize
		buttonMap = new HashMap<>();
		buttonBox = new JPanel();
		//set layout
		buttonBox.setLayout(new BoxLayout(buttonBox, BoxLayout.X_AXIS));
		//generate back button
		JButton back = new JButton();
		back.setText(Constants.GOTO_MAIN_BUTTON_LABEL);
		back.setActionCommand(Constants.GOTO_MAIN_BUTTON);
		back.addActionListener(l);
		//generate calculator button
		JButton calc = new JButton();
		calc.setText(Constants.LABEL_SUBMIT_CALCULATE);
		calc.setActionCommand(Constants.ENTER_CALCULATE);
		calc.addActionListener(new CalculateButtonListener());
		//put into map for later use
		buttonMap.put(Constants.GOTO_MAIN_BUTTON_LABEL, back);
		buttonMap.put(Constants.LABEL_SUBMIT_CALCULATE, calc);
		//add to panel
		for(JButton b : buttonMap.values())
		{
			buttonBox.add(b);
		}
		//add to screen
		g.gridy = 2;
		g.gridx = 2;
		add(buttonBox, g);
	}
	/**
	 * Sets the Records shown on the outputTable.
	 * uses the values of buildBox to determine which CharacterBuild objects to use
	 * 
	 */
	protected void setResults()
	{
		JTextField rangeBox = (JTextField) optionsMap.get(Constants.LABEL_LEVEL_RANGE_FIELD);
		String rangeText = rangeBox.getText();
		
		//Record dummy = new Record(20);
	}
	private class CalculateButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e) 
	    {
			String a = e.getActionCommand();
	    	switch(a)
	    	{
	    		case Constants.ENTER_CALCULATE: 
	    			setResults();
	    			break;
	    		
	    	}
	    }
	} 

}
