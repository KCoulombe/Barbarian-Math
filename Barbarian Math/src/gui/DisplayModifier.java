package gui;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import logic.Component;
import logic.Constants;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import logic.Core;
import logic.Modifier;
import logic.Scanner;
import logic.Value;

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
	Core core;
	
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
		core = c;
		setSize(x,y);
		setLayout(new GridBagLayout());
		GridBagConstraints g = new GridBagConstraints();
		//holds the vertical list
		//uses BoxLayout to list vertically.
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
		attribute.addTextBox(Constants.LABEL_STATIC_ATTRIBUTE_FIELD, Constants.TAG_STATIC_ATTRIBUTE);
		attribute.addButton(Constants.LABEL_ADD_BUTTON, Constants.ENTER_ATTRIBUTE, new ModifierButtonListener());
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
		////back button
		JButton back = new JButton();
		back.setText(Constants.LABEL_BACK_BUTTON);
		back.addActionListener(l);
		back.setActionCommand(Constants.GOTO_MAIN_BUTTON);
		g.gridy = 2;
		add(back, g);
		////confirm button
		JButton confirm = new JButton();
		confirm.setText(Constants.LABEL_CONFIRM_BUTTON);
		confirm.addActionListener(new ModifierButtonListener());
		confirm.setActionCommand(Constants.ENTER_CREATE_MODIFIER);
		g.gridx = 1;
		add(confirm, g);
		
	}
	public void updateContents()
	{
		ArrayList<String> filter = new ArrayList<String>();
		loadMod.updateDropdown(Constants.LABEL_MODIFIER_LOAD, core.getSublist(filter, null));
		type.updateStringDropdown(Constants.LABEL_TYPE_DROP, core.getTags(null));
		attribute.updateDropdown(Constants.LABEL_ATTRIBUTE_SELECT, core.getSublist(filter, null));
		limit.updateStringDropdown(Constants.LABEL_LIMIT_DROP, core.getTags(filter));
		loadMod.setVisible(false);
		type.setVisible(false);
		attribute.setVisible(false);
		limit.setVisible(false);
		loadMod.setVisible(true);
		type.setVisible(true);
		attribute.setVisible(true);
		limit.setVisible(true);
	}
	/**
	 * creates Modifier out of materials in selection and other fields.
	 * adds new Modifier to loadedComponents in core.
	 */
	protected void createModifier()
	{
		//get name
		String n = name.parts.get(Constants.LABEL_NAME_FIELD).getToolTipText();
		//get attributes
		/**VALUE CREATION NEEDS HELP*/
		ArrayList<Value> v = new ArrayList<>();
		for(EntryElement e : totalList)
		{
			if(e.parts.containsKey(Constants.LABEL_ATTRIBUTE_SELECT))
			{
				JComboBox<Component> cbo = (JComboBox) e.parts.get(Constants.LABEL_ATTRIBUTE_SELECT);
				Component comp = (Component) cbo.getSelectedItem();
				Value val = new Value(comp.name, 0);
				val.tags.add(Constants.TAG_ATTRIBUTE);
				v.add(val);
			}
			if(e.parts.containsKey(Constants.LABEL_STATIC_ATTRIBUTE_FIELD))
			{
				JTextField txt = (JTextField)e.parts.get(Constants.LABEL_STATIC_ATTRIBUTE_FIELD);
				v.add(Scanner.ParseValue(txt.getText()));
			}
		}
		//get tags and limits
		ArrayList<String> tags = new ArrayList<>();
		ArrayList<String> limits = new ArrayList<>();
		for(EntryElement e : totalList)
		{
			if(e.parts.containsKey(Constants.LABEL_TYPE_DROP))
			{
				JComboBox<String>  drop =(JComboBox) e.parts.get(Constants.LABEL_TYPE_DROP);
				tags.add((String) drop.getSelectedItem());
			}
			if(e.parts.containsKey(Constants.LABEL_LIMIT_DROP))
			{
				JComboBox<String>  drop =(JComboBox) e.parts.get(Constants.LABEL_LIMIT_DROP);
				limits.add((String) drop.getSelectedItem());
			}
		}
		
		ArrayList<String> costs = new ArrayList<>();
		
		//get costs
		
		Modifier m = new Modifier(n, v, tags, limits, costs);
		core.loadedComponents.add(m);
	}
	/**
	 * Adds selection to the right hand side
	 * @param e EntryElement being referenced
	 */
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
	    		case Constants.ENTER_ATTRIBUTE:
	    			insertToSelection(attribute);
	    			break;
	    		case Constants.ENTER_TYPE:
	    			insertToSelection(type);
	    			break;
	    		case Constants.ENTER_CREATE_MODIFIER:
	    			createModifier();
	    			
	    			break;
	    	}
	    }
	} 
}
