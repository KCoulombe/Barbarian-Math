package logic;

import java.util.List;
import java.util.Map;

/** 
 * A subclass of Component.  It represents a map of values
 * @author Kyle Coulombe
 * @see Component
 * @see Value
 */
public class Bundle extends Component
{
	public List<String> tags;
	
	public Map<Integer, String> values;
	
	/** 
	 * Constructor for receiving data directly from whatever created this object
	 */
	public Bundle(String name, List<String> tags, Map<Integer, String> values)
	{
		super.name = name;
		this.tags = tags;
		this.values = values;
	}
	
	/** 
	 * Constructor for reading data from xml.
	 */
	public Bundle(String name, String path)
    {

    }
}
