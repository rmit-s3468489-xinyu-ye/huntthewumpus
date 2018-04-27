package huntwumpus;

/**
 * 
 * @author Xinyu YE s3468489, Yifan ZHANG s3615625
 *
 */

public class Wumpus extends GameItem

{

	public Wumpus(char c) 
	{
		super(c);
		
	}

	public Wumpus() 
	{
		super('W');
	}
	
	public String toString() 
	{
		return "Vile Smell";
	}
	
}
