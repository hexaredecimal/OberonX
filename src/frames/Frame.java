package frames;

import java.awt.Component;

/**
 *
 * @author hexaredecimal
 */


public abstract class Frame {
	public static int Text = 1 << 0;
	public static int Canvas = 1 << 2;
	public static int WebView = 1 << 3;
	public static int Editable = 1 << 4;
	public static int Basic = 1 << 5;
	public static int Indent = 1 << 6;
	
	private Component parent;

	public Component getParent() {
		return parent;
	}

	public void setParent(Component parent) {
		this.parent = parent;
	}
	
	public abstract Component getCenterComponent();
	public abstract void setCenterComponent(Component c);
	public abstract void processArgs(String ... args);
}
