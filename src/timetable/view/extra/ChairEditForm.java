package timetable.view.extra;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.bsac.timetable.command.CommandProvider;
import by.bsac.timetable.command.ICommand;
import by.bsac.timetable.command.exception.CommandException;
import by.bsac.timetable.command.util.Request;
import by.bsac.timetable.hibernateFiles.entity.Chair;
import by.bsac.timetable.hibernateFiles.entity.builder.ChairBuilder;
import by.bsac.timetable.service.exception.ServiceException;
import components.OneColumnTable;
import supportClasses.SupportClass;
import timetable.util.ActionMode;
import timetable.view.util.FormInitializer;

public class ChairEditForm extends JDialog {
	private static final long serialVersionUID = 1L;
	
	private static final Logger LOGGER = LogManager.getLogger(ChairEditForm.class.getName());

	private JTable table;
	private Chair chair;

	/**
	 * Launch the application.
	 */
	public static void main() {
		try {
			ChairEditForm dialog = new ChairEditForm();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			LOGGER.error("Ошибка при загрузке класса ChairEditForm", e);
		}
	}

	public ChairEditForm() {

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
					FormInitializer.initChairTable(table);
				} catch (ServiceException ex) {
					LOGGER.error(ex.getCause().getMessage(), ex);
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

		JButton deleteButton = new JButton("Удалить");
		deleteButton.setFont(new Font("Tahoma", Font.PLAIN, 14));

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

					chair.setNameChair(textField.getText());

					ICommand command = CommandProvider.getInstance().getCommand(ActionMode.Update_Chair);
					Request request = new Request();
					request.putParam("chair", chair);
					command.execute(request);

					FormInitializer.initChairTable(table);

				} catch (CommandException | ServiceException ex) {
					LOGGER.error(ex.getCause().getMessage(), ex);
					JOptionPane.showMessageDialog(getContentPane(), ex.getCause().getMessage());
				} finally {
					editButton.setEnabled(true);
					resetComponents(editButton, deleteButton, textField);
				}

			}
		});

		deleteButton.setBounds(391, 179, 89, 23);
		contentPanel.add(deleteButton);
		deleteButton.setVisible(false);
		deleteButton.addActionListener(new java.awt.event.ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					deleteButton.setEnabled(false);

					ICommand command = CommandProvider.getInstance().getCommand(ActionMode.Delete_Chair);
					Request request = new Request();
					request.putParam("chair", chair);
					command.execute(request);

					FormInitializer.initChairTable(table);

				} catch (CommandException | ServiceException ex) {
					LOGGER.error(ex.getCause().getMessage(), ex);
					JOptionPane.showMessageDialog(getContentPane(), ex.getCause().getMessage());

				} finally {
					deleteButton.setEnabled(true);
					resetComponents(editButton, deleteButton, textField);
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
					chair = new ChairBuilder().buildNameChair(textField.getText()).build();

					ICommand command = CommandProvider.getInstance().getCommand(ActionMode.Add_Chair);
					Request request = new Request();
					request.putParam("chair", chair);
					command.execute(request);

					FormInitializer.initChairTable(table);

				} catch (CommandException | ServiceException ex) {
					LOGGER.error(ex.getCause().getMessage(), ex);
					JOptionPane.showMessageDialog(getContentPane(), ex.getCause().getMessage());
				} finally {
					addButton.setEnabled(true);
					resetComponents(editButton, deleteButton, textField);
				}
			}
		});

		table = new OneColumnTable();
		table.setCellSelectionEnabled(true);

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
					chair = (Chair) table.getModel().getValueAt(rowIndex, columnIndex);
					System.out.println(chair);

					textField.setText(chair.getName());
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

	private void resetComponents(JButton editButton, JButton deleteButton, JTextArea textField) {
		editButton.setVisible(false);
		deleteButton.setVisible(false);
		textField.setText("");
	}
}