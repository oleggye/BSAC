package tableClasses;

import by.bsac.component.multiSpanCellTable.AttributiveCellTableModel;
import by.bsac.component.multiSpanCellTable.CellSpan;
import by.bsac.component.multiSpanCellTable.MultiSpanCellTable;
import by.bsac.timetable.hibernateFiles.entity.Record;

import java.awt.Font;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import supportClasses.SupportClass;
import supportClasses.TableTips;

/**
 *
 * @author hello
 */
public class MyMultiSpanCellTable extends MultiSpanCellTable {
	private static final long serialVersionUID = 1L;

	public MyMultiSpanCellTable() {
		super();
		this.setFont(new Font("Tahoma", Font.BOLD, 14));
	}

	public MyMultiSpanCellTable(AttributiveCellTableModel model) {
		super(model);
		this.setFont(new Font("Tahoma", Font.BOLD, 14));
	}

	// добавленный метод к изначальному
	public void changeTableModel(Record record) {
		int[] rows = new int[] { record.getSubjOrdinalNumber()
				- 1 }; /* т.к. в таблице индексы с 0 */
		int[] cols = SupportClass.getColsFromSubgroup(record.getSubjectFor().getId());

		AttributiveCellTableModel tableModel = (AttributiveCellTableModel) this
				.getModel();/* задаем переменную с моделью текущей таблицы */
		tableModel.setValueAt(record, rows[0], cols[0]);// задаем значение

		/*
		 * т.е. если нужно объединять ячейки, т.к. в качестве подгруппы
		 * "Вся группа"
		 */
		if (cols.length > 1) {

			final CellSpan cellAttribute = (CellSpan) tableModel.getCellAttribute();
			cellAttribute.combine(rows, cols);
			this.revalidate();
			this.repaint();
		}
	}

	public void setDefaultAttributiveCellTableModel() {
		AttributiveCellTableModel tableModel = new AttributiveCellTableModel(new Object[][] { { 1, null, null },
				{ 2, null, null }, { 3, null, null }, { 4, null, null }, { 5, null, null }, { 6, null, null }, },
				new String[] { "№", "1 подгруппа", "2 подгруппа" });
		this.setModel(tableModel);

		this.getColumnModel().getColumn(0).setMaxWidth(10);
	}

	public void setModelForTable(List<Record> mainRecordsCollection, ArrayPosition arrPosition) {

		this.setDefaultAttributiveCellTableModel();// задаем дефолтную модель
													// для таблицы
		for (Record e : mainRecordsCollection) {
			if (e.getWeekDay() == (arrPosition.getColIndex() + 1)
					&& e.getWeekNumber() == (arrPosition.getRowIndex() + 1)) {

				this.changeTableModel(e);
			}
		}
	}

	// public String getToolTipText(MouseEvent e) {//подсказки
	// String tip = null;
	// java.awt.Point p = e.getPoint();
	// int rowIndex = rowAtPoint(p);
	// int colIndex = columnAtPoint(p);
	// int realColumnIndex = convertColumnIndexToModel(colIndex);
	//
	// if (realColumnIndex == 1 || realColumnIndex == 2) {
	// tip = TableTips.getTextForTip(this, colIndex, rowIndex);
	// //tip = (String) getValueAt(rowIndex, colIndex);
	// }
	// return tip;
	// }
}
