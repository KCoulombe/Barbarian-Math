package logic;

import java.util.List;

public class CharacterBuild extends Component
{
	public int level;
	public List<Modifier> modifiers;
	public List<Bundle> bundles;
	
	public CharacterBuild(String name, int level, List<Modifier> modifiers, List<Bundle> bundles)
	{
		super.name = name;
		this.level = level;
		this.modifiers = modifiers;
		this.bundles = bundles;
	}
	
	public CharacterBuild(String name, String path)
	{
		
	}
}
