package timetable.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

import by.bsac.timetable.hibernateFiles.entity.Chair;
import by.bsac.timetable.hibernateFiles.entity.Faculty;
import by.bsac.timetable.hibernateFiles.entity.Group;
import by.bsac.timetable.hibernateFiles.entity.Lecturer;
import by.bsac.timetable.hibernateFiles.entity.Subject;
import by.bsac.timetable.service.exception.ServiceException;
import by.bsac.timetable.service.factory.IServiceFactory;
import by.bsac.timetable.service.factory.ServiceFactoryName;
import by.bsac.timetable.service.factory.ServiceFactoryProvider;
import supportClasses.CheckGeneralization;
import java.util.List;
import javax.swing.JOptionPane;

import supportClasses.SupportClass;
import supportClasses.GetNamesClass;

public class EditForm extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textField;// текстовое поле
	private JTextField textFieldForAbbr;// специально для аббревиатур
	private JTable table;
	private JComboBox comboBox;
	private JLabel lblNewLabel;// для текстовое поле
	private JLabel abrLabel;// специально для аббревиатур
	private JButton editButton;

	private List<Faculty> facultiesCollection;// массив-кол-ция всех факультетов
	private List<Chair> chairsCollection;// массив всех кафедр
	private List<Group> groupsCollection; // массив-кол-ция групп, в зависимости
											// от выбранного факультета
	private List<Subject> subjectsCollection; // массив предметов, в зависимости
												// от выбранной кафедры
	private List<Lecturer> lecturersCollection; // массив преподавателей, в
												// зависимости от выбранной
												// кафедры
	private byte edu_level = 1;

	private int selItemIndex = -1;
	private int cBoxMode = 0;
	private int tableMode = 0;

	/**
	 * Launch the application.
	 */
	public static void main() {
		try {
			EditForm dialog = new EditForm(null, 1, 2, (byte) 1);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			Logger.getLogger(EditForm.class.getName()).log(Level.SEVERE, null, e);
		}
	}

	public EditForm() {
	}

	/**
	 * Create the dialog.
	 *
	 * @param parent
	 * @param cBoxMode
	 * @param tableMode
	 * @param edu_level
	 */
	public EditForm(JFrame parent, int cBoxMode, int tableMode, byte edu_level) {
		super(parent, true);
		this.cBoxMode = cBoxMode;
		this.tableMode = tableMode;
		this.edu_level = edu_level;

		setBounds(100, 100, 590, 380);
		setResizable(false);
		SupportClass.formCentering(this);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowOpened(java.awt.event.WindowEvent evt) {
				try {
					chooseCBByMode();
					chooseTableByMode();
				} catch (ServiceException ex) {
					Logger.getLogger(EditForm.class.getName()).log(Level.SEVERE, null, ex);
					JOptionPane.showMessageDialog(getContentPane(), ex);
				}
			}
		});

		lblNewLabel = new JLabel("New label");

		lblNewLabel.setBounds(400, 20, 70, 14);
		contentPanel.add(lblNewLabel);

		JLabel label = new JLabel("Редактирование/Добавление");
		label.setBounds(345, 100, 190, 14);
		contentPanel.add(label);

		this.comboBox = new JComboBox() {
			@Override
			public String getToolTipText(MouseEvent e) {

				return comboBox.getSelectedItem().toString();
			}
		};
		this.comboBox.setToolTipText("");

		this.comboBox.setBounds(300, 40, 255, 23);
		contentPanel.add(this.comboBox);

		this.comboBox.addItemListener(new java.awt.event.ItemListener() {
			@Override
			public void itemStateChanged(java.awt.event.ItemEvent evt) {
				try {
					chooseTableByMode();
					editButton.setVisible(false);
				} catch (ServiceException ex) {
					Logger.getLogger(EditForm.class.getName()).log(Level.SEVERE, null, ex);
					JOptionPane.showMessageDialog(getContentPane(), ex);
				}
			}
		});

		textField = new JTextField();

		textField.setBounds(325, 123, 220, 23);
		contentPanel.add(textField);

		textField.setColumns(10);

		abrLabel = new JLabel("Аббревиатура");
		abrLabel.setBounds(395, 200, 190, 14);
		contentPanel.add(abrLabel);
		abrLabel.setVisible(false);

		textFieldForAbbr = new JTextField();
		textFieldForAbbr.setBounds(370, 220, 130, 23);
		contentPanel.add(textFieldForAbbr);

		textFieldForAbbr.setColumns(10);
		textFieldForAbbr.setVisible(false);

		editButton = new JButton("Изменить");
		editButton.setVisible(false);
		editButton.setBounds(335, 169, 100, 23);
		contentPanel.add(editButton);

		editButton.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				try {
					editButton.setEnabled(false);
					addOrUpdateRecord(ActionMode.Update);
					chooseTableByMode();
				} catch (ServiceException ex) {
					Logger.getLogger(EditForm.class.getName()).log(Level.SEVERE, null, ex);
					JOptionPane.showMessageDialog(getContentPane(), ex);
				} finally {
					editButton.setEnabled(true);
				}

			}
		});

		JButton button2 = new JButton("Добавить");

		button2.setBounds(440, 169, 95, 23);
		contentPanel.add(button2);

		button2.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!textField.getText().equals("")) {
					try {
						button2.setEnabled(false);
						addOrUpdateRecord(ActionMode.Add);
						chooseTableByMode();
						editButton.setVisible(false);
					} catch (ServiceException ex) {
						Logger.getLogger(EditForm.class.getName()).log(Level.SEVERE, null, ex);
						JOptionPane.showMessageDialog(getContentPane(), ex);
					} finally {
						button2.setEnabled(true);
					}
				}
			}
		});

		this.table = new JTable() {
			// Implement table cell tool tips.
			public String getToolTipText(MouseEvent e) {
				String tip = null;
				java.awt.Point p = e.getPoint();
				int rowIndex = rowAtPoint(p);
				int colIndex = columnAtPoint(p);
				int realColumnIndex = convertColumnIndexToModel(colIndex);

				if (realColumnIndex == 0) {
					tip = (String) getValueAt(rowIndex, colIndex);
				}
				return tip;
			}
		};

		this.table.setModel(new javax.swing.table.DefaultTableModel(new Object[][] { {} }, new String[] {}) {
			Class[] types = new Class[] { java.lang.String.class };
			boolean[] canEdit = new boolean[] { false };

			public Class getColumnClass(int columnIndex) {
				return types[columnIndex];
			}

			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return canEdit[columnIndex];
			}
		});
		this.table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		this.table.setFont(new Font("Verdana", Font.BOLD, 14));

		JScrollPane scrollPane = new JScrollPane(table);

		scrollPane.setBounds(25, 40, 259, 245);
		// scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		contentPanel.add(scrollPane);

		this.table.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				selItemIndex = table.getSelectedRow();
				if (selItemIndex >= 0) {
					initTextFieldBy();
					editButton.setVisible(true);
				}
			}
		});

		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);

				okButton.addActionListener(new java.awt.event.ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
			}
		}
	}

	private void initFacultyComboBox() throws ServiceException {

		IServiceFactory factory = ServiceFactoryProvider.getInstance().getServiceFactory(ServiceFactoryName.CHOKE);
		//
		facultiesCollection = factory.getFacultyService().getAllFaculties();
		final DefaultComboBoxModel model1 = new DefaultComboBoxModel(
				GetNamesClass.getFacultiesNames(facultiesCollection));
		this.comboBox.setModel(model1);
	}

	private void initChairComboBox() throws ServiceException {

		IServiceFactory factory = ServiceFactoryProvider.getInstance().getServiceFactory(ServiceFactoryName.CHOKE);
		//
		chairsCollection = factory.getChairService().getAllChair();
		final DefaultComboBoxModel model1 = new DefaultComboBoxModel(GetNamesClass.getChairsNames(chairsCollection));
		this.comboBox.setModel(model1);
	}

	private void chooseCBByMode() throws ServiceException {
		switch (this.cBoxMode) {
		case 1:
			initFacultyComboBox();
			lblNewLabel.setText("Факультет");
			break;
		case 2:
			initChairComboBox();
			lblNewLabel.setText("Кафедра");
			break;
		default:
			this.dispose();
		}
	}

	private void initTableBySubjects(List<Subject> collection) {

		DefaultTableModel tModel = (DefaultTableModel) this.table.getModel();
		Vector title = new Vector();
		title.add("Предмет");
		tModel.setDataVector(arrayListSubjectsToNamesVector(collection), title);
		SupportClass.setHorizontalAlignmentToTable(this.table);
	}

	private void initTableByGroups(List<Group> collection) {

		DefaultTableModel tModel = (DefaultTableModel) this.table.getModel();
		Vector title = new Vector();
		title.add("Группа");
		tModel.setDataVector(arrayListGroupsToNamesVector(collection), title);
		SupportClass.setHorizontalAlignmentToTable(this.table);
	}

	private void initTableByLecturers(List<Lecturer> collection) {

		DefaultTableModel tModel = (DefaultTableModel) this.table.getModel();
		Vector title = new Vector();
		title.add("Преподаватель");
		tModel.setDataVector(arrayListLecturersToNamesVector(collection), title);
		SupportClass.setHorizontalAlignmentToTable(this.table);
	}

	private void chooseTableByMode() throws ServiceException {

		switch (this.tableMode) {
		case 1:
			getSubjectsCollection();
			break;
		case 2:
			getGroupsCollection();
			break;
		case 3:
			getLecturersCollection();
			break;
		default:
			this.dispose();
		}
		;
	}

	private void initTextFieldBy() {
		switch (this.tableMode) {
		case 1:
			initTextFieldBySubjectN(subjectsCollection);
			textFieldForAbbr.setVisible(true);
			abrLabel.setVisible(true);
			break;
		case 2:
			initTextFieldByGroupsN(groupsCollection);
			break;
		case 3:
			initTextFieldByLecturersN(lecturersCollection);
			break;
		default:
			this.dispose();
		}
		;
	}

	private void initTextFieldBySubjectN(List<Subject> collection) {

		String text = collection.get(this.selItemIndex).getNameSubject();
		textField.setText(text);
		String abrText = collection.get(this.selItemIndex).getAbnameSubject();
		textFieldForAbbr.setText(abrText);
	}

	private void initTextFieldByGroupsN(List<Group> collection) {

		String text = collection.get(this.selItemIndex).getNameGroup();
		textField.setText(text);
	}

	private void initTextFieldByLecturersN(List<Lecturer> collection) {

		String text = collection.get(this.selItemIndex).getNameLecturer();
		textField.setText(text);
	}

	private void getSubjectsCollection() throws ServiceException {
		// т.к. есть разделение по уровню образования, то изменен запрос
		IServiceFactory factory = ServiceFactoryProvider.getInstance().getServiceFactory(ServiceFactoryName.CHOKE);
		//
		int CB_index = this.comboBox.getSelectedIndex(); // передаем index
															// выбранного
															// элемента в
															// ComboBox
		// subjectsCollection = (ArrayList)
		// Factory.getInstance().getSubjectsDAO().getSubjectsRecordsByChairId(chairsCollection.get(CB_index));
		subjectsCollection = factory.getSubjectService()
				.getSubjectsRecordsByChairIdAndEduLevel(chairsCollection.get(CB_index), this.edu_level);

		/*
		 * использовалась для автоматического генерирования аббревиатур
		 * предметов subjectsCollection =
		 * SupportClass.addAbrToCollection(subjectsCollection);
		 */
		initTableBySubjects(subjectsCollection);
	}

	private void getGroupsCollection() throws ServiceException {

		IServiceFactory factory = ServiceFactoryProvider.getInstance().getServiceFactory(ServiceFactoryName.CHOKE);
		//
		int CB_index = this.comboBox.getSelectedIndex(); // передаем index
															// выбранного
															// элемента в
															// ComboBox
		groupsCollection = factory.getGroupService()
				.getGroupsRecordsByFacultyIdAndEduLevel(facultiesCollection.get(CB_index), this.edu_level);
		initTableByGroups(groupsCollection);
	}

	private void getLecturersCollection() throws ServiceException {

		IServiceFactory factory = ServiceFactoryProvider.getInstance().getServiceFactory(ServiceFactoryName.CHOKE);
		//
		int CB_index = this.comboBox.getSelectedIndex(); // передаем index
															// выбранного
															// элемента в
															// ComboBox
		lecturersCollection = factory.getLecturerService()
				.getLecturersRecordsByChairId(chairsCollection.get(CB_index));
		initTableByLecturers(lecturersCollection);
	}

	private Vector<String> arrayListGroupsToNamesVector(List<Group> collection) {

		Vector vec = new Vector<String>();
		String[] str = new String[1];// 1 - кол-во столбцов

		for (Group e : collection) {
			Vector element = new Vector();
			for (int i = 0; i < 1; i++) {
				str[i] = e.getNameGroup();
				element.add(str[i]);
			}
			vec.add(element);
		}
		return vec;
	}

	private Vector<String> arrayListLecturersToNamesVector(List<Lecturer> collection) {

		Vector vec = new Vector<String>();
		String[] str = new String[1];// 1 - кол-во столбцов

		for (Lecturer e : collection) {
			Vector element = new Vector();
			for (int i = 0; i < 1; i++) {
				str[i] = e.getNameLecturer();
				element.add(str[i]);
			}
			vec.add(element);
		}
		return vec;
	}

	private Vector<String> arrayListSubjectsToNamesVector(List<Subject> collection) {

		Vector vec = new Vector<String>();
		String[] str = new String[1];// 1 - кол-во столбцов

		for (Subject e : collection) {
			Vector element = new Vector();
			for (int i = 0; i < 1; i++) {
				str[i] = e.getNameSubject();
				element.add(str[i]);
			}
			vec.add(element);
		}
		return vec;
	}

	private void addOrUpdateSubjects(ActionMode mode) throws ServiceException {

		IServiceFactory factory = ServiceFactoryProvider.getInstance().getServiceFactory(ServiceFactoryName.CHOKE);
		switch (mode) {
		case Add:
			Chair chair = chairsCollection.get(comboBox.getSelectedIndex());
			String subjName = textField.getText();
			Subject newSubj;
			if (textFieldForAbbr.getText().equals("")) {// если поле пустое, то
														// генерируем
														// автоматически
				newSubj = new Subject(chair, subjName, this.edu_level);
			} else {
				String abrName = textFieldForAbbr.getText();
				newSubj = new Subject(chair, subjName, this.edu_level, abrName);
			}

			// if (SupportClass.checkSubjectsRecBeforeAdd(subjectsCollection,
			// newSubj)) {
			/* !!!!!временна мера, чтобы попробывать обобщения!!!!! */
			CheckGeneralization<Subject> obj = new CheckGeneralization<Subject>();
			if (obj.checkRecordBeforeAdd(subjectsCollection, newSubj)) {
				factory.getSubjectService().addSubject(newSubj);
			}
			break;
		case Update:
			Subject subj = subjectsCollection.get(this.selItemIndex);
			subj.setNameSubject(textField.getText());
			subj.setAbnameSubject(textFieldForAbbr.getText());
			factory.getSubjectService().updateSubject(subj);
			break;
		default:
			this.dispose();
		}

	}

	private void addOrUpdateGroups(ActionMode mode) throws ServiceException {

		IServiceFactory factory = ServiceFactoryProvider.getInstance().getServiceFactory(ServiceFactoryName.CHOKE);
		switch (mode) {
		case Add:
			Faculty facult = facultiesCollection.get(comboBox.getSelectedIndex());
			String groupName = textField.getText();

			// FIXME: change!!!
			Group newGroup = new Group();
			// facult, groupName, this.edu_level
			newGroup.setFaculty(facult);
			newGroup.setNameGroup(groupName);
			newGroup.setEduLevel(this.edu_level);

			// if (SupportClass.checkGroupsRecBeforeAdd((ArrayList<Group>)
			// groupsCollection, newGroup)) {
			/* !!!!!временна мера, чтобы попробывать обобщения!!!!! */
			CheckGeneralization<Group> obj = new CheckGeneralization<Group>();
			if (obj.checkRecordBeforeAdd((ArrayList<Group>) groupsCollection, newGroup)) {
				factory.getGroupService().addGroup(newGroup);
			}
			break;
		case Update:
			Group group = groupsCollection.get(this.selItemIndex);
			group.setNameGroup(textField.getText());
			factory.getGroupService().updateGroup(group);
			break;
		default:
			this.dispose();
		}

	}

	private void addOrUpdateLecturers(ActionMode mode) throws ServiceException {

		IServiceFactory factory = ServiceFactoryProvider.getInstance().getServiceFactory(ServiceFactoryName.CHOKE);
		switch (mode) {
		case Add:
			Chair chair = chairsCollection.get(comboBox.getSelectedIndex());
			String lectName = textField.getText();
			Lecturer newLect = new Lecturer(chair, lectName);

			// if (SupportClass.checkLecturersRecBeforeAdd(lecturersCollection,
			// newLect)) {
			/* !!!!!временна мера, чтобы попробывать обобщения!!!!! */
			CheckGeneralization<Lecturer> obj = new CheckGeneralization<Lecturer>();
			if (obj.checkRecordBeforeAdd(lecturersCollection, newLect)) {
				factory.getLecturerService().addLecturer(newLect);
			}
			break;
		case Update:
			Lecturer lect = lecturersCollection.get(this.selItemIndex);
			lect.setNameLecturer(textField.getText());
			factory.getLecturerService().updateLecturer(lect);
			break;
		default:
			this.dispose();
		}

	}

	private void addOrUpdateRecord(ActionMode mode) throws ServiceException {

		switch (this.tableMode) {
		case 1:
			try {
				addOrUpdateSubjects(mode);

			} finally {
				textField.setText("");
				textFieldForAbbr.setText("");
				break;
			}

		case 2:
			try {
				addOrUpdateGroups(mode);
			} finally {
				textField.setText("");
				textFieldForAbbr.setText("");
				break;
			}
		case 3:
			try {
				addOrUpdateLecturers(mode);
			} finally {
				textField.setText("");
				textFieldForAbbr.setText("");
				break;
			}
		default:
			dispose();
		}

	}

	enum ActionMode {
		Add, Update, Delete;
	}
}
