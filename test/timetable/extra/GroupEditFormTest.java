package timetable.extra;

import javax.swing.JDialog;

import by.bsac.timetable.view.extra.GroupEditForm;

public class GroupEditFormTest {

	private static final GroupEditFormTest test = new GroupEditFormTest();

	private static final byte educationLevel = 1;

	public static void main(String[] args) {

		test.test();
	}

	public void test() {
		GroupEditForm dialog = new GroupEditForm(educationLevel);
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialog.setVisible(true);
	}
}