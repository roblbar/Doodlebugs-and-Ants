
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
