package timetable.extra;

import javax.swing.JDialog;

import by.bsac.timetable.view.extra.FlowEditForm;

public class FlowEditFormTest {

	private static final FlowEditFormTest test = new FlowEditFormTest();

	public static void main(String[] args) {

		test.test();
	}

	public void test() {
		FlowEditForm dialog = new FlowEditForm();
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialog.setVisible(true);
	}
}