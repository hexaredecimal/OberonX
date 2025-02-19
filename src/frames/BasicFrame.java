package frames;

import elements.RenderPanel;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.Consumer;

/**
 *
 * @author hexaredecimal
 */
public class BasicFrame extends Frame implements ActionListener{

	private Component frame;
	public BasicFrame() {
		this.frame = new RenderPanel();
		((Container)this.frame).setLayout(new BorderLayout());
	}


	@Override
	public Component getCenterComponent() {
		return this.frame;
	}

	public void onUpdate(Consumer<Graphics> fx) {
		if (this.frame instanceof RenderPanel render) {
			render.onUpdate(fx);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		this.frame.repaint();
	}

	@Override
	public void processArgs(String... args) {
	}

	@Override
	public void setCenterComponent(Component c) {
		this.frame = c;
	}
}
