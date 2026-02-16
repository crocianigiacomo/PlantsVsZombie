package tp1.p2.logic;

import java.io.FileNotFoundException;
import java.io.IOException;

import tp1.p2.control.Level;
import tp1.p2.control.Record;
import tp1.p2.control.exceptions.GameException;
import tp1.p2.control.exceptions.RecordException;

public interface GameStatus {

	/**
	 * Get game cycles.
	 * 
	 * @return the game cycles
	 */
	int getCycle();

	/**
	 * Get available suncoins
	 * 
	 * @return the available suncoins
	 */
	int getSuncoins();

	/**
	 * Get the number of remaining zombies for this game.
	 * 
	 * @return the number of remaining zombies.
	 */
	int getRemainingZombies();

	/**
	 * Draw a cell of the game.
	 * 
	 * @param col Column of the cell to draw.
	 * @param row Row of the cell to draw.
	 * 
	 * @return a string that represents the content of the cell (col, row).
	 */
	String positionToString(int col, int row);

	/**
	 * Get the number of generated suns.
	 * 
	 * @return the number of generated suns
	 */
	int getGeneratedSuns();

	/**
	 * Get the number of caught suns.
	 * 
	 * @return the number of caught suns
	 */
	int getCaughtSuns();
	/**
	 * Get the current level of the game.
	 * 
	 * @return {@link Level} of the game.
	 */
	Level getLevel();
	
	/**
	 * Get the current seed of the game.
	 * 
	 * @return the <code>long</code> number of the seed
	 */
	long getSeed();
	
	boolean allZombiesDied();
	
	boolean isPlayerQuits();
	
	boolean isPlayerDied();

	int getScore();
	
	int getRecord();	
}