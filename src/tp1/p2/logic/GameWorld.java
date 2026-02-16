package tp1.p2.logic;

import tp1.p2.control.Level;
import tp1.p2.logic.gameobjects.GameObject;

import tp1.p2.control.Command;
import tp1.p2.control.exceptions.GameException;
import tp1.p2.logic.actions.GameAction;

public interface GameWorld {

	public static final int NUM_ROWS = 4;
	public static final int NUM_COLS = 8;
	
	void addSuncoins(int income);
	
	void addSun();

	void tryToCatchObject(int col, int row) throws GameException;

	boolean addItem(GameObject item);
	
	boolean addMinion(GameObject gameObject) throws GameException; //addNpc()

	boolean isValidPlantPosition(int col, int row) throws GameException;
	
	boolean isValidZombiePosition(int col, int row) throws GameException;
	
	boolean tryToBuy(boolean consumeCoins, String plantName, int col, int row) throws GameException;
	
	boolean execute(Command command) throws GameException;

	void update() throws GameException;

	void playerQuits();

	String printPlantList();

	void reset() throws GameException;

	void reset(Level level, long seed) throws GameException;
	
	boolean attackPlant(int col, int row, int damage);
	
	boolean attackZombie(int col, int row, int damage) ;

	boolean isFullyOccupied(int col, int row);

	boolean isObjectInPosition(int col, int row);

	void zombieArrived();
	
	void zombieDied(int score);
	
	void pushAction(GameAction gameAction);

	String printZombieList();
	
	int getRecord();
	
	Level getLevel();
}