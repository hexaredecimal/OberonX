package imageviewer;

import frames.Frame;
import frames.FrameFactory;
import java.awt.Color;
import java.awt.Container;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import oberonui.OberonUI;
import oberonui.Window;

/**
 *
 * @author hexaredecimal
 */
public class ImageViewer extends Window {
	private JLabel label;
	
	public ImageViewer() {
		super(FrameFactory.getFrame(Frame.Basic), "Image.app");
		label = new JLabel();
		label.setBackground(Color.WHITE);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setVerticalAlignment(SwingConstants.CENTER);
		var c = new OberonUI.CommandClickListener();
		label.addMouseListener(c);

		var scroll = new JScrollPane(label);
		var cont = (Container) (this.getFrame().getCenterComponent());
		cont.add(scroll);
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
			label.setText(ex.getMessage());
		}
	}

	@Override
	public void handleCommand(String command, String[] args) {
		System.out.println("ImageView: " + command);
		super.handleCommand(command, args);
	}

	@Override
	public void processArgs(String ... args) {
		
		for (var arg: args) {
			System.out.println("ImageViewer: Arg found : " + arg);
			loadImage(arg);
		}
	}
	
	@Override
	public String[] getCommands() {
		return new String[]{"Image.prev", "Image.next"};
	}

}
