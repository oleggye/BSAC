package supportClasses;

import hibernateFiles.entity.Group;
import hibernateFiles.entity.Lecturer;
import tableClasses.TablesArray;
import tableClasses.ArrayPosition;
import hibernateFiles.entity.Record;
import hibernateFiles.entity.Subject;

import java.awt.Component;
import java.awt.Window;
import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import service.exception.ServiceException;
import service.factory.impl.ServiceFactory;
import timetable.view.MainForm;

public class SupportClass {

	private static final int LENGTHFORWORD = 10;
	private static final int LENGTHOFI = 1;

	public final static void setColumnsWidth(JTable table) {

		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		int prefWidth;

		JTableHeader th = table.getTableHeader();
		for (int i = 0; i < table.getColumnCount(); i++) {
			TableColumn column = table.getColumnModel().getColumn(i);
			int prefWidthMax = 0;
			for (int j = 0; j < table.getRowCount(); j++) {
				try {
					String s = table.getModel().getValueAt(j, i).toString();
					prefWidth = Math.round(
							(float) th.getFontMetrics(th.getFont()).getStringBounds(s, th.getGraphics()).getWidth());
					if (prefWidth > prefWidthMax) {
						prefWidthMax = prefWidth;
					}
				} catch (NullPointerException e) {
					continue;
				}

			}
			column.setPreferredWidth(prefWidthMax + 10);
		}
	}

	public final static ArrayPosition findArrayPositionOfTable(JTable table, JTable[][] tablesArray) {

		ArrayPosition result = null;
		for (int i = 0; i < tablesArray.length; i++) {
			for (int j = 0; j < tablesArray[i].length; j++) {
				if (tablesArray[i][j].equals(table)) {
					result = new ArrayPosition(i, j);
					i = tablesArray.length;
					break;
				}
			}
		}
		return result;
	}

	public final static ArrayPosition findArrayPositionOfTable(JTable table, TablesArray tablesArray) {

		ArrayPosition result = null;
		for (int i = 0; i < tablesArray.getWidth(); i++) {
			for (int j = 0; j < tablesArray.getHeight(); j++) {
				if (tablesArray.getElementAt(i, j).equals(table)) {
					result = new ArrayPosition(i, j);
					i = tablesArray.getWidth();
					break;
				}
			}
		}
		return result;
	}

	public static void formCentering(Window frame) {
		java.awt.Dimension dim = frame.getToolkit().getScreenSize();// получаем
																	// разрешение
																	// текущего
																	// экрана
		frame.setLocation(dim.width / 2 - frame.getWidth() / 2, dim.height / 2 - frame.getHeight() / 2);
	}

	public static List<Record> findMainRecordsByCriterias(List<Record> mainRecordsCollection, byte currentWeekDay,
			byte currentWeekNumber, byte currentSubjOrdinalNumber) {
		List<Record> tempArray = new LinkedList<>();

		for (Record f : mainRecordsCollection) {
			if ((f.getWeekDay() == currentWeekDay) && (f.getWeekNumber() == currentWeekNumber)
					&& (f.getSubjOrdinalNumber() == currentSubjOrdinalNumber)) {
				tempArray.add(f);
			}
		}
		return tempArray;
	}

	public static void setModelsForTables(List<Record> mainRecordsCollection, TablesArray tablesArray) {
		for (int i = 0; i < tablesArray.getWidth(); i++) {
			for (int j = 0; j < tablesArray.getHeight(); j++) {

				ArrayPosition arrPos = new ArrayPosition(i, j);// позиция
																// таблицы в
																// двумерном
																// массиве
				tablesArray.getElementAt(i, j).setModelForTable(mainRecordsCollection, arrPos);

			}
		}
	}

	public static int[] getColsFromSubgroup(byte subgroup) {
		// т.к. в таблице индексы с 0

		/*
		 * начало костыля!!!! т.к. в БД subGroup 1 - 1-ая подгруппа, 2 - 2-ая
		 * подгруппа, 3 - Вся группа, а в ComboBox item 0 - Вся группа, 1 - 1-ая
		 * подгруппа, 3 - 2-ая подгруппа, то делаем проверку и замену.
		 */
		/*
		 * было до правки 18.10.2016 switch (subgroup) { case 1: return new
		 * int[]{1, 2}; case 2: return new int[]{1}; case 3: return new
		 * int[]{2}; default: return new int[]{1}; }
		 */
		switch (subgroup) {
		case 1:
			return new int[] { 1 };
		case 2:
			return new int[] { 2 };
		case 3:
			return new int[] { 1, 2 };
		default:
			return new int[] { 1 };
		}
		/* конец костыля!!!! */

	}

	public static JCheckBox getCheckBoxByWeekDay(byte currentWeekNumber, JCheckBox[] checkBoxes) {
		switch (currentWeekNumber) {
		case 1:
			return checkBoxes[0];
		case 2:
			return checkBoxes[1];
		case 3:
			return checkBoxes[2];
		case 4:
			return checkBoxes[3];
		default:
			return null;

		}
	}

	public static boolean checkMainRecordBeforeAdd(List<Record> mainRecordsCollection, Record mainRecord) {

		byte mainRecSubGroupValue = mainRecord.getSubjectFor().getId();
		byte elRecSubGroupValue;

		for (Record element : mainRecordsCollection) {
			if ((element.getWeekDay() == mainRecord.getWeekDay())
					&& (element.getWeekNumber() == element.getWeekNumber())
					&& (element.getSubjOrdinalNumber() == mainRecord.getSubjOrdinalNumber())) {

				elRecSubGroupValue = element.getSubjectFor().getId();

				if ((elRecSubGroupValue == mainRecSubGroupValue)) {
					JOptionPane.showMessageDialog(null, "Данная запись конфликтует с существующей");
					return false;
				} // т.к. имеет место костыль, то 1 изменяеться на 3 (Вся
					// группа)
				else if (mainRecSubGroupValue == 3 || elRecSubGroupValue == 3) {
					JOptionPane.showMessageDialog(null, "Данная запись конфликтует с существующей");
					return false;
				}
			}
		}
		return true;
	}

	/* данный метод заменён обобщением */
	public static boolean checkSubjectsRecBeforeAdd(List<Subject> subjectsCollection, Subject subj) {
		for (Subject e : subjectsCollection) {
			if (e.getNameSubject().equals(subj.getNameSubject())) {
				JOptionPane.showMessageDialog(null, "Данная запись конфликтует с существующей");
				return false;
			}
		}

		return true;
	}

	/* данный метод заменён обобщением */
	public static boolean checkGroupsRecBeforeAdd(List<Group> groupsCollection, Group group) {
		for (Group e : groupsCollection) {
			if (e.getNameGroup().equals(group.getNameGroup())) {
				JOptionPane.showMessageDialog(null, "Данная запись конфликтует с существующей");
				return false;
			}
		}
		return true;
	}

	/* данный метод заменён обобщением */
	public static boolean checkLecturersRecBeforeAdd(List<Lecturer> lecturersCollection, Lecturer lect) {
		for (Lecturer e : lecturersCollection) {
			if (e.getNameLecturer().equals(lect.getNameLecturer())) {
				JOptionPane.showMessageDialog(null, "Данная запись конфликтует с существующей");
				return false;
			}
		}
		return true;
	}

	// полиморфизм
	/*
	 * public static boolean checkRecordBeforeAdd(ArrayList<MyInt> collection,
	 * MyInt element) { for (MyInt e : collection) { if
	 * (e.getName().equals(element.getName())) {
	 * JOptionPane.showMessageDialog(null,
	 * "Данная запись конфликтует с существующей"); return false; } } return
	 * true; }
	 */
	public static void setHorizontalAlignmentToTable(JTable table) {
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(JLabel.CENTER);
		table.getColumnModel().getColumn(0).setCellRenderer(rightRenderer);
	}

	public static JLabel getJLabelWithPicture(MainForm frame) throws IOException {
		return new JLabel(new ImageIcon(frame.getClass().getClassLoader().getResource("images/1.png")));
	}

	public static String makeAnAbbreviation(String name) {
		String[] wordsArr;// array for separated words from name
		String result = ""; // variable for result

		if (name != null) {
			if (name.length() <= LENGTHFORWORD) {//
				return name;
			} else {
				wordsArr = name.split(" ");
				for (String e : wordsArr) {
					if (!e.equals("")) {
						if (e.length() == LENGTHOFI) {// если это слово из 1
														// буквы, то добавляем
														// его в нижнем регистре
														// к аббривиатуре
							result = result.concat(String.valueOf(e.charAt(0)).toLowerCase());
						} else /*
								 * если это не 2 слова, написанные через знак
								 * "-"
								 */ if (!e.contains("-")) {
							result = result.concat(String.valueOf(e.charAt(0)).toUpperCase());
						} else {
							/*
							 * если это 2 слова разделенных через "-", то
							 * необходимо взять по первой букве из каждого
							 */
							String[] temp = e.split("-");
							for (String ee : temp) {
								result = result.concat(String.valueOf(ee.charAt(0)).toUpperCase());
							}
						}
					}
				}
				return result;
			}
		} else {
			return result;// when name is null
		}

	}

	// добавление аббривиатуры к названию дисциплины
	public static List<Subject> addAbrToCollection(List<Subject> subjCol) throws SQLException {

		final String defValue = "Unknown";
		for (int i = 0; i < subjCol.size(); i++) {
			if (subjCol.get(i).getAbnameSubject().equals(defValue)) {
				subjCol.get(i).setAbnameSubject(SupportClass.makeAnAbbreviation(subjCol.get(i).getNameSubject()));
			}
			try {
				ServiceFactory.getInstance().getSubjectService().updateSubject(subjCol.get(i));
			} catch (ServiceException e) {
				e.printStackTrace();
			}
		}
		return subjCol;
	}
}