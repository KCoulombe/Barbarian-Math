import javax.xml.parsers.ParserConfigurationException;

import gui.DisplayMain;
import logic.Ruleset;

public class TestMain {

	public static void main(String[] args) throws ParserConfigurationException {
		DisplayMain maths = new DisplayMain();
		maths.show();
		Ruleset DnD_5e = new Ruleset("BarbarianMathData/DnD_5e");
	}

}
