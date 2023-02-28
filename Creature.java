/* ------------------------------------------------
* Author: Robert L Barrera
* Class: CS 211, Spring 2017
* Program: #7
* System: Windows 7, Eclipse
* April 24, 2017
* -------------------------------------------------
*/

public class Creature {
	private Island isl;
	private int x;
	private int[] xVal;
	private int y;
	private int[] yVal;
	private int dayLastMoved;
	private int age;	// Age of creature
	private int spawnableAge;	// Spawnable age 
	private int starveCount;	// Counting starving days
	private boolean spawnable;	// True of false for spawning
	private boolean isAnt;	// Is Ant or Doodlebug
	

	public Creature (Island island, boolean con)
	{
		isl = island;
		x = (int)(Math.random() * island.getRows() );
		xVal = new int[]{0, 0, -1, 1, -1, 1, -1, 1};
		y = (int)(Math.random() * island.getCols() );
		yVal = new int[]{-1, 1, 0, 0, -1, -1, 1, 1};
		dayLastMoved = 0;
		age = 0;
		spawnableAge = 5;
		spawnable = false;
		isAnt = con;
		starveCount = 0;
  
		while(!island.addCreature (this, x, y)) {
			x = (int)(Math.random() * island.getRows() );
			y = (int)(Math.random() * island.getCols() );
		}
	}
	
	public Creature (Island island, int newX, int newY, boolean con) {	// Constructor for spawn
		isl = island;
		x = newX;
		xVal = new int[]{0, 0, -1, 1, -1, 1, -1, 1};
		y = newY;
		yVal = new int[]{-1, 1, 0, 0, -1, -1, 1, 1};
		dayLastMoved = 0;
		age = 0;
		spawnableAge = 5;
		spawnable = false;
		isAnt = con;
		starveCount = 0;
  
		island.addCreature (this, x, y);	
	}
  
	public void move()
	{	
		if (hunt() == false) {	// If hunt failed, wander
			wander();	
		}
		spawn();
		if (isAnt == false) {	// If Doodlebug, starve
			++starveCount;
			starve();
		}
	}
	
	public boolean hunt () {
		if (isAnt == true) {
			return false;
		}
		int nextX = -999;
		int nextY = -999;
		int i = (int)(Math.random() * 8);	// Pick random adjacent location
		for (int count = 0; count < 8; ++count) {	// Check every adjacent location
			if (i == 8) {
				i = 0;
			}
			nextX = x + xVal[i];			
			nextY = y + yVal[i];		
			
			// try to eat an ant in the NEXT position
			if (isl.huntAnt(x, y, nextX, nextY) == true) {
				starveCount = 0;	// Reset starveCount
				x = nextX;
				y = nextY;
				
				++age; 	// increment age
				if (checkSpawnableAge()) {	// True if age can spawn
					setSpawnableToTrue ();
				}
				return true;
			}
			++i;
		}
		return false;		
	}
      
	public void wander ()
	{ 
		int nextX = -999;
		int nextY = -999;
		int direction = (int)(Math.random() * 8);
		
		nextX = x + xVal[direction];			
		nextY = y + yVal[direction];

		// try to move the Creature to the NEXT position
		if ( isl.moveCreature ( x, y, nextX, nextY ) == true)
		{
			x = nextX;
			y = nextY;
		}
		
		++age; 	// increment age
		if (checkSpawnableAge()) {	// True if age can spawn
			setSpawnableToTrue ();
		}
	}
    
	public void spawn ()
	{ 
		if (!spawnable) {	// do not spawn if not spawnable
			return;
		}
		int nextX = -999;
		int nextY = -999;
		for (int i = 0; i < 8; ++i) {	// Check every adjacent location
			nextX = x + xVal[i];			
			nextY = y + yVal[i];		
			
			// try to spawn the Creature to the NEXT position
			if ( isl.spawnCreature ( x, y, nextX, nextY) == true)
			{
				setSpawnableToFalse ();	// Spawn reset after successful spawn
				resetAge ();
				return;
			}
		}
	}
	
	public void starve() {
		if (starveCount == 3) {
			isl.removeCreature(x,y);
		}
	}
  
	public void setDayLastMoved (int dlm)
	{
		dayLastMoved = dlm;
	}
  
	public int getDayLastMoved ()
	{
		return dayLastMoved;
	}
	
	public void setSpawnableToTrue () {
		spawnable = true;
	}
	
	public void setSpawnableToFalse () {
		spawnable = false;
	}
	
	public void resetAge () {
		age = 0;
	}
	
	public boolean checkSpawnableAge() {
		if (age == spawnableAge) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public void setSpawnableAge (int newAge) {
		spawnableAge = newAge;
	}
	
	public boolean isAnt() {
		return isAnt;
	}	
}
