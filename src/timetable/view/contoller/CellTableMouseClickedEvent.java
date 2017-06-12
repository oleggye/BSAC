package timetable.view.contoller;

import java.awt.event.MouseEvent;
import java.util.Date;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;

import org.jdatepicker.impl.JDatePickerImpl;

import hibernateFiles.entity.Group;
import hibernateFiles.entity.Record;
import supportClasses.DateUtil;
import supportClasses.SupportClass;
import tableClasses.ArrayPosition;
import tableClasses.MyMultiSpanCellTable;
import tableClasses.TablesArray;
import timetable.command.CommandProvider;
import timetable.command.ICommand;
import timetable.command.exception.CommandException;
import timetable.command.util.Request;
import timetable.util.ActionMode;
import timetable.view.AddNewRecordForm;
import timetable.view.MainForm;
import timetable.view.UpdateOrCancelForm;

public class CellTableMouseClickedEvent extends java.awt.event.MouseAdapter {

	private MainForm mainForm;
	private TablesArray tableArray;
	private JComboBox<Group> groupComboBox;
	private JDatePickerImpl datePicker;

	public CellTableMouseClickedEvent(MainForm mainForm, TablesArray tableArray, JComboBox<Group> groupComboBox,
			JDatePickerImpl datePicker) {
		this.mainForm = mainForm;
		this.tableArray = tableArray;
		this.groupComboBox = groupComboBox;
		this.datePicker = datePicker;
	}

	@Override
	public void mouseClicked(MouseEvent event) {

		if (mainForm.getIsGroupSelected()) {

			MyMultiSpanCellTable table = (MyMultiSpanCellTable) event.getSource();

			// получаем положение (координаты) выделенной строки таблицы
			int row = table.rowAtPoint(event.getPoint());
			System.err.println("row:" + row);
			int column = table.columnAtPoint(event.getPoint());
			System.err.println("column:" + column);

			if (column == 0)
				return;

			System.out.println(table.getValueAt(row, column));
			Object value = table.getValueAt(row, column);

			// Получаем положение таблицы в массиве
			ArrayPosition aPosition = tableArray.getElementPosition(table);
			System.out.println("aPosition: " + aPosition);

			byte selectedWeekNumber = (byte) (aPosition.getRowIndex() + 1);
			byte selectedLessonOrdinalNumber = (byte) (table.getSelectedRow() + 1);
			byte selectedWeekDay = (byte) (aPosition.getColIndex() + 1);
			Group group = (Group) groupComboBox.getSelectedItem();

			Date referenceDate = (Date) datePicker.getModel().getValue();
			Date lessonDate = DateUtil.getDateByRefDateAndWeekNumberAndDay(referenceDate, selectedWeekNumber,
					selectedWeekDay);

			if (value != null) {
				Record record = (Record) value;

				invokeUpdateOrCancelForm(mainForm.getMainFrame(), lessonDate, group, record, selectedWeekNumber,
						selectedWeekDay, selectedLessonOrdinalNumber);
			} else {
				System.out.println("It isn't a record:");
				invokeAddNewRecordForm(mainForm.getMainFrame(), lessonDate, group, selectedWeekNumber, selectedWeekDay,
						selectedLessonOrdinalNumber);
			}
			table.clearSelection(); // убираем выделение

			try {
				ICommand command = CommandProvider.getInstance().getCommand(ActionMode.GetGroupTimetable);
				Request request = new Request();
				request.putParam("group", group);
				request.putParam("referenceDate", referenceDate);
				command.execute(request);
				
				List<Record> recordList = (List<Record>) request.getValue("recordList");
				
				if (!recordList.isEmpty()) {
					SupportClass.setModelsForTables(recordList, tableArray);
				}
				

			} catch (CommandException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void invokeUpdateOrCancelForm(JFrame parent, Date lessonDate, Group group, Record record, byte weekNumber,
			byte weekDay, byte lessonOrdinalNumber) {
		UpdateOrCancelForm updateOrCancelForm = new UpdateOrCancelForm(parent, lessonDate, group, record, weekNumber,
				weekDay, lessonOrdinalNumber);
		updateOrCancelForm.setModal(true);
		updateOrCancelForm.setVisible(true);
	}

	private void invokeAddNewRecordForm(JFrame parent, Date lessonDate, Group group, byte weekNumber, byte weekDay,
			byte lessonOrdinalNumber) {
		AddNewRecordForm addNewRecord = new AddNewRecordForm(parent, lessonDate, group, weekNumber, weekDay,
				lessonOrdinalNumber);
		addNewRecord.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		addNewRecord.setModal(true);
		addNewRecord.setVisible(true);
	}
}