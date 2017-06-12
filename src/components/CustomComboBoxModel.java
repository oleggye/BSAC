package components;

import java.util.List;
import javax.swing.DefaultComboBoxModel;

/**
 * Wrapper for {@link #DefaultComboBoxModel<String>} and instances of
 * {@link #IName}
 *
 * @param <E>
 */

public class CustomComboBoxModel<E> extends DefaultComboBoxModel<String> {

	private static final long serialVersionUID = 1L;
	
	public CustomComboBoxModel(List<? extends IName> list) {
		for (IName elem : list) {
			super.addElement(elem.getName());
		}
	}
}