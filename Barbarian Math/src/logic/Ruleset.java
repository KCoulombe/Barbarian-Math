package logic;

import java.util.List;

public class Ruleset 
{
	public String name;
	public List<String> components;
	
	public Ruleset(String name, List<String> components)
	{
		this.name = name;
		this.components = components;
	}
	
	public Ruleset(String name, String path)
	{
		
	}
}
