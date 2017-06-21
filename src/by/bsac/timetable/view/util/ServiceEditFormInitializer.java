package by.bsac.timetable.view.util;

import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;

import by.bsac.timetable.hibernateFiles.HibernateUtil;
import by.bsac.timetable.hibernateFiles.entity.Faculty;
import by.bsac.timetable.service.exception.ServiceException;
import by.bsac.timetable.service.factory.IServiceFactory;
import by.bsac.timetable.service.factory.ServiceFactoryName;
import by.bsac.timetable.service.factory.ServiceFactoryProvider;
import components.MyComboBoxModel;

public class ServiceEditFormInitializer {
	private JFrame frame;

	public ServiceEditFormInitializer(JFrame frame) {
		this.frame = frame;
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
}