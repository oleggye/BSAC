package timetable;

import java.awt.Frame;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import by.bsac.timetable.hibernateFiles.entity.Group;
import by.bsac.timetable.hibernateFiles.entity.Record;
import by.bsac.timetable.view.AddNewRecordForm;

import javax.swing.JDialog;
import javax.swing.JFrame;

public final class AddNewRecordTest {

	private static final AddNewRecordTest test = new AddNewRecordTest();

	public static void main(String[] args) {

		test.testAddNewRecord();
	}

	// public void testAddNewRecordDefault() {
	// AddNewRecord dialog = new AddNewRecord();
	// dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	// dialog.setVisible(true);
	// }

	public void testAddNewRecord() {

		AddNewRecordForm dialog = new AddNewRecordForm(parent, lessonDate, group, currentWeekNumber, currentWeekDay,
				lessonOrdinalNumber);
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialog.setVisible(true);
	}

	private JFrame parent = null;
	private Date lessonDate = new Date("2017/04/12");
	private Group group = new Group();
	private byte currentWeekNumber = 4;
	private byte currentWeekDay = 1;
	private byte lessonOrdinalNumber = 1;
}