package frames;

import java.awt.Component;
import java.awt.TextArea;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import oberonui.OberonUI;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;

/**
 *
 * @author hexaredecimal
 */
public class CodeFrame extends Frame{
	private RSyntaxTextArea textArea ;
	private RTextScrollPane editor_scroll;
	
	public CodeFrame(boolean is_edit) {
		textArea = new RSyntaxTextArea();
		textArea.setEditable(is_edit);
		var c = new OberonUI.CommandClickListener();
		textArea.addMouseListener(c);
		editor_scroll = new RTextScrollPane(textArea);
		editor_scroll.setLineNumbersEnabled(true); // Line numbers are enabled by default
		editor_scroll.setFoldIndicatorEnabled(true);
		editor_scroll.setIconRowHeaderEnabled(true);

		textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
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

	

	public JTextArea getTextArea() {
		return textArea;
	}
	
	@Override
	public Component getCenterComponent() {
		return editor_scroll;
	}

	@Override
	public void processArgs(String... args) {
		for (var arg: args) {
			this.loadFile(arg);
		}
	}
}
