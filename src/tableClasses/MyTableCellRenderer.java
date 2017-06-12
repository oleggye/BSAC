package tableClasses;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import components.IName;

public class MyTableCellRenderer extends DefaultTableCellRenderer {

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {

		Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		if (value != null) {
			System.out.println();
		}

		// Object valueAt = table.getModel().getValueAt(row, column);
//
//		Object printedValue;
//		if (value instanceof IName) {
//			printedValue = ((IName) value).getName();
//		} else {
//			printedValue = value;
//		}
//		//table.getModel().setValueAt(printedValue, row, column);
//		component.sette
		

		return component;
	}

	@Override
	protected void setValue(Object value) {

		Object printedValue;
		if (value instanceof IName) {
			printedValue = ((IName) value).getName();
		} else {
			printedValue = value;
		}
		setText((printedValue == null) ? "" : printedValue.toString());
	}

}
