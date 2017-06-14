package timetable.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import by.bsac.timetable.command.CommandProvider;
import by.bsac.timetable.command.ICommand;
import by.bsac.timetable.command.exception.CommandException;
import by.bsac.timetable.command.util.Request;
import by.bsac.timetable.hibernateFiles.entity.Faculty;
import by.bsac.timetable.hibernateFiles.entity.builder.FacultyBuilder;
import by.bsac.timetable.service.exception.ServiceException;
import components.OneColumnTable;
import components.OneColumnTableCellRenderer;
import supportClasses.SupportClass;
import timetable.util.ActionMode;
import timetable.view.test.ServiceEditFormTest;
import timetable.view.util.FormInitializer;

public class FacultyEditForm extends JDialog {
	private static final long serialVersionUID = 1L;

	private JTable table;
	private Faculty faculty;

	/**
	 * Launch the application.
	 */
	public static void main() {
		try {
			FacultyEditForm dialog = new FacultyEditForm();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			Logger.getLogger(ServiceEditFormTest.class.getName()).log(Level.SEVERE, null, e);
		}
	}

	public FacultyEditForm() {

		setBounds(100, 100, 590, 380);
		setResizable(false);
		SupportClass.formCentering(this);
		getContentPane().setLayout(new BorderLayout());

		JPanel contentPanel = new JPanel();
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowOpened(java.awt.event.WindowEvent evt) {
				try {
					FormInitializer.initFacultyTable(table);
				} catch (ServiceException ex) {
					Logger.getLogger(ServiceEditFormTest.class.getName()).log(Level.SEVERE, null, ex);
					JOptionPane.showMessageDialog(getContentPane(), ex.getCause().getMessage());
				}
			}
		});

		JLabel label = new JLabel("Редактирование/Добавление");
		label.setFont(new Font("Tahoma", Font.BOLD, 14));
		label.setBounds(323, 56, 220, 18);
		contentPanel.add(label);

		JButton editButton = new JButton("Изменить");
		editButton.setFont(new Font("Tahoma", Font.PLAIN, 14));

		JTextArea textField = new JTextArea(1, 1);
		textField.setFont(new Font("Tahoma", Font.PLAIN, 14));

		JScrollPane scrollPane = new JScrollPane(textField);
		scrollPane.setBounds(323, 85, 220, 46);
		contentPanel.add(scrollPane);

		textField.setColumns(1);

		editButton.setVisible(false);
		editButton.setBounds(323, 142, 100, 26);
		contentPanel.add(editButton);

		editButton.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				try {
					editButton.setEnabled(false);

					faculty.setNameFaculty(textField.getText());

					ICommand command = CommandProvider.getInstance().getCommand(ActionMode.Update_Faculty);
					Request request = new Request();
					request.putParam("faculty", faculty);
					command.execute(request);

				} catch (CommandException ex) {
					Logger.getLogger(ServiceEditFormTest.class.getName()).log(Level.SEVERE, null, ex);
					JOptionPane.showMessageDialog(getContentPane(), ex.getCause().getMessage());
				} finally {
					editButton.setEnabled(true);
				}

			}
		});

		JButton addButton = new JButton("Добавить");
		addButton.setFont(new Font("Tahoma", Font.PLAIN, 14));

		addButton.setBounds(438, 142, 105, 26);
		contentPanel.add(addButton);

		addButton.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					addButton.setEnabled(false);
					faculty = new FacultyBuilder().buildName(textField.getText()).build();

					ICommand command = CommandProvider.getInstance().getCommand(ActionMode.Add_Faculty);
					Request request = new Request();
					request.putParam("faculty", faculty);
					command.execute(request);

				} catch (CommandException ex) {
					Logger.getLogger(ServiceEditFormTest.class.getName()).log(Level.SEVERE, null, ex);
					JOptionPane.showMessageDialog(getContentPane(), ex.getCause().getMessage());
				} finally {
					editButton.setVisible(false);
					addButton.setEnabled(true);
				}
			}
		});

		table = new OneColumnTable();
		table.setCellSelectionEnabled(true);
		table.setDefaultRenderer(Object.class, new OneColumnTableCellRenderer());

		table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		table.setFont(new Font("Verdana", Font.BOLD, 14));

		JScrollPane tableScrollPane = new JScrollPane(table);

		tableScrollPane.setBounds(25, 40, 259, 245);
		contentPanel.add(tableScrollPane);

		table.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				int columnIndex = table.getSelectedColumn();
				int rowIndex = table.getSelectedRow();
				if (rowIndex >= 0) {
					faculty = (Faculty) table.getModel().getValueAt(rowIndex, columnIndex);
					System.out.println(faculty);

					textField.setText(faculty.getName());
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
				okButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
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
}