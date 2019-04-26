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
	public List<Modifier> classModifiers = new ArrayList<Modifier>();
	private List<String> modifierNames;
	private List<String> classModifierNames = new ArrayList<String>();
	
	public List<Scalar> scalars;
	private List<String> scalarNames;
	
	public List<Adventurer> classes;
	private List<String> classNames;
	private List<Integer> classLevels = new ArrayList<Integer>();
	private List<String> classSubclasses;
	
	
	public Map<String, Value> attributes;
	
	/** 
	 * Constructor for receiving data directly from whatever created this object
	 */
	public CharacterBuild(String name, List<Modifier> modifiers, List<Modifier> classModifiers, List<Scalar> scalars, List<Adventurer> classes,  Map<String, Value> attributes)
	{
		super.name = name;
		this.modifiers = modifiers;
		this.classModifiers = classModifiers;
		this.scalars = scalars;
		this.classes = classes;
		this.attributes = attributes;
		
		for(Adventurer adv : classes)
		{
			classLevels.add(adv.level);
		}
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
	
	/** 
	 * Sets the level of the chosen class, then updates the modifiers the charicter should have
	 * @param className the name of the class you want to update
	 * @param level the number you want to update the classes level to
	 * @param ruleset the ruleset that contains the modifiers
	 */
	public void SetClassLevel(String className, int level, Ruleset ruleset)
	{
		for(Adventurer adv : classes)
		{
			if(adv.name.equalsIgnoreCase(className))
			{
				adv.level = level;
				UpdateCassModifiers(ruleset);
				
				break;
			}
		}
	}
	
	/** 
	 * Sets the level of the chosen class, then updates the modifiers the charicter should have
	 * @param classIndex the index of the class you want to update
	 * @param level the number you want to update the classes level to
	 * @param ruleset the ruleset that contains the modifiers
	 */
	public void SetClassLevel(int classIndex, int level, Ruleset ruleset)
	{
		classes.get(classIndex).level = level;
		UpdateCassModifiers(ruleset);
	}
	
	public void UpdateAll(Ruleset ruleset)
	{
		//Link all of the classes
		UpdateClasses(ruleset);
		//Get all of the modifiers from the classes based on the class level
		UpdateCassModifiers(ruleset);
		//Link all of the modifiers
		UpdateModifiers(ruleset);
		//Link all of the sclalars
		UpdateScalars(ruleset);
		
	}
	
	public void UpdateCassModifiers(Ruleset ruleset)
	{
		//find the names of the modifiers from the classes
		classModifierNames.clear();
		for(Adventurer adv : classes)
		{	
			for(int i = adv.level; i > 0; i--)
			{					
				if(adv.main_class.values.get(i) != null)
				{
					classModifierNames.add(adv.main_class.values.get(i));
				}
				
				if(adv.sub_classes.get(adv.chosenSubclass).values.get(i) != null && adv.chosenSubclass > 0)
				{
					classModifierNames.add(adv.sub_classes.get(adv.chosenSubclass).values.get(i));
				}
			}
			
			//System.out.println(adv.level);
			//System.out.println(classModifierNames);
		}
		
		//find the modifiers in the ruleset
		classModifiers.clear();
		
		List<String> remainingModifiers = new ArrayList<String>();
		remainingModifiers.addAll(classModifierNames);
		
		//Search the modifiers in the ruleset
		for(int i = 0; i < ruleset.modifiers.size(); i++)
		{
			for(int n = 0; n < classModifierNames.size(); n++)
			{		
				if(ruleset.modifiers.get(i).name.equals(classModifierNames.get(n)))
				{
					//System.out.println(ruleset.modifiers.get(i).name + "=" + classModifierNames.get(n));
					
					classModifiers.add(ruleset.modifiers.get(i));
					remainingModifiers.remove(classModifierNames.get(n));
				}
			}
		}
		
		//System.out.println("The following class modifiers for character '" + name +  "'  were not found in ruleset '" + ruleset.name + "':" + remainingModifiers + "\n");
	}
	
	public void UpdateModifiers(Ruleset ruleset)
	{
		//Empty all existing modifiers
		modifiers.clear();
		
		List<String> remainingModifiers = new ArrayList<String>();
		remainingModifiers.addAll(modifierNames);
		
		//Search the modifiers in the ruleset
		for(int i = 0; i < ruleset.modifiers.size(); i++)
		{	
			for(int n = 0; n < modifierNames.size(); n++)
			{		
				if(ruleset.modifiers.get(i).name.equals(modifierNames.get(n)))
				{
					modifiers.add(ruleset.modifiers.get(i));
					remainingModifiers.remove(modifierNames.get(n));
				}
			}
			
		}
		
		//System.out.println("The following modifiers for character '" + name +  "'  were not found in ruleset '" + ruleset.name + "':" + remainingModifiers + "\n");
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
		
		//System.out.println("\n" + classSubclasses + "\n" );
				
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















