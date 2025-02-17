package oberonui;

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

/**
 *
 * @author hexaredecimal
 */
public class TextFrame extends Frame{
	private JTextArea textArea ;
	public TextFrame(boolean is_edit) {
		textArea = new JTextArea();
		textArea.setEditable(is_edit);
		var c = new OberonUI.CommandClickListener();
		textArea.addMouseListener(c);
	}

	public void loadFile(String path) {
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
		return new JScrollPane(textArea);
	}
}
