package components;

import java.awt.event.MouseEvent;

import javax.swing.JComboBox;
import javax.swing.ListCellRenderer;

public class MyComboBox<E> extends JComboBox<E> {

	private static final long serialVersionUID = 1L;

	public MyComboBox() {
		this.setRenderer(new MyRenderer<>());
	}

	public MyComboBox(ListCellRenderer<E> renderer) {
		this.setRenderer(renderer);
	}
	
	@Override
	public String getToolTipText(MouseEvent e) {
		return this.getSelectedItem().toString();
	}
}