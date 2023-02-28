/* ------------------------------------------------
* Author: Robert L Barrera
* Class: CS 211, Spring 2017
* Program: #7
* System: Windows 7, Eclipse
* April 24, 2017
* -------------------------------------------------
*/

public class Doodlebug extends Creature {
	
	public Doodlebug (Island island, boolean con) {
		super(island, con);
		setSpawnableAge(8);	// Set new spawnable age
	}
	
	public Doodlebug (Island island, int newX, int newY, boolean con) {
		super(island, newX, newY, con);
		setSpawnableAge(8);	// Set new spawnable age
	}
}
