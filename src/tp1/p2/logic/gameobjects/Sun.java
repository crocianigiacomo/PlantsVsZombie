/**
 * 
 */
package tp1.p2.logic.gameobjects;

import tp1.p2.logic.GameWorld;
import tp1.p2.view.Messages;

public class Sun extends GameObject {

	public static final int SUN_LIVES = 10;
	private static final int SUN_COINS = 10;
	private static final int INITIAL_SUNS = 0;
	
	private static int generatedSuns;
	private static int catchedSuns;
	
	public Sun() {
		super();
		lives = SUN_LIVES;
	}
	public Sun(GameWorld game, int col, int row) {
		super(game, col, row);
		lives = SUN_LIVES;
	}
	public static void resetCounters() {
		generatedSuns = INITIAL_SUNS;
		catchedSuns = INITIAL_SUNS;
	}
	public static void increaseCatchedSuns() {
		++catchedSuns;
	}
	public static int getGeneratedSuns() {
		return generatedSuns;
	}
	public static int getCatchedSuns() {
		return catchedSuns;
	}
	@Override
	public boolean receiveZombieAttack(int damage) {return false;}
	@Override
	public boolean receivePlantAttack(int damage) {return false;}
	@Override
	public boolean isInPosition(int col, int row) {
		return this.col == col && this.row == row;
	}
	@Override
	public boolean isNpc() {
		return false;
	}
	@Override
	protected String getSymbol() {
		return Messages.SUN_SYMBOL;
	}
	@Override
	public String getDescription() {
		return Messages.SUN_DESCRIPTION;
	}
	public static final int getValue() {
		return SUN_COINS;
	}
	@Override
	public void update() {
		--lives;
	}
	@Override
	public void onEnter() {
		++generatedSuns;
	}
	@Override
	public void onExit() {}
	@Override
	public boolean fillPosition() {
		return false;
	}
	@Override
	protected GameObject copy(GameWorld game2, int col2, int row2) {
		return new Sun(game2,col2,row2);
	}
}