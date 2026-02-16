package tp1.p2.logic.gameobjects;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import tp1.p2.control.exceptions.GameException;
import tp1.p2.logic.GameWorld;
import tp1.p2.view.Messages;

public class ZombieFactory {
	/* @formatter:off */
	private static final List<Zombie> AVAILABLE_ZOMBIES = Arrays.asList(
		new Zombie(),
		new BucketHead(),
		new Sporty(),
		new ExplosiveZombie()
	);
	/* @formatter:on */

	public static boolean isValidZombie(int zombieIdx) {
		return zombieIdx >= 0 && zombieIdx < AVAILABLE_ZOMBIES.size();
	}
	public static Zombie spawnZombie(int zombieIdx, GameWorld game, int col, int row) throws GameException {
		Zombie ret = null;
		
		if (isValidZombie(zombieIdx)) {
			int index = 0;
			boolean found = false;
			
			while (!found && index < AVAILABLE_ZOMBIES.size()){
				if(index == zombieIdx) {
					ret = AVAILABLE_ZOMBIES.get(index).copy(game,col,row);
					found = true;
				}else ++index;
			}
		}
		
		return ret;
	}
	public static String printZombieList() {
		StringBuilder buffer = new StringBuilder();
		
		for (int index = 0; index < AVAILABLE_ZOMBIES.size(); ++index)
			buffer.append(AVAILABLE_ZOMBIES.get(index).getDescription()).append(Messages.LINE_SEPARATOR);
		
		return buffer.toString();
	}
	public static List<Zombie> getAvailableZombies() { 
		return Collections.unmodifiableList(AVAILABLE_ZOMBIES);
	}
	/*
	 * Avoid creating instances of this class
	 */
	private ZombieFactory() {}
}