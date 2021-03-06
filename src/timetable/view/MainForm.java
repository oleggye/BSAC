package timetable.view;

import tableClasses.TablesArray;
import timetable.view.contoller.CellTableMouseClickedEvent;
import timetable.view.contoller.ShowBtnActionEvent;
import timetable.view.extra.ChairEditForm;
import timetable.view.extra.ClassroomEditForm;
import timetable.view.extra.EditForm;
import timetable.view.extra.FacultyEditForm;
import timetable.view.extra.FlowEditForm;
import timetable.view.util.FormInitializer;
import supportClasses.SupportClass;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JComboBox;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JMenuItem;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;

import by.bsac.timetable.exception.ApplicationException;
import by.bsac.timetable.hibernateFiles.entity.Faculty;
import by.bsac.timetable.hibernateFiles.entity.Group;
import by.bsac.timetable.hibernateFiles.entity.Record;
import by.bsac.timetable.service.exception.ServiceException;
import components.MyComboBox;
import org.jdatepicker.impl.*;

/**
 * Класс главной формы приложения
 *
 * @author Maksimovich Oleg
 * @version 2.0
 */

public class MainForm {

	private JFrame mainFrame;// переменная формы
	private TablesArray tableArray = new TablesArray(7, 4);

	private JButton showRecordsButton;
	private List<Record> mainRecordList; // массив-кол-ция для
											// записей группы
	private JComboBox<Faculty> facultyComboBox; // combobox с названиями
												// факультетов
	private JComboBox<Group> groupComboBox; // combobox с названиями групп
	private byte educationLevel = 1;// переменная для уровня образования

	private JDatePickerImpl datePicker;

	private Boolean isGroupSelected = false;

	/**
	 * Launch the application.
	 *
	 * @param args
	 *            не используется
	 * @see Exception
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				MainForm window = null;
				try {
					window = new MainForm();
					window.mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
					window.mainFrame.setVisible(true);
				} catch (Throwable e) {
					JOptionPane.showMessageDialog(window.mainFrame.getContentPane(), e.getCause().getMessage());
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainForm() throws IOException {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() throws IOException {
		mainFrame = new JFrame("Расписание УО \"БГАС\"");
		mainFrame.setBounds(100, 100, 770, 533);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.getContentPane().setLayout(new BorderLayout(0, 0));
		mainFrame.setIconImage(new ImageIcon(getClass().getClassLoader().getResource("images/table.png")).getImage());

		JLabel progressBarLbl = new JLabel("Загрузка");
		JProgressBar progressBar = new javax.swing.JProgressBar();

		JPanel topPanel = new JPanel();
		mainFrame.getContentPane().add(topPanel, BorderLayout.NORTH);
		topPanel.setLayout(new GridLayout(2, 1, 0, 0));
		topPanel.setPreferredSize(new Dimension(100, 78));
		topPanel.setMaximumSize(new Dimension(100, 78));

		JPanel menuPanel = new JPanel();
		FlowLayout fl_menuPanel = (FlowLayout) menuPanel.getLayout();
		fl_menuPanel.setAlignment(FlowLayout.LEFT);
		topPanel.add(menuPanel);

		JMenuBar menuBar = new JMenuBar();
		menuPanel.add(menuBar);

		JMenu mnFile = new JMenu("Главная");
		menuBar.add(mnFile);

		JMenuItem mntmNewFile = new JMenuItem("Уровень образования");
		mntmNewFile.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				try {
					showWindow(false, progressBarLbl, progressBar);
				} catch (ServiceException | ApplicationException e) {
					Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, e);
					JOptionPane.showMessageDialog(mainFrame.getContentPane(), e.getCause().getMessage());
				}
			}
		});
		mnFile.add(mntmNewFile);

		JMenu mnNewMenu = new JMenu("\u041D\u0430\u0441\u0442\u0440\u043E\u0439\u043A\u0438");
		menuBar.add(mnNewMenu);

		JMenuItem changeFacultyMenuItem = new JMenuItem("Редактирование факультетов");
		changeFacultyMenuItem.addActionListener((ActionEvent e) -> {
			FacultyEditForm dialog = new FacultyEditForm();
			dialog.setVisible(true);
			facultyComboBox.setSelectedIndex(facultyComboBox.getItemCount() - 1 - facultyComboBox.getSelectedIndex());
			/* очищаем данные во всех таблицах */
			tableArray.resetAllTablesInArray();
		});
		mnNewMenu.add(changeFacultyMenuItem);

		JMenuItem changeFlowMenuItem = new JMenuItem("Редактирование потоков");
		changeFlowMenuItem.addActionListener((ActionEvent e) -> {
			FlowEditForm edf = new FlowEditForm();
			edf.setVisible(true);
			facultyComboBox.setSelectedIndex(facultyComboBox.getItemCount() - 1 - facultyComboBox.getSelectedIndex());
			/* очищаем данные во всех таблицах */
			tableArray.resetAllTablesInArray();
		});
		mnNewMenu.add(changeFlowMenuItem);

		JMenuItem changeChairMenuItem = new JMenuItem("Редактирование кафедр");
		changeChairMenuItem.addActionListener((ActionEvent e) -> {
			ChairEditForm dialog = new ChairEditForm();
			dialog.setVisible(true);
			facultyComboBox.setSelectedIndex(facultyComboBox.getItemCount() - 1 - facultyComboBox.getSelectedIndex());
			/* очищаем данные во всех таблицах */
			tableArray.resetAllTablesInArray();
		});
		mnNewMenu.add(changeChairMenuItem);

		JMenuItem changeGroupMenuItem = new JMenuItem("Редактирование группы");
		changeGroupMenuItem.addActionListener((ActionEvent e) -> {
			EditForm edf = new EditForm(mainFrame, 1, 2, educationLevel);
			edf.setVisible(true);
			facultyComboBox.setSelectedIndex(facultyComboBox.getItemCount() - 1 - facultyComboBox.getSelectedIndex());
			/* очищаем данные во всех таблицах */
			tableArray.resetAllTablesInArray();
		});
		mnNewMenu.add(changeGroupMenuItem);

		JMenuItem changeSubjectMenuItem = new JMenuItem("Редактирование предметов");
		changeSubjectMenuItem.addActionListener((ActionEvent e) -> {
			EditForm edf = new EditForm(mainFrame, 2, 1, educationLevel);
			edf.setVisible(true);
		});
		mnNewMenu.add(changeSubjectMenuItem);

		JMenuItem changeLecturerMenuItem = new JMenuItem("Редактирование преподавателей");
		changeLecturerMenuItem.addActionListener((ActionEvent e) -> {
			EditForm edf = new EditForm(mainFrame, 2, 3, educationLevel);
			edf.setVisible(true);
		});
		mnNewMenu.add(changeLecturerMenuItem);

		JMenuItem changeClassroomMenuItem = new JMenuItem("Редактирование аудиторий");
		changeClassroomMenuItem.addActionListener((ActionEvent e) -> {
			ClassroomEditForm edf = new ClassroomEditForm();
			edf.setVisible(true);
		});
		mnNewMenu.add(changeClassroomMenuItem);

		JMenu mnNewMenu_1 = new JMenu("О программе");
		mnNewMenu_1.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				AboutForm edf = new AboutForm();
				edf.setVisible(true);
			}
		});
		menuBar.add(mnNewMenu_1);

		JPanel FCSubpanel = new JPanel();
		FlowLayout fl_FCSubpanel = (FlowLayout) FCSubpanel.getLayout();
		fl_FCSubpanel.setAlignment(FlowLayout.LEFT);
		topPanel.add(FCSubpanel);

		FCSubpanel.add(new JLabel("Факультет: "));
		facultyComboBox = new MyComboBox<>();
		FCSubpanel.add(facultyComboBox);

		facultyComboBox.addItemListener((ItemEvent ev) -> {
			if (ev.getStateChange() == ItemEvent.SELECTED) {
				tableArray.resetAllTablesInArray();
				try {
					FormInitializer.initGroupComboBox(facultyComboBox, groupComboBox, educationLevel);
				} catch (ServiceException | ApplicationException e) {
					Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, e);
					JOptionPane.showMessageDialog(mainFrame.getContentPane(), e.getCause().getMessage());
				} finally {
					if (groupComboBox.getItemCount() > 0) {
						groupComboBox.setSelectedIndex(0);
					}
					actOrDeactivateButton();
				}
			}
		});
		FCSubpanel.add(new JLabel("Группа: "));
		groupComboBox = new MyComboBox<>();
		FCSubpanel.add(groupComboBox);
		groupComboBox.addItemListener((ItemEvent e) -> {
			if (e.getStateChange() == ItemEvent.SELECTED) {
				tableArray.resetAllTablesInArray();
				isGroupSelected = false;
				showRecordsButton.setEnabled(true);
			}
		});
		FCSubpanel.add(new JLabel("Опорная дата: "));
		// задаем текущую дату для календаря в качестве опорной
		UtilDateModel model = new UtilDateModel();
		Calendar calendar = Calendar.getInstance();
		model.setValue(calendar.getTime());
		Properties p = new Properties();
		p.put("text.today", "Сегодня");
		JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
		datePicker = new JDatePickerImpl(datePanel, new supportClasses.DateLabelFormatter());
		datePicker.addActionListener((ActionEvent e) -> {
			//
		});
		FCSubpanel.add(datePicker);

		/* 27/10/16 was changed */
		showRecordsButton = new JButton("Показать");
		FCSubpanel.add(showRecordsButton);
		showRecordsButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/toolbar_find.png")));
		showRecordsButton.addActionListener(new ShowBtnActionEvent(this, tableArray, datePicker, groupComboBox));

		JPanel centralPanel = new JPanel();
		centralPanel.setLayout(new GridLayout(7, 4, 10, 10));

		JPanel rightPanel = new JPanel();
		mainFrame.getContentPane().add(rightPanel, BorderLayout.EAST);

		JPanel bottomPanel = new JPanel();
		mainFrame.getContentPane().add(bottomPanel, BorderLayout.SOUTH);

		// добавляем progressbar и progresslabel на панель
		bottomPanel.add(progressBarLbl);
		bottomPanel.add(progressBar);

		final JScrollPane scrollPane = new JScrollPane(centralPanel);
		mainFrame.getContentPane().add(scrollPane, BorderLayout.CENTER);
		scrollPane.setColumnHeaderView(SupportClass.getJLabelWithPicture(this));

		MouseListener listener = new CellTableMouseClickedEvent(this, tableArray, groupComboBox, datePicker);

		// задаем предварительные значения для таблиц в двумерном массиве таблиц
		tableArray.initArray(mainRecordList, centralPanel, listener);
		// ********************************************************************************

		FormInitializer.initLeftPanel(scrollPane);
		addWindowListener(new MainFormWindowListener(mainFrame, progressBarLbl, progressBar));
	}

	private boolean getEduLevel(boolean isJustStarted) {
		EduForm edf = new EduForm();
		edf.setVisible(true);
		byte new_eduLvl = edf.getEduLevel();
		if (new_eduLvl != this.educationLevel) {
			this.educationLevel = new_eduLvl;
			return true;
		} else if (isJustStarted) {
			return true;
		} else {
			return false;
		}
	}

	private void showWindow(boolean isJustStarted, JLabel progressBarLbl, JProgressBar progressBar)
			throws ServiceException {
		mainFrame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		try {
			// TODO add your handling code here:
			if (getEduLevel(isJustStarted))// уровень образования
			{
				mainFrame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				FormInitializer.initMainForm(facultyComboBox, groupComboBox, progressBarLbl, progressBar,
						educationLevel);
				// 27.10.16this.actOrDeactivateButton();
			}
		} catch (ServiceException ex) {
			Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
			JOptionPane.showMessageDialog(mainFrame.getContentPane(), ex.getCause().getMessage());
			progressBarLbl.setText(ex.toString());

		} catch (Throwable ex) {
			JOptionPane.showMessageDialog(mainFrame.getContentPane(), ex.getCause().getMessage());
			JOptionPane.showMessageDialog(mainFrame.getContentPane(), "Обратитесь к администратору!");
			JOptionPane.showMessageDialog(mainFrame.getContentPane(), "Приложение будет закрыто!");
			mainFrame.dispose();

		} finally {
			mainFrame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}
	}

	private void actOrDeactivateButton() { // для кнопки
		if (this.groupComboBox.getItemCount() < 1) {
			this.showRecordsButton.setEnabled(false);
		} else {
			this.showRecordsButton.setEnabled(true);
		}
	}

	public TablesArray getTablesArray() {
		return tableArray;
	}

	public void addWindowListener(WindowListener wl) {
		mainFrame.addWindowListener(wl);
	}

	public class MainFormWindowListener extends WindowAdapter {
		private JFrame mainFrame;
		private JLabel progressBarLbl;
		private JProgressBar progressBar;

		MainFormWindowListener(JFrame mainFrame, JLabel progressBarLbl, JProgressBar progressBar) {
			this.mainFrame = mainFrame;
			this.progressBarLbl = progressBarLbl;
			this.progressBar = progressBar;
		}

		@Override
		public void windowOpened(java.awt.event.WindowEvent evt) {
			try {
				showWindow(true, progressBarLbl, progressBar);
			} catch (ServiceException | ApplicationException e) {
				Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, e);
				JOptionPane.showMessageDialog(mainFrame.getContentPane(), e.getCause().getMessage());
				progressBarLbl.setText(e.toString());
			} finally {
				mainFrame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		}

		@Override
		public void windowClosing(WindowEvent e) {
			// try {
			// initializer.closeSession();
			// } finally {
			super.windowClosing(e);
		}
	}

	public byte getEducationLevel() {
		return educationLevel;
	}

	public void setEducationLevel(byte educationLevel) {
		this.educationLevel = educationLevel;
	}

	public boolean isGroupSelected() {
		return isGroupSelected;
	}

	public void setGroupSelected(boolean isGroupSelected) {
		this.isGroupSelected = isGroupSelected;
	}

	public Boolean getIsGroupSelected() {
		return isGroupSelected;
	}

	public void setIsGroupSelected(Boolean isGroupSelected) {
		this.isGroupSelected = isGroupSelected;
	}

	public JFrame getMainFrame() {
		return mainFrame;
	}

	public void setMainFrame(JFrame mainFrame) {
		this.mainFrame = mainFrame;
	}
}