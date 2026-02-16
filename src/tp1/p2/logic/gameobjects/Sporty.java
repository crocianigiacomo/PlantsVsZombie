
package tp1.p2.logic.gameobjects;

import tp1.p2.logic.GameWorld;
import tp1.p2.view.Messages;

public class Sporty extends Zombie {
	
	private static final int SPEED = 1;
	private static final int ENDURANCE = 2;
	
	public Sporty() {
		super();
		cooldown = SPEED;
	}
	private Sporty (GameWorld game, int col, int row) {
		super(game, col, row, ENDURANCE, SPEED);
	}
	@Override
	public Sporty clone(GameWorld game, int col, int row) {
		return new Sporty(game,col,row);
	}
	@Override
	public String getName() {
		return Messages.SPORTY_ZOMBIE_NAME;
	}
	@Override
	public String getSymbol() {
		return Messages.SPORTY_ZOMBIE_SYMBOL;
	}
	@Override
	public int getSpeed() {
		return SPEED;
	}
	@Override
	public int getEndurance() {
		return ENDURANCE;
	}
	@Override
	public void update() {
		super.update();
	}
	@Override
	public void onExit() {
		super.onExit();
	}
	@Override
	public void onEnter() {
		super.onEnter();
	}
}