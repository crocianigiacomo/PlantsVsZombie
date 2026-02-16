/**
 * 
 */
package tp1.p2.logic.gameobjects;

import tp1.p2.logic.GameWorld;
import tp1.p2.view.Messages;

public class BucketHead extends Zombie {
//Constantes
	private static final int ENDURANCE = 8;
	private static final int SPEED = 4;

	public BucketHead() {
		super();
		lives = ENDURANCE;
		cooldown = SPEED;
	}
	private BucketHead(GameWorld game, int col, int row) {
		super(game, col, row, ENDURANCE, SPEED);
	}
	@Override
	public BucketHead clone(GameWorld game, int col, int row) {
		return new BucketHead(game, col, row);
	}
	@Override
	public String getName() {
		return Messages.BUCKET_HEAD_ZOMBIE_NAME;
	}
	@Override
	public String getSymbol() {
		return Messages.BUCKET_HEAD_ZOMBIE_SYMBOL;
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