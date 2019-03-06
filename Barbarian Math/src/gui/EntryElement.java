package gui;

import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

import logic.Component;
import logic.Core;
/**
 * Serves as a single row for entry fields. 
 * As such, may be used for holding related elements such as "Cost", "Cost Type" and "Enter"
 * @author Tim
 *
 */
public class EntryElement extends DisplayPanel {

	public EntryElement() {
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
	}
	/**
	 * Adds a Button with given name and event.
	 * @param label outward name
	 * @param event control used for Action Listener
	 */
	public void addButton(String label, String event)
	{
		JButton b = new JButton(label);
		b.setActionCommand(event);
		add(b);
	}

}
