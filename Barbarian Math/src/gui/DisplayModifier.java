package gui;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import logic.Constants;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import logic.Core;

public class DisplayModifier extends DisplayPanel {

	public DisplayModifier() {
	}

	public DisplayModifier(LayoutManager layout) {
		super(layout);

	}

	public DisplayModifier(boolean isDoubleBuffered) {
		super(isDoubleBuffered);

	}

	public DisplayModifier(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);

	}

	EntryElement loadMod;
	EntryElement name;
	EntryElement type;
	EntryElement attribute;
	EntryElement cost;
	EntryElement limit;
	
	ArrayList<EntryElement> totalList;
	JPanel selection;
	JScrollPane scroll;
	@Override
	public void setup(int x, int y, ActionListener l, Core c) {
		
		setSize(x,y);
		setLayout(new GridBagLayout());
		GridBagConstraints g = new GridBagConstraints();
		//holds the vertical list
		//uses BoxLayout to list vertivally.
		JPanel optionContainer = new JPanel();
		optionContainer.setLayout(new BoxLayout(optionContainer, BoxLayout.Y_AXIS));
		//initialize elements
		loadMod = new EntryElement(Constants.LABEL_LOAD_MOD_ROW);
		name = new EntryElement(Constants.LABEL_NAME_FIELD);
		type = new EntryElement(Constants.LABEL_TYPE_DROP);
		attribute = new EntryElement(Constants.LABEL_ATTRIBUTE_SELECT);
		cost = new EntryElement(Constants.LABEL_COST_FIELD);
		limit = new EntryElement(Constants.LABEL_LIMIT_DROP);
		totalList = new ArrayList<EntryElement>();
		////populate elements
		//load
		ArrayList<String> filter = new ArrayList<String>();
		filter.add(Constants.TAG_MODIFIER);
		loadMod.addDropdown(Constants.LABEL_MODIFIER_LOAD, c.getSublist(filter, null));
		loadMod.addButton(Constants.LABEL_MODIFIER_LOAD_BUTTON, Constants.ENTER_MODIFIER_LOAD, new ModifierButtonListener());
		//name
		name.addTextBox(Constants.LABEL_NAME_FIELD, Constants.TAG_NAME);
		//type
		filter.clear();
		type.addStringDropdown(Constants.LABEL_TYPE_DROP, c.getTags(null));
		type.addButton(Constants.LABEL_ADD_BUTTON, Constants.ENTER_TYPE, new ModifierButtonListener());
		//attribute
		filter.add(Constants.TAG_ATTRIBUTE);
		attribute.addDropdown(Constants.LABEL_ATTRIBUTE_SELECT, c.getSublist(filter, null));
		//cost
		cost.addTextBox(Constants.LABEL_COST_FIELD, Constants.TAG_INTEGER);
		cost.addButton(Constants.LABEL_ADD_BUTTON, Constants.ENTER_COST, new ModifierButtonListener());
		//limit
		filter.clear();
		filter.add(Constants.TAG_LIMIT);
		limit.addStringDropdown(Constants.LABEL_LIMIT_DROP, c.getTags(filter));
		limit.addButton(Constants.LABEL_ADD_BUTTON, Constants.ENTER_LIMIT, new ModifierButtonListener());
		
		////add components
		optionContainer.add(loadMod);
		optionContainer.add(name);
		optionContainer.add(type);
		optionContainer.add(attribute);
		optionContainer.add(cost);
		optionContainer.add(limit);
		//set constraint
		g.gridx = 0;
		g.gridy = 0;
		g.fill = GridBagConstraints.VERTICAL;
		add(optionContainer, g);
		
		
		///scrollpane
		selection = new JPanel();
		selection.setLayout(new BoxLayout(selection, BoxLayout.Y_AXIS));
		selection.add(new JCheckBox());
		//scroll = new JScrollPane(selection);
		scroll = new JScrollPane();
		//scroll.add(selection);
		g.gridx = 2;
		g.gridy = 0;
		g.fill = GridBagConstraints.VERTICAL;
		add(selection, g);
		//
		JButton back = new JButton();
		back.setText(Constants.LABEL_BACK_BUTTON);
		back.addActionListener(l);
		back.setActionCommand(Constants.GOTO_MAIN_BUTTON);
		g.gridy = 2;
		add(back, g);
		
	}
	protected void insertToSelection(EntryElement e)
	{
		totalList.add(e);
		JCheckBox c = new JCheckBox();
		c.setText(e.toString());
		
		selection.add(c);
		selection.setVisible(false);
		selection.setVisible(true);
	}
	private class ModifierButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e) 
	    {
			String a = e.getActionCommand();
	    	switch(a)
	    	{
	    		case Constants.ENTER_COST: 
	    			insertToSelection(cost);
	    			break;
	    		case Constants.ENTER_LIMIT:
	    			insertToSelection(limit);
	    			break;
	    		case Constants.ENTER_MODIFIER_LOAD:
	    			
	    			break;
	    		case Constants.ENTER_TYPE:
	    			insertToSelection(type);
	    			break;
	    	}
	    }
	} 
}
