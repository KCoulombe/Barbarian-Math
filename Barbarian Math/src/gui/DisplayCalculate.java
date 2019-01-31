package gui;

import java.awt.LayoutManager;
import java.awt.event.ActionListener;

import logic.Core;

public class DisplayCalculate extends DisplayPanel {

	public DisplayCalculate() {
	}

	public DisplayCalculate(LayoutManager layout) {
		super(layout);

	}

	public DisplayCalculate(boolean isDoubleBuffered) {
		super(isDoubleBuffered);

	}

	public DisplayCalculate(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);

	}

	@Override
	public void setup(int x, int y, ActionListener l, Core c) {
	}

}
