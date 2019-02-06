package logic;

import java.util.ArrayList;
/**
 * Intermediary between GUI and internal logic.
 * @author Tim
 *
 */
public class Core {
	ArrayList<Component> loadedComponents;
	ArrayList<CharacterBuild> loadedCharacterBuilds;
	Ruleset activeRuleset = null;
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
			for(String s : c.tags)
			{
				if(tagsWith.contains(s))
				{
					sublist.add(c);
				}
			}
		}
		return sublist;
	}
	/**
	 * Sets the ruleset to the given option. Will not work if there is already a ruleset
	 * @param ruleset
	 */
	public void setRuleset(String ruleset)
	{
		if(activeRuleset == null)
		{
			//activeRuleset=
		}
	}
			
}
