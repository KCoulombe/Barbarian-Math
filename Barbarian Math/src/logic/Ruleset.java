package logic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The rules for a tabletop game.  Contains all the components needed to calculate a character's values
 * @author Kyle Coulombe
 * @see Component
 */
public class Ruleset 
{
	public String name;
	public List<Modifier> modifiers = new ArrayList<Modifier> ();
	public List<Adventurer> adventurers = new ArrayList<Adventurer> ();
	public List<String> dataFiles;
	
	/** 
	 * Constructor for receiving data directly from whatever created this object
	 */
	public Ruleset(String name, List<Modifier> modifiers, List<Adventurer> adventurers)
	{
		this.name = name;
		this.modifiers = modifiers;
		this.adventurers = adventurers;
	}
}



























