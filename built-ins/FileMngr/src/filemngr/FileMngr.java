package filemngr;

import frames.FrameFactory;
import frames.Frame;
import frames.TextFrame;
import java.io.File;
import java.util.Arrays;
import java.util.Comparator;
import javax.swing.JTextArea;
import oberonui.Window;

/**
 *
 * @author hexaredecimal
 */
public class FileMngr extends Window {

	private JTextArea text_area;
	private String root; 
	public FileMngr() {
		super(FrameFactory.getFrame(Frame.Text | Frame.Indent), "Dir.ls");

		var frame = (TextFrame) this.getFrame();
		text_area = frame.getTextArea();
		root = "";
    handleCommand("Dir.nav", new String[]{"."});
	}

	@Override
	public void handleCommand(String command, String[] args) {
		if (command.equals("Dir.nav") && args.length > 0) {
			var text = listPath(args[0]);
			this.text_area.setText(text);
		}
		super.handleCommand(command, args);
	}

	@Override
	public void processArgs(String... args) {
		for (var arg : args) {
			System.out.println("DIR: " + arg);
			var text = listPath(arg);
			this.text_area.setText(text);
		}
	}

	@Override
	public String[] getCommands() {
		return new String[]{};
	}


	private String listPath(String path) {
		if (path.startsWith("~")) {
			String home = System.getProperty("user.home");
			path = home + path.substring(1);
		}

		File fp = new File(path);
		if (fp.isFile()) {
			path = fp.getAbsolutePath();
			int last_slash = path.lastIndexOf("/");
			path = path.substring(0, last_slash).replace(".", "");
			return listPath(path);
		}

		if (path.equals("..")) {
			var rt = this.root += "/.."; 
			fp = new File(rt);
			this.root = fp.getParent();
			fp = new File(this.root);
			fp = fp.getParentFile();
			fp = new File(this.root);
			this.root = fp.getParent();
			fp = new File(this.root);
			System.out.println("root = " + this.root);
		} else {
			this.root = path;
		}
		StringBuilder sb = new StringBuilder();
		sb.append("Dir.nav ..\n");

		var files = fp.listFiles();
		Arrays.sort(files, Comparator.comparing(File::getName));
		
		for (var file : files) {
			if (file.isDirectory()) {
				sb.append("Dir.nav ").append(file.getAbsolutePath()).append("\n");
			} else {
				var file_path = file.getAbsolutePath();
				if (file_path.endsWith(".java")) {
					sb.append("Code.edit ").append(file.getAbsolutePath()).append("\n");
				} else if (isImage(file_path)) {
					sb.append("Image.show ").append(file.getAbsolutePath()).append("\n");
				} else {
					sb.append("Edit.open ").append(file.getAbsolutePath()).append("\n");
				}
			}
		}

		return sb.toString();
	}

	private boolean isImage(String path) {
		return path.endsWith(".png") 
      || path.endsWith(".jpg") 
      || path.endsWith(".bmp") 
      || path.endsWith(".gif") 
      || path.endsWith(".webp");
	}

}
