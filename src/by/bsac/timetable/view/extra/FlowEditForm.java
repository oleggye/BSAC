package by.bsac.timetable.view.extra;

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
import by.bsac.timetable.hibernateFiles.entity.Flow;
import by.bsac.timetable.hibernateFiles.entity.builder.FlowBuilder;
import by.bsac.timetable.service.exception.ServiceException;
import by.bsac.timetable.service.exception.ServiceValidationException;
import by.bsac.timetable.util.ActionMode;
import by.bsac.timetable.view.util.FormInitializer;
import components.OneColumnTable;

import javax.swing.JTextField;

public class FlowEditForm extends JDialog {
	private static final long serialVersionUID = 1L;

	private static final Logger LOGGER = LogManager.getLogger(FlowEditForm.class.getName());

	private JTable table;
	private Flow flow;
	private JTextField textField;
	private JTextArea groupInFlowTextArea;

	public FlowEditForm() {

		setBounds(100, 100, 590, 380);
		setModal(true);
		setResizable(false);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());

		JPanel contentPanel = new JPanel();
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowOpened(java.awt.event.WindowEvent evt) {
				try {
					FormInitializer.initFlowTable(table);
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

		editButton.setVisible(false);
		editButton.setBounds(323, 126, 100, 26);
		contentPanel.add(editButton);

		editButton.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				String nameFlow = textField.getText();
				boolean isTableHasNot = isTableHasNotAlikeValue(table, nameFlow);

				if (isTableHasNot) {

					try {
						editButton.setEnabled(false);

						flow.setName(textField.getText());

						ICommand command = CommandProvider.getInstance().getCommand(ActionMode.Update_Flow);
						Request request = new Request();
						request.putParam("flow", flow);
						command.execute(request);

						FormInitializer.initFlowTable(table);

					} catch (CommandException | ServiceException ex) {
						LOGGER.error(ex.getCause().getMessage(), ex);
						JOptionPane.showMessageDialog(getContentPane(), ex.getCause().getMessage());
					} finally {
						editButton.setEnabled(true);
						resetComponents(editButton, deleteButton, textField, groupInFlowTextArea);
					}
				}
			}
		});

		deleteButton.setBounds(388, 163, 89, 23);
		contentPanel.add(deleteButton);
		deleteButton.setVisible(false);
		deleteButton.addActionListener(new java.awt.event.ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int result = JOptionPane.showConfirmDialog(getContentPane(),
						"Перед удалением потока, убедитесь, что он не используется в текущем расписании", "Внимание!",
						JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
				if (result == JOptionPane.YES_OPTION) {
					try {
						deleteButton.setEnabled(false);

						ICommand command = CommandProvider.getInstance().getCommand(ActionMode.Delete_Flow);
						Request request = new Request();
						request.putParam("flow", flow);
						command.execute(request);

						FormInitializer.initFlowTable(table);

					} catch (CommandException | ServiceException ex) {
						LOGGER.error(ex.getCause().getMessage(), ex);
						JOptionPane.showMessageDialog(getContentPane(), ex.getCause().getMessage());

					} finally {
						deleteButton.setEnabled(true);
						resetComponents(editButton, deleteButton, textField, groupInFlowTextArea);
					}
				}
			}
		});

		JButton addButton = new JButton("Добавить");
		addButton.setFont(new Font("Tahoma", Font.PLAIN, 14));

		addButton.setBounds(438, 126, 105, 26);
		contentPanel.add(addButton);

		addButton.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String nameFlow = textField.getText();
				boolean isTableHasNot = isTableHasNotAlikeValue(table, nameFlow);

				if (isTableHasNot) {
					try {
						addButton.setEnabled(false);

						flow = new FlowBuilder().buildName(nameFlow).build();

						ICommand command = CommandProvider.getInstance().getCommand(ActionMode.Add_Flow);
						Request request = new Request();
						request.putParam("flow", flow);
						command.execute(request);

						FormInitializer.initFlowTable(table);

					} catch (CommandException | ServiceException ex) {
						LOGGER.error(ex.getCause().getMessage(), ex);
						JOptionPane.showMessageDialog(getContentPane(), ex.getCause().getMessage());
					} finally {
						addButton.setEnabled(true);
						resetComponents(editButton, deleteButton, textField, groupInFlowTextArea);
					}
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

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(323, 222, 220, 67);
		contentPanel.add(scrollPane);

		groupInFlowTextArea = new JTextArea();
		groupInFlowTextArea.setColumns(1);
		groupInFlowTextArea.setVisible(false);
		groupInFlowTextArea.setEditable(false);
		scrollPane.setViewportView(groupInFlowTextArea);

		textField = new JTextField();
		textField.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textField.setBounds(323, 95, 220, 20);
		contentPanel.add(textField);
		textField.setColumns(10);

		JLabel label_1 = new JLabel("Список групп данного потока");
		label_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		label_1.setBounds(323, 197, 229, 17);
		contentPanel.add(label_1);

		table.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				int columnIndex = table.getSelectedColumn();
				int rowIndex = table.getSelectedRow();
				if (rowIndex >= 0) {
					flow = (Flow) table.getModel().getValueAt(rowIndex, columnIndex);
					LOGGER.debug("selected flow:" + flow);

					resetComponents(editButton, deleteButton, textField, groupInFlowTextArea);

					try {
						FormInitializer.initFlowGroupTextArea(groupInFlowTextArea, flow);

					} catch (ServiceException | ServiceValidationException ex) {
						LOGGER.error(ex.getCause().getMessage(), ex);
						JOptionPane.showMessageDialog(getContentPane(), ex.getCause().getMessage());
					} finally {
						textField.setText(flow.getName());
						editButton.setVisible(true);
						deleteButton.setVisible(true);
					}
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

	private void resetComponents(JButton editButton, JButton deleteButton, JTextField textField,
			JTextArea groupInFlowTextArea) {
		editButton.setVisible(false);
		deleteButton.setVisible(false);
		textField.setText("");
		groupInFlowTextArea.setVisible(false);
		groupInFlowTextArea.setText("");
	}

	private boolean isTableHasNotAlikeValue(JTable table, String nameFlow) {
		int colCount = table.getColumnCount();
		int rowCount = table.getRowCount();
		for (int column = 0; column < colCount; column++)
			for (int row = 0; row < rowCount; row++) {
				Flow value = (Flow) table.getValueAt(row, column);
				if (value.getName().equals(nameFlow)) {
					LOGGER.warn("try to dublicete nameFlow:" + nameFlow);
					JOptionPane.showMessageDialog(getContentPane(), "Поток с таким именем уже есть");
					return false;
				}
			}
		return true;
	}
}