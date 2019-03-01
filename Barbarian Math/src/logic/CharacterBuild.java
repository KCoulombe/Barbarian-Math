package logic;

import java.util.List;

/** 
 * A subclass of Component.  It represents a Build of a Character 
 * @author Kyle Coulombe
 * @see Component
 */
public class CharacterBuild extends Component
{
	public int level;
	public List<Modifier> modifiers;
	public List<Bundle> bundles;
	public List<Adventurer> classes;
	
	/** 
	 * Constructor for receiving data directly from whatever created this object
	 */
	public CharacterBuild(String name, int level, List<Modifier> modifiers, List<Bundle> bundles, List<Adventurer> classes)
	{
		super.name = name;
		this.level = level;
		this.modifiers = modifiers;
		this.bundles = bundles;
		this.classes = classes;
	}
	
	/** 
	 * Constructor for reading data from xml.
	 */
	public CharacterBuild(String name, String path)
	{
		
	}
}
