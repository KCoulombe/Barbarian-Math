package logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/** 
 * A subclass of Component.  It represents a Build of a Character 
 * @author Kyle Coulombe
 * @see Component
 */
public class CharacterBuild extends Component
{
	public List<Modifier> modifiers;
	public List<Modifier> classModifiers;
	private List<String> modifierNames;
	private List<String> classModifierNames;
	
	public List<Scalar> scalars;
	private List<String> scalarNames;
	
	public List<Adventurer> classes;
	private List<String> classNames;
	private List<Integer> classLevels;
	private List<String> classSubclasses;
	
	
	public Map<String, Value> attributes;
	
	/** 
	 * Constructor for receiving data directly from whatever created this object
	 */
	public CharacterBuild(String name, List<Modifier> modifiers, List<Scalar> scalars, List<Adventurer> classes, Map<String, Value> attributes)
	{
		super.name = name;
		this.modifiers = modifiers;
		this.scalars = scalars;
		this.classes = classes;
		this.attributes = attributes;
	}

	public CharacterBuild(String name, List<String> modifierNames, List<String> scalarNames, 
			List<String> classNames, List<Integer> classLevels, List<String> classSubclasses, Map<String, Value> attributes)
	{
		super.name = name;
		this.modifierNames = modifierNames;
		this.scalarNames = scalarNames;
		this.classNames = classNames;
		this.classLevels = classLevels;
		this.classSubclasses = classSubclasses;
		this.attributes = attributes;
		
		modifiers = new ArrayList<Modifier>();
		scalars = new ArrayList<Scalar>();
		classes = new ArrayList<Adventurer>();
		
	}
	
	public void UpdateAll(Ruleset ruleset)
	{
		//Link all of the classes
		UpdateClasses(ruleset);
		//Get all of the modifiers from the classes based on the class level
		UpdateCassModifiers();
		//Link all of the modifiers
		UpdateModifiers(ruleset);
		//Link all of the sclalars
		UpdateScalars(ruleset);
		
	}
	
	public void UpdateCassModifiers()
	{
		for(Adventurer adv : classes)
		{	
			for(int i = adv.level; i > 0; i--)
			{	
				if(adv.main_class.values.get(i) != null)
				{
					classModifierNames.add(adv.main_class.values.get(i))
				}
				
				if(adv.sub_classes.get(adv.chosenSubclass))
				{
					classModifierNames.add(adv.main_class.values.get(i))
				}
			}
		}
	}
	
	public void UpdateModifiers(Ruleset ruleset)
	{
		//Empty all existing modifiers
		modifiers.clear();
		
		//Search the modifiers in the ruleset
		for(int i = 0; i < ruleset.modifiers.size(); i++)
		{	
			for(int n = 0; n < modifierNames.size(); n++)
			{		
				if(ruleset.modifiers.get(i).name.equals(modifierNames.get(n)))
				{
					modifiers.add(ruleset.modifiers.get(i));
				}
			}
		}
	}
	
	public void UpdateScalars(Ruleset ruleset)
	{
		//Empty all existing scalars
		scalars.clear();
		
		//Search the modifiers in the scalars
		for(int i = 0; i < ruleset.scalars.size(); i++)
		{	
			for(int n = 0; n < scalarNames.size(); n++)
			{
				//System.out.println("\n" + ruleset.scalars.get(i).name  + " =? " + scalarNames.get(n) + "\n");
				
				if(ruleset.scalars.get(i).name.equals(scalarNames.get(n)))
				{
					scalars.add(ruleset.scalars.get(i));
				}
			}
		}
	}
	
	public void UpdateClasses(Ruleset ruleset)
	{
		//Empty all existing classes
		classes.clear();
		
		System.out.println("\n" + classSubclasses + "\n" );
				
		//Search the modifiers in the classes
		for(int i = 0; i < ruleset.adventurers.size(); i++)
		{	
			Adventurer adv  = ruleset.adventurers.get(i);
			for(int n = 0; n < classNames.size(); n++)
			{
				if(adv.name.equals(classNames.get(n)))
				{
					//if there is no chosen subclass, the subclass value is -1
					int subclass = -1;
					
					//Find the chosen subclass by comparing the names
					for(int t = 0; t < adv.sub_classes.size(); t++)
					{
						if(adv.sub_classes.get(t).name.equals(classSubclasses.get(n)))
						{
							subclass = t;
						}
					}
					
					classes.add(new Adventurer(adv.name, adv.main_class, adv.sub_classes, 
							adv.modifiers, adv.scalars, classLevels.get(n), subclass));
				}
			}
		}
	}
	
	/** 
	 * Constructor for reading data from xml.
	 */
	public CharacterBuild(String name, String path)
	{
		
	}
}















