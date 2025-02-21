package codeeditor;

import frames.BasicFrame;
import frames.Frame;
import frames.FrameFactory;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
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

	private String file_path = null;
	private RSyntaxTextArea textArea;
	private RTextScrollPane editor_scroll;
	private JFileChooser file_chooser;
	private JLabel file_label;
	private JLabel status_label;
	
	public CodeEditor() {
		super(FrameFactory.getFrame(Frame.Basic), "Code.app");
		file_chooser = new JFileChooser();
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

		
		JPanel statusPanel = new JPanel();
		statusPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
		cont.add(statusPanel, BorderLayout.SOUTH);
		statusPanel.setPreferredSize(new Dimension(cont.getWidth(), 16));
		statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.X_AXIS));
		
		file_label = new JLabel("File: N/A");
		file_label.setHorizontalAlignment(SwingConstants.LEFT);
		statusPanel.add(file_label);

		statusPanel.add(new JSeparator(JSeparator.VERTICAL));
		
		status_label = new JLabel();
		status_label.setHorizontalAlignment(SwingConstants.LEFT);
		statusPanel.add(status_label);
	}

	private void loadFile(String path, boolean errout) {
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
			this.status_label.setText(content.toString().length() + " bytes read");
		} catch (IOException e) {
			if (errout) {
				textArea.setText(e.getMessage());
			}
		} finally {
			this.file_path = path;
			this.file_label.setText("File: " + path);
		}
	}

	@Override
	public void handleCommand(String command, String[] args) {
		if (command.equals("Code.write")) {
			operate(false);
		} else if (command.equals("Code.open")) {
			operate(true);
		}
		super.handleCommand(command, args);
	}

	@Override
	public void processArgs(String... args) {
		for (var arg : args) {
			this.loadFile(arg, false);
		}
	}

	@Override
	public String[] getCommands() {
		return new String[]{"Code.write", "Code.open"};
	}

	private void operate(boolean read) {
		File fp = null;
		if (this.file_path == null || read) {
			fp = chooseFile(read);
			if (fp == null) {
				return;
			}
		} else {
			fp = new File(this.file_path);
		}

		if (!read) {
			this.write(fp);
		}
		this.read(fp);
	}

	private void write(File fp) {

		try (FileWriter fw = new FileWriter(fp)) {
			fw.write(this.textArea.getText());
			this.status_label.setText(this.textArea.getText().length() + " bytes written");
		} catch (IOException ex) {
			Logger.getLogger(CodeEditor.class.getName()).log(Level.SEVERE, null, ex);
		}

	}

	private void read(File fp) {
		StringBuilder content = new StringBuilder();
		try (BufferedReader reader = new BufferedReader(new FileReader(fp.getAbsolutePath()))) {
			String line;
			while ((line = reader.readLine()) != null) {
				content.append(line).append("\n");
			}
			var text = String.valueOf(content.toString());
			this.status_label.setText(text.length() + " bytes read");
			this.textArea.setText(text);
			this.repaint();
		} catch (IOException ex) {
			Logger.getLogger(CodeEditor.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	private File chooseFile(boolean read) {
		int result = !read ? file_chooser.showSaveDialog(this) : file_chooser.showOpenDialog(this);
		if (result == JFileChooser.APPROVE_OPTION) {
			var selected = file_chooser.getSelectedFile();
			this.file_path = selected.getAbsolutePath();
			this.file_label.setText("File: " + this.file_path);
			return selected;
		}

		return null;
	}
}
