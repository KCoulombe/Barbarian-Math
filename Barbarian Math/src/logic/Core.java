package logic;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
/**
 * Intermediary between GUI and internal logic.
 * @author Tim
 *
 */
public class Core {
	public ArrayList<Component> loadedComponents;
	public ArrayList<CharacterBuild> loadedCharacterBuilds;
	public Ruleset activeRuleset = null;
	public Core(){
		loadedComponents = new ArrayList<>();
		loadedCharacterBuilds = new ArrayList<>();
		
	}
	/**
	 * Retrieves a partial list containing the given constraints.
	 * @param tagsWith Given tags will be used to keep Components in new list
	 * @param owner The active CharacterBuild
	 * @return The returned list of Components in Owner, that possess tags in tagsWith 
	 */
	public ArrayList<Component> getSublist(ArrayList<String> tagsWith, CharacterBuild owner)
	{
		ArrayList<Component> sublist = new ArrayList<>();
		for(Component c : loadedComponents)
		{
			boolean keep = true;
			for(String s : tagsWith)
			{
				if(!c.tags.contains(s))
				{
					//sublist.add(c);
					keep = false;
				}
			}
			if(owner != null)
			{
				if(!owner.modifiers.contains(c))
				{
					keep = false;
				}
			}
			if(keep)
			{
				sublist.add(c);
			}
		}
		
		return sublist;
	}
	/**
	 * Sets the ruleset to the given option. Will not work if there is already a ruleset
	 * @param ruleset
	 * @throws ParserConfigurationException 
	 */
	public void setRuleset(String ruleset) throws ParserConfigurationException
	{
		Scanner scanner = new Scanner();
		
		if(activeRuleset == null)
		{
			activeRuleset = scanner.LoadRuleset(ruleset);
		}
	}
}











