package tp1.p2.logic.gameobjects;

import tp1.p2.logic.GameWorld;
import tp1.p2.view.Messages;

public class Sunflower extends Plant {
//Constantes
	private static final int COST = 20;
	private static final int ENDURANCE = 1;
	private static final int DAMAGE = 0;
	private static final int TIME_RECHARGE = 3;
	
	/*HEREDADOS
	 * protected int col;
	 * protected int row;
	 * protected int lives;
	 * protected int cooldown;
	 * protected GameWorld game;
	 */
	public Sunflower() {
		super();
		lives = ENDURANCE;
		cooldown = TIME_RECHARGE;
	}
	public Sunflower(GameWorld game, int col, int row) {
		super(game, col, row, TIME_RECHARGE);
		lives = ENDURANCE;
	}
	@Override
	protected String getName() {
		return Messages.SUNFLOWER_NAME;
	}
	@Override
	public int getCost() {
		return COST;
	}
	@Override
	public Plant copy(GameWorld game, int col, int row) {
		return new Sunflower(game, col, row);
	}
	@Override
	protected String getSymbol() {
		return Messages.SUNFLOWER_SYMBOL;
	}
	@Override
	public String getDescription() {
		return Messages.plantDescription(Messages.SUNFLOWER_NAME_SHORTCUT, COST, DAMAGE, ENDURANCE);
	}
	@Override
	public void update() {
		if(cooldown == 0) {
			delayedAction();
			cooldown = TIME_RECHARGE;
		}else --cooldown;
	}
	@Override
	protected void delayedAction() {
		game.addSun();
	}
}