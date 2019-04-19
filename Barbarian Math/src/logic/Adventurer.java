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
	public int level;
	public int chosenSubclass = -1; //a chosenSubclass of less than 0 means no subclass
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
	
	public Adventurer(String name, Bundle main_class, List<Bundle> sub_classes, List<Modifier> modifiers, List<Scalar> scalars,
			int level, int chosenSubclass)
	{
		super.name = name;
		this.main_class = main_class;
		this.sub_classes = sub_classes;
		this.modifiers = modifiers;
		this.scalars = scalars;
		this.level = level;
		this.chosenSubclass = chosenSubclass;
	}
	
	public Bundle GetSubclass()
	{
		if(chosenSubclass >= 0)
		{
			return sub_classes.get(chosenSubclass);
		}
		else
		{
			return null;
		}
	}
	
	public void SetSubclass(int subclassID)
	{
		if(subclassID < sub_classes.size())
		{
			chosenSubclass = subclassID;
		}
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
















