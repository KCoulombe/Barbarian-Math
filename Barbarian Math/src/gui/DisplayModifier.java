package gui;

import java.awt.LayoutManager;
import java.awt.event.ActionListener;

import logic.Core;

public class DisplayModifier extends DisplayPanel {

	public DisplayModifier() {
	}

	public DisplayModifier(LayoutManager layout) {
		super(layout);

	}

	public DisplayModifier(boolean isDoubleBuffered) {
		super(isDoubleBuffered);

	}

	public DisplayModifier(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);

	}

	@Override
	public void setup(int x, int y, ActionListener l, Core c) {
	}

}
