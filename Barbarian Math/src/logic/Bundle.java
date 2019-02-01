package logic;

import java.util.List;
import java.util.Map;

public class Bundle extends Component
{
	public List<String> tags;
	
	public Map<Integer, String> values;
	
	public Bundle(String name, List<String> tags, Map<Integer, String> values)
	{
		super.name = name;
		this.tags = tags;
		this.values = values;
	}
	
	public Bundle(String name, String path)
    {

    }
}
