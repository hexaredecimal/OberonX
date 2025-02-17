package apps;

import frames.Frame;
import frames.FrameFactory;
import frames.ImageFrame;
import oberonui.Window;

/**
 *
 * @author hexaredecimal
 */
public class Editor extends Window {
	public Editor() {
		super(FrameFactory.getFrame(Frame.Text | Frame.Editable), "Edit.app");
	}

	@Override
	public void handleCommand(String command, String[] args) {
		System.out.println("Editor: " + command);
	}

	@Override
	public void processArgs(String ... args) {
		super.getFrame().processArgs(args);
	}
	
	@Override
	public String[] getCommands() {
		return new String[]{"Editor.write", "Editor.open"};
	}
}
