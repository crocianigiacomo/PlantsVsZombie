package tp1.p2.logic.gameobjects;

import tp1.p2.logic.GameWorld;
import tp1.p2.view.Messages;

public class Peashooter extends Plant {
//Constantes
	private static final int COST = 50;
	private static final int ENDURANCE = 3;
	private static final int DAMAGE = 1;
	
	/*HEREDADOS
	 * protected int col;
	 * protected int row;
	 * protected int lives;
	 * protected int cooldown;
	 * protected GameWorld game;
	 */
	public Peashooter() {
		super();
		lives = ENDURANCE;
	}
	public Peashooter(GameWorld game, int col, int row) {
		super(game, col, row);
		lives = ENDURANCE;
	}
	@Override
	protected String getName() {
		return Messages.PEASHOOTER_NAME;
	}
	@Override
	public int getCost() {
		return COST;
	}
	@Override
	public Plant copy(GameWorld game, int col, int row) {
		return new Peashooter(game, col, row);
	}
	@Override
	protected String getSymbol() {
		return Messages.PEASHOOTER_SYMBOL;
	}
	@Override
	public String getDescription() {
		return Messages.plantDescription(Messages.PEASHOOTER_NAME_SHORTCUT, COST, DAMAGE, ENDURANCE);
	}
	@Override
	public void update() {
		int pos = col + 1;
		while (pos < GameWorld.NUM_COLS && !game.attackZombie(pos, row, DAMAGE))
			++pos;
	}
}