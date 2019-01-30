package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import logic.Constants;



public class DisplayOpen extends JPanel {
	
	public DisplayOpen() {
	}

	public DisplayOpen(LayoutManager layout) {
		super(layout);

	}

	public DisplayOpen(boolean isDoubleBuffered) {
		super(isDoubleBuffered);

	}

	public DisplayOpen(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);

	}
	
	public void setup(int x, int y, ActionListener l)
	{
		setSize(y,x);
		//setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		setLayout(new GridBagLayout());
		GridBagConstraints g = new GridBagConstraints();
		JLabel text = new JLabel("Barbarian Math");
		
		
		JButton startButton = new JButton(Constants.START_BUTTON);
		
		startButton.setActionCommand(Constants.START_BUTTON);
		
		startButton.addActionListener(l);
		
		g.gridx = 1;
		g.gridy = 1;
		g.ipady = 40;
		g.fill = GridBagConstraints.BOTH;
		add(text, g);
		g.ipady = 0;
		g.gridx = 0;
		g.gridy = 2;
		g.fill = GridBagConstraints.HORIZONTAL;
		add(startButton, g);

	}

}
