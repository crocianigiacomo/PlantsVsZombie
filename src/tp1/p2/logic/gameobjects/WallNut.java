/**
 * 
 */
package tp1.p2.logic.gameobjects;

import tp1.p2.logic.GameWorld;
import tp1.p2.view.Messages;

public class WallNut extends Plant {

	private static final int COST = 50;
	private static final int ENDURANCE = 10;
	private static final int DAMAGE = 0;
	
	public WallNut() {
		super();
		lives = ENDURANCE;
	}
	public WallNut(GameWorld game, int col, int row) {
		super(game, col, row);
		lives = ENDURANCE;
	}
	@Override
	protected String getName() {
		return Messages.WALL_NUT_NAME;
	}
	@Override
	public int getCost() {
		return COST;
	}
	@Override
	public Plant copy(GameWorld game, int col, int row) {
		return new WallNut(game, col, row);
	}
	@Override
	protected String getSymbol() {
		return Messages.WALL_NUT_SYMBOL;
	}
	@Override
	public String getDescription() {
		return Messages.plantDescription(Messages.WALL_NUT_NAME_SHORTCUT, COST, DAMAGE, ENDURANCE);
	}
	@Override
	public void update() {}
}