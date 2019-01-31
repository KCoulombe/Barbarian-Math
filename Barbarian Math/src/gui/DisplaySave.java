package gui;

import java.awt.LayoutManager;
import java.awt.event.ActionListener;

import logic.Core;

public class DisplaySave extends DisplayPanel {

	public DisplaySave() {
	}

	public DisplaySave(LayoutManager layout) {
		super(layout);

	}

	public DisplaySave(boolean isDoubleBuffered) {
		super(isDoubleBuffered);

	}

	public DisplaySave(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);

	}

	@Override
	public void setup(int x, int y, ActionListener l, Core c) {
	}

}
