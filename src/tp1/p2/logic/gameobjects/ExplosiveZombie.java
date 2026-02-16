/**
 * 
 */
package tp1.p2.logic.gameobjects;

import tp1.p2.logic.GameWorld;
import tp1.p2.logic.actions.ExplosionAction;
import tp1.p2.view.Messages;

public class ExplosiveZombie extends Zombie {
	private static final int EXPLOSION_DAMAGE = 3;
	
	public ExplosiveZombie() {super();}
	
	private ExplosiveZombie(GameWorld game, int col, int row) {
		super(game, col, row);
	}
	@Override
	public ExplosiveZombie clone(GameWorld game, int col, int row) {
		return new ExplosiveZombie(game, col, row);
	}
	@Override
	public String getSymbol() {
		return Messages.EXPLOSIVE_ZOMBIE_SYMBOL;
	}
	@Override
	public String getName() {
		return Messages.EXPLOSIVE_ZOMBIE_NAME;
	}
	@Override
	public void onExit() {
		super.onExit();
		delayedAction();
	}
	@Override
	public void onEnter() {
		super.onEnter();
	}
	@Override
	public void delayedAction() {
		ExplosionAction explosion = new ExplosionAction(col, row, EXPLOSION_DAMAGE);
		game.pushAction(explosion);
	}
	@Override
	public void update() {
		super.update();
	}
}