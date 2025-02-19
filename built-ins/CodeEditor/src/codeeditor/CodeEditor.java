package codeeditor;

import frames.BasicFrame;
import frames.Frame;
import frames.FrameFactory;
import java.awt.BorderLayout;
import java.awt.Container;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import oberonui.OberonUI;
import oberonui.Window;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;

/**
 *
 * @author hexaredecimal
 */


public class CodeEditor extends Window {

	private RSyntaxTextArea textArea ;
	private RTextScrollPane editor_scroll;

	public CodeEditor() {
		super(FrameFactory.getFrame(Frame.Basic), "Code.app");
		
		textArea = new RSyntaxTextArea();
		var c = new OberonUI.CommandClickListener();
		textArea.addMouseListener(c);
		editor_scroll = new RTextScrollPane(textArea);
		editor_scroll.setLineNumbersEnabled(true); // Line numbers are enabled by default
		editor_scroll.setFoldIndicatorEnabled(true);
		editor_scroll.setIconRowHeaderEnabled(true);
		textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);

		var frame = (BasicFrame) this.getFrame();
		var cont = (Container) frame.getCenterComponent();
		cont.setLayout(new BorderLayout());
		cont.add(editor_scroll, BorderLayout.CENTER);
	}

	private void loadFile(String path) {
		StringBuilder content = new StringBuilder();
		
		if (path.startsWith("~")) {
			String home = System.getProperty("user.home");
			path = home + path.substring(1);
		}
		

		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
			String line;
			while ((line = br.readLine()) != null) {
				content.append(line).append(System.lineSeparator());
			}
			textArea.setText(content.toString());
		} catch (IOException e) {
			textArea.setText("Unable to open file: " + e.getMessage());
		}
	}

	
	@Override
	public void handleCommand(String command, String[] args) {
		System.out.println("CodeEditor: " + command);
		super.handleCommand(command, args);
	}
	
	@Override
	public void processArgs(String... args) {
		for (var arg: args) {
			this.loadFile(arg);
		}
	}

	@Override
	public String[] getCommands() {
		return new String[]{ "Code.write", "Code.open"};
	}
}
