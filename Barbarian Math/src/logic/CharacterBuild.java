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
	
	/** 
	 * Constructor for receiving data directly from whatever created this object
	 */
	public CharacterBuild(String name, int level, List<Modifier> modifiers, List<Bundle> bundles)
	{
		super.name = name;
		this.level = level;
		this.modifiers = modifiers;
		this.bundles = bundles;
	}
	
	/** 
	 * Constructor for reading data from xml.
	 */
	public CharacterBuild(String name, String path)
	{
		
	}
}
