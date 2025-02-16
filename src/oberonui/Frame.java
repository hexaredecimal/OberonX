package oberonui;

import java.awt.Component;

/**
 *
 * @author hexaredecimal
 */


public abstract class Frame {
	public static int Text = 1 << 0;
	public static int Img = 1 << 1;
	public static int Canvas = 1 << 2;
	public static int WebView = 1 << 3;
	public static int Editable = 1 << 3;
	
	private Component parent;

	public Component getParent() {
		return parent;
	}

	public void setParent(Component parent) {
		this.parent = parent;
	}
	
	public abstract Component getCenterComponent();
}
