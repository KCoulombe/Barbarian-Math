package gui;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import logic.Constants;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

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

	@Override
	public void setup(int x, int y, ActionListener l, Core c) {
		
		setSize(x,y);
		setLayout(new GridBagLayout());
		GridBagConstraints g = new GridBagConstraints();
		//holds the vertical list
		JPanel optionContainer = new JPanel();
		optionContainer.setLayout(new BoxLayout(optionContainer, BoxLayout.Y_AXIS));
		//initialize elements
		EntryElement loadMod = new EntryElement();
		EntryElement name = new EntryElement();
		EntryElement type = new EntryElement();
		EntryElement attribute = new EntryElement();
		EntryElement cost = new EntryElement();
		EntryElement limit = new EntryElement();
		////populate elements
		//load
		ArrayList<String> filter = new ArrayList<String>();
		filter.add(Constants.TAG_MODIFIER);
		loadMod.addDropdown(Constants.LABEL_MODIFIER_LOAD, c.getSublist(filter, null));
		loadMod.addButton(Constants.LABEL_MODIFIER_LOAD_BUTTON, Constants.ENTER_MODIFIER_LOAD);
		//name
		name.addTextBox(Constants.LABEL_NAME_FIELD, Constants.TAG_NAME);
		//type
		filter.clear();
		type.addStringDropdown(Constants.LABEL_TYPE_DROP, c.getTags(null));
		type.addButton(Constants.LABEL_ADD_BUTTON, Constants.ENTER_TYPE);
		//attribute
		filter.add(Constants.TAG_ATTRIBUTE);
		attribute.addDropdown(Constants.LABEL_ATTRIBUTE_SELECT, c.getSublist(filter, null));
		//cost
		cost.addTextBox(Constants.LABEL_COST_FIELD, Constants.TAG_INTEGER);
		cost.addButton(Constants.LABEL_ADD_BUTTON, Constants.ENTER_COST);
		//limit
		
	}

}
