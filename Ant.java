/* ------------------------------------------------
* Author: Robert L Barrera
* Class: CS 211, Spring 2017
* Program: #7
* System: Windows 7, Eclipse
* April 24, 2017
* -------------------------------------------------
*/

public class Ant extends Creature {
	
	public Ant (Island island, boolean con) {
		super(island, con);
		setSpawnableAge(3);	// Set new spawnable age
	}
	
	public Ant (Island island, int newX, int newY, boolean con) {
		super(island, newX, newY, con);
		setSpawnableAge(3);	// Set new spawnable age
	}
}
