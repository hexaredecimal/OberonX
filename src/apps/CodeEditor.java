package apps;

import frames.Frame;
import frames.FrameFactory;
import frames.ImageFrame;
import oberonui.Window;

/**
 *
 * @author hexaredecimal
 */
public class CodeEditor extends Window {
	public CodeEditor() {
		super(FrameFactory.getFrame(Frame.CodeView | Frame.Editable), "Code.app");
	}

	@Override
	public void handleCommand(String command, String[] args) {
		System.out.println("CodeEditor: " + command);
		super.handleCommand(command, args);
	}

	@Override
	public void processArgs(String ... args) {
		super.getFrame().processArgs(args);
	}
	
	@Override
	public String[] getCommands() {
		return new String[]{ "Code.write", "Code.open"};
	}
}
