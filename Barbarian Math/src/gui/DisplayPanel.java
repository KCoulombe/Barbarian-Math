package gui;

import java.awt.LayoutManager;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import logic.Core;

public abstract class DisplayPanel extends JPanel {

	public DisplayPanel() {
	}

	public DisplayPanel(LayoutManager layout) {
		super(layout);

	}

	public DisplayPanel(boolean isDoubleBuffered) {
		super(isDoubleBuffered);

	}

	public DisplayPanel(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);

	}
	public abstract void setup(int x, int y, ActionListener l, Core c);

}
