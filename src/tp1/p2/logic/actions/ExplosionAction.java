package tp1.p2.logic.actions;

import tp1.p2.logic.GameWorld;

public class ExplosionAction implements GameAction {
	
	private static final int SQUARE_MATRIX_DAMAGED_LENGTH = 3;
	
	private int col;
	private int row;
	private int damage;

	public ExplosionAction() {}
	
	public ExplosionAction(int col, int row, int damage) {
		this.col = col;
		this.row = row;
		this.damage = damage;
	}
	@Override
	public void execute(GameWorld game) {
		for (int i = -1; i <= SQUARE_MATRIX_DAMAGED_LENGTH - 2; ++i) {
			for (int j = -1; j <= SQUARE_MATRIX_DAMAGED_LENGTH - 2; ++j) {
				if (!(i == 0 && j == 0)) {
					game.attackZombie(col + i, row + j, damage);
					game.attackPlant(col + i, row + j, damage);
				}
			}
		}
		
	}
}