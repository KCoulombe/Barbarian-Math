package logic;

import java.util.List;
import java.util.Map;

public class Bundle 
{
	public String name;
	public List<String> tags;
	
	public Map<Integer, String> values;
	
	public Bundle(String name, List<String> tags, Map<Integer, String> values)
	{
		this.name = name;
		this.tags = tags;
		this.values = values;
	}
	
	public Bundle(String name, String path)
    {

    }
}
