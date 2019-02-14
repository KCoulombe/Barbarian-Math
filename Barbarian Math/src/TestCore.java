import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.jupiter.api.Test;

import logic.CharacterBuild;
import logic.Component;
import logic.Core;
import logic.Modifier;
import logic.Ruleset;

class TestCore {

	@Test
	void testGetSublist() {
		ArrayList<Component> testList = new ArrayList<>();
		Core testCore = new Core();
		ArrayList<String> prefab = new ArrayList<>();
		for(int i = 0; i < 16; i++){
			prefab.add("T"+i);
		}
		ArrayList<String> dummyValue = new ArrayList<>();
		dummyValue.add("1");
		ArrayList<String> tagSet1 = new ArrayList<>();
		tagSet1.add(prefab.get(1));
		tagSet1.add(prefab.get(2));
		tagSet1.add(prefab.get(3));
		ArrayList<String> tagSet2 = new ArrayList<>();
		tagSet2.add(prefab.get(1));
		tagSet2.add(prefab.get(2));
		//tagSet2.add(prefab.get(3));
		ArrayList<String> tagSet3 = new ArrayList<>();
		tagSet3.add(prefab.get(1));
		//tagSet3.add(prefab.get(2));
		//tagSet3.add(prefab.get(3));
		
		Modifier m = new Modifier("m1",dummyValue, tagSet1,dummyValue,dummyValue);
		Modifier m2 = new Modifier("m2",dummyValue, tagSet2,dummyValue,dummyValue);
		Modifier m3 = new Modifier("m3",dummyValue, tagSet3,dummyValue,dummyValue);
		testList.add(m);
		testList.add(m2);
		testList.add(m3);
		testCore.loadedComponents = testList;
		ArrayList<String> reqList = new ArrayList<>();
		reqList.add(prefab.get(1));
		reqList.add(prefab.get(2));
		//reqList.add("T5");
		ArrayList<Component> newList = testCore.getSublist(reqList, null);
		
		assertTrue("Success", newList.size() > 1);
		ArrayList<Modifier> charList = new ArrayList<>();
		charList.add(m2);
		CharacterBuild c = new CharacterBuild("C", 1, charList, null);
		assertEquals("charbuild add", 1, testCore.getSublist(reqList, c).size());
	}

	@Test
	void testSetRuleset() throws ParserConfigurationException {
		Core c = new Core();
		//Ruleset r = new Ruleset("A");
		c.setRuleset("A");
		assertNotNull("Ruleset", c.activeRuleset);
	}

	@Test
	void testLocalRulesets() {
		Core c = new Core();
		assertTrue("Local Ruleset", c.localRulesets(new File("./Barbarian Math Data")).size() > 0);
	}

}
