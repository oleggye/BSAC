package timetable.view.extra;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import by.bsac.timetable.hibernateFiles.entity.Faculty;
import by.bsac.timetable.service.exception.ServiceException;
import components.MyComboBox;
import supportClasses.SupportClass;
import timetable.view.test.ServiceEditFormTest;
import timetable.view.util.FormInitializer;
import timetable.view.util.ServiceEditFormInitializer;

public class FacultyEditFormBlank extends JDialog {
	private static final long serialVersionUID = 1L;

	private JFrame parent;

	/**
	 * Launch the application.
	 */
	public static void main() {
		try {
			FacultyEditFormBlank dialog = new FacultyEditFormBlank(null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			Logger.getLogger(ServiceEditFormTest.class.getName()).log(Level.SEVERE, null, e);
		}
	}

	public FacultyEditFormBlank(JFrame parent) {

		JComboBox<Faculty> facultyComboBox = new MyComboBox<>();

		this.parent = parent;

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
					FormInitializer.initFacultyComboBox(facultyComboBox);
				} catch (ServiceException ex) {
					Logger.getLogger(ServiceEditFormTest.class.getName()).log(Level.SEVERE, null, ex);
					JOptionPane.showMessageDialog(getContentPane(), ex);
				}
			}
		});

		JLabel lblNewLabel = new JLabel("New label");

		lblNewLabel.setBounds(400, 20, 70, 14);
		contentPanel.add(lblNewLabel);

		JLabel label = new JLabel("Редактирование/Добавление");
		label.setBounds(345, 100, 190, 14);
		contentPanel.add(label);

		JButton editButton = new JButton("Изменить");

		// {
		// /**
		// *
		// */
		// private static final long serialVersionUID = 1L;
		//
		// @Override
		// public String getToolTipText(MouseEvent e) {
		//
		// return comboBox.getSelectedItem().toString();
		// }
		// };
		facultyComboBox.setToolTipText("");

		facultyComboBox.setBounds(300, 40, 255, 23);
		contentPanel.add(facultyComboBox);

		facultyComboBox.addItemListener(new java.awt.event.ItemListener() {
			@Override
			public void itemStateChanged(java.awt.event.ItemEvent evt) {
				try {
					// chooseTableByMode();
					editButton.setVisible(false);
					// FIXME: убрать!
					throw new ServiceException(null);
				} catch (ServiceException ex) {
					Logger.getLogger(ServiceEditFormTest.class.getName()).log(Level.SEVERE, null, ex);
					JOptionPane.showMessageDialog(getContentPane(), ex);
				}
			}
		});

		JTextField textField = new JTextField();

		textField.setBounds(325, 123, 220, 23);
		contentPanel.add(textField);

		textField.setColumns(10);

		JLabel abrLabel = new JLabel("Аббревиатура");
		abrLabel.setBounds(395, 200, 190, 14);
		contentPanel.add(abrLabel);
		abrLabel.setVisible(false);

		JTextField textFieldForAbbr = new JTextField();
		textFieldForAbbr.setBounds(370, 220, 130, 23);
		contentPanel.add(textFieldForAbbr);

		textFieldForAbbr.setColumns(10);
		textFieldForAbbr.setVisible(false);

		editButton.setVisible(false);
		editButton.setBounds(335, 169, 100, 23);
		contentPanel.add(editButton);

		editButton.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				try {
					editButton.setEnabled(false);
					// addOrUpdateRecord(ActionMode.Update);
					// chooseTableByMode();
					// FIXME: убрать!
					throw new ServiceException(null);
				} catch (ServiceException ex) {
					Logger.getLogger(ServiceEditFormTest.class.getName()).log(Level.SEVERE, null, ex);
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
						// addOrUpdateRecord(ActionMode.ADD);
						// chooseTableByMode();
						editButton.setVisible(false);
						// FIXME: убрать!
						throw new ServiceException(null);
					} catch (ServiceException ex) {
						Logger.getLogger(ServiceEditFormTest.class.getName()).log(Level.SEVERE, null, ex);
						JOptionPane.showMessageDialog(getContentPane(), ex);
					} finally {
						button2.setEnabled(true);
					}
				}
			}
		});

		JTable table = new JTable() {
			private static final long serialVersionUID = 1L;

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

		table.setModel(new javax.swing.table.DefaultTableModel(new Object[][] { {} }, new String[] {}) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			Class[] types = new Class[] { java.lang.String.class };
			boolean[] canEdit = new boolean[] { false };

			public Class getColumnClass(int columnIndex) {
				return types[columnIndex];
			}

			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return canEdit[columnIndex];
			}
		});
		table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		table.setFont(new Font("Verdana", Font.BOLD, 14));

		JScrollPane scrollPane = new JScrollPane(table);

		scrollPane.setBounds(25, 40, 259, 245);
		contentPanel.add(scrollPane);

		table.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				int selItemIndex = table.getSelectedRow();
				if (selItemIndex >= 0) {
					// initTextFieldBy();
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
}