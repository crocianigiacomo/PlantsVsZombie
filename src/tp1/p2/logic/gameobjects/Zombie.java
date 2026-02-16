/**
 * 
 */
package tp1.p2.logic.gameobjects;

import tp1.p2.logic.GameWorld;
import tp1.p2.view.Messages;

public class Zombie extends GameObject {
	//constantes
	private static final int ENDURANCE = 5;
	private static final int SPEED = 2;
	private static final int DAMAGE = 1;
	
	//Atributos
	/*HEREDADOS
	 * protected int col;
	 * protected int row;
	 * protected GameWorld game;
	 * protected int lives;
	 * protected int cooldown;
	 */
	protected int score;
	
	public Zombie() {
		super();
		lives = ENDURANCE;
		cooldown = SPEED + 1;		
	}
	protected Zombie(GameWorld game, int col, int row) {
		super(game, col, row);
		lives = ENDURANCE;
		cooldown = SPEED + 1;
	}
	protected Zombie(GameWorld game, int col, int row, int vidas, int espera) {
		super(game,col,row);
		lives = vidas;
		cooldown = espera;
	}
	@Override
	public boolean receiveZombieAttack(int damage) {return false;}
	@Override
	public boolean receivePlantAttack(int damage) {
		if (isAlive()) {
			lives -= damage;
			if(!isAlive()&&(damage==10)) {
				score = 20;
			}else if(!isAlive()) {
				score = 10;
			}
			return true;
		}else return false;
	}
	@Override
	public boolean isNpc() {
		return true;
	}
	public String getName() {
		return Messages.ZOMBIE_NAME;
	}
	@Override
	protected String getSymbol() {
		return Messages.ZOMBIE_SYMBOL;
	}
	@Override
	public String getDescription() {
		return Messages.ZOMBIE_DESCRIPTION.formatted(getName(), getSpeed(), getDamage(), getEndurance());
	}
	@Override
	public void update() {
		if (isAlive()) {
			if (!game.isObjectInPosition(col - 1, row)) {					
				--cooldown;
				if (cooldown == 0) {
					--col;
					cooldown = this.getSpeed();
				}
			} else game.attackPlant(col - 1, row, this.getDamage());
		}
	}
	@Override
	public void onEnter() {
		++cooldown;
	}
	@Override
	public void onExit() {
		game.zombieDied(score);
	}
	@Override
	public boolean fillPosition() {
		return true;
	}
	@Override
	public Zombie copy(GameWorld game2, int col2, int row2) {
		return this.clone(game2, col2, row2);
	}
	public Zombie clone(GameWorld game, int col, int row) {
		return new Zombie(game, col, row);
	}
	protected int getSpeed() {
		return SPEED;
	}
	protected int getEndurance() {
		return ENDURANCE;
	}
	protected int getDamage() {
		return DAMAGE;
	}
	public int getScore() {
		return score;
	}
}