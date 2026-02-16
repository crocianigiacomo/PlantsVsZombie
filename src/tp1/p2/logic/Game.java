package tp1.p2.logic;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Random;

import tp1.p2.control.Command;
import tp1.p2.control.Level;
import tp1.p2.control.Record;
import tp1.p2.control.exceptions.GameException;
import tp1.p2.control.exceptions.InvalidPositionException;
import tp1.p2.control.exceptions.NotCatchablePositionException;
import tp1.p2.control.exceptions.NotEnoughCoinsException;
import tp1.p2.control.exceptions.RecordException;
import tp1.p2.logic.actions.GameAction;
import tp1.p2.logic.gameobjects.GameObject;
import tp1.p2.logic.gameobjects.Plant;
import tp1.p2.logic.gameobjects.PlantFactory;
import tp1.p2.logic.gameobjects.Zombie;
import tp1.p2.logic.gameobjects.ZombieFactory;
import tp1.p2.view.Messages;

public class Game implements GameStatus, GameWorld {
	//Constantes Iniciadoras
	private static final int INITIAL_CYCLES = 0;
	private static final int INITIAL_SUNCOINS = 50;
	private static final int INITIAL_SCORE = 0;
	//Atributos
	//Condiciones de entorno de juego
	private long seed;
	private Level level;
	private Random rand;

	//Condiciones de finalizacion
	private boolean playerQuits;
	private boolean playerDied;

	//Estado del juego
	private int cycle;
	private int suncoins;
	private int score;

	//Almacenes del juego
	private GameObjectContainer container;
	private Record record;
	private Deque<GameAction> actions;

	//Generadores de objetos
	private ZombiesManager zombiesManager;
	private SunsManager sunsManager;

	//Khe
	private GameItem usa;

	//Constructores
	/*
	 * Solo UNA instancia en toda la ejecucion del programa
	 */
	public Game(long seed, Level level) throws GameException {
		rand = new Random();
		this.container = new GameObjectContainer();
		this.record = new Record();
		reset(level, seed);
	}

	//Reiniciadores
	/**
	 * Resets the game.
	 */
	@Override
	public void reset() throws GameException{
		reset(this.level, this.seed);
	}
	/**
	 * Resets the game with the provided level and seed.
	 * 
	 * @param level {@link Level} Used to initialize the game.
	 * @param seed Random seed Used to initialize the game.
	 */
	@Override
	public void reset(Level newLevel, long newSeed) throws GameException{
		this.cycle = INITIAL_CYCLES;
		suncoins = INITIAL_SUNCOINS;
		playerQuits = false;
		playerDied = false;
		level = newLevel;
		seed = newSeed;
		rand.setSeed(newSeed);
		zombiesManager = new ZombiesManager(this, level, rand);
		sunsManager = new SunsManager(this, rand);
		container.clear();
		this.actions = new ArrayDeque<>();
		usa = null;
		record.reconfigureRecord(level);
		score = INITIAL_SCORE;
	}

	//Business logic
	private void executePendingActions() {
		while (!this.actions.isEmpty()) {
			GameAction action = this.actions.removeLast();
			action.execute(this);
		}
	}
	public boolean execute(Command command) throws GameException{
		return command.execute(this);
	}

	//Getters
	@Override
	public int getCycle() {
		return cycle;
	}
	@Override
	public int getSuncoins() {
		return suncoins;
	}
	@Override
	public int getScore() {
		return score;
	}
	@Override
	public int getRecord() {
		return record.getRecord();
	}
	@Override
	public int getRemainingZombies() {
		return zombiesManager.getRemainingZombies();
	}
	@Override
	public int getGeneratedSuns() {
		return sunsManager.getGeneratedSuns();
	}
	@Override
	public int getCaughtSuns() {
		return sunsManager.getCatchedSuns();
	}
	@Override
	public Level getLevel() {
		return level;
	}
	@Override
	public long getSeed() {
		return seed;
	}
	@Override
	public boolean isPlayerDied() {
		return playerDied;
	}
	@Override
	public boolean isPlayerQuits() {
		return playerQuits;
	}

	//Setters
	@Override
	public void playerQuits() {
		playerQuits = true;
	}
	@Override
	public void zombieArrived() {
		int row = 0;

		while (row < NUM_ROWS && !playerDied) {
			GameItem sus = container.getGameItemInPosition(-1, row);
			if (sus != null && sus.isNpc())
				playerDied = true;
			else ++row;
		}		
	}

	//Verifiers
	private boolean isPositionInLimits(int col, int row) {
		return 
				(col >= 0) && (col <= NUM_COLS) && 
				(row >= 0) && (row < NUM_ROWS);
	}
	private boolean areTherePendingActions() {
		return this.actions.size() > 0;
	}
	public boolean isFinished() {
		return isPlayerDied() || allZombiesDied();
	}
	public boolean allZombiesDied() {
		return zombiesManager.allZombiesDied();
	}
	@Override
	public boolean isObjectInPosition(int col, int row) { 
		return container.isObjectInPosition(col, row);
	}
	@Override
	public boolean isValidPlantPosition(int col, int row) throws GameException{
		boolean ret = true;
		
		if (!isPositionInLimits(col, row) ||
			container.isObjectInPosition(col, row)) {
			ret = false;
			throw new InvalidPositionException
			(Messages.INVALID_POSITION.formatted(col,row));
		}
		
		return ret;
	}
	@Override
	public boolean isValidZombiePosition(int col, int row) throws GameException{
		boolean ret = true;
		
		if (!isPositionInLimits(col, row) ||
			container.isObjectInPosition(col, row)) {
			ret = false;
			throw new InvalidPositionException
			(Messages.INVALID_POSITION.formatted(col,row));
		}
		
		return ret;
	}
	@Override
	public boolean tryToBuy(
			boolean consumeCoins,
			String plantName,
			int col,
			int row
			) throws GameException {

		Plant purchased = PlantFactory.spawnPlant
				(plantName, this, col, row);
		boolean ret = false;

		if (consumeCoins && suncoins >= purchased.getCost()) {
			suncoins -= purchased.getCost();
			addItem(purchased);
			ret = true;
		} else if (!consumeCoins) {
			addItem(purchased);
			ret = true;
		} else throw new NotEnoughCoinsException(Messages.NOT_ENOUGH_COINS);

		return ret;
	}
	/**
	 * Checks if a cell is fully occupied, that is, the position can be shared between an NPC (Plant, Zombie) and Suns .
	 * 
	 * @param col Column of the cell
	 * @param row Row of the cell
	 * 
	 * @return <code>true</code> if the cell is fully occupied, <code>false</code>
	 *         otherwise.
	 */
	@Override
	public boolean isFullyOccupied(int col, int row) {
		return container.isFullyOccupied(col,row);
	}

	//Operators
	private void updateRecord() throws GameException{
		if(score >= record.getRecord()) {
			record.setRecord(score);
			record.save();
		}		
	}
	@Override
	public void addSuncoins(int income) {
		suncoins += income;	
	}	
	@Override
	public void addSun() {
		sunsManager.addSun();
	}
	@Override
	public boolean addMinion(GameObject gameObject) throws GameException{
		return addItem(gameObject);
	}
	@Override
	public boolean addItem(GameObject item) {
		if (!isPositionInLimits(item.getCol(), item.getRow()))
			return false;
		else {
			if (item.fillPosition() && isFullyOccupied(item.getCol(), item.getRow()))
				return false;
			else {
				container.add(item);
				return true;
			}
		}
	}
	@Override
	public void tryToCatchObject(int col, int row) throws GameException{
		if (!container.catchObject(col, row))
			throw new NotCatchablePositionException
			(Messages.NO_CATCHABLE_IN_POSITION.formatted(col,row));
	}
	@Override
	public void pushAction(GameAction gameAction) {
		actions.addLast(gameAction);
	}

	//Process
	@Override
	public boolean attackPlant(int col, int row, int damage) {
		boolean ok = false;

		if(isPositionInLimits(col, row) && container.isObjectInPosition(col, row)) {
			List<GameItem> victims = container.getGameItemsInPosition(col,row);
			for (GameItem victim : victims) {
				if(victim.receiveZombieAttack(damage))
					ok = true;
			}
		}
		return ok;
	}
	@Override
	public boolean attackZombie(int col, int row, int damage){
		boolean ok = false;

		if(isPositionInLimits(col, row) && container.isObjectInPosition(col, row)) {	
			List<GameItem> victims = container.getGameItemsInPosition(col,row);
			for (GameItem victim : victims) {
				if(victim.receivePlantAttack(damage))
					ok = true;
			}
		}
		return ok;
	}
	@Override
	public void zombieDied(int score) {
		this.score += score;
		zombiesManager.zombieDied();
	}
	/**
	 * Executes the game actions and update the game objects in the board.
	 * 
	 */
	@Override
	public void update() throws GameException {

		// 1. Execute pending actions
		executePendingActions();

		// 2. Execute game Actions
		container.update();
		

		// 3. Game object updates
		zombiesManager.update();
		sunsManager.update();

		// 4. & 5. Remove dead and execute pending actions
		boolean deadRemoved = true;
		while (deadRemoved || areTherePendingActions()) {
			// 4. Remove dead
			deadRemoved = this.container.removeDead();

			// 5. execute pending actions
			executePendingActions();
		}

		this.cycle++;

		// 6. Notify commands that a new cycle started
		Command.newCycle();

		//7. Update record
		updateRecord();
	}

	//Utils
	@Override
	public String positionToString(int col, int row) {
		return container.positionToString(col, row);
	}
	@Override
	public String printPlantList() {
		return PlantFactory.printPlantList();

	}
	@Override
	public String printZombieList() {
		return ZombieFactory.printZombieList();
	}
}