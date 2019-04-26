package gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import logic.CharacterBuild;
import logic.Constants;
import logic.Core;
import logic.Modifier;
import logic.Scanner;
import logic.Value;

public class DisplayBuild extends DisplayPanel 
{
    // Table 
    JTable modifierTable; 
    JScrollPane modifierScroll;
    JTable attributeTable;
    JScrollPane attributeScroll;
    
    JLabel charicterListLabel;
    
    JComboBox charicterList;
    
    JButton saveButton;
    JButton addButton;
    JButton removeButton;
    
    CharacterBuild activeCharacter = null;
    int activeCharacterIndex = 0;
    
    Core core;
	
	public DisplayBuild() 
	{
		
	}

	public DisplayBuild(LayoutManager layout) 
	{
		super(layout);

	}

	public DisplayBuild(boolean isDoubleBuffered) 
	{
		super(isDoubleBuffered);

	}

	public DisplayBuild(LayoutManager layout, boolean isDoubleBuffered) 
	{
		super(layout, isDoubleBuffered);
	}
	
	public void updateData(Core c)
	{	
		//if there is no active character, default to the first
		activeCharacter = c.activeRuleset.characters.get(activeCharacterIndex);
		
		//update the list of characters
		charicterList.removeAllItems();
		for (int i = c.activeRuleset.characters.size(); i > 0; i--)
		{
			//update the list of characters
			charicterList.insertItemAt(c.activeRuleset.characters.get(i-1).name, 0);
			
			//System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~" + build.attributes + "~~~~~~~~~~~~~~~~~~~~~~~~~");
		}
		//Default to the first character in the list
		charicterList.setSelectedIndex(activeCharacterIndex);
		
		//update the list of attributes
		DefaultTableModel attributeModel = (DefaultTableModel) attributeTable.getModel();
		attributeModel.setRowCount(0);
		for (Entry<String, Value>  entry : activeCharacter.attributes.entrySet())
		{
			attributeModel.addRow(new Object[]{entry.getKey(), entry.getValue().valueName, entry.getValue().active});
		}
		
		//update the list of modifiers
		DefaultTableModel modifierModel = (DefaultTableModel) modifierTable.getModel();
		modifierModel.setRowCount(0);
		for(Modifier modifier : activeCharacter.modifiers)
		{
			modifierModel.addRow(new Object[]{ modifier.name, modifier.values, modifier.tags, modifier.limits, modifier.costs , modifier.active});
		}
		
		
		Object[][] data = { 
            { "Dummy 1", "0", "Dummy", "Dummy", "Dummy",false}, 
            { "Dummy 2", "0", "Dummy", "Dummy", "Dummy",false},
            { "Dummy 3", "0", "Dummy", "Dummy", "Dummy",false}
        }; 
  
        // Column Names 
        String[] columnNames = { "Name", "Valus", "Tags", "Limits", "costs", "Active"}; 
	  
		
		
		
		
	}


	@Override
	public void setup(int x, int y, ActionListener l, Core c) 
	{		
		setSize(y,x);
		 
		setLayout(new GridBagLayout());
		GridBagConstraints g = new GridBagConstraints();//used to tell where the item goes
		setupCharacterDropdown(x,y,l,c,g);
		setupAddButton(x,y,l,c,g);
		setupRemoveButton(x,y,l,c,g);
		setupSaveButton(x,y,l,c,g);
		setupAttributeTable(x,y,l,c,g);
		setupModifierTable(x,y,l,c,g);
		setupBackButton(x,y,l,c,g);
	}
	
	private void setupCharacterDropdown(int x, int y, ActionListener l, Core c, GridBagConstraints g) 
	{
		charicterListLabel = new JLabel("Charicter: ");
		
		g.ipady = 0;
		g.ipadx = 0;
		g.gridx = 0;//left
		g.gridy = 0;//bottom
		g.gridwidth = 1;
		//g.fill = GridBagConstraints.HORIZONTAL;
		add(charicterListLabel, g);
		
		//List<String> charicters = new  ArrayList("Generiko", "Generikette");
		List<String> characters = new  ArrayList<String>();
		characters.add("Dummy 1");
		characters.add("Dummy 2");
		characters.add("Dummy 3");

		//Create the combo box, select item at index 4.
		//Indices start at 0, so 4 specifies the pig.
		charicterList = new JComboBox(characters.toArray());
		charicterList.setSelectedIndex(0);
		charicterList.addActionListener(new ActionListener () 
		{
		    public void actionPerformed(ActionEvent e) 
		    {  	
		    	if(charicterList.getItemCount() > 0)
		    	{
			    	activeCharacterIndex = charicterList.getSelectedIndex();
			    	updateData(c);
		    	}
		    }
		});
		g.ipady = 0;
		g.ipadx = 0;
		g.gridx = 1;//left
		g.gridy = 0;//bottom
		g.gridwidth = 1;
		//g.fill = GridBagConstraints.HORIZONTAL;
		add(charicterList, g);
	}
	
	private void setupAttributeTable(int x, int y, ActionListener l, Core c, GridBagConstraints g) 
	{
        // Frame Title 
        //setTitle("JTable Example"); 
  
        // Data to be displayed in the JTable 
		Object[][] data = { 
            { "Dummy 1", "0", false}, 
            { "Dummy 2", "0", false},
            { "Dummy 3", "0", false} 
        }; 
  
        // Column Names 
        String[] columnNames = { "Attribute", "Value", "Active"}; 
  
        // Initializing the JTable 
        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        attributeTable = new JTable(model) {

            private static final long serialVersionUID = 1L;

            /*@Override
            public Class getColumnClass(int column) {
            return getValueAt(0, column).getClass();
            }*/
            @Override
            public Class getColumnClass(int column) {
                switch (column) {
                    case 0:
                        return String.class;
                    case 1:
                        return String.class;
                    default:
                        return Boolean.class;
                }
            }
        }; 
        //charicterBuilder.setBounds(30, 40, 200, 300); 
        
        
        //charicterBuilder.setSize(43, 23);
  
        // adding it to JScrollPane 
        attributeScroll = new JScrollPane(attributeTable); 
        
        attributeScroll.setPreferredSize(new Dimension(500, 150));
       
        
        g.ipady = 0;
		g.ipadx = 0;
		g.gridx = 0;//left
		g.gridy = 1;//bottom
		g.fill = GridBagConstraints.BOTH;
		g.gridheight = 2;
		g.gridwidth = 3;
        
        add(attributeScroll, g); 
        // Frame Size 
        //setSize(500, 200); 
        // Frame Visible = true 
        setVisible(true); 
	}
	
	private void setupModifierTable(int x, int y, ActionListener l, Core c, GridBagConstraints g) 
	{
        // Frame Title 
        //setTitle("JTable Example"); 
  
        // Data to be displayed in the JTable 
		Object[][] data = { 
            { "Dummy 1", "0", "Dummy", "Dummy", "Dummy",false}, 
            { "Dummy 2", "0", "Dummy", "Dummy", "Dummy",false},
            { "Dummy 3", "0", "Dummy", "Dummy", "Dummy",false}
        }; 
  
        // Column Names 
        String[] columnNames = { "Name", "Valus", "Tags", "Limits", "costs", "Active"}; 
  
        // Initializing the JTable 
        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        modifierTable = new JTable(model) {

            private static final long serialVersionUID = 1L;

            /*@Override
            public Class getColumnClass(int column) {
            return getValueAt(0, column).getClass();
            }*/
            @Override
            public Class getColumnClass(int column) {
                switch (column) {
                    case 0:
                        return String.class;
                    case 1:
                        return String.class;
                    case 2:
                        return String.class;
                    case 3:
                        return String.class;
                    case 4:
                        return String.class;
                    default:
                        return Boolean.class;
                }
            }
        }; 
        //charicterBuilder.setBounds(30, 40, 200, 300); 
        
        
        //charicterBuilder.setSize(43, 23);
  
        // adding it to JScrollPane 
        modifierScroll = new JScrollPane(modifierTable); 
        
        modifierScroll.setPreferredSize(new Dimension(500, 150));
        
        //charicterBuilder.setPreferredSize(new Dimension(300, 70));
        //charicterBuilder.setPreferredScrollableViewportSize(charicterBuilder.getPreferredSize());
        //charicterBuilder.setFillsViewportHeight(true);
        
        g.ipady = 0;
		g.ipadx = 0;
		g.gridx = 0;//left
		g.gridy = 5;//bottom
		g.fill = GridBagConstraints.BOTH;
		g.gridheight = 2;
		g.gridwidth = 3;
        
        add(modifierScroll, g); 
        // Frame Size 
        //setSize(500, 200); 
        // Frame Visible = true 
        setVisible(true); 
	}
	
	private void setupSaveButton(int x, int y, ActionListener l, Core c, GridBagConstraints g) 
	{	
		//establish button
		saveButton = new JButton(Constants.GOTO_SAVE_BUTTON_LABEL);
		//establish dropDown	
		
		//action commands are used for identifying what to do. 
		saveButton.setActionCommand(Constants.CHARACTER_BUILD_SAVE_POPUP);
		//universal listener
		saveButton.addActionListener(l);
		//populate dropdown
		g.ipady = 0;
		g.ipadx = 0;
		g.gridx = 0;//left
		g.gridy = 7;//bottom
		g.gridwidth = 3;
		g.fill = GridBagConstraints.HORIZONTAL;
		//g.gridwidth = 2;
		add(saveButton, g);
	}
	
	private void setupAddButton(int x, int y, ActionListener l, Core c, GridBagConstraints g) 
	{	
		//establish button
		addButton = new JButton("Add Attribute");
		//establish dropDown	
		
		//action commands are used for identifying what to do. 
		addButton.setActionCommand(Constants.CHARACTER_BUILD_SAVE_POPUP);
		//universal listener
		addButton.addActionListener(l);
		g.ipady = 0;
		g.ipadx = 0;
		g.gridx = 1;//left
		g.gridy = 4;//bottom
		g.gridwidth = 1;
		//g.fill = GridBagConstraints.HORIZONTAL;
		add(addButton, g);
	}

	private void setupRemoveButton(int x, int y, ActionListener l, Core c, GridBagConstraints g)
	{
		//establish button
		removeButton = new JButton("Remove Attribute");
		//establish dropDown	
		
		//action commands are used for identifying what to do. 
		removeButton.setActionCommand(Constants.CHARACTER_BUILD_SAVE_POPUP);
		//universal listener
		removeButton.addActionListener(l);
		//populate dropdown
		g.ipady = 0;
		g.ipadx = 0;
		g.gridx = 2;//left
		g.gridy = 4;//bottom
		g.gridwidth = 1;
		//g.fill = GridBagConstraints.HORIZONTAL;
		add(removeButton, g);
	}
	private void setupBackButton(int x, int y, ActionListener l, Core c, GridBagConstraints g)
	{
		JButton back = new JButton();
		back.setText(Constants.LABEL_BACK_BUTTON);
		back.setActionCommand(Constants.GOTO_MAIN_BUTTON);
		back.addActionListener(l);
		g.gridy = 8;
		add(back, g);
	}
}




























