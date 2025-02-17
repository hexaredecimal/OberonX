package apps;

import frames.Frame;
import frames.FrameFactory;
import frames.ImageFrame;
import oberonui.Window;

/**
 *
 * @author hexaredecimal
 */
public class ImageViewer extends Window {
	public ImageViewer() {
		super(FrameFactory.getFrame(Frame.Img), "Image.app");
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
