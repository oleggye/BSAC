package timetable.view.util;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableModel;

import by.bsac.timetable.hibernateFiles.HibernateUtil;
import by.bsac.timetable.hibernateFiles.entity.Chair;
import by.bsac.timetable.hibernateFiles.entity.Faculty;
import by.bsac.timetable.hibernateFiles.entity.Group;
import by.bsac.timetable.hibernateFiles.entity.Record;
import by.bsac.timetable.service.exception.ServiceException;
import by.bsac.timetable.service.factory.IServiceFactory;
import by.bsac.timetable.service.factory.ServiceFactoryName;
import by.bsac.timetable.service.factory.ServiceFactoryProvider;
import components.MyComboBoxModel;
import components.OneColumnTableModel;
import supportClasses.DateUtil;
import supportClasses.GetNamesClass;
import supportClasses.SupportClass;
import supportClasses.VerticalLabelUI;
import tableClasses.TablesArray;

public class FormInitializer {

	public static void initMainForm(JComboBox<Faculty> facultyComboBox, JComboBox<Group> groupComboBox,
			JLabel progressBarLbl, JProgressBar progressBar, byte educationLevel) throws ServiceException {

		progressBarLbl.setText("Инициализация факультетов");
		progressBar.setValue(30);
		try {
			initFacultyComboBox(facultyComboBox);
			progressBar.setValue(60);

			progressBarLbl.setText("Инициализация групп");
			initGroupComboBox(facultyComboBox, groupComboBox, educationLevel);
			progressBar.setValue(100);
			progressBarLbl.setText("Завершено");

			progressBar.setVisible(false);
			progressBarLbl.setVisible(false);

		} catch (ServiceException e) {
			progressBar.setValue(0);
			progressBarLbl.setText("Ошибка при инициализации");
			StringBuilder builder = new StringBuilder();
			builder.append("Ошибка при инициализации: ");
			builder.append("Приложение завершилось с ошибкой");
			builder.append(e.getMessage());
			throw new ServiceException(builder.toString(), e);
		}

	}

	/**
	 * Инициализация ComboBox строками с названиями факультетов
	 *
	 * @exception при
	 *                ошибке SQL-запроса
	 * @throws ServiceException
	 */
	public static void initFacultyComboBox(JComboBox<Faculty> facultyComboBox) throws ServiceException {

		HibernateUtil.getSession();
		try {
			IServiceFactory factory = ServiceFactoryProvider.getInstance().getServiceFactory(ServiceFactoryName.CHOKE);
			List<Faculty> facultyList = factory.getFacultyService().getAllFaculties();

			if (!facultyList.isEmpty()) {
				DefaultComboBoxModel<Faculty> model = new MyComboBoxModel<>(facultyList);
				facultyComboBox.setModel(model);
			}
		} finally {
			HibernateUtil.closeSession();
		}
	}

	/**
	 * Инициализация ComboBox строками с названиями групп, в зависимости от
	 * выбранного факультета
	 *
	 * @exception при
	 *                ошибке SQL-запроса
	 * @throws ServiceException
	 * @see ServiceException
	 */
	public static void initGroupComboBox(JComboBox<Faculty> facultyComboBox, JComboBox<Group> groupComboBox,
			byte educationLevel) throws ServiceException {

		HibernateUtil.getSession();

		try {
			IServiceFactory factory = ServiceFactoryProvider.getInstance().getServiceFactory(ServiceFactoryName.CHOKE);

			Faculty selectedFaculty = (Faculty) facultyComboBox.getSelectedItem();
			List<Group> groupList = factory.getGroupService().getGroupsRecordsByFacultyIdAndEduLevel(selectedFaculty,
					educationLevel);
			groupComboBox.removeAllItems();

			if (!groupList.isEmpty()) {
				DefaultComboBoxModel<Group> model = new MyComboBoxModel<>(groupList);
				groupComboBox.setModel(model);
			}
		} finally {
			HibernateUtil.closeSession();
		}
	}

	/**
	 * Получаем коллекцию с расписанием для выбранной группы и заполняем
	 * записями таблицу
	 *
	 * @exception при
	 *                ошибке SQL-запроса
	 * @see ServiceException
	 */
	public static void getGroupTimeTable(JComboBox<Group> groupComboBox, TablesArray tableArray, Date referenceDate)
			throws ServiceException {
		HibernateUtil.getSession();

		try {
			if (groupComboBox.getSelectedIndex() >= 0) {

				Group group = (Group) groupComboBox.getSelectedItem();

				IServiceFactory factory = ServiceFactoryProvider.getInstance()
						.getServiceFactory(ServiceFactoryName.CHOKE);

				Date[] dateArr = DateUtil.getDateFromAndDateToByReferenceDate(referenceDate);
				Date dateFrom = dateArr[0];
				Date dateTo = dateArr[1];

				List<Record> recordList = factory.getRecordService().getAllRecordListForGroup(group, dateFrom, dateTo);

				if (!recordList.isEmpty()) {
					SupportClass.setModelsForTables(recordList, tableArray);
				}

				// } else if (!someFlag) {
			} else
				JOptionPane.showMessageDialog(null, "Пожалуйста, выберите группу");
		} finally {
			HibernateUtil.closeSession();
		}

		// someFlag = true;
		// } else {
		// someFlag = false;
		// }
	}

	public static void initLeftPanel(JScrollPane scrollPane) {
		// задаем дни недели в метках
		// leftPanel.add(addDayOfWeekTitlesToPanel()
		JPanel leftPanel = new JPanel();
		leftPanel.setBorder(new MatteBorder(0, 0, 0, 1, (Color) new Color(0, 0, 0)));
		leftPanel.setLayout(new GridLayout(7, 1, 0, 0));
		JLabel[] jLbls = new JLabel[] { new JLabel("Понедельник"), new JLabel("Вторник"), new JLabel("Среда"),
				new JLabel("Четверг"), new JLabel("Пятница"), new JLabel("Суббота"), new JLabel("Воскресенье") };
		for (JLabel lbl : jLbls) {
			lbl.setUI(new VerticalLabelUI(false));// задаем вертикальное
			// отображение для текста в метках
			lbl.setHorizontalAlignment(SwingConstants.CENTER);// задаем
			// горизонтальное выравнивание по центру
			leftPanel.add(lbl);
		}
		scrollPane.setRowHeaderView(leftPanel);
	}

	public static void initFacultyTable(JTable table) throws ServiceException {
		HibernateUtil.getSession();
		try {
			IServiceFactory factory = ServiceFactoryProvider.getInstance().getServiceFactory(ServiceFactoryName.CHOKE);
			List<Faculty> facultyList = factory.getFacultyService().getAllFaculties();

			if (!facultyList.isEmpty()) {

				DefaultTableModel tModel = new OneColumnTableModel<Faculty>(Faculty.class, facultyList,
						"Список факультетов");
				table.setModel(tModel);
				SupportClass.setHorizontalAlignmentToTable(table);
			}
		} finally {
			HibernateUtil.closeSession();
		}
	}

	public static void initChairTable(JTable table) throws ServiceException {
		HibernateUtil.getSession();
		try {
			IServiceFactory factory = ServiceFactoryProvider.getInstance().getServiceFactory(ServiceFactoryName.CHOKE);
			List<Chair> chairList = factory.getChairService().getAllChair();

			if (!chairList.isEmpty()) {

				DefaultTableModel tModel = new OneColumnTableModel<Chair>(Chair.class, chairList, "Список кафедр");
				table.setModel(tModel);
				SupportClass.setHorizontalAlignmentToTable(table);
			}
		} finally {
			HibernateUtil.closeSession();
		}
	}
}
