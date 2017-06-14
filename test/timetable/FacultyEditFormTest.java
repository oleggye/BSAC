package timetable;

import javax.swing.JDialog;

import org.junit.Test;

import timetable.view.FacultyEditForm;

public class FacultyEditFormTest {

	private static FacultyEditFormTest facultyEditFormTest = new FacultyEditFormTest();

	public static void main(String[] args) {

		facultyEditFormTest.test();
	}

	@Test
	public void test() {
		FacultyEditForm dialog = new FacultyEditForm();
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialog.setVisible(true);
	}

}