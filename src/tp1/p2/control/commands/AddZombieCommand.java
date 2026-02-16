package tp1.p2.control.commands;

import static tp1.p2.view.Messages.error;

import tp1.p2.control.Command;
import tp1.p2.control.exceptions.CommandParseException;
import tp1.p2.control.exceptions.GameException;
import tp1.p2.control.exceptions.InvalidPositionException;
import tp1.p2.logic.GameWorld;
import tp1.p2.logic.gameobjects.Zombie;
import tp1.p2.logic.gameobjects.ZombieFactory;
import tp1.p2.view.Messages;

public class AddZombieCommand extends Command {
//Atributos
	private int zombieIdx;
	private int col;
	private int row;
//Constructores
	public AddZombieCommand() {super(false);}
	private AddZombieCommand(int zombieIdx, int col, int row) {
		super(false);
		this.zombieIdx = zombieIdx;
		this.col = col;
		this.row = row;
	}
	@Override
	public Command create(String[] parameters) throws GameException {
		AddZombieCommand created = null;
		
		if (parameters.length == 3) {
			try {
				int zombieIndex = Integer.parseInt(parameters[0]), 
						col = Integer.parseInt(parameters[1]), 
						row = Integer.parseInt(parameters[2]);
				
				if(ZombieFactory.isValidZombie(zombieIndex))
					created = new AddZombieCommand(zombieIndex, col, row);
				else throw new CommandParseException
						(Messages.INVALID_GAME_OBJECT.formatted(zombieIndex));
			} catch (NumberFormatException nfe) {
				throw new CommandParseException(Messages.INVALID_COMMAND);
			}
		}else throw new CommandParseException(Messages.COMMAND_PARAMETERS_MISSING);
		
		return created;
	}
//Getters
	@Override
	protected String getName() {return Messages.COMMAND_ADD_ZOMBIE_NAME;}
	@Override
	protected String getShortcut() {return Messages.COMMAND_ADD_ZOMBIE_SHORTCUT;}
	@Override
	public String getDetails() {return Messages.COMMAND_ADD_ZOMBIE_DETAILS;}
	@Override
	public String getHelp() {return Messages.COMMAND_ADD_ZOMBIE_HELP;}
	@Override
//Executers
	public boolean execute(GameWorld game) throws GameException {
		if (!game.isValidZombiePosition(col, row))
			throw new InvalidPositionException
					(Messages.INVALID_POSITION.formatted(col, row));
		else {
			Zombie purchased = ZombieFactory.spawnZombie(zombieIdx, game, col, row);
			
			//if (purchased != null)
			game.addMinion(purchased);				
			game.update();
			
			return true;
		}
	}
}