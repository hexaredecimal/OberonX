package apps;

import frames.Frame;
import frames.FrameFactory;
import oberonui.Window;

/**
 *
 * @author hexaredecimal
 */
public class WebBrowser extends Window {
	public WebBrowser() {
		super(FrameFactory.getFrame(Frame.WebView), "Web.app");
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
		return new String[]{"Web.go", "Web.prev", "Web.forward"};
	}
}
