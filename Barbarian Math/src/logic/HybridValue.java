package logic;

public class HybridValue extends DiceValue
{
	public int staticNum;
	
	public HybridValue(String name, int numDice, int sides, int staticNum)
	{
		super(name, numDice, sides);
		this.staticNum = staticNum;
		
		//the DiceValue value is the average roll of the dice, HybridValue's is that plus the static number
		super.value += staticNum;
		
		super.max += staticNum;
		super.min += staticNum;
		
	}

	public int Roll()
    {
        return super.Roll() + staticNum;
    }
	
	public String toString()
	{
		return  name + ":" + die_name + "+" + staticNum;
	}
}
