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
	public List<Modifier> modifiers;
	public List<Scalar> scalars;
	public List<Adventurer> adventurers;
	public List<String> dataFiles;
	
	/** 
	 * Constructor for receiving data directly from whatever created this object
	 */
	public Ruleset(String name, List<Modifier> modifiers, List<Scalar> scalars, List<Adventurer> adventurers)
	{
		this.name = name;
		this.modifiers = modifiers;
		this.scalars = scalars;
		this.adventurers = adventurers;
	}


























