package oberonui;

import frames.Frame;
import apps.SystemLog;
import dynamics.JarFileLoader;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
		if (OberonUI.hasAlias(command)) {
			execFromAlias(OberonUI.getAlias(command), args);
			return;
		}

		
		switch (command) {
			case "System.exit":
				System.exit(0);
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

	private void execFromAlias(String alias, String[] args) {
		var splits = alias.replaceAll("\"", "").split(" ");
		var command = splits[0]; 

		List<String> _args = new ArrayList<>();
		for (int i = 1; i < splits.length; i++) {
			_args.add(splits[i]);
		}

		_args.addAll(Arrays.asList(args));

		args = _args.toArray(String[]::new);
		System.out.println("Alias: " + command + " " + _args);
		handleCommand(command, args);
	}

}
