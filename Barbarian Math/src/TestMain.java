import java.io.File;

import javax.xml.parsers.ParserConfigurationException;

import gui.DisplayMain;
import logic.*;

public class TestMain {

	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException 
	{
		DisplayMain maths = new DisplayMain();
		maths.show();
		
		File workingDirectory = new File("BarbarianMathData");
		Scanner scanner = new Scanner();
		
		File[] rulesets = scanner.ListRulesets(workingDirectory);
		
		Ruleset DnD_5e = null;
		for(int i = 0; i < rulesets.length; i++)
		{
			if(rulesets[i].getName().equals("DnD_5e"))
			{
				DnD_5e = scanner.LoadRuleset(rulesets[i]);
				break;
			}
		}
		
		System.out.println(DnD_5e.name);
		
		System.out.println(scanner.FindComponentInXML("Dagger", "BarbarianMathData/DnD_5e/Modifiers_Weapons.val", "modifier").getElementsByTagName("tags").item(0).getTextContent());
		
		Modifier testModifier = new Modifier("Dagger", null, null, null, null);
		scanner.Write(testModifier, "BarbarianMathData/DnD_5e/Modifiers_Weapons.val");
	}

}
