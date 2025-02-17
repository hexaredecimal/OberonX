package oberonui;

import frames.Frame;
import apps.CodeEditor;
import apps.Editor;
import apps.ImageViewer;
import apps.SystemLog;
import apps.WebBrowser;
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
				OberonUI
					.addTiledFrame(new Editor())
					.processArgs(args);
			}
			break;
			case "Image.show": {
				OberonUI
					.addTiledFrame(new ImageViewer())
					.processArgs(args);
			}
			break;
			case "Web.browse": {
				OberonUI
					.addTiledFrame(new WebBrowser())
					.processArgs(args);
			}
			break;
			case "Code.edit" : {
				OberonUI
					.addTiledFrame(new CodeEditor())
					.processArgs(args);
			}
			case "System.log": {
				OberonUI
					.addTiledFrame(new SystemLog())
					.processArgs(args);
			}
			break;
		}
	}

	public void processArgs(String ... args) {
		this.frame.processArgs(args);
	}

	public final void setColumn(JPanel lastColumn) {
		this.column = lastColumn;
	}

	public final JPanel getColumn() {
		return column;
	}

	public Frame getFrame() {
		return frame;
	}

	public final String getName() {
		return name;
	}

	public String[] getCommands() {
		return new String[]{};
	}

}
