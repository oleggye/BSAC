package by.bsac.timetable.view.extra;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.bsac.timetable.command.CommandProvider;
import by.bsac.timetable.command.ICommand;
import by.bsac.timetable.command.exception.CommandException;
import by.bsac.timetable.command.util.Request;
import by.bsac.timetable.hibernateFiles.entity.Faculty;
import by.bsac.timetable.hibernateFiles.entity.Flow;
import by.bsac.timetable.hibernateFiles.entity.Group;
import by.bsac.timetable.hibernateFiles.entity.builder.GroupBuilder;
import by.bsac.timetable.service.exception.ServiceException;
import by.bsac.timetable.util.ActionMode;
import by.bsac.timetable.view.extra.controller.CoincidenceTextFieldController;
import by.bsac.timetable.view.extra.controller.FlowLabelMouseController;
import by.bsac.timetable.view.util.FormInitializer;
import components.MyComboBox;
import components.OneColumnTable;

import javax.swing.ImageIcon;
import java.awt.Color;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

public class GroupEditForm extends JDialog {
	private static final long serialVersionUID = 1L;

	private static final Logger LOGGER = LogManager.getLogger(GroupEditForm.class.getName());

	private GroupEditForm frame = this;

	private JTable table;
	private Group group;
	private Flow flow;

	private boolean isNeedUpdate;

	public GroupEditForm(byte educationLevel) {
		isNeedUpdate = false;

		setBounds(100, 100, 590, 380);
		setModal(true);
		setResizable(false);
		getContentPane().setLayout(new BorderLayout());
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		JComboBox<Faculty> facultyComboBox = new MyComboBox<>();
		facultyComboBox.setFont(new Font("Tahoma", Font.PLAIN, 14));

		JButton addButton = new JButton("Добавить");
		addButton.setFont(new Font("Tahoma", Font.PLAIN, 12));

		JButton editButton = new JButton("Изменить");
		editButton.setFont(new Font("Tahoma", Font.PLAIN, 12));

		JButton deleteButton = new JButton("Удалить");
		deleteButton.setFont(new Font("Tahoma", Font.PLAIN, 12));

		JTextField groupNameTextField = new JTextField();
		groupNameTextField.setFont(new Font("Tahoma", Font.PLAIN, 14));

		JLabel flowLabel = new JLabel("Не задан");
		flowLabel.setBounds(130, 36, 90, 23);

		JLabel coincidenceLabel = new JLabel("Совпадения");
		coincidenceLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));

		JTextArea coincidenceTextArea = new JTextArea();
		coincidenceTextArea.setEditable(false);
		coincidenceTextArea.setFont(new Font("Monospaced", Font.ITALIC, 14));

		JPanel contentPanel = new JPanel();
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowOpened(java.awt.event.WindowEvent evt) {
				try {
					FormInitializer.initFacultyComboBox(facultyComboBox);
					Faculty faculty = (Faculty) facultyComboBox.getSelectedItem();

					FormInitializer.initGroupTable(table, faculty, educationLevel);
				} catch (ServiceException ex) {
					LOGGER.error(ex.getCause().getMessage(), ex);
					JOptionPane.showMessageDialog(getContentPane(), ex);
				}
			}
		});

		JLabel lblNewLabel = new JLabel("Факультет");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));

		lblNewLabel.setBounds(334, 11, 78, 18);
		contentPanel.add(lblNewLabel);

		JLabel label = new JLabel("Редактирование/Добавление");
		label.setFont(new Font("Tahoma", Font.BOLD, 14));
		label.setBounds(334, 74, 234, 18);
		contentPanel.add(label);

		facultyComboBox.setBounds(334, 40, 234, 23);
		contentPanel.add(facultyComboBox);
		facultyComboBox.setToolTipText("");

		facultyComboBox.addItemListener(new java.awt.event.ItemListener() {
			@Override
			public void itemStateChanged(java.awt.event.ItemEvent evt) {
				if (evt.getStateChange() == ItemEvent.SELECTED) {
					try {
						Faculty faculty = (Faculty) facultyComboBox.getSelectedItem();

						FormInitializer.initGroupTable(table, faculty, educationLevel);

					} catch (ServiceException ex) {
						LOGGER.error(ex.getCause().getMessage(), ex);
						JOptionPane.showMessageDialog(getContentPane(), ex);
					} finally {
						refreshFormField(editButton, deleteButton, groupNameTextField, flowLabel);
						flow = null;
					}
				}
			}
		});

		ImageIcon icon = new ImageIcon("C:\\Users\\hello\\Desktop\\add-icon.png");
		Image img = icon.getImage();
		Image newimg = img.getScaledInstance(37, 23, java.awt.Image.SCALE_SMOOTH);
		icon = new ImageIcon(newimg);

		table = new OneColumnTable();
		table.setCellSelectionEnabled(true);
		table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		table.setFont(new Font("Verdana", Font.BOLD, 14));

		JScrollPane scrollPane = new JScrollPane(table);

		scrollPane.setBounds(25, 40, 259, 245);
		contentPanel.add(scrollPane);

		JPanel panel = new JPanel();
		panel.setBounds(334, 98, 234, 131);
		contentPanel.add(panel);
		panel.setLayout(null);

		JLabel label_1 = new JLabel("Поток");
		label_1.setForeground(Color.BLUE);
		label_1.setBounds(149, 11, 46, 14);
		panel.add(label_1);
		label_1.setFont(new Font("Tahoma", Font.BOLD, 14));

		groupNameTextField.setBounds(20, 35, 100, 23);
		panel.add(groupNameTextField);

		groupNameTextField.setColumns(10);

		JLabel label_2 = new JLabel("Название");
		label_2.setForeground(new Color(0, 128, 0));
		label_2.setFont(new Font("Tahoma", Font.BOLD, 14));
		label_2.setBounds(20, 11, 79, 14);
		panel.add(label_2);

		flowLabel.setHorizontalAlignment(SwingConstants.CENTER);
		flowLabel.addMouseListener(new FlowLabelMouseController(frame, flowLabel));
		panel.add(flowLabel);
		flowLabel.setFont(new Font("Tahoma", Font.ITALIC, 12));

		editButton.setBounds(20, 69, 100, 23);
		panel.add(editButton);

		addButton.setBounds(125, 69, 95, 23);
		panel.add(addButton);

		deleteButton.setBounds(78, 103, 100, 23);
		deleteButton.setVisible(false);
		panel.add(deleteButton);

		addButton.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addButton.setEnabled(false);

				try {
					String groupName = groupNameTextField.getText();
					Faculty faculty = (Faculty) facultyComboBox.getSelectedItem();
					Group newGroup = new GroupBuilder().buildEduLevel(educationLevel).buildFaculty(faculty)
							.buildFlow(flow).buildName(groupName).build();

					ICommand command = CommandProvider.getInstance().getCommand(ActionMode.Add_Group);
					Request request = new Request();
					request.putParam("group", newGroup);

					command.execute(request);

					FormInitializer.initGroupTable(table, faculty, educationLevel);

				} catch (CommandException | ServiceException ex) {
					LOGGER.error(ex.getCause().getMessage(), ex);
					JOptionPane.showMessageDialog(getContentPane(), ex);
				} finally {
					addButton.setEnabled(true);
					refreshFormField(editButton, deleteButton, groupNameTextField, flowLabel);
					flow = null;
				}
			}
		});

		editButton.setVisible(false);
		editButton.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				editButton.setEnabled(false);
				try {

					String groupName = groupNameTextField.getText();
					Faculty faculty = (Faculty) facultyComboBox.getSelectedItem();
					group.setFaculty(faculty);
					group.setNameGroup(groupName);

					ICommand command = CommandProvider.getInstance().getCommand(ActionMode.Update_Group);
					Request request = new Request();
					request.putParam("group", group);
					request.putParam("flow", flow);

					command.execute(request);

					FormInitializer.initGroupTable(table, faculty, educationLevel);

				} catch (CommandException | ServiceException ex) {
					LOGGER.error(ex.getCause().getMessage(), ex);
					JOptionPane.showMessageDialog(getContentPane(), ex);
				} finally {
					editButton.setEnabled(true);
					refreshFormField(editButton, deleteButton, groupNameTextField, flowLabel);
					flow = null;
				}
			}
		});

		deleteButton.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int result = JOptionPane.showConfirmDialog(getContentPane(),
						"Удаление группы повлечет за собой удаление всех связанных с ней занятий", "Внимание!",
						JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
				if (result == JOptionPane.YES_OPTION) {
					deleteButton.setEnabled(false);
					try {
						ICommand command = CommandProvider.getInstance().getCommand(ActionMode.Delete_Group);
						Request request = new Request();
						request.putParam("group", group);

						command.execute(request);

						Faculty faculty = (Faculty) facultyComboBox.getSelectedItem();
						FormInitializer.initGroupTable(table, faculty, educationLevel);

					} catch (CommandException | ServiceException ex) {
						LOGGER.error(ex.getCause().getMessage(), ex);
						JOptionPane.showMessageDialog(getContentPane(), ex);
					} finally {
						deleteButton.setEnabled(true);
						refreshFormField(editButton, deleteButton, groupNameTextField, flowLabel);
						flow = null;
					}
				}
			}
		});

		table.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				int columnIndex = table.getSelectedColumn();
				int rowIndex = table.getSelectedRow();
				if (rowIndex >= 0) {
					group = (Group) table.getModel().getValueAt(rowIndex, columnIndex);

					LOGGER.debug("selected group:" + group.getName());

					refreshFormField(editButton, deleteButton, groupNameTextField, flowLabel);

					groupNameTextField.setText(group.getName());

					flow = group.getFlow();
					if (flow != null) {
						flowLabel.setText(flow.getName());
					} else {
						flowLabel.setText("Не задан");
					}

					editButton.setVisible(true);
					deleteButton.setVisible(true);
				}
			}
		});

		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
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
		JScrollPane coincidenceScrollPane = new JScrollPane();
		coincidenceScrollPane.setBounds(334, 251, 234, 54);
		coincidenceScrollPane.setVisible(false);
		contentPanel.add(coincidenceScrollPane);

		coincidenceScrollPane.setViewportView(coincidenceTextArea);
		groupNameTextField.addKeyListener(new CoincidenceTextFieldController(this, Group.class, coincidenceTextArea,
				groupNameTextField, coincidenceScrollPane, coincidenceLabel));

		coincidenceLabel.setForeground(Color.RED);
		coincidenceLabel.setBounds(334, 232, 91, 14);
		coincidenceLabel.setVisible(false);
		contentPanel.add(coincidenceLabel);
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public Flow getFlow() {
		return flow;
	}

	public void setFlow(Flow flow) {
		this.flow = flow;
	}

	public void setNeedUpdate(boolean isNeedUpdate) {
		this.isNeedUpdate = isNeedUpdate;
	}

	public boolean isNeedUpdate() {
		return isNeedUpdate;
	}

	private void refreshFormField(JButton editButton, JButton deleteButton, JTextField groupNameTextField,
			JLabel flowLabel) {
		editButton.setVisible(false);
		deleteButton.setVisible(false);
		groupNameTextField.setText("");
		flowLabel.setText("Не задан");
	}
}