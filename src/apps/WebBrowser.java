package apps;

import frames.Frame;
import frames.FrameFactory;
import frames.ImageFrame;
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
		System.out.println("ImageView: " + command);
	}

	@Override
	public void processArgs(String ... args) {
		super.getFrame().processArgs(args);
	}
	
	@Override
	public String[] getCommands() {
		return new String[]{"Image.prev", "Image.next"};
	}
}
