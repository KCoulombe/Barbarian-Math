package logic;

import java.util.List;

/** 
 * A subclass of Component.  It represents a character class 
 * @author Kyle Coulombe
 * @see Component
 */
public class Adventurer extends Component
{
	public Bundle main_class;
	public Bundle sub_class;
	public List<Modifier> modifiers;
	
	/** 
	 * Constructor for receiving data directly from whatever created this object
	 */
	public Adventurer(String name, Bundle main_class, Bundle sub_class, List<Modifier> modifiers)
	{
		super.name = name;
		this.main_class = main_class;
		this.sub_class = sub_class;
		this.modifiers = modifiers;
	}
	
	/** 
	 * Constructor for reading data from xml.
	 */
	public Adventurer(String name, String path)
	{
		
	}
}
