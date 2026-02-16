package tp1.p2.control.commands;

import tp1.p2.control.Command;
import tp1.p2.control.Level;
import tp1.p2.control.exceptions.CommandParseException;
import tp1.p2.control.exceptions.GameException;
import tp1.p2.logic.GameWorld;
import tp1.p2.view.Messages;

public class ResetCommand extends Command {

	private Level level;
	private long seed;
	
	public ResetCommand() {
		super(false);
		level = null;
	}
	public ResetCommand(Level level, long seed) {
		super(false);
		this.level = level;
		this.seed = seed;
	}
	@Override
	protected String getName() {return Messages.COMMAND_RESET_NAME;}
	@Override
	protected String getShortcut() {return Messages.COMMAND_RESET_SHORTCUT;}
	@Override
	public String getDetails() {return Messages.COMMAND_RESET_DETAILS;}
	@Override
	public String getHelp() {return Messages.COMMAND_RESET_HELP;}
	@Override
	public boolean execute(GameWorld game) throws GameException {
		if(level != null)
			game.reset(this.level, this.seed);
		else
			game.reset();
			
		return true;
	}
	@Override
	public Command create(String[] parameters) throws GameException {
		ResetCommand created = null;
		
		if(parameters.length == 2) {
			try {
				created = new ResetCommand(
						Level.valueOfIgnoreCase(parameters[0]),
						Long.parseLong(parameters[1]));
			}catch (NumberFormatException nfe) {
				throw new CommandParseException(Messages.INVALID_COMMAND);
			}
		}
		else if(parameters.length == 0)
			created = new ResetCommand();
		else throw new CommandParseException(Messages.COMMAND_PARAMETERS_MISSING);
		
		return created;
	}
}