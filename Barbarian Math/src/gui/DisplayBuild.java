package gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import logic.Adventurer;
import logic.Bundle;
import logic.CharacterBuild;
import logic.Constants;
import logic.Core;
import logic.Modifier;
import logic.Scalar;
import logic.Scanner;
import logic.Value;

public class DisplayBuild extends DisplayPanel 
{
    // Tables
    JTable modifierTable; 
    JScrollPane modifierScroll;
    JTable attributeTable;
    JScrollPane attributeScroll;
    JTable classTable;
    JScrollPane classScroll;
    
    JLabel charicterListLabel;
    
    JComboBox charicterList;
    JComboBox modifierList;
    JComboBox classList;
    JComboBox subclassList;
    
    JButton saveButton;
    JButton importButton;
    JButton exportButton;
    JButton addButton;
    JButton removeButton;
    JButton addModButton;
    JButton removeModButton;
    JButton addClassButton;
    JButton newCharacterButton;
    JButton removeClassButton;
    JButton backButton;
    
    JTextField characterName;
    
    //CharacterBuild activeCharacter = null;
    List<CharacterBuild> dummyCharacters;
    int activeCharacterIndex;
    
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
	
	public void initData(Core c)
	{	
		//Create a copy of all of the charicters in the ruleset
		dummyCharacters = new ArrayList<CharacterBuild>();
		activeCharacterIndex = 0;
		for(CharacterBuild cb : c.activeRuleset.characters)
		{
			dummyCharacters.add(new CharacterBuild(cb.name, new ArrayList<Modifier>(cb.modifiers), new ArrayList<Modifier>(cb.classModifiers), 
				new ArrayList<Scalar>(cb.scalars), new ArrayList<Adventurer>(cb.classes), new HashMap<String, Value>(cb.attributes)));
		}
		/*CharacterBuild cb = c.activeRuleset.characters.get(activeCharacterIndex);
		//create a copy of the active character to edit
		activeCharacter = new CharacterBuild(cb.name, new ArrayList<Modifier>(cb.modifiers), new ArrayList<Modifier>(cb.classModifiers), 
				new ArrayList<Scalar>(cb.scalars), new ArrayList<Adventurer>(cb.classes), new HashMap<String, Value>(cb.attributes));	*/
		updateValues(c);
		
		
	}
	
	public void updateValues(Core c)
	{
		//update the name field
		characterName.setText(dummyCharacters.get(activeCharacterIndex).name);
	
		//update the list of characters
		charicterList.removeAllItems();
		for (int i = dummyCharacters.size(); i > 0; i--)
		{
			//update the list of characters
			charicterList.insertItemAt(dummyCharacters.get(i-1).name, 0);
			
			//System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~" + build.attributes + "~~~~~~~~~~~~~~~~~~~~~~~~~");
		}
		//Default to the active charicter
		charicterList.setSelectedIndex(activeCharacterIndex);
		
		
		//update the list of modifiers
		dummyCharacters.get(activeCharacterIndex).UpdateCassModifiers(c.activeRuleset);
		modifierList.removeAllItems();
		for (int i = c.activeRuleset.modifiers.size(); i > 0; i--)
		{
			//update the list of characters
			modifierList.insertItemAt(c.activeRuleset.modifiers.get(i-1).name, 0);
		}
		modifierList.setSelectedIndex(0);
		
		//update the list of classes
		classList.removeAllItems();
		for (int i = c.activeRuleset.adventurers.size(); i > 0; i--)
		{
			//update the list of characters
			//System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~" + c.activeRuleset.adventurers.get(i-1).name + "~~~~~~~~~~~~~~~~~~~~~~~~~");
			classList.insertItemAt(c.activeRuleset.adventurers.get(i-1).name, 0);
		}
		classList.setSelectedIndex(0);
		
		//update the list of attributes
		DefaultTableModel attributeModel = (DefaultTableModel) attributeTable.getModel();
		attributeModel.setRowCount(0);
		for (Entry<String, Value>  entry : dummyCharacters.get(activeCharacterIndex).attributes.entrySet())
		{
			attributeModel.addRow(new Object[]{entry.getKey(), entry.getValue().valueName, entry.getValue().active});
		}
		
		//update the list of modifiers
		DefaultTableModel modifierModel = (DefaultTableModel) modifierTable.getModel();
		modifierModel.setRowCount(0);
		
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~" + dummyCharacters.get(activeCharacterIndex).classModifiers + "~~~~~~~~~~~~~~~~~~~~~~~~~");
		for(Modifier modifier : dummyCharacters.get(activeCharacterIndex).classModifiers)
		{
			modifierModel.addRow(new Object[]{"*" + modifier.name, modifier.values, modifier.tags, modifier.limits, modifier.costs , modifier.active});
		}
		for(Modifier modifier : dummyCharacters.get(activeCharacterIndex).modifiers)
		{
			modifierModel.addRow(new Object[]{ modifier.name, modifier.values, modifier.tags, modifier.limits, modifier.costs , modifier.active});
		}
		
		
		//update the list of classes
		DefaultTableModel classModel = (DefaultTableModel) classTable.getModel();
		classModel.setRowCount(0);	
		for(Adventurer adv : dummyCharacters.get(activeCharacterIndex).classes)
		{
			/*JComboBox comboBox = new JComboBox();
			for(Bundle subclass : adv.sub_classes)
			{
				comboBox.addItem(subclass.name);
			}*/
			classModel.addRow(new Object[]{ adv.name, adv.GetSubclass().name, adv.level});
		}
	}

	@Override
	public void setup(int x, int y, ActionListener l, Core c) 
	{		
		setSize(y,x);
		 
		setLayout(new GridBagLayout());
		
		GridBagConstraints g = new GridBagConstraints();//used to tell where the item goes
		
		setupClassTable(x,y,l,c,g);
		setupNewCharacterButton(x,y,l,c,g);
		setupRemoveButton(x,y,l,c,g);
		setupAddModButton(x,y,l,c,g);
		setupCharacterDropdown(x,y,l,c,g);
		setupCharacterName(x,y,l,c,g);
		setupAddButton(x,y,l,c,g);
		setupModifierDropdown(x,y,l,c,g);
		setupRemoveModButton(x,y,l,c,g);
		setupSaveButton(x,y,l,c,g);
		setupExportButton(x,y,l,c,g);
		setupAttributeTable(x,y,l,c,g);
		setupModifierTable(x,y,l,c,g);
		setupBackButton(x,y,l,c,g);
		setupImportButton(x,y,l,c,g);
		
		setupAddClassButton(x,y,l,c,g);
		setupRemoveClassButton(x,y,l,c,g);
		setupClassDropdown(x,y,l,c,g);
		setupSubClassDropdown(x,y,l,c,g);
		
	}
	
	private void setupCharacterDropdown(int x, int y, ActionListener l, Core c, GridBagConstraints g) 
	{
		/*charicterListLabel = new JLabel("Charicter: ");
		g.ipady = 0;
		g.ipadx = 0;
		g.gridx = 0;//left
		g.gridy = 0;//bottom
		g.gridwidth = 1;
		//g.fill = GridBagConstraints.HORIZONTAL;
		add(charicterListLabel, g);*/
		
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
		    		dummyCharacters.get(activeCharacterIndex).name = characterName.getText();
			    	activeCharacterIndex = charicterList.getSelectedIndex();
			    	updateValues(c);
		    	}
		    }
		});
		
		charicterList.setPreferredSize(new Dimension(150, 25));
		g.ipady = 0;
		g.ipadx = 0;
		g.gridx = 1;//left
		g.gridy = 0;//bottom
		g.gridwidth = 1;
		//g.fill = GridBagConstraints.HORIZONTAL;
		add(charicterList, g);
	}
	
	private void setupCharacterName(int x, int y, ActionListener l, Core c, GridBagConstraints g) 
	{
		characterName = new JTextField(20);
		
		characterName.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e)
			{
				dummyCharacters.get(activeCharacterIndex).name = characterName.getText();
			}
		});
		g.ipady = 0;
		g.ipadx = 0;
		g.gridx = 2;//left
		g.gridy = 0;//bottom
		g.gridwidth = 1;
		add(characterName, g);
	}
	
	private void setupModifierDropdown(int x, int y, ActionListener l, Core c, GridBagConstraints g) 
	{
		
		//List<String> charicters = new  ArrayList("Generiko", "Generikette");
		List<String> characters = new  ArrayList<String>();
		characters.add("Dummy 1");
		characters.add("Dummy 2");
		characters.add("Dummy 3");

		//Create the combo box, select item at index 4.
		//Indices start at 0, so 4 specifies the pig.
		modifierList = new JComboBox(characters.toArray());
		modifierList.setSelectedIndex(0);
		
		g.ipady = 0;
		g.ipadx = 0;
		g.gridx = 0;//left
		g.gridy = 11;//bottom
		g.gridwidth = 2;
		//g.fill = GridBagConstraints.HORIZONTAL;
		add(modifierList, g);
	}
	
	private void setupClassDropdown(int x, int y, ActionListener l, Core c, GridBagConstraints g) 
	{	
		//List<String> charicters = new  ArrayList("Generiko", "Generikette");
		List<String> classes = new  ArrayList<String>();
		classes.add("Dummy 1");
		classes.add("Dummy 2");
		classes.add("Dummy 3");

		//Create the combo box, select item at index 4.
		//Indices start at 0, so 4 specifies the pig.
		classList = new JComboBox(classes.toArray());
		classList.setSelectedIndex(0);
		
		classList.addActionListener(new ActionListener () 
		{
		    public void actionPerformed(ActionEvent e) 
		    {  	
		    	if(classList.getItemCount() > 0)
		    	{
		    		//update the list of subclasses
		    		subclassList.removeAllItems();
		    		for (int i = c.activeRuleset.adventurers.get(classList.getSelectedIndex()).sub_classes.size(); i > 0; i--)
		    		{
		    			subclassList.insertItemAt(c.activeRuleset.adventurers.get(classList.getSelectedIndex()).sub_classes.get(i-1).name, 0);
		    		}
		    		
		    		/*for (Bundle subclass : c.activeRuleset.adventurers.get(classList.getSelectedIndex()).sub_classes)
		    		{
		    			//update the list of characters
		    			//System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~" + c.activeRuleset.adventurers.get(i-1).name + "~~~~~~~~~~~~~~~~~~~~~~~~~");
		    			subclassList.insertItemAt(subclass.name, 0);
		    		}*/
		    		subclassList.setSelectedIndex(0);
		    	}
		    }
		});
		
		g.ipady = 0;
		g.ipadx = 0;
		g.gridx = 1;//left
		g.gridy = 14;//bottom
		g.gridwidth = 1;
		//g.fill = GridBagConstraints.HORIZONTAL;
		add(classList, g);
	}
	
	private void setupSubClassDropdown(int x, int y, ActionListener l, Core c, GridBagConstraints g) 
	{	
		//List<String> charicters = new  ArrayList("Generiko", "Generikette");
		List<String> subclasses = new  ArrayList<String>();
		subclasses.add("Sub Dummy 1");
		subclasses.add("Sub Dummy 2");
		subclasses.add("Sub Dummy 3");

		//Create the combo box, select item at index 4.
		//Indices start at 0, so 4 specifies the pig.
		subclassList = new JComboBox(subclasses.toArray());
		subclassList.setSelectedIndex(0);
		
		g.ipady = 0;
		g.ipadx = 0;
		g.gridx = 2;//left
		g.gridy = 14;//bottom
		g.gridwidth = 1;
		//g.fill = GridBagConstraints.HORIZONTAL;
		add(subclassList, g);
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
        
		attributeTable.getModel().addTableModelListener(new TableModelListener() 
		{
			public void tableChanged(TableModelEvent e) 
			{
				DefaultTableModel attributeModel = (DefaultTableModel) attributeTable.getModel();
				
				Map<String, Value> newAttributes = new HashMap<String, Value>();
				
				for(int row = 0; row < attributeModel.getRowCount(); row++) 
				{
					String str = attributeModel.getValueAt(row, 0).toString();
					Value val = Scanner.ParseValue(attributeModel.getValueAt(row, 1).toString());
					val.active = (boolean) attributeModel.getValueAt(row, 2);
					newAttributes.put(str, val);
					
					dummyCharacters.get(activeCharacterIndex).attributes = new HashMap<String, Value>(newAttributes);
					
					/*for(int col = 0; col < attributeModel.getColumnCount() -1; col++) 
					{
						System.out.println(attributeModel.getValueAt(row, col));
					}*/
				}

			}
		});
        //charicterBuilder.setBounds(30, 40, 200, 300); 
        
        
        //charicterBuilder.setSize(43, 23);
  
        // adding it to JScrollPane 
        attributeScroll = new JScrollPane(attributeTable); 
        
        attributeScroll.setPreferredSize(new Dimension(500, 150));
       
        
        g.ipady = 0;
		g.ipadx = 0;
		g.gridx = 0;//left
		g.gridy = 2;//bottom
		g.fill = GridBagConstraints.BOTH;
		g.gridheight = 2;
		g.gridwidth = 5;
        
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
            @Override
            public boolean isCellEditable(int row, int column) 
            {
                //all cells false
                //return false;
            	return column == 5;
            }
        }; 
        
        modifierTable.getModel().addTableModelListener(new TableModelListener() 
		{
			public void tableChanged(TableModelEvent e) 
			{
				DefaultTableModel modifierModel = (DefaultTableModel) modifierTable.getModel();
				
				//Map<String, Value> newAttributes = new HashMap<String, Value>();
				
				for(int row = 0; row < modifierModel.getRowCount(); row++) 
				{
					for(Modifier mod : dummyCharacters.get(activeCharacterIndex).classModifiers)
					{
						if(modifierModel.getValueAt(row, 0).toString().equals("*" + mod.name))
						{
							mod.active = (boolean) modifierModel.getValueAt(row, 5);
						}
					}
					
					for(Modifier mod : dummyCharacters.get(activeCharacterIndex).modifiers)
					{
						if(modifierModel.getValueAt(row, 0).toString().equals(mod.name))
						{
							mod.active = (boolean) modifierModel.getValueAt(row, 5);
						}
					}
				}
			}
		});
        
        // adding it to JScrollPane 
        modifierScroll = new JScrollPane(modifierTable); 
        
        modifierScroll.setPreferredSize(new Dimension(500, 150));
        
        //charicterBuilder.setPreferredSize(new Dimension(300, 70));
        //charicterBuilder.setPreferredScrollableViewportSize(charicterBuilder.getPreferredSize());
        //charicterBuilder.setFillsViewportHeight(true);
        
        modifierTable.getColumnModel().getColumn(0).setPreferredWidth(200);
        g.ipady = 0;
		g.ipadx = 0;
		g.gridx = 0;//left
		g.gridy = 6;//bottom
		g.fill = GridBagConstraints.BOTH;
		g.gridheight = 1;
		g.gridwidth = 5;
        
        add(modifierScroll, g); 
        // Frame Size 
        //setSize(500, 200); 
        // Frame Visible = true 
        setVisible(true); 
	}
	
	private void setupClassTable(int x, int y, ActionListener l, Core c, GridBagConstraints g) 
	{

        // Frame Title 
        //setTitle("JTable Example"); 
  
        // Data to be displayed in the JTable 
		Object[][] data = { 
            { "Dummy Class 1", "Dummy Sub Class", 0}, 
            { "Dummy Class 2", "Dummy Sub Class", 0},
            { "Dummy Class 3", "Dummy Sub Class", 0}
        }; 
  
        // Column Names 
        String[] columnNames = { "Name", "Subclass", "Level"}; 
  
        // Initializing the JTable 
        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        classTable = new JTable(model) {

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
                    return Integer.class;
            	}
            }
            
            @Override
            public boolean isCellEditable(int row, int column) 
            {
                //all cells false
                //return false;
            	return column == 2;
            }
        }; 
  
        classTable.getModel().addTableModelListener(new TableModelListener() 
		{
			public void tableChanged(TableModelEvent e) 
			{
				DefaultTableModel classModel = (DefaultTableModel) classTable.getModel();
				
				//Map<String, Value> newAttributes = new HashMap<String, Value>();
				
				for(int i = 0; i < classModel.getRowCount(); i++) 
				{
					dummyCharacters.get(activeCharacterIndex).SetClassLevel(i, (Integer)classModel.getValueAt(i, 2), c.activeRuleset);
				}
			}
		});
        
        // adding it to JScrollPane 
        classScroll = new JScrollPane(classTable); 
        
        classScroll.setPreferredSize(new Dimension(500, 150));
        
        //charicterBuilder.setPreferredSize(new Dimension(300, 70));
        //charicterBuilder.setPreferredScrollableViewportSize(charicterBuilder.getPreferredSize());
        //charicterBuilder.setFillsViewportHeight(true);
        
        g.ipady = 0;
		g.ipadx = 0;
		g.gridx = 0;//left
		g.gridy = 12;//bottom
		g.fill = GridBagConstraints.BOTH;
		g.gridheight = 1;
		g.gridwidth = 5;
        
        add(classScroll, g); 
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
		
		saveButton.addActionListener(new ActionListener () 
		{
		    public void actionPerformed(ActionEvent e) 
		    {  	
		    	//outerloop:
		    	for(CharacterBuild dummy : dummyCharacters)
		    	{
		    		Boolean found = false;
		    		
		    		
		    		for(int i = 0; i < c.activeRuleset.characters.size(); i++)
		    		{
		    			
		    			System.out.println(dummy.name + "=?" + c.activeRuleset.characters.get(i).name);
		    			
		    			if(dummy.name.equals(c.activeRuleset.characters.get(i).name))
		    			{
		    				/*int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to override build '" + 
			    					dummyCharacters.get(activeCharacterIndex).name +  "'?", "Confirm",
			    			        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		    				if(response == JOptionPane.YES_OPTION)
		    				{*/
		    				
		    				c.activeRuleset.characters.set(i, new CharacterBuild(dummy.name, new ArrayList<Modifier>(dummy.modifiers), new ArrayList<Modifier>(dummy.classModifiers), 
	    							new ArrayList<Scalar>(dummy.scalars), new ArrayList<Adventurer>(dummy.classes), new HashMap<String, Value>(dummy.attributes)));
		    				
		    				/*build = new CharacterBuild(dummy.name, new ArrayList<Modifier>(dummy.modifiers), new ArrayList<Modifier>(dummy.classModifiers), 
		    							new ArrayList<Scalar>(dummy.scalars), new ArrayList<Adventurer>(dummy.classes), new HashMap<String, Value>(dummy.attributes));*/
		    				found = true;
		    				//break outerloop;
		    				
		    				
		    				//}
		    				//break outerloop;
		    			} 
		    		}
		    		
		    		if(!found)
		    		{
		    			System.out.println("~~~~~~~~~~~~~~~~~~~~No Match~~~~~~~~~~~~~~~~~~~~");
		    			c.activeRuleset.characters.add(new CharacterBuild(dummy.name, new ArrayList<Modifier>(dummy.modifiers), new ArrayList<Modifier>(dummy.classModifiers), 
		    						new ArrayList<Scalar>(dummy.scalars), new ArrayList<Adventurer>(dummy.classes), new HashMap<String, Value>(dummy.attributes)));
		    		}
		    	}
		    }
		});
		//populate dropdown
		g.ipady = 0;
		g.ipadx = 0;
		g.gridx = 0;//left
		g.gridy = 16;//bottom
		g.gridwidth = 2;
		//g.fill = GridBagConstraints.HORIZONTAL;
		//g.gridwidth = 2;
		add(saveButton, g);
	}
	
	private void setupBackButton(int x, int y, ActionListener l, Core c, GridBagConstraints g) 
	{	
		//establish button
		backButton = new JButton("Back");
		//establish dropDown	
		
		//action commands are used for identifying what to do. 
		backButton.setActionCommand(Constants.GOTO_MAIN_BUTTON);
		//universal listener
		backButton.addActionListener(l);
		//populate dropdown
		g.ipady = 2;
		g.insets = new Insets(120,0,0,0);
		g.ipadx = 0;
		g.gridx = 0;//left
		g.gridy = 17;//bottom
		g.gridwidth = 4;
		//g.fill = GridBagConstraints.HORIZONTAL;
		//g.gridwidth = 2;
		add(backButton, g);
		
		g.insets = new Insets(0,0,0,0);
	}
	
	private void setupExportButton(int x, int y, ActionListener l, Core c, GridBagConstraints g) 
	{	
		//establish button
		exportButton = new JButton("Export");
		//establish dropDown	
		
		//action commands are used for identifying what to do. 
		exportButton.setActionCommand(Constants.CHARACTER_BUILD_SAVE_POPUP);
		//universal listener
		exportButton.addActionListener(l);
		//populate dropdown
		g.ipady = 0;
		g.ipadx = 0;
		g.gridx = 3;//left
		g.gridy = 16;//bottom
		g.gridwidth = 1;
		//g.fill = GridBagConstraints.HORIZONTAL;
		//g.gridwidth = 2;
		add(exportButton, g);
	}
	private void setupImportButton(int x, int y, ActionListener l, Core c, GridBagConstraints g) 
	{	
		//establish button
		importButton = new JButton("Import");
		//establish dropDown	
		
		//action commands are used for identifying what to do. 
		importButton.setActionCommand(Constants.CHARACTER_BUILD_LOAD_POPUP);
		//universal listener
		importButton.addActionListener(l);
		//populate dropdown
		g.ipady = 0;
		g.ipadx = 0;
		g.gridx = 2;//left
		g.gridy = 16;//bottom
		g.gridwidth = 1;
		//g.fill = GridBagConstraints.HORIZONTAL;
		//g.gridwidth = 2;
		add(importButton, g);
	}
	
	private void setupAddButton(int x, int y, ActionListener l, Core c, GridBagConstraints g) 
	{	
		//establish button
		addButton = new JButton("Add Attribute");
		//establish dropDown	
		
		//action commands are used for identifying what to do. 
		//addButton.setActionCommand(Constants.CHARACTER_BUILD_SAVE_POPUP);
		
		addButton.addActionListener(new ActionListener () 
		{
		    public void actionPerformed(ActionEvent e) 
		    {  	
		    	DefaultTableModel attributeModel = (DefaultTableModel) attributeTable.getModel();
		    	attributeModel.addRow(new Object[]{"Unnamed", "0", false});
		    }
		});
		//universal listener
		addButton.addActionListener(l);
		g.ipady = 0;
		g.ipadx = 0;
		g.gridx = 0;//left
		g.gridy = 4;//bottom
		g.gridwidth = 2;
		//g.fill = GridBagConstraints.HORIZONTAL;
		add(addButton, g);
	}
	
	private void setupNewCharacterButton(int x, int y, ActionListener l, Core c, GridBagConstraints g) 
	{	
		//establish button
		newCharacterButton = new JButton("New Character");
		//establish dropDown	
		
		//action commands are used for identifying what to do. 
		//addButton.setActionCommand(Constants.CHARACTER_BUILD_SAVE_POPUP);
		
		newCharacterButton.addActionListener(new ActionListener () 
		{
		    public void actionPerformed(ActionEvent e) 
		    {  	
		    	dummyCharacters.add(new CharacterBuild("Unnamed Charicter", new ArrayList<String>(), new ArrayList<String>(), 
		    			new ArrayList<String>(), new ArrayList<Integer>(), new ArrayList<String>(), new HashMap<String, Value>()));
		    	activeCharacterIndex = dummyCharacters.size()-1;
		    	updateValues(c);
		    	
		    }
		});
		//universal listener
		newCharacterButton.addActionListener(l);
		g.ipady = 0;
		g.ipadx = 0;
		g.gridx = 3;//left
		g.gridy = 0;//bottom
		g.gridwidth = 2;
		//g.fill = GridBagConstraints.HORIZONTAL;
		add(newCharacterButton, g);
	}

	private void setupRemoveButton(int x, int y, ActionListener l, Core c, GridBagConstraints g)
	{
		//establish button
		removeButton = new JButton("Remove Attribute");
		//establish dropDown	
		
		//action commands are used for identifying what to do. 
		//removeButton.setActionCommand(Constants.CHARACTER_BUILD_SAVE_POPUP);
		
		removeButton.addActionListener(new ActionListener () 
		{
		    public void actionPerformed(ActionEvent e) 
		    {  	
		    	if(!attributeTable.getSelectionModel().isSelectionEmpty())
		    	{
			    	DefaultTableModel attributeModel = (DefaultTableModel) attributeTable.getModel();
			    	attributeModel.removeRow(attributeTable.getSelectedRow());
		    	}

		    }
		});
		
		//universal listener
		removeButton.addActionListener(l);
		//populate dropdown
		g.ipady = 0;
		g.ipadx = 0;
		g.gridx = 2;//left
		g.gridy = 4;//bottom
		g.gridwidth = 2;
		//g.fill = GridBagConstraints.HORIZONTAL;
		add(removeButton, g);
	}
	
	private void setupAddModButton(int x, int y, ActionListener l, Core c, GridBagConstraints g) 
	{	
		//establish button
		addModButton = new JButton("Add Modifier");
		//establish dropDown	
		
		//action commands are used for identifying what to do. 
		//addButton.setActionCommand(Constants.CHARACTER_BUILD_SAVE_POPUP);
		
		addModButton.addActionListener(new ActionListener () 
		{
		    public void actionPerformed(ActionEvent e) 
		    {  	
		    	dummyCharacters.get(activeCharacterIndex).modifiers.add(c.activeRuleset.modifiers.get(modifierList.getSelectedIndex()));
		    	updateValues(c);
		    }
		});
		//universal listener
		addModButton.addActionListener(l);
		g.ipady = 0;
		g.ipadx = 0;
		g.gridx = 2;//left
		g.gridy = 11;//bottom
		g.gridwidth = 1;
		//g.fill = GridBagConstraints.HORIZONTAL;
		add(addModButton, g);
	}

	private void setupRemoveModButton(int x, int y, ActionListener l, Core c, GridBagConstraints g)
	{
		//establish button
		removeModButton = new JButton("Remove Modifier");
		//establish dropDown	
		
		//action commands are used for identifying what to do. 
		//removeButton.setActionCommand(Constants.CHARACTER_BUILD_SAVE_POPUP);
		
		removeModButton.addActionListener(new ActionListener () 
		{
		    public void actionPerformed(ActionEvent e) 
		    {  	
		    	if(!modifierTable.getSelectionModel().isSelectionEmpty())
		    	{
			    	for(Modifier mod : dummyCharacters.get(activeCharacterIndex).modifiers)
			    	{
			    		//System.out.println(modifierTable.getValueAt(modifierTable.getSelectedRow(), 0) + "=?" + mod.name);
			    		
			    		if(modifierTable.getValueAt(modifierTable.getSelectedRow(), 0).equals(mod.name))
			    		{
			    			dummyCharacters.get(activeCharacterIndex).modifiers.remove(mod);
			    			updateValues(c);
			    			break;
			    		}
			    	}
		    	}
		    }
		});
		
		//universal listener
		removeModButton.addActionListener(l);
		//populate dropdown
		g.ipady = 0;
		g.ipadx = 0;
		g.gridx = 3;//left
		g.gridy = 11;//bottom
		g.gridwidth = 1;
		//g.fill = GridBagConstraints.HORIZONTAL;
		add(removeModButton, g);
	}

	private void setupAddClassButton(int x, int y, ActionListener l, Core c, GridBagConstraints g) 
	{	
		//establish button
		addClassButton = new JButton("Add Class");
		//establish dropDown	
		
		//action commands are used for identifying what to do. 
		//addButton.setActionCommand(Constants.CHARACTER_BUILD_SAVE_POPUP);
		
		addClassButton.addActionListener(new ActionListener () 
		{
		    public void actionPerformed(ActionEvent e) 
		    {  	
		    	Adventurer adv = c.activeRuleset.adventurers.get(classList.getSelectedIndex());
		    	dummyCharacters.get(activeCharacterIndex).classes.add(new Adventurer(adv.name, adv.main_class, adv.sub_classes, 
		    								adv.modifiers, adv.scalars, 0, subclassList.getSelectedIndex()));
		    	updateValues(c);
		    }
		});
		//universal listener
		addClassButton.addActionListener(l);
		g.ipady = 0;
		g.ipadx = 0;
		g.gridx = 3;//left
		g.gridy = 14;//bottom
		g.gridwidth = 1;
		//g.fill = GridBagConstraints.HORIZONTAL;
		add(addClassButton, g);
	}
	
	private void setupRemoveClassButton(int x, int y, ActionListener l, Core c, GridBagConstraints g)
	{
		//establish button
		removeClassButton = new JButton("Remove Class");
		//establish dropDown	
		
		//action commands are used for identifying what to do. 
		//removeButton.setActionCommand(Constants.CHARACTER_BUILD_SAVE_POPUP);
		
		removeClassButton.addActionListener(new ActionListener () 
		{
		    public void actionPerformed(ActionEvent e) 
		    {  	
		    	if(!classTable.getSelectionModel().isSelectionEmpty())
		    	{
			    	for(Adventurer adv : dummyCharacters.get(activeCharacterIndex).classes)
			    	{
			    		System.out.println(classTable.getValueAt(classTable.getSelectedRow(), 0) + "=?" + adv.name);
			    		
			    		if(classTable.getValueAt(classTable.getSelectedRow(), 0).equals(adv.name))
			    		{
			    			dummyCharacters.get(activeCharacterIndex).classes.remove(adv);
			    			updateValues(c);
			    			break;
			    		}
			    	}
		    	}
		    }
		});
		
		//universal listener
		removeClassButton.addActionListener(l);
		//populate dropdown
		g.ipady = 0;
		g.ipadx = 0;
		g.gridx = 1;//left
		g.gridy = 15;//bottom
		g.gridwidth = 4;
		//g.fill = GridBagConstraints.HORIZONTAL;
		add(removeClassButton, g);
	}
}



























