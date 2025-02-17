package oberonui;

/**
 *
 * @author hexaredecimal
 */
public class FrameFactory {
	public static Frame getFrame(int flags) {
		if ((flags & Frame.Text) == Frame.Text) {
			return new TextFrame((flags & Frame.Editable) == Frame.Editable);
		}

		if ((flags & Frame.Img) == Frame.Img) {
			return new ImageFrame();
		}

		System.out.println("TODO: Exhaust the list of frames");
		System.exit(0);
		return null;
	}
}
