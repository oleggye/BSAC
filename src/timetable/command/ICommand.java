package timetable.command;

import timetable.command.exception.CommandException;
import timetable.command.util.Request;

public interface ICommand {

	public void execute(Request request) throws CommandException;
}
