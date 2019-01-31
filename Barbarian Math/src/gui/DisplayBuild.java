package gui;

import java.awt.LayoutManager;
import java.awt.event.ActionListener;

import logic.Core;

public class DisplayBuild extends DisplayPanel {

	public DisplayBuild() {
	}

	public DisplayBuild(LayoutManager layout) {
		super(layout);

	}

	public DisplayBuild(boolean isDoubleBuffered) {
		super(isDoubleBuffered);

	}

	public DisplayBuild(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);

	}

	@Override
	public void setup(int x, int y, ActionListener l, Core c) {
	}

}
