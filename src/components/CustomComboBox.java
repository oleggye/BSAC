package components;

import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

public class CustomComboBox<E> extends JComboBox<E> {

	private static final long serialVersionUID = 1L;

	public void init(List<? extends IName> list) {
		DefaultComboBoxModel<E> model = new CustomComboBoxModel<>(list);
		setModel(model);
	}

	private class CustomComboBoxModel<E> extends DefaultComboBoxModel<E> {

		private static final long serialVersionUID = 1L;

		public CustomComboBoxModel(List<? extends IName> list) {
			for (IName elem : list) {
				super.addElement((E) elem.getName());
			}
		}
	}
}
