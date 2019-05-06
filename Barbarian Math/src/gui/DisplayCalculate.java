package gui;

import java.awt.FlowLayout;
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
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import logic.CharacterBuild;
import logic.Constants;
import logic.Core;
import logic.Mathemagics;
import logic.Record;

public class DisplayCalculate extends DisplayPanel {

	JScrollPane buildPane;
	JScrollPane resultPane;
	JPanel buildBox;
	JPanel optionsBox;
	JPanel resultBox;
	JTable resultTable;
	DefaultTableModel resultModel;
	JPanel buttonBox;
	HashMap<CharacterBuild, JCheckBox> buildMap;
	HashMap<String, JComponent> optionsMap;
	HashMap<String, JButton> buttonMap;
	Core core;
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
		core = c;
		/**BuildBox******************/
		buildBox = new JPanel();
		buildBox.setLayout(new BoxLayout(buildBox, BoxLayout.Y_AXIS));
		//populate map
		buildMap = new HashMap<>();
		//setCharacterBar(c);
		buildPane = new JScrollPane(buildBox);
		g.gridx = 0;
		g.gridy = 0;
		g.fill = GridBagConstraints.BOTH;
		add(buildPane, g);
		/**OptionBox*****************/
		optionsBox = new JPanel();
		optionsBox.setLayout(new BoxLayout(optionsBox, BoxLayout.Y_AXIS));
		optionsMap = new HashMap<>();
		JComboBox<Integer> levels = new JComboBox();
		
		for(int i = 1; i <= 20; i++)
		{
			levels.addItem(i);
		}
		optionsMap.put(Constants.LABEL_LEVEL_RANGE_FIELD, levels);
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
		resultBox = new JPanel();
		//resultBox.setLayout(new FlowLayout());
		
		resultBox.setLayout(new BoxLayout(resultBox, BoxLayout.Y_AXIS));
		
		String[][] datab = {{" ", " "}};
		
		String[] headb = new String[21];
		headb[0]= Constants.LABEL_TABLE_NAME;
		for (int i = 1; i <21; i++)
		{
			headb[i] = ""+i;
		}
		resultModel = new DefaultTableModel(datab, headb);
		
		resultTable = new JTable(resultModel);
		
		g.gridy = 0;
		g.gridx = 2;
		g.fill = GridBagConstraints.EAST;
		resultPane = new JScrollPane(resultTable);
		
		add(resultPane, g);
		
		/**ButtonBox****************/
		//initialize
		buttonMap = new HashMap<>();
		buttonBox = new JPanel();
		//set layout
		buttonBox.setLayout(new BoxLayout(buttonBox, BoxLayout.X_AXIS));
		//generate back button
		JButton back = new JButton();
		back.setText(Constants.LABEL_BACK_BUTTON);
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
		// clear the old data
		if (resultModel.getRowCount()>0)
		{
			for(int i = resultModel.getRowCount()-1; i>=0; i--)
			{
				resultModel.removeRow(i);
			}
		}
		//get builds that are selected
		ArrayList<CharacterBuild> chosenBuilds = new ArrayList<>();
		for(CharacterBuild c : buildMap.keySet())
		{
			if(buildMap.get(c).isSelected())
			{
				chosenBuilds.add(c);
			}
		}
		ArrayList<Record> results;
		results = new ArrayList<Record>();
		results.add(new Record(20));///dummy
		/*for(CharacterBuild build : chosenBuilds)
		{
			results.add(Mathemagics.MathLogic(build, Constants.MATH_MODE_AVG));
		}*/
		results.addAll(Mathemagics.makeRecords(chosenBuilds, 5, Constants.MATH_MODE_AVG, core));
		//add to model
		for (Record r : results)
		{
			resultModel.addRow(r.toRow().toArray(new String[0]));
		}
		resultTable.validate();
		resultTable.repaint();
		resultTable.setVisible(false);
		resultTable.setVisible(true);
		resultPane.setVisible(false);
		resultPane.setVisible(true);
		setVisible(false);
		setVisible(true);
	}
	
	public void setCharacterBar()
	{
		for(CharacterBuild b : core.activeRuleset.characters)
		{
			if(!buildMap.containsKey(b))
			{
				JCheckBox check = new JCheckBox();
				check.setText(b.name);
				buildMap.put(b, check);
				buildBox.add(check);
			}
			
		}
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
