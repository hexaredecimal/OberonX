package elements;

import java.awt.Color;
import java.awt.Graphics;
import java.util.function.Consumer;
import javax.swing.JPanel;
import oberonui.OberonUI;

/**
 *
 * @author hexaredecimal
 */
public class RenderPanel extends JPanel {
	private Consumer<Graphics> update; 
	public RenderPanel() {
		super();
		this.setBackground(Color.WHITE);
		this.update = null;
		this.addMouseListener(new OberonUI.CommandClickListener());
	}

	public void onUpdate(Consumer<Graphics> update) {
		this.update = update;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (this.update != null) {
			this.update.accept(g);
		}
	}
}
