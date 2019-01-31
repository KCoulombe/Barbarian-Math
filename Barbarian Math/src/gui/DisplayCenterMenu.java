package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import logic.Constants;
import logic.Core;

public class DisplayCenterMenu extends DisplayPanel {
	
	
	public DisplayCenterMenu() {
	}

	public DisplayCenterMenu(LayoutManager layout) {
		super(layout);

	}

	public DisplayCenterMenu(boolean isDoubleBuffered) {
		super(isDoubleBuffered);

	}

	public DisplayCenterMenu(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);

	}
	
	public void setup(int x, int y, ActionListener l, Core c)
	{
		setSize(y,x);
		//setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		setLayout(new GridBagLayout());
		GridBagConstraints g = new GridBagConstraints();
		JLabel text = new JLabel("Barbarian Math");
		
		
		JButton calculatorButton = new JButton(Constants.GOTO_CALCULATOR_BUTTON_LABEL);
		JButton buildButton = new JButton(Constants.GOTO_BUILD_BUTTON_LABEL);
		JButton modifierButton = new JButton(Constants.GOTO_MODIFIER_BUTTON_LABEL);
		JButton saveButton = new JButton(Constants.GOTO_SAVE_BUTTON_LABEL);
		JButton loadButton = new JButton(Constants.GOTO_LOAD_BUTTON_LABEL);
		
		calculatorButton.setActionCommand(Constants.GOTO_CALCULATOR_BUTTON);
		buildButton.setActionCommand(Constants.GOTO_BUILD_BUTTON);
		modifierButton.setActionCommand(Constants.GOTO_MODIFIER_BUTTON);
		saveButton.setActionCommand(Constants.GOTO_SAVE_BUTTON);
		loadButton.setActionCommand(Constants.GOTO_LOAD_BUTTON);
		
		calculatorButton.addActionListener(l);
		buildButton.addActionListener(l);
		modifierButton.addActionListener(l);
		saveButton.addActionListener(l);
		loadButton.addActionListener(l);
		
		g.gridx = 1;
		g.gridy = 0;
		g.ipady = 40;
		g.fill = GridBagConstraints.BOTH;
		add(text, g);
		g.ipady = 0;
		g.gridx = 1;
		g.gridy = 1;
		g.fill = GridBagConstraints.HORIZONTAL;
		add(calculatorButton, g);
		g.ipady = 0;
		g.gridx = 0;
		g.gridy = 1;
		g.fill = GridBagConstraints.HORIZONTAL;
		add(buildButton, g);
		g.ipady = 0;
		g.gridx = 2;
		g.gridy = 1;
		g.fill = GridBagConstraints.HORIZONTAL;
		add(modifierButton, g);
		g.ipady = 0;
		g.gridx = 0;
		g.gridy = 2;
		g.fill = GridBagConstraints.HORIZONTAL;
		add(saveButton, g);
		g.ipady = 0;
		g.gridx = 2;
		g.gridy = 2;
		g.fill = GridBagConstraints.HORIZONTAL;
		add(loadButton, g);
		
		
	}
}
