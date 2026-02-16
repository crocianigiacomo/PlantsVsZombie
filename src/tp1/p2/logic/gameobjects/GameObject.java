package tp1.p2.logic.gameobjects;

import static tp1.p2.view.Messages.status;

import tp1.p2.logic.GameItem;
import tp1.p2.logic.GameWorld;
import tp1.p2.view.Messages;

/*** Base class for game non playable character in the game.*/
public abstract class GameObject implements GameItem {
	
	protected static final int DEFAULT_COOLDOWN = 0;
	
	protected GameWorld game;
	protected int col;
	protected int row;
	protected int lives;
	protected int cooldown;

	GameObject() {}

	GameObject(GameWorld game, int col, int row) {
		this.game = game;
		this.col = col;
		this.row = row;
		cooldown = DEFAULT_COOLDOWN;
	}	
	GameObject(GameWorld game, int col, int row, int cooldown) {
		this.game = game;
		this.col = col;
		this.row = row;
		this.cooldown = cooldown;
	}
	public boolean isInPosition(int col, int row) {
		return this.col == col && this.row == row;
	}
	public int getCol() {
		return col;
	}
	public int getRow() {
		return row;
	}
	public String toString() {
		if (isAlive()) {
			return status(getSymbol(), lives);
		} else return "";
	}
	public boolean isAlive() {
		return lives > 0;
	}
	public void receiveDamage(int damage) {
		lives -= damage;
	}
	@Override
	public boolean catchObject() {
		if (fillPosition())
			return false;
		else {
			game.addSuncoins(Sun.getValue());
			Sun.increaseCatchedSuns();
			this.kill();
			return true;
		}
	}
	protected void kill() {
		lives = 0;
	}
	protected void delayedAction() {}

	abstract protected String getSymbol();
	abstract public String getDescription();
	abstract public void update();
	abstract public void onEnter();
	abstract public void onExit();
	abstract public boolean fillPosition();
	abstract protected GameObject copy(GameWorld game2, int col2, int row2);
	
	
}