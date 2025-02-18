package frames;

import java.awt.Color;
import java.awt.Component;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import oberonui.OberonUI;

/**
 *
 * @author hexaredecimal
 */
public class ImageFrame extends Frame {

	private JLabel label;

	public ImageFrame() {
		label = new JLabel();
		label.setBackground(Color.WHITE);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setVerticalAlignment(SwingConstants.CENTER);
		var c = new OberonUI.CommandClickListener();
		label.addMouseListener(c);
	}

	public ImageFrame(String path) {
		loadImage(path);
	}

	private void loadImage(String path) {

		if (path.startsWith("~")) {
			String home = System.getProperty("user.home");
			path = home + path.substring(1);
		}

		try {
			var img = ImageIO.read(new File(path));
			ImageIcon icon = new ImageIcon(img);
			int w = img.getWidth();
			int h = img.getHeight();
			if (w > 500 || h > 500) {
				w = w / 2;
				h = h / 2;
				var image = icon.getImage(); // transform it 
				var newimg = image.getScaledInstance(w, h, java.awt.Image.SCALE_SMOOTH);
				icon = new ImageIcon(newimg);
			}
			label.setIcon(icon);
		} catch (IOException ex) {
			Logger.getLogger(ImageFrame.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	@Override
	public Component getCenterComponent() {
		return new JScrollPane(label);
	}

	@Override
	public void processArgs(String... args) {
		for (var arg: args) {
			this.loadImage(arg);
		}
	}
}
