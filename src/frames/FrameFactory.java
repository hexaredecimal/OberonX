package frames;

/**
 *
 * @author hexaredecimal
 */
public class FrameFactory {
	public static Frame getFrame(int flags) {
		if ((flags & Frame.Text) == Frame.Text) {
			return new TextFrame(
				(flags & Frame.Editable) == Frame.Editable, 
				(flags & Frame.Indent) == Frame.Indent 
			);
		}

		if ((flags & Frame.Img) == Frame.Img) {
			return new ImageFrame();
		}

		if ((flags & Frame.WebView) == Frame.WebView) {
			return new WebViewer();
		}
		
		if ((flags & Frame.CodeView) == Frame.CodeView) {
			return new CodeFrame((flags & Frame.Editable) == Frame.Editable);
		}

		if ((flags & Frame.Basic) == Frame.Basic) {
			return new BasicFrame();
		}
		
		System.out.println("TODO: Exhaust the list of frames");
		System.exit(0);
		return null;
	}
}
