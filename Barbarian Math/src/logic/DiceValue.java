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
    public int numDice;

    public String die_name;

    public int max_roll;
    public int min_roll;
    
    /** 
	 * Constructor for DiceValue
	 * @param name the name of the value
	 * @param number the number of dice
	 * @param the number of sides each die has
	 */
    public DiceValue (String name, int numDice, int sides)
    {
    	//give the superclass the name and the value
        super(name, ((sides + 1) / 2) * numDice);

        this.numDice = numDice;
        this.sides = sides;
        

        die_name = Integer.toString(numDice) + "d" + Integer.toString(sides);

        this.max_roll = numDice * sides;
        this.min_roll = numDice;
    }

    /**
     * Rolls the virtual dice
     * @return The result of the dice roll as an integer 
     */
    public int Roll()
    {
        int result = 0;

        Random rand = new Random();

        for(int i = 0; i < numDice; i++)
        {
            result += rand.nextInt(sides) + 1;
        }

        return result;
    }
}










