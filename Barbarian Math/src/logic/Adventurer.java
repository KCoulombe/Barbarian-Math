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
	public List<Bundle> sub_classes;
	public List<Modifier> modifiers;
	public List<Scalar> scalars;
	
	/** 
	 * Constructor for receiving data directly from whatever created this object
	 */
	public Adventurer(String name, Bundle main_class, List<Bundle> sub_classes, List<Modifier> modifiers, List<Scalar> scalars)
	{
		super.name = name;
		this.main_class = main_class;
		this.sub_classes = sub_classes;
		this.modifiers = modifiers;
		this.scalars = scalars;
	}
	
	/** 
	 * Constructor for reading data from xml.
	 */
	public Adventurer(String name, String path)
	{
		
	}
	
	public String toString()
	{	
		return "Name: " + name +
				"\nMainclass\n------------------------------\n" + main_class + 
				"\n\nSubclasses\n------------------------------\n" + sub_classes +
				"\n\nModifiers\n------------------------------\n" + modifiers +
				"\n\nScalars\n------------------------------\n" + scalars + "\n";
	}
}
















