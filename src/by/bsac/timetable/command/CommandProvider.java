package by.bsac.timetable.command;

import java.util.HashMap;
import java.util.Map;

import by.bsac.timetable.command.impl.AddChair;
import by.bsac.timetable.command.impl.AddClassroom;
import by.bsac.timetable.command.impl.AddFaculty;
import by.bsac.timetable.command.impl.AddRecord;
import by.bsac.timetable.command.impl.CancelRecord;
import by.bsac.timetable.command.impl.DeleteChair;
import by.bsac.timetable.command.impl.DeleteClassroom;
import by.bsac.timetable.command.impl.DeleteFaculty;
import by.bsac.timetable.command.impl.GetGroupTimetable;
import by.bsac.timetable.command.impl.UpdateChair;
import by.bsac.timetable.command.impl.UpdateClassroom;
import by.bsac.timetable.command.impl.UpdateFaculty;
import by.bsac.timetable.command.impl.UpdateRecord;
import timetable.util.ActionMode;

public class CommandProvider {

	private static final CommandProvider INSTANCE = new CommandProvider();
	private final Map<ActionMode, ICommand> commandStore = new HashMap<>();

	public static CommandProvider getInstance() {
		return INSTANCE;
	}

	private CommandProvider() {
		commandStore.put(ActionMode.Update_Record, new UpdateRecord());
		commandStore.put(ActionMode.Cancel_Record, new CancelRecord());
		commandStore.put(ActionMode.Add_Record, new AddRecord());
		commandStore.put(ActionMode.Get_Group_Timetable, new GetGroupTimetable());
		commandStore.put(ActionMode.Add_Faculty, new AddFaculty());
		commandStore.put(ActionMode.Update_Faculty, new UpdateFaculty());
		commandStore.put(ActionMode.Delete_Faculty, new DeleteFaculty());
		commandStore.put(ActionMode.Add_Chair, new AddChair());
		commandStore.put(ActionMode.Update_Chair, new UpdateChair());
		commandStore.put(ActionMode.Delete_Chair, new DeleteChair());
		commandStore.put(ActionMode.Add_Classroom, new AddClassroom());
		commandStore.put(ActionMode.Update_Classroom, new UpdateClassroom());
		commandStore.put(ActionMode.Delete_Classroom, new DeleteClassroom());
	}

	public ICommand getCommand(ActionMode action) {
		ICommand command = commandStore.get(action);

		if (command == null) {
			throw new RuntimeException("Wrong ActionMode:" + action);
		}
		return command;
	}
}