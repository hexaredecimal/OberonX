package oberonui;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 *
 * @author hexaredecimal
 */
public class Window extends JPanel {
	private Frame frame;
	private JPanel column;
	private String name; 

	public Window(Frame frame, String name) {
		super(new BorderLayout());
		this.frame = frame;
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		this.add(frame.getCenterComponent(), BorderLayout.CENTER);
		this.frame.setParent(this);
		this.name = name;
	}

	public void handleCommand(String command, String[] args) {
		switch (command) {
			case "Edit.open": {
				if (args.length == 0) {
					OberonUI.addTiledFrame("Edit.app", Frame.Text | Frame.Editable, "Editor.save", "Editor.open");
				} else {
					for (var arg : args) {
						var frame = (TextFrame) OberonUI.addTiledFrame("Edit.app", Frame.Text | Frame.Editable, "Editor.save", "Editor.open");
						frame.loadFile(arg);
					}
				}
			}
			break;
			case "Image.show": {
				if (args.length == 0) {
					OberonUI.addTiledFrame("Image.app", Frame.Img, "Image.prev", "Image.next");
				} else {
					for (var arg : args) {
						var frame = (ImageFrame) OberonUI.addTiledFrame("Image.app", Frame.Img, "Image.prev", "Image.next");
						frame.loadImage(arg);
					}
				}
			}
			break;
		}
	}

	public final void setColumn(JPanel lastColumn) {
		this.column = lastColumn;
	}

	public final JPanel getColumn() {
		return column;
	}

	public final String getName() {
		return name;
	}
	
}
