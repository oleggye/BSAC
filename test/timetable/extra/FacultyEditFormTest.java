package timetable.extra;

import javax.swing.JDialog;

import by.bsac.timetable.view.extra.FacultyEditForm;

public class FacultyEditFormTest {

	private static final FacultyEditFormTest test = new FacultyEditFormTest();

	public static void main(String[] args) {

		test.test();
	}

	public void test() {
		FacultyEditForm dialog = new FacultyEditForm();
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialog.setVisible(true);
	}
}