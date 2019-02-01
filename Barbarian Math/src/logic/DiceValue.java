package logic;
import java.util.Random;

/** 
 * A subclass of Value that holds a dice value instead of static value
 * @author Kyle Coulombe
 * @see Value
 */
public class DiceValue extends Value
{
    public int sides;

    public String die_name;

    public int max_roll;
    public int min_roll;
    public float avg_roll;
    
    /** 
	 * Constructor for DiceValue
	 * @param name the name of the value
	 * @param number the number of dice
	 * @param the number of sides each die has
	 */
    public DiceValue (String name, int number, int sides)
    {
    	super.name = name;
        super.number = number;
        this.sides = sides;

        die_name = Integer.toString(number) + "d" + Integer.toString(sides);

        this.max_roll = number * sides;
        this.min_roll = number;
        this.avg_roll = ((sides + 1) / 2) * number;
    }

    /**
     * Rolls the virtual dice
     * @return The result of the dice roll as an integer 
     */
    public int Roll()
    {
        int result = 0;

        Random rand = new Random();

        for(int i = 0; i < number; i++)
        {
            result += rand.nextInt(sides) + 1;
        }

        return result;
    }
}










