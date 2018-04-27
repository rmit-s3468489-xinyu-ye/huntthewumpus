package huntwumpus;

/**
 * 
 * @author Xinyu YE s3468489, Yifan ZHANG s3615625
 *
 */

public abstract class GameItem 

{

	char c;
	
	private int [] position;
	
	public int[] getPosition() 
	{
		return position;
	}
	

	public void setPosition(int[] position) 
	{
		this.position = position;
	}

	public GameItem(char c) 
	{

		this.c = c;
	}



	public void display() {
		System.out.print(c);
	} 
	
}
