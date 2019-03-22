package logic;

import java.util.List;
import java.util.Map;

public class Scalar extends Bundle 
{
	public List<String> limits;
	public Map<Integer, List<Value>> values;
	
	public Scalar(String name, List<String> tags, Map<Integer, List<Value>> values, List<String> limits)
	{
		super(name, tags, null);
		this.limits = limits;
		this.values = values;
	}
	
	public String toString()
	{
		return "Name: " + name + "\nTags: " + tags + "\nValues: " + values + "\nLimits: " + limits ;
	}
}
