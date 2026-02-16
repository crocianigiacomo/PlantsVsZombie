package tp1.p2.logic.gameobjects;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import tp1.p2.control.exceptions.CommandParseException;
import tp1.p2.control.exceptions.GameException;
import tp1.p2.logic.GameWorld;
import tp1.p2.view.Messages;

public class PlantFactory {
	/* @formatter:off */
	private static final List<Plant> AVAILABLE_PLANTS = Arrays.asList(
		new Sunflower(),
		new Peashooter(),
		new WallNut(),
		new CherryBomb()
	);
	/* @formatter:on */

	public static boolean isValidPlant(String plantName) throws GameException {
		boolean valid = false;

		for (Plant p : AVAILABLE_PLANTS) {
			if(equalPlantsName(p, plantName))
				valid = true;
		}
		if(!valid)
			throw new CommandParseException
				(Messages.INVALID_GAME_OBJECT.formatted(plantName));

		return valid;
	}
	public static Plant spawnPlant(String plantName, GameWorld game, int col, int row) throws GameException {
		Plant spawned = null;
		
		for (Plant p : AVAILABLE_PLANTS) {
			if(equalPlantsName(p, plantName))
				spawned = p.copy(game, col, row);
		}
		if (!(spawned != null))
			throw new CommandParseException
				(Messages.UNEXPECTED_RUNTIME_ERROR);
		
		return spawned;
	}
	public static Iterable<Plant> getAvailablePlants() {
		return Collections.unmodifiableList(AVAILABLE_PLANTS);
	}
	private static boolean equalPlantsName(Plant plant, String plantName) {
		String shortcut = plant.getSymbol();
		String name = plant.getName();

		return shortcut.equalsIgnoreCase(plantName) || name.equalsIgnoreCase(plantName);
	}
	/*
	 * Avoid creating instances of this class
	 */
	private PlantFactory() {}
	public static String printPlantList() {
		StringBuilder buffer = new StringBuilder();

		for(Plant p : AVAILABLE_PLANTS)
			buffer.append(p.getDescription()).append(Messages.LINE_SEPARATOR);

		return buffer.toString();
	}
}