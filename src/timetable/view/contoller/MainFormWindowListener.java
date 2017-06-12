package timetable.view.contoller;

import java.awt.Cursor;
import java.awt.event.WindowAdapter;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;

import service.exception.ServiceException;
import timetable.view.EduForm;
import timetable.view.MainForm;

/*FIXME: it isn't used*/

public class MainFormWindowListener extends WindowAdapter {
	private JFrame mainFrame;
	private JLabel progressBarLbl;

	public MainFormWindowListener(JFrame mainFrame, JLabel progressBarLbl) {
		this.mainFrame = mainFrame;
		this.progressBarLbl = progressBarLbl;
	}

//	@Override
//	public void windowOpened(java.awt.event.WindowEvent evt) {
//		try {
//			//showWindow(true);
//		} catch (ServiceException e) {
//			Logger.getLogger(MainFormTest.class.getName()).log(Level.SEVERE, null, e);
//			JOptionPane.showMessageDialog(mainFrame.getContentPane(), e);
//			progressBarLbl.setText(e.toString());
//		} finally {
//			mainFrame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
//		}
//	}

}
