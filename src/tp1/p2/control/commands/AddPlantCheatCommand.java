/*** User action which allows to put a Plant in the game without paying*/
package tp1.p2.control.commands;

import tp1.p2.view.Messages;

public class AddPlantCheatCommand extends AddPlantCommand {
//Constructor
	public AddPlantCheatCommand() {super(false);}
	private AddPlantCheatCommand(String plantName, int col, int row) {
		super(plantName, col, row, false);
	}
	/* public Command create(String[] parameters) throws GameException
	 * public boolean execute(GameWorld game) throws GameException*/
//Getters
	@Override
	protected String getName() {return Messages.COMMAND_CHEAT_PLANT_NAME;}
	@Override 
	protected String getShortcut() {return Messages.COMMAND_CHEAT_PLANT_SHORTCUT;}
	@Override 
	public String getDetails() {return Messages.COMMAND_CHEAT_PLANT_DETAILS;}
	@Override
	public String getHelp() {return Messages.COMMAND_CHEAT_PLANT_HELP;}
//Utils
	@Override
	protected AddPlantCheatCommand clone(String plantName, int col, int row) {
		return new AddPlantCheatCommand(plantName, col, row);
	}
/*
 * Dado que los métodos create y execute funcionan prácticamente igual,
 * con una pequeña adaptacion en los métodos de la clase padre, la
 * clase AddPlantCheatCommand puede reusarlos, ahorrándonos la necesidad
 * de repetir código
 */
}