package tp1.p2.control.commands;

import tp1.p2.control.Command;
import tp1.p2.control.exceptions.CommandExecuteException;
import tp1.p2.control.exceptions.CommandParseException;
import tp1.p2.control.exceptions.GameException;
import tp1.p2.logic.GameWorld;
import tp1.p2.view.Messages;

public class CatchCommand extends Command {

	private static boolean caughtSunThisCycle = false;

	private int col;
	private int row;

	public CatchCommand() {
		super(false);
		caughtSunThisCycle = false;
	}
	@Override
	protected void newCycleStarted() {
		caughtSunThisCycle = false;
	}
	private CatchCommand(int col, int row) {
		super(false);
		this.col = col;
		this.row = row;
	}
	@Override
	protected String getName() {
		return Messages.COMMAND_CATCH_NAME;
	}
	@Override
	protected String getShortcut() {
		return Messages.COMMAND_CATCH_SHORTCUT;
	}
	@Override
	public String getDetails() {
		return Messages.COMMAND_CATCH_DETAILS;
	}
	@Override
	public String getHelp() {
		return Messages.COMMAND_CATCH_HELP;
	}
	@Override
	public boolean execute(GameWorld game) throws GameException {
		boolean ret = false;
		if (caughtSunThisCycle)
			throw new CommandExecuteException(Messages.SUN_ALREADY_CAUGHT);
		else {
			game.tryToCatchObject(col, row);
			caughtSunThisCycle = true;
			ret = true;			
		}
		return ret;
	}
	@Override
	public Command create(String[] parameters) throws GameException {
		CatchCommand created = null;
		
		if (parameters.length != 2)
			throw new CommandParseException(Messages.COMMAND_PARAMETERS_MISSING);
		else {
			try {
			created = new CatchCommand(
					Integer.parseInt(parameters[0]),
					Integer.parseInt(parameters[1]));
			} catch (NumberFormatException nfe) {
				throw new CommandParseException(Messages.INVALID_COMMAND);
			}
		}
		
		return created;
	}
}