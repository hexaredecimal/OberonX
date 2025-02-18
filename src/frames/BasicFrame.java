package frames;

import elements.RenderPanel;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import oberonui.OberonUI;

/**
 *
 * @author hexaredecimal
 */
public class BasicFrame extends Frame implements ActionListener{

	private RenderPanel frame;
	public BasicFrame() {
		this.frame = new RenderPanel();
	}


	@Override
	public Component getCenterComponent() {
		return this.frame;
	}

	public void onUpdate(Consumer<Graphics> fx) {
		this.frame.onUpdate(fx);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		this.frame.repaint();
	}

	@Override
	public void processArgs(String... args) {
	}
}
