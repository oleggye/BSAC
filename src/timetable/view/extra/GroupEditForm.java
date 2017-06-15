package timetable.view.extra;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
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
import javax.swing.ImageIcon;

public class GroupEditForm extends JDialog {
	private static final long serialVersionUID = 1L;

	private JFrame parent;

	/**
	 * Launch the application.
	 */
	public static void main() {
		try {
			GroupEditForm dialog = new GroupEditForm(null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			Logger.getLogger(ServiceEditFormTest.class.getName()).log(Level.SEVERE, null, e);
		}
	}

	public GroupEditForm(JFrame parent) {

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

		JLabel lblNewLabel = new JLabel("Факультет");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));

		lblNewLabel.setBounds(334, 35, 78, 18);
		contentPanel.add(lblNewLabel);

		JLabel label = new JLabel("Редактирование/Добавление");
		label.setFont(new Font("Tahoma", Font.BOLD, 14));
		label.setBounds(334, 90, 190, 18);
		contentPanel.add(label);

		JButton editButton = new JButton("Изменить");
		editButton.setFont(new Font("Tahoma", Font.PLAIN, 14));

		facultyComboBox.setBounds(334, 56, 234, 23);
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

		textField.setBounds(334, 112, 191, 23);
		contentPanel.add(textField);

		textField.setColumns(10);

		editButton.setVisible(false);
		editButton.setBounds(334, 235, 100, 23);
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
		button2.setFont(new Font("Tahoma", Font.PLAIN, 14));

		button2.setBounds(466, 235, 95, 23);
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

		JLabel label_1 = new JLabel("Поток");
		label_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		label_1.setBounds(334, 146, 46, 14);
		contentPanel.add(label_1);

		JButton button = new JButton("Удалить");
		button.setFont(new Font("Tahoma", Font.PLAIN, 14));
		button.setBounds(403, 269, 100, 23);
		contentPanel.add(button);

		JButton addFlowbutton = new JButton("");

		ImageIcon icon = new ImageIcon("C:\\Users\\hello\\Desktop\\add-icon.png");
		Image img = icon.getImage();
		Image newimg = img.getScaledInstance(37, 23, java.awt.Image.SCALE_SMOOTH);
		icon = new ImageIcon(newimg);

		addFlowbutton.setIcon(icon);
		addFlowbutton.setBounds(434, 167, 37, 23);
		addFlowbutton.setPreferredSize(new Dimension(16, 16));
		contentPanel.add(addFlowbutton);

		JButton changeFlowButton = new JButton("-");
		changeFlowButton.setBounds(513, 167, 37, 23);
		contentPanel.add(changeFlowButton);

		JLabel lblNewLabel_1 = new JLabel("Не задан");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_1.setBounds(334, 171, 78, 14);
		contentPanel.add(lblNewLabel_1);

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
	}
}