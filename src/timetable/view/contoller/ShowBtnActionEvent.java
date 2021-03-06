package timetable.view.contoller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import org.jdatepicker.impl.JDatePickerImpl;

import by.bsac.timetable.command.CommandProvider;
import by.bsac.timetable.command.ICommand;
import by.bsac.timetable.command.exception.CommandException;
import by.bsac.timetable.command.util.Request;
import by.bsac.timetable.hibernateFiles.entity.Group;
import by.bsac.timetable.hibernateFiles.entity.Record;
import by.bsac.timetable.service.exception.ServiceException;
import supportClasses.SupportClass;
import tableClasses.TablesArray;
import timetable.util.ActionMode;
import timetable.view.MainForm;

public class ShowBtnActionEvent implements ActionListener {

	private MainForm mainForm;
	private TablesArray tableArray;
	private JDatePickerImpl datePicker;
	private JComboBox<Group> groupComboBox;

	public ShowBtnActionEvent(MainForm mainForm, TablesArray tableArray, JDatePickerImpl datePicker,
			JComboBox<Group> groupComboBox) {
		this.tableArray = tableArray;
		this.datePicker = datePicker;
		this.groupComboBox = groupComboBox;
		this.mainForm = mainForm;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton showRecordsButton = (JButton) e.getSource();
		showRecordsButton.setEnabled(false);
		tableArray.resetAllTablesInArray();
		Group group = (Group) groupComboBox.getSelectedItem();
		try {
			Date referenceDate = (Date) datePicker.getModel().getValue();
			ICommand command = CommandProvider.getInstance().getCommand(ActionMode.Get_Group_Timetable);
			Request request = new Request();
			request.putParam("group", group);
			request.putParam("referenceDate", referenceDate);
			command.execute(request);
			initGroupTimeTable(request);
			mainForm.setIsGroupSelected(true);

		} catch (CommandException | ServiceException ex) {
			Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
			JOptionPane.showMessageDialog(mainForm.getMainFrame().getContentPane(), ex);
			mainForm.setIsGroupSelected(false);
		} finally {
			showRecordsButton.setEnabled(true);
		}
	}

	private void initGroupTimeTable(Request request) throws ServiceException {

		List<Record> recordList = (List<Record>) request.getValue("recordList");

		if (!recordList.isEmpty()) {
			SupportClass.setModelsForTables(recordList, tableArray);
		}
	}
}