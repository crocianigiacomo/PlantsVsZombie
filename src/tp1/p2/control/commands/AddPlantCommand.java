package tp1.p2.control.commands;

import tp1.p2.control.Command;
import tp1.p2.control.exceptions.CommandExecuteException;
import tp1.p2.control.exceptions.CommandParseException;
import tp1.p2.control.exceptions.GameException;
import tp1.p2.control.exceptions.InvalidPositionException;
import tp1.p2.control.exceptions.NotEnoughCoinsException;
import tp1.p2.logic.GameWorld;
import tp1.p2.logic.gameobjects.Plant;
import tp1.p2.logic.gameobjects.PlantFactory;
import tp1.p2.view.Messages;

public class AddPlantCommand extends Command implements Cloneable {
//Atributos
	private int col;
	private int row;
	private String plantName;
	private boolean consumeCoins;
//Constructores
	public AddPlantCommand() {this(true);}
	protected AddPlantCommand(boolean consumeCoins) {
		super(false);
		this.consumeCoins = consumeCoins;
	}
	protected AddPlantCommand(String plantName, int col, int row, boolean consumeCoins) {
		this(consumeCoins);
		this.plantName = plantName;
		this.col = col;
		this.row = row;
	}
	@Override
	public Command create(String[] parameters) throws GameException{
		AddPlantCommand created = null;
		
		if (parameters.length == 3) {
			try {
				int cols = Integer.parseInt(parameters[1]);
				int rows = Integer.parseInt(parameters[2]);
				
				if (PlantFactory.isValidPlant(parameters[0]))
					created = this.clone(parameters[0], cols, rows);
			} catch (NumberFormatException nfe) {
				throw new CommandParseException(Messages.INVALID_COMMAND);
			}			
		} else throw new CommandParseException(Messages.COMMAND_PARAMETERS_MISSING);
		return created;
	}
//Utils
	protected AddPlantCommand clone(String plantName, int col, int row) {
		return new AddPlantCommand(plantName, col, row, true);
	}
//Getters
	@Override
	protected String getName() {return Messages.COMMAND_ADD_NAME;}
	@Override
	protected String getShortcut() {return Messages.COMMAND_ADD_SHORTCUT;}
	@Override
	public String getDetails() {return Messages.COMMAND_ADD_DETAILS;}
	@Override
	public String getHelp() {return Messages.COMMAND_ADD_HELP;}
//Executers
	@Override
	public boolean execute(GameWorld game) throws GameException {
		boolean ret = false;
		
		if(
			game.isValidPlantPosition(col, row) && 
			game.tryToBuy(consumeCoins, plantName, col, row)
		  ) {
			game.update();
			ret = true;
		}
		
		return ret;
	}
}//end class