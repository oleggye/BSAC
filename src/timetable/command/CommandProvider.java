package timetable.command;

import java.util.HashMap;
import java.util.Map;

import timetable.command.impl.AddRecord;
import timetable.command.impl.CancelRecord;
import timetable.command.impl.GetGroupTimetable;
import timetable.command.impl.UpdateRecord;
import timetable.util.ActionMode;

public class CommandProvider {

	private static final CommandProvider INSTANCE = new CommandProvider();
	private final Map<ActionMode, ICommand> commandStore = new HashMap<>();

	public static CommandProvider getInstance() {
		return INSTANCE;
	}

	private CommandProvider() {
		commandStore.put(ActionMode.Update, new UpdateRecord());
		commandStore.put(ActionMode.Cancel, new CancelRecord());
		commandStore.put(ActionMode.ADD, new AddRecord());
		commandStore.put(ActionMode.GetGroupTimetable, new GetGroupTimetable());
	}

	public ICommand getCommand(ActionMode action) {
		ICommand command = commandStore.get(action);

		if (command == null) {
			throw new RuntimeException("Wrong ActionMode:" + action);
		}
		return command;
	}
}