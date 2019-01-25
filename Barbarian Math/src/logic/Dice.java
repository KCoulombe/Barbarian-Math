package logic;
import java.util.Random;

public class Dice 
{
    public int number;
    public int sides;

    public String name;

    public int max_roll;
    public int min_roll;
    public float avg_roll;

    public Dice (int number, int sides)
    {
        this.number = number;
        this.sides = sides;

        name = Integer.toString(number) + "d" + Integer.toString(sides);

        this.max_roll = number * sides;
        this.min_roll = number;
        this.avg_roll = ((sides + 1) / 2) * number;
    }

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
