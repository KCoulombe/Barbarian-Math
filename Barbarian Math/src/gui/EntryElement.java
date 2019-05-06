package gui;

import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import logic.Component;
import logic.Constants;
import logic.Core;
/**
 * Serves as a single row for entry fields. 
 * As such, may be used for holding related elements such as "Cost", "Cost Type" and "Enter"
 * @author Tim
 *
 */
public class EntryElement extends DisplayPanel {
	
	String purpose = "";
	HashMap<String,JComponent> parts;
	public EntryElement(String l) {
		parts = new HashMap<>();
		purpose = l;
		JLabel label = new JLabel(purpose);
		add(label);
	}

	public EntryElement(LayoutManager layout) {
		super(layout);

	}

	public EntryElement(boolean isDoubleBuffered) {
		super(isDoubleBuffered);

	}

	public EntryElement(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);

	}

	@Override
	public void setup(int x, int y, ActionListener l, Core c) {
		setLayout(new FlowLayout());
		
	}
	
	
	/**
	 * adds a textbox with given data.
	 * @param label the outward name
	 * @param tag associated tag
	 */
	public void addTextBox(String label, String tag)
	{
		JTextField t = new JTextField(label);
		t.setActionCommand(tag);
		add(t);
		parts.put(label, t);
	}
	/**
	 * Adds a ComboBox with the given components 
	 * @param label outward name
	 * @param items list of Components
	 */
	public void addDropdown(String label, ArrayList<Component> items)
	{
		JComboBox<Component> c = new JComboBox<Component>();
		for(Component i : items)
		{
			c.addItem(i);
		}
		add(c);
		parts.put(label, c);
	}
	/**
	 * Updates ComboBox 
	 * @param label
	 * @param items
	 */
	public void updateDropdown(String label, ArrayList<Component> items)
	{
		JComboBox<Component> c =(JComboBox<Component>) parts.get(label);
		for(Component i : items)
		{
			c.addItem(i);
		}
	}
	/**
	 * Adds a ComboBox with the given components. Same as addDropdown, but for String arrays.
	 * @param label outward name
	 * @param items list of Strings
	 */
	public void addStringDropdown(String label, ArrayList<String> items)
	{
		JComboBox<String> c = new JComboBox<String>();
		for(String s : items)
		{
			c.addItem(s);
		}
		add(c);
		parts.put(label, c);
	}
	public void updateStringDropdown(String label, ArrayList<String> items)
	{
		JComboBox<String> c =(JComboBox<String>) parts.get(label);
		for(String s : items)
		{
			c.addItem(s);
		}
	}
	
	/**
	 * Adds a Button with given name and event.
	 * @param label outward name
	 * @param event control used for Action Listener
	 */
	public void addButton(String label, String event, ActionListener l)
	{
		JButton b = new JButton(label);
		b.addActionListener(l);
		b.setActionCommand(event);
		add(b);
		parts.put(label, b);
	}

	@Override
	public String toString() {
		
		String out = purpose +": ";
		if(purpose.equals(Constants.LABEL_NAME_FIELD))
		{
			JTextField f =(JTextField) parts.get(Constants.LABEL_NAME_FIELD);
			out = out + f.getText();
		}
		if(purpose.equals(Constants.LABEL_TYPE_DROP))
		{
			JComboBox<String> b = (JComboBox) parts.get(Constants.LABEL_TYPE_DROP);
			out = out + b.getSelectedItem();
		}
		if(purpose.equals(Constants.LABEL_ATTRIBUTE_SELECT))
		{
			/*JComboBox<Component> b = (JComboBox) parts.get(Constants.LABEL_ATTRIBUTE_SELECT);
			out = out + b.getSelectedItem();*/
		}
		if(purpose.equals(Constants.LABEL_COST_FIELD))
		{
			JTextField f =(JTextField) parts.get(Constants.LABEL_COST_FIELD);
			out = out + f.getText();
		}
		if(purpose.equals(Constants.LABEL_LIMIT_DROP))
		{
			JComboBox<String> b = (JComboBox) parts.get(Constants.LABEL_LIMIT_DROP);
			out = out + b.getSelectedItem();
		}
		return out;
	}
	

}
