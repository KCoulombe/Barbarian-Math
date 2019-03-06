package logic;

import java.util.List;

/** 
 * A subclass of Component.  It represents anything that modifies the status of an adventurer
 * @author Kyle Coulombe
 * @see Component
 */
public class Modifier extends Component
{
	public List<Value> values;
	public List<String> limits;
	public List<String> costs;

	/** 
	 * Constructor for receiving data directly from whatever created this object
	 * @param name The name of the Modifier
	 * @param values All of the values the Modifier uses
	 */
    public Modifier(String name, List<Value> values, List<String> tags, List<String> limits, List<String> costs)
    {
        super.name = name;
        this.values = values;
        this.tags = tags;
        this.limits = limits;
        this.costs = costs;
    }

    /** 
	 * Constructor for reading data from xml.
	 */
    public Modifier(String name, String path)
    {

    }
    
    public String toString()
	{
		return "Name: " + name + "\nTags: " + tags + "\nValues: " + values + "\nLimits: " + limits + "\nCosts " + costs;
	}
}















