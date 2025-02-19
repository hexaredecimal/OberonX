package apps;

import frames.Frame;
import frames.FrameFactory;
import oberonui.Window;

/**
 *
 * @author hexaredecimal
 */
public class SystemLog extends Window {
	public SystemLog() {
		super(FrameFactory.getFrame(Frame.Text), "System.log");
	}

	@Override
	public void handleCommand(String command, String[] args) {
		super.handleCommand(command, args);
	}

	@Override
	public void processArgs(String ... args) {
		super.getFrame().processArgs(args);
	}
	
	@Override
	public String[] getCommands() {
		return new String[]{};
	}
}
