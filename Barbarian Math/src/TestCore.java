import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.jupiter.api.Test;

import java.util.List;

import logic.CharacterBuild;
import logic.Component;
import logic.Core;
import logic.Modifier;
import logic.Ruleset;
import logic.Scanner;
import logic.Value;

class TestCore {

	@Test
	void testGetSublist() {
		ArrayList<Component> testList = new ArrayList<>();
		Core testCore = new Core();
		ArrayList<String> prefab = new ArrayList<>();
		for(int i = 0; i < 16; i++){
			prefab.add("T"+i);
		}
		List<Value> dummyValues = new ArrayList<Value>();
		dummyValues.add(new Value("dummy", 1));
		List<String> dummyStrings = new ArrayList<String>();
		dummyStrings.add("1");
		
		List<String> tagSet1 = new ArrayList<String>();
		tagSet1.add(prefab.get(1));
		tagSet1.add(prefab.get(2));
		tagSet1.add(prefab.get(3));
		List<String> tagSet2 = new ArrayList<String>();
		tagSet2.add(prefab.get(1));
		tagSet2.add(prefab.get(2));
		//tagSet2.add(prefab.get(3));
		List<String> tagSet3 = new ArrayList<String>();
		tagSet3.add(prefab.get(1));
		//tagSet3.add(prefab.get(2));
		//tagSet3.add(prefab.get(3));
		
		Modifier m = new Modifier("m1",dummyValues, tagSet1,dummyStrings,dummyStrings);
		Modifier m2 = new Modifier("m2",dummyValues, tagSet2,dummyStrings,dummyStrings);
		Modifier m3 = new Modifier("m3",dummyValues, tagSet3,dummyStrings,dummyStrings);
		testList.add(m);
		testList.add(m2);
		testList.add(m3);
		testCore.loadedComponents = testList;
		ArrayList<String> reqList = new ArrayList<>();
		reqList.add(prefab.get(1));
		reqList.add(prefab.get(2));
		//reqList.add("T5");
		List<Component> newList = testCore.getSublist(reqList, null);
		
		assertTrue("Success", newList.size() > 1);
		
		List<Modifier> charList = new ArrayList<>();
		charList.add(m2);
		CharacterBuild c = new CharacterBuild("C",  charList, null, null, null, null);
		assertEquals("charbuild add", 1, testCore.getSublist(reqList, c).size());
	}

	@Test
	void testSetRuleset() throws ParserConfigurationException 
	{
		File workingDirectory = new File("BarbarianMathData");
		
		Core c = new Core();
		//Ruleset r = new Ruleset("A");
		c.setRuleset(Scanner.LoadRuleset("BarbarianMathData/DnD_5e"));
		assertNotNull("Ruleset", c.activeRuleset);
	}

	@Test
	void testLocalRulesets() 
	{
		File workingDirectory = new File("BarbarianMathData");
		
		System.out.println(Scanner.ListRulesets(workingDirectory));
		
		assertTrue("Local Ruleset", Scanner.ListRulesets(workingDirectory).length > 0);
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
