package huntwumpus;

import java.lang.reflect.Array;
import java.util.*;
import java.io.*;

/**
 * 
 * @author Xinyu YE s3468489, Yifan ZHANG s3615625
 *
 */

public class Game 
{

	private Scanner sc;
	
	private ArrayList boardItemList = new ArrayList();
	
	private ArrayList<Gold> goldList = new ArrayList<Gold>();	
	
	private ArrayList<Integer> indexList = new ArrayList<Integer>();
	
	private boolean askToContinue = false;
	
	final int NUM_COL = 4;
	
	final int NUM_ROW = 4;
	
	private int[][] board = {{0,0},{0,1},{0,2},{0,3},
							{1,0},{1,1},	{1,2},{1,3},
							{2,0},{2,1},{2,2},{2,3},
							{3,0},{3,1},{3,2},{3,3}};
			
	private int col, row;
	
	private int [] player;
	
	private GameItem up, down, left, right;
	
	private Pit pit1, pit2, pit3;
	
	private Wumpus wum;

	private int score = 0;
	
	
	
	
	
	
	
	public Game( ) 
	{
		
		this.sc = new Scanner(System.in);
		
		col = 0;
		
		row = 0;
		
	}

	
	
	private void setBoard() 
	{
		Wumpus w = new Wumpus();
		Pit p = new Pit();
		Gold g = new Gold();
		ClearGround c = new ClearGround();
	}
	
	
	private int randomNum(int a) 
	{
		Random randomGenerator = new Random();
		
		return randomGenerator.nextInt(a);
		
	}
	
	  
	
	private void positionGenarator()
	{
		for(int i = 0; i< 16; i++ )
		{
			indexList.add(i);
			ClearGround c = new ClearGround();
			c.setPosition(board[i]);
			boardItemList.add(c);
		}
		
		
		int r = randomNum(16);
		int w = indexList.get(r);
		indexList.remove(r);
		wum = new Wumpus();
		wum.setPosition(board[w]);
		boardItemList.set(w, wum);
		
		r = randomNum(15);
		int p1 = indexList.get(r);
		indexList.remove(r);
		pit1 = new Pit();
		pit1.setPosition(board[p1]);
		boardItemList.set(p1, pit1);
		
		r = randomNum(14);
		int p2 = indexList.get(r);
		indexList.remove(r);
		pit2 = new Pit();
		pit2.setPosition(board[p2]);
		boardItemList.set(p2, pit2);
		
		r = randomNum(13);
		int p3 = indexList.get(r);
		indexList.remove(r);
		pit3 = new Pit();
		pit3.setPosition(board[p3]);
		boardItemList.set(p3, pit3);
		
		int numOfGold = randomNum(3) + 1;
		for (int i = 1; i <= numOfGold; i++) 
		{//Generating random numbers of gold
			r = randomNum(13 - i);
			int g = indexList.get(r);
			indexList.remove(r);
			Gold gold = new Gold();
			gold.setPosition(board[g]);
			goldList.add(gold);
			boardItemList.set(g, gold);			
		}
		
		
		//the Player is implemented through an array of int type;
		r = randomNum(12 - numOfGold);
		int pl = indexList.get(r);
		indexList.remove(r);
		player = board[pl];
		boardItemList.set(pl, player);
		
		
	}
	
	
	
	
	private void display() 
	{
		System.out.println(" _______________________ ");
		System.out.println("|     |     |     |     |");
		
		for(int i=1; i<= 16; i++) 
		{
			if(i%4 == 1) 
			{
				System.out.print("|  ");
			}
			else 
			{
				System.out.print("  ");
			}
			
			
			int index = i-1;
			int[] position = board[index];
			
			if(position[0] == player[0] && position[1] == player[1]) 
			{
				System.out.print("*");
			}
			
			else 
			{
				displayCorrectSymbol((GameItem)boardItemList.get(index));
			}
			
			System.out.print("  |");
			if(i%4 == 0) 
			{
				System.out.println();
				System.out.println("|_____|_____|_____|_____|");
				if(i != 16) 
				{
					System.out.println("|     |     |     |     |");
				}
			}
		}
	}
	
	public void displayCorrectSymbol(GameItem item) 
	{
		item.display();

	}
	
	private void senseNearby() 
	{
		int [] upIndex = {player[0] - 1, player[1]};
		if(upIndex[0] < 0) 
		{
			upIndex[0] = 3;
		}
		
		up = (GameItem) boardItemList.get(getIndexOfBoard(upIndex));
		
		int [] downIndex = {player[0] + 1, player[1]};
		if(downIndex[0] > 3) 
		{
			downIndex[0] = 0;
		}
		
		down = (GameItem) boardItemList.get(getIndexOfBoard(downIndex));
		
		int [] leftIndex = {player[0], player[1] - 1};
		if(leftIndex[1] < 0) 
		{
			leftIndex[1] = 3;
		}
		left = (GameItem) boardItemList.get(getIndexOfBoard(leftIndex));
		
		int [] rightIndex = {player[0], player[1] + 1};
		if(rightIndex[1] > 3) 
		{
			rightIndex[1] = 0;
		}
		right = (GameItem) boardItemList.get(getIndexOfBoard(rightIndex));
		
		
		
			System.out.println("The player is surrounding by a " + up.toString() + " above it, " + "a " + down.toString()+ " beneath it, "
					+ "a " +left.toString() + " to its left, " + "and a " + right.toString() + " to its right.");
		
		
		
	}
	
	private int getIndexOfBoard(int [] a) 
	{
		for(int i = 0; i< board.length; i++) 
		{
			int b[] = board[i];
			if(b[0] == a[0] && b[1] == a[1]) 
			{
				return i;
			}
		}
		return -1;
	}
	
	private void menu() 
	{
		while(true) 
		{
			System.out.println("=====Wumpus====");
			System.out.println("1. Move player left");
			System.out.println("2. Move player right");
			System.out.println("3. Move player up");
			System.out.println("4. Move player down");
			System.out.println("5.Quit");
		
				
			processInput();
			
			senseNearby();
			display();
			
			if(checkGold())
			{
			
				System.out.println("You've collected all the golds! You win!");
				
				this.askToContinue = true;
			}
			
			if(this.askToContinue) 
			{
				break;
			}
			
			continue;			
		}
		
		
		if(this.askToContinue) 
		{
			System.out.print( "Do you want to play again (Y/N):" );
			
			String again = sc.next().toUpperCase();
			if( "N".equals( again ) )
			{
				System.exit(0);
			}
			else if( "Y".equals( again ) )
			{
				
				this.askToContinue = false;
			}
		}
	}
	
	private void processInput() 
	{
		boolean continueLoop = false;
		do {
			continueLoop = false;
			try
			{	Scanner keyboard = new Scanner(System.in);
				int input = keyboard.nextInt();
			
				switch (input)
				{
					case 1: moveLeft();
						break;
						
					case 2: moveRight();
						break;
						
					case 3: moveUp();
						break;
					
					case 4: moveDown();
						break;
						
					case 5: System.exit(0);
						break;
						
					default: 					
						System.out.println("Incorrect number of Choice supplied to command ！Enter Again !");
						if (input != 1 || input != 2 || input !=3 ||input !=4 ||input !=5 ) 
						{
							continueLoop = true;
						}									
				}
																
			}
			catch(InputMismatchException ex) 
			{
				System.out.println("Invalid input ! Enter again : ");
				continueLoop = true;
			}
		} while(continueLoop);
		
			
	}



	private boolean checkGold()
	{
		return goldList.size() == 0;
	}
	
	
	private void moveLeft() 
	{  	
		if (left instanceof Wumpus) 
		{			
			System.out.println("Unfortunately, you have been killed by the Wumpus ! Game Over!");
			
			
			
			this.askToContinue = true;
		}

		if (left instanceof Pit) 
		{
			System.out.println("Unfortunately, you got caught by the trap ! Game Over!");
			
			
			
			this.askToContinue = true;
		}
	
		if (left instanceof Gold) 
		{
			System.out.println("Congrats! You collected a gold!");
			score += 1;
			if (score == 1) 
			{
				System.out.println("You have earned " + score + " score.");
			}
			else 
			{
				System.out.println("You have earned " + score + " scores.");
			}
			
			
			int i = getIndexOfBoard(left.getPosition());
			ClearGround c = new ClearGround();
			c.setPosition(left.getPosition());
			boardItemList.set(i, c);
			goldList.remove(0);
			
		}
		
		
		int [] leftIndex = {player[0], player[1] - 1};
		if(leftIndex[1] < 0) 
		{
			leftIndex[1] = 3;
		}
		
		ClearGround c = new ClearGround();
		c.setPosition(player);
		
		int i = getIndexOfBoard(left.getPosition());
				
		if (i == 3 || i == 7 || i == 11 || i == 15) 
		{
			boardItemList.set(i - 3, c);
		}
		else
		{
			boardItemList.set(i + 1, c);
		}
		player = leftIndex;
		
	}
	
	private void moveRight() 
	{  	
		if (right instanceof Wumpus) 
		{
			
			System.out.println("Unfortunately, you have been killed by the Wumpus ! Game Over!");
			
			
			
			this.askToContinue = true;
		}

		if (right instanceof Pit) 
		{			
			
			System.out.println("Unfortunately, you got caught by the trap ! Game Over!");
			
			
			
			this.askToContinue = true;
		}
	
		if (right instanceof Gold) 
		{
			System.out.println("Congrats! You collected a gold!");
			score += 1;
			if (score == 1) 
			{
				System.out.println("You have earned " + score + " score.");
			}
			else 
			{
				System.out.println("You have earned " + score + " scores.");
			}
			
			int i = getIndexOfBoard(right.getPosition());
			ClearGround c = new ClearGround();
			c.setPosition(right.getPosition());
			boardItemList.set(i, c);
			goldList.remove(0);
			
		}
		
		int [] rightIndex = {player[0], player[1] + 1};
		if(rightIndex[1] > 3) 
		{
			rightIndex[1] = 0;
		}
		
		ClearGround c = new ClearGround();
		c.setPosition(player);
		
		int i = getIndexOfBoard(right.getPosition());
		
		if (i == 0 || i == 4 || i == 8 || i ==12) 
		{
			boardItemList.set(i + 3, c);
		}
		else 
		{
			boardItemList.set(i - 1, c);
		}
		
		player = rightIndex;
	}
	
	
	private void moveUp() 
	{  	
		if (up instanceof Wumpus) 
		{
			System.out.println("Unfortunately, you have been killed by the Wumpus ! Game Over!");
			
			
			this.askToContinue = true;
		}

		if (up instanceof Pit) 
		{
			System.out.println("Unfortunately, you got caught by the trap ! Game Over!");
			
			
			this.askToContinue = true;
		}
	
		if (up instanceof Gold) 
		{
			System.out.println("Congrats! You collected a gold!");
			
			score += 1;
			
			if (score == 1) 
			{
				System.out.println("You have earned " + score + " score.");
			}
			else 
			{
				System.out.println("You have earned " + score + " scores.");
			}
			
			int i = getIndexOfBoard(up.getPosition());
			ClearGround c = new ClearGround();
			c.setPosition(up.getPosition());
			boardItemList.set(i, c);
			goldList.remove(0);
			
		}
		
		int [] upIndex = {player[0] - 1, player[1]};
		if(upIndex[0] < 0) 
		{
			upIndex[0] = 3;
		}
		
		ClearGround c = new ClearGround();
		c.setPosition(player);
		int i = getIndexOfBoard(up.getPosition());
		
		if (i == 12 || i == 13 || i == 14 || i == 15) 
		{
			boardItemList.set(i - 12, c);
		}
		else 
		{
			boardItemList.set(i + 4, c);
		}
		
		player = upIndex;
	}
	
	private void moveDown() 
	{  	
		if (down instanceof Wumpus) 
		{
			System.out.println("Unfortunately, you have been killed by the Wumpus ! Game Over!");
				
			
			this.askToContinue = true;
		}
	
		if (down instanceof Pit) 
		{
			System.out.println("Unfortunately, you got caught by the trap ! Game Over!");
			
			
			this.askToContinue = true;
		}
	
		if (down instanceof Gold) 
		{
			System.out.println("Congrats! You collected a gold!");
			
			score += 1;
			
			if (score == 1) 
			{
				System.out.println("You have earned " + score + " score.");
			}
			else 
			{
				System.out.println("You have earned " + score + " scores.");
			}
			
			int i = getIndexOfBoard(down.getPosition());
			ClearGround c = new ClearGround();
			c.setPosition(down.getPosition());
			boardItemList.set(i, c);
			goldList.remove(0);
			
		}
		
		int [] downIndex = {player[0] + 1, player[1]};
		if(downIndex[0] > 3) 
		{
			downIndex[0] = 0;
		}
		
		ClearGround c = new ClearGround();
		c.setPosition(player);
		
		int i = getIndexOfBoard(down.getPosition());
		
		if (i == 0 || i == 1 || i == 2 || i == 3) 
		{
			boardItemList.set(i + 12, c);
		}
		else 
		{
			boardItemList.set(i - 4, c);
		}
		
		player = downIndex;
	}
	



	
	
	public void runGame() 
	{
		while (askToContinue == false)
		{
			score = 0;
			goldList.clear();
			boardItemList.clear();
			positionGenarator();
			setBoard();
			senseNearby();
			display();  
			menu();
		}

	}
	
	
}
