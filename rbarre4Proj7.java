import java.awt.Color;

/* ------------------------------------------------
* Author: Robert L Barrera
* Class: CS 211, Spring 2017
* Program: #2
* System: Windows 7, Eclipse
* April 24, 2017
* -------------------------------------------------
*/


/**  Initial attempt at creating some code to get things going for Project 7
  * 
  * This code contains 2 classes
  *   Island - the 20x20 area for the Creatures
  *   Beetle - objects that not quite what is required but give me a good starting point
  */

public class rbarre4Proj7
{
  public static void main (String[] args)
  {
	  int sleep = 100;
	  if(args.length > 0 && args[0].equals("-d")){
		  sleep = Integer.parseInt(args[1]);
	  }

    // create my island
    Island island = new Island();
    
    // create 100 Ants
    for (int i = 0 ; i < 100 ; i++)
    {
      Ant a = new Ant(island, true);	// True for an Ant
    }
    
    // create 5 Doodlebugs
    for (int i = 0 ; i < 5 ; i++)
    {
      Doodlebug d = new Doodlebug(island, false);	// False if not an ant
    }
    
    // run simulation for 20 days
    for (int i = 0 ; i < 9999 ; i++)
    {
      GridDisplay.mySleep(sleep);
      island.nextDay();
    }
  }
}

class Island
{
  private GridDisplay disp;
  private Creature[][] grid;
  private int dayCount;
  private int rows;
  private int cols;
  
  public Island ()
  {
    rows = 20;
    cols = 20;
    
    disp = new GridDisplay(rows, cols);
    grid = new Creature[rows][cols];
    
    for (int i = 0 ; i < rows ; i++)
      for (int j = 0 ; j < cols ; j++)
      {
         disp.setColor (i, j, new Color(210, 180, 140)); // Pavement
         grid[i][j] = null;
      }
    
    dayCount = 0;
  }
  
  public int getRows()
  {
    return rows;
  }
  
  public int getCols()
  {
    return cols;
  }
  
  public boolean isOccupied (int i, int j)
  {
    if (grid[i][j] == null)
      return false;
    else
      return true;
  }
  
  public void nextDay ()
  {
    dayCount++;
    System.out.println ("DayCount: " + dayCount);
    
    for (int k = 0; k < 2; ++k) {
    // loop through the grid positions
	    for (int i = 0 ; i < rows ; i++) {
	      for (int j = 0 ; j < cols ; j++)
	      {
	        // System.out.println (i + " " + j + ", " + grid[i][j] != null );
	        
	        // make sure grid location contains a creature and the creature has not moved this day
	        if ( (isOccupied(i,j) == true)  && (grid[i][j].getDayLastMoved() != dayCount) )
	        {
	          // System.out.println (i + " " + j + ", " + grid[i][j].getDayLastMoved() );
	          
	          // Access the creature at this position and try to move it
	          Creature c = grid[i][j];
	          if (k == 0) {
	        	  if (c.isAnt() == false) {
			          c.move();
			          c.setDayLastMoved (dayCount);
	        	  }
	          }
	          else {
        		  c.move();
        		  c.setDayLastMoved (dayCount);
	          }
	        }
	      }
	    }
    }
  }
  
  public boolean addCreature (Creature c, int x, int y)
  {
    // make sure x and y are valid
    if (x < 0 || x >= rows || y < 0 || y >= cols)
      return false;
    
    // make sure no creature is already at that space
    if ( isOccupied(x, y) == true)
      return false;
    
    grid[x][y] = c;
    if (c.isAnt()) {
    	disp.setColor (x, y, new Color(178, 34, 34));	// Ant color
    }
    else {
    	disp.setColor (x, y, new Color(139, 69, 19));	// Doodlebug color
    }
    return true;
  }
  
  public boolean moveCreature (int currX, int currY, int nextX, int nextY)
  {
    // make sure x and y positions are both valid
    if (currX < 0 || currX >= rows || currY < 0 || currY >= cols)
      return false;
    if (nextX < 0 || nextX >= rows || nextY < 0 || nextY >= cols)
      return false;
    
    // make sure a creature exists at the CURR space
    if ( isOccupied(currX, currY) == false)
      return false;
    // make sure no creature is already at the NEXT space
    if ( isOccupied(nextX, nextY) == true)
      return false;
    
    Creature c = grid[currX][currY];
    // move the creature to the new position
    grid[nextX][nextY] = grid[currX][currY];
    grid[currX][currY] = null;
           
    if (c.isAnt()) {
	    // update the GridDisplay colors to show the creature has moved
	    disp.setColor(nextX,nextY, new Color(178, 34, 34));		// Ant color
	    disp.setColor(currX,currY, new Color(210, 180, 140));	// Pavement
    }
    else {
    	// update the GridDisplay colors to show the creature has moved
	    disp.setColor(nextX,nextY, new Color(139, 69 ,19));	// Doodlebug color
	    disp.setColor(currX,currY, new Color(210, 180, 140));	// Pavement
    }
    return true;
  }
  
  public boolean spawnCreature (int currX, int currY, int nextX, int nextY) {
	// make sure x and y positions are both valid
    if (currX < 0 || currX >= rows || currY < 0 || currY >= cols)
      return false;
    if (nextX < 0 || nextX >= rows || nextY < 0 || nextY >= cols)
      return false;
    
    // make sure no creature is already at the NEXT space
    if ( isOccupied(nextX, nextY) == true)
      return false;
    
    Creature c = grid[currX][currY];
    if (c.isAnt()) {    
    	Ant ant = new Ant(this, nextX, nextY, true);	// True for ant
    }
    else {
    	Doodlebug doo = new Doodlebug(this, nextX, nextY, false);	// False for doodlebug
    }
    return true;
  }
  
  public void removeCreature(int currX, int currY) {
	  grid[currX][currY] = null;	// Remove creature from grid
	  disp.setColor(currX,currY, new Color(210, 180, 140)); // Pavement
  }
  
  public boolean huntAnt(int currX, int currY, int nextX, int nextY) {
    // make sure x and y positions are both valid
    if (nextX < 0 || nextX >= rows || nextY < 0 || nextY >= cols)
      return false;
    
    // make sure a creature is already at the NEXT space
    if ( isOccupied(nextX, nextY) == false)
      return false;
    
    if (grid[nextX][nextY].isAnt() == true) {	// Found an adjacent ant
    	grid[nextX][nextY] = null;
    	grid[nextX][nextY] = grid[currX][currY];	// Move doodlebug to eaten ants spot
    	grid[currX][currY] = null;
    	disp.setColor(nextX,nextY, new Color(139, 69 ,19));	// Doodlebug color
    	disp.setColor(currX,currY, new Color(210, 180, 140));	// Pavement
    	return true;
    }
    return false;
  }
}