package components;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class OneColumnTableCellRenderer extends DefaultTableCellRenderer {

	@Override
	protected void setValue(Object value) {
		String printValue = null;
		if (value instanceof IName) {
			printValue = ((IName) value).getName();
		} else {
			printValue = value.toString();
		}
		super.setValue(printValue);
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
	}
}
