package timetable.extra;

import javax.swing.JDialog;

import by.bsac.timetable.view.extra.LecturerEditForm;

public class LecturerEditFormTest {

	private static final LecturerEditFormTest test = new LecturerEditFormTest();

	public static void main(String[] args) {

		test.test();
	}

	public void test() {
		LecturerEditForm dialog = new LecturerEditForm();
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialog.setVisible(true);
	}

}
