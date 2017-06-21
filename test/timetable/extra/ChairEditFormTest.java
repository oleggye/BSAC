package timetable.extra;

import javax.swing.JDialog;

import by.bsac.timetable.view.extra.ChairEditForm;

public class ChairEditFormTest {

	private static final ChairEditFormTest test = new ChairEditFormTest();

	public static void main(String[] args) {

		test.test();
	}

	public void test() {
		ChairEditForm dialog = new ChairEditForm();
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialog.setVisible(true);
	}
}