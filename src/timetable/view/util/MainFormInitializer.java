package timetable.view.util;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.Date;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;

import components.MyComboBoxModel;
import hibernateFiles.HibernateUtil;
import hibernateFiles.entity.Faculty;
import hibernateFiles.entity.Group;
import hibernateFiles.entity.Record;
import service.exception.ServiceException;
import service.factory.IServiceFactory;
import service.factory.ServiceFactoryName;
import service.factory.ServiceFactoryProvider;
import supportClasses.DateUtil;
import supportClasses.SupportClass;
import supportClasses.VerticalLabelUI;
import tableClasses.TablesArray;
import timetable.view.MainForm;

public class MainFormInitializer {

	private MainForm frame;

	public MainFormInitializer(MainForm frame) {
		this.frame = frame;
	}

	public void initMainForm(JComboBox<Faculty> facultyComboBox, JComboBox<Group> groupComboBox, JLabel progressBarLbl,
			JProgressBar progressBar) throws ServiceException {

		progressBarLbl.setText("Инициализация факультетов");
		progressBar.setValue(30);
		try {
			initFacultyComboBox(facultyComboBox);
			progressBar.setValue(60);

			progressBarLbl.setText("Инициализация групп");
			initGroupComboBox(facultyComboBox, groupComboBox);
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
	public void initFacultyComboBox(JComboBox<Faculty> facultyComboBox) throws ServiceException {

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
	public void initGroupComboBox(JComboBox<Faculty> facultyComboBox, JComboBox<Group> groupComboBox)
			throws ServiceException {

		HibernateUtil.getSession();

		try {
			IServiceFactory factory = ServiceFactoryProvider.getInstance().getServiceFactory(ServiceFactoryName.CHOKE);

			Faculty selectedFaculty = (Faculty) facultyComboBox.getSelectedItem();
			List<Group> groupList = factory.getGroupService().getGroupsRecordsByFacultyIdAndEduLevel(selectedFaculty,
					frame.getEducationLevel());
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
	public void getGroupTimeTable(JComboBox<Group> groupComboBox, TablesArray tableArray, Date referenceDate)
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

	public void initLeftPanel(JScrollPane scrollPane) {
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

	public void beginSession() {

	}

	public void closeSession() {
		HibernateUtil.closeSession();
	}
}
