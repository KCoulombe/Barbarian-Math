
package logic;

/** 
 * A subclass of Component that is mostly a wrapper for a number
 * @author Kyle Coulombe
 * @see Component
 * @see DiceValue
 */
public class Value extends Component
{
	public float value;
	public int max;
    public int min;
	
	/** 
	 * Constructor for receiving data directly from whatever created this object
	 * @param name The name of the Value
	 * @param number The value of the Value
	 */
	public Value(String name, int number)
	{
		super.name = name;
		this.value = number;
		this.max = number;
		this.min = number;
	}
	
	public String toString()
	{
		return name + ":" + value;
	}
}
