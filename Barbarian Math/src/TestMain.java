import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.w3c.dom.DOMException;

import gui.DisplayMain;
import jdk.internal.org.xml.sax.SAXException;
import logic.*;

public class TestMain {

	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, DOMException, org.xml.sax.SAXException, TransformerException 
	{
		DisplayMain maths = new DisplayMain();
		maths.show();
		
		/*Scanner scanner = new Scanner();
		
		File workingDirectory = new File("BarbarianMathData");
		
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
		scanner.Write(testModifier, "BarbarianMathData/DnD_5e/Modifiers_Weapons.val");*/
	}

}
