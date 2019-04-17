package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import logic.Constants;
import logic.Core;
import logic.Scanner;

public class DisplayBuild extends DisplayPanel 
{
    // Table 
    JTable charicterBuilder; 
	
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

	@Override
	public void setup(int x, int y, ActionListener l, Core c) 
	{
		setSize(y,x);
		 
		setLayout(new GridBagLayout());
		GridBagConstraints g = new GridBagConstraints();//used to tell where the item goes
		
		setupAddButton(x,y,l,c,g);
		setupRemoveButton(x,y,l,c,g);
		setupSaveButton(x,y,l,c,g);
		setupTable(x,y,l,c,g);
		
	}
	
	private void setupTable(int x, int y, ActionListener l, Core c, GridBagConstraints g) 
	{
        // Frame Title 
        //setTitle("JTable Example"); 
  
        // Data to be displayed in the JTable 
        String[][] data = { 
            { "Int", "20" }, 
            { "Dex", "7" },
            { "Str", "18" } 
        }; 
  
        // Column Names 
        String[] columnNames = { "Attribute", "Value"}; 
  
        // Initializing the JTable 
        charicterBuilder = new JTable(data, columnNames); 
        //charicterBuilder.setBounds(30, 40, 200, 300); 
  
        // adding it to JScrollPane 
        JScrollPane sp = new JScrollPane(charicterBuilder); 
        
        g.ipady = 0;
		g.ipadx = 0;
		g.gridx = 1;//left
		g.gridy = 0;//bottom
		g.fill = GridBagConstraints.BOTH;
		g.gridheight = 3;
        
        add(sp, g); 
        // Frame Size 
        //setSize(500, 200); 
        // Frame Visible = true 
        setVisible(true); 
	}
	
	private void setupSaveButton(int x, int y, ActionListener l, Core c, GridBagConstraints g) 
	{	
		//establish button
		JButton button = new JButton(Constants.GOTO_SAVE_BUTTON_LABEL);
		//establish dropDown	
		
		//action commands are used for identifying what to do. 
		button.setActionCommand(Constants.CHARACTER_BUILD_SAVE_POPUP);
		//universal listener
		button.addActionListener(l);
		//populate dropdown
		g.ipady = 0;
		g.ipadx = 0;
		g.gridx = 1;//left
		g.gridy = 3;//bottom
		g.fill = GridBagConstraints.HORIZONTAL;
		//g.gridwidth = 2;
		add(button, g);
	}
	
	private void setupAddButton(int x, int y, ActionListener l, Core c, GridBagConstraints g) 
	{	
		//establish button
		JButton button = new JButton("Add Attribute");
		//establish dropDown	
		
		//action commands are used for identifying what to do. 
		button.setActionCommand(Constants.CHARACTER_BUILD_SAVE_POPUP);
		//universal listener
		button.addActionListener(l);
		//populate dropdown
		g.ipady = 0;
		g.ipadx = 1;
		g.gridx = 0;//left
		g.gridy = 0;//bottom
		g.fill = GridBagConstraints.HORIZONTAL;
		add(button, g);
	}

	private void setupRemoveButton(int x, int y, ActionListener l, Core c, GridBagConstraints g)
	{
		//establish button
		JButton button = new JButton("Remove Attribute");
		//establish dropDown	
		
		//action commands are used for identifying what to do. 
		button.setActionCommand(Constants.CHARACTER_BUILD_SAVE_POPUP);
		//universal listener
		button.addActionListener(l);
		//populate dropdown
		g.ipady = 0;
		g.ipadx = 0;
		g.gridx = 0;//left
		g.gridy = 1;//bottom
		g.fill = GridBagConstraints.HORIZONTAL;
		add(button, g);
	}
}




























