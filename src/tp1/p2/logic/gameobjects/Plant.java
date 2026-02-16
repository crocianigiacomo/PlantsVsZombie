package tp1.p2.logic.gameobjects;

import tp1.p2.logic.GameWorld;

public abstract class Plant extends GameObject {
	/*HEREDADOS
	 * protected int col;
	 * protected int row;
	 * protected int lives;
	 * protected int cooldown;
	 * protected GameWorld game;
	 */
	public Plant() {
		super();
	}
	public Plant(GameWorld game, int col, int row) {
		super(game, col, row);
	}
	public Plant(GameWorld game, int col, int row, int cooldown) {
		super(game, col, row, cooldown);
	}
	@Override
	public boolean receiveZombieAttack(int damage) {
		if (isAlive()) {
			lives -= damage;
			return true;
		}else return false;
	}
	@Override
	public boolean receivePlantAttack(int damage) {return false;}

	abstract protected String getName();
	abstract public int getCost();
	abstract public Plant copy(GameWorld game, int col, int row);
	
	@Override
	public void onEnter() {
		if (cooldown > 0)
			++cooldown;
	}
	@Override
	public void onExit() {}
	@Override
	public boolean isNpc() {
		return false;
	}
	@Override
	public boolean fillPosition() {
		return true;
	}
}