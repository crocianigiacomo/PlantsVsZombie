/**
 * 
 */
package tp1.p2.logic.gameobjects;

import tp1.p2.logic.GameWorld;
import tp1.p2.logic.actions.ExplosionAction;
import tp1.p2.view.Messages;

public class CherryBomb extends Plant {

	private static final int COST = 50;
	private static final int ENDURANCE = 2;
	private static final int DAMAGE = 10;
	private static final int DELAY_TO_EXPLODE = 2;
	
	private boolean explode;
	
	public CherryBomb() {
		super();
		lives = ENDURANCE;
		cooldown = DELAY_TO_EXPLODE;
		explode = false;
	}
	/**
	 * @param game
	 * @param col
	 * @param row
	 */
	public CherryBomb(GameWorld game, int col, int row) {
		super(game, col, row, DELAY_TO_EXPLODE);
		lives = ENDURANCE;
		explode = false;
	}
	@Override
	protected String getName() {
		return Messages.CHERRY_BOMB_NAME;
	}
	@Override
	public int getCost() {
		return COST;
	}
	@Override
	public Plant copy(GameWorld game, int col, int row) {
		return new CherryBomb(game, col, row);
	}
	@Override
	protected String getSymbol() {
		if (cooldown == 2)
			return Messages.CHERRY_BOMB_SYMBOL;
		else 
			return Messages.CHERRY_BOMB_SYMBOL.toUpperCase();
	}
	@Override
	public String getDescription() {
		return Messages.plantDescription(Messages.CHERRY_BOMB_NAME_SHORTCUT, COST, DAMAGE, ENDURANCE);
	}
	@Override
	public void update() {
		if (cooldown == 1) {
			explode = true;
			kill();
		} else --cooldown;
	}
	@Override
	public void onExit() {
		if(explode)
			delayedAction();
	}
	@Override
	protected void delayedAction() {
		ExplosionAction explosion = new ExplosionAction(col, row, DAMAGE);
		game.pushAction(explosion);
	}
}