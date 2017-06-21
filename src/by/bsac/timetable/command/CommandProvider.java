package by.bsac.timetable.command;

import java.util.HashMap;
import java.util.Map;

import by.bsac.timetable.command.impl.AddChair;
import by.bsac.timetable.command.impl.AddClassroom;
import by.bsac.timetable.command.impl.AddFaculty;
import by.bsac.timetable.command.impl.AddFlow;
import by.bsac.timetable.command.impl.AddGroup;
import by.bsac.timetable.command.impl.AddLecturer;
import by.bsac.timetable.command.impl.AddRecord;
import by.bsac.timetable.command.impl.AddSubject;
import by.bsac.timetable.command.impl.CancelRecord;
import by.bsac.timetable.command.impl.DeleteChair;
import by.bsac.timetable.command.impl.DeleteClassroom;
import by.bsac.timetable.command.impl.DeleteFaculty;
import by.bsac.timetable.command.impl.DeleteFlow;
import by.bsac.timetable.command.impl.DeleteGroup;
import by.bsac.timetable.command.impl.DeleteLecturer;
import by.bsac.timetable.command.impl.DeleteSubject;
import by.bsac.timetable.command.impl.GetFlowGroupList;
import by.bsac.timetable.command.impl.GetGroupTimetable;
import by.bsac.timetable.command.impl.UpdateChair;
import by.bsac.timetable.command.impl.UpdateClassroom;
import by.bsac.timetable.command.impl.UpdateFaculty;
import by.bsac.timetable.command.impl.UpdateFlow;
import by.bsac.timetable.command.impl.UpdateGroup;
import by.bsac.timetable.command.impl.UpdateLecturer;
import by.bsac.timetable.command.impl.UpdateRecord;
import by.bsac.timetable.command.impl.UpdateSubject;
import by.bsac.timetable.util.ActionMode;

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

		commandStore.put(ActionMode.Add_Flow, new AddFlow());
		commandStore.put(ActionMode.Update_Flow, new UpdateFlow());
		commandStore.put(ActionMode.Delete_Flow, new DeleteFlow());
		commandStore.put(ActionMode.Get_Flow_Group_List, new GetFlowGroupList());

		commandStore.put(ActionMode.Add_Group, new AddGroup());
		commandStore.put(ActionMode.Update_Group, new UpdateGroup());
		commandStore.put(ActionMode.Delete_Group, new DeleteGroup());

		commandStore.put(ActionMode.Add_Lecturer, new AddLecturer());
		commandStore.put(ActionMode.Update_Lecturer, new UpdateLecturer());
		commandStore.put(ActionMode.Delete_Lecturer, new DeleteLecturer());

		commandStore.put(ActionMode.Add_Subject, new AddSubject());
		commandStore.put(ActionMode.Update_Subject, new UpdateSubject());
		commandStore.put(ActionMode.Delete_Subject, new DeleteSubject());

	}

	public ICommand getCommand(ActionMode action) {
		ICommand command = commandStore.get(action);

		if (command == null) {
			throw new RuntimeException("Wrong ActionMode:" + action);
		}
		return command;
	}
}