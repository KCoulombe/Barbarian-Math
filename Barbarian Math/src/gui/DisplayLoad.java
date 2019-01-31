package gui;

import java.awt.LayoutManager;
import java.awt.event.ActionListener;

import logic.Core;

public class DisplayLoad extends DisplayPanel {

	public DisplayLoad() {
	}

	public DisplayLoad(LayoutManager layout) {
		super(layout);

	}

	public DisplayLoad(boolean isDoubleBuffered) {
		super(isDoubleBuffered);

	}

	public DisplayLoad(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);

	}

	@Override
	public void setup(int x, int y, ActionListener l, Core c) {
	}

}
