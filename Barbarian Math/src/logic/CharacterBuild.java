package logic;

import java.util.List;

public class CharacterBuild 
{
	public String name;
	public int level;
	public List<Modifier> modifiers;
	public List<Bundle> bundles;
	
	public CharacterBuild(String name, int level, List<Modifier> modifiers, List<Bundle> bundles)
	{
		this.name = name;
		this.level = level;
		this.modifiers = modifiers;
		this.bundles = bundles;
	}
	
	public CharacterBuild(String name, String path)
	{
		
	}
}
