package oberonui;

import frames.Frame;
import apps.DebugView;
import apps.Editor;
import apps.DigitalClock;
import apps.SystemLog;
import apps.WebBrowser;
import dynamics.JarFileLoader;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
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
			case "Debug.show": {
				OberonUI
					.addTiledFrame(new DebugView())
					.processArgs(args);
			}
			break;
			case "DigitalClock.run": {
				OberonUI
					.addTiledFrame(new DigitalClock())
					.processArgs(args);
			}
			break;
			case "Edit.open": {
				OberonUI
					.addTiledFrame(new Editor())
					.processArgs(args);
			}
			break;
			case "Web.browse": {
				OberonUI
					.addTiledFrame(new WebBrowser())
					.processArgs(args);
			}
			break;
			case "System.log": {
				OberonUI
					.addTiledFrame(new SystemLog())
					.processArgs(args);
			}
			break;
			case "System.close": {
				closeFrame();
			}
			break;
			case "System.max": {
				OberonUI.toggleFullScreen(true);
			}
			case "System.min": {
				OberonUI.toggleFullScreen(false);
			}
			break;
			case "System.run": {
				JarFileLoader.loadAndRunJarFile(args);
			}
		}
	}

	
	private void closeFrame() {
		Container parent = this.getParent();
		if (parent != null) {
			parent.remove(this);
			parent.revalidate();
			parent.repaint();
		}
		System.gc();
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

	@Override
	public final String getName() {
		return name;
	}

	public String[] getCommands() {
		return new String[]{};
	}

}
