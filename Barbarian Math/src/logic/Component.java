package logic;

import java.util.List;

/**
 * The superclass for all of the various parts of a tabletop game.  Does nothing on it's own
 * @author Kyle Coulombe
 * @see Adventurer
 * @see CharacterBuild
 * @see Bundle
 * @see Modifier
 * @see Value
 */
public class Component 
{
	public String name;
	public List<String> tags;
	public boolean active = false;
	@Override
	public String toString() {
		return "Component [name=" + name + "]";
	}
}
