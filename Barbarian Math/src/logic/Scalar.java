package logic;

import java.util.List;
import java.util.Map;

public class Scalar extends Bundle 
{
	public List<String> limits;
	
	public Scalar(String name, List<String> tags, Map<Integer, String> values, List<String> limits)
	{
		super(name, tags, values);
		this.limits = limits;
	}
	
	public String toString()
	{
		return "Name: " + name + "\nTags: " + tags + "\nValues: " + values + "\nLimits: " + limits ;
	}
}
