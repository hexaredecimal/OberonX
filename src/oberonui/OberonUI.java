package oberonui;

/**
 *
 * @author hexaredecimal
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.TextArea;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class OberonUI {

	private static JFrame frame;
	private static JPanel columnContainer;
	private static int columnCount = 2;
	private static Window selectedFrame = null;

	public OberonUI() {
		frame = new JFrame("Oberon UI in Java");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.setBackground(Color.WHITE);

		frame.setSize(1200, 600);
		//frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		//frame.setUndecorated(true);

		// Container for columns
		columnContainer = new JPanel(new GridLayout(1, columnCount));
		columnContainer.setBackground(Color.WHITE);
		for (int i = 0; i < columnCount; i++) {
			columnContainer.add(createColumn());
		}

		//frame.add(new JScrollPane(columnContainer), BorderLayout.CENTER);
		frame.add(columnContainer, BorderLayout.CENTER);
		frame.setVisible(true);
	}

	private JPanel createColumn() {
		JPanel column = new JPanel();
		column.setLayout(new BoxLayout(column, BoxLayout.Y_AXIS));
		column.setBorder(BorderFactory.createLineBorder(Color.BLACK, 0, false));
		column.setBackground(Color.WHITE);
		column.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (selectedFrame != null && SwingUtilities.isLeftMouseButton(e)) {
					selectedFrame.setColumn(columnContainer);
					moveFrameToColumn(column);
				}
			}
		});
		return column;
	}

	public static Frame addTiledFrame(String title, int flags, String... commands) {
		// Header with commands
		JPanel headerPanel = new JPanel();
		headerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		// headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.X_AXIS)); // Use BoxLayout for horizontal arrangement
		headerPanel.setBackground(Color.BLACK);
		var cmds = makeCommands(title, commands);
		for (String command : cmds) {

			if (command.equals("_")) {
				JLabel sep_lbl = new JLabel("| ");
				sep_lbl.setForeground(Color.WHITE);
				headerPanel.add(sep_lbl);
				continue;
			}

			JLabel commandLabel = new JLabel(command + "  ");
			var fnt = commandLabel.getFont();
			fnt = new Font(fnt.getFontName(), fnt.getStyle(), 9);
			commandLabel.setFont(fnt);
			commandLabel.setForeground(Color.WHITE);
			commandLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
			headerPanel.add(commandLabel);
			commandLabel.addMouseListener(new CommandClickListener());
		}

		var comp = FrameFactory.getFrame(flags);
		Window win = new Window(comp, cmds[0]);
		win.add(headerPanel, BorderLayout.NORTH);

		win.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (SwingUtilities.isMiddleMouseButton(e)) {
					selectedFrame = win;
					return;
				}

				if (selectedFrame != null && SwingUtilities.isLeftMouseButton(e)) {
					//moveFrameToColumn(win.getColumn());
					System.out.println("HERE: " + win.getName());
				}
			}
		});

		JPanel lastColumn = (JPanel) columnContainer.getComponent(columnCount - 1);
		win.setColumn(lastColumn);
		lastColumn.add(win);
		lastColumn.revalidate();
		lastColumn.repaint();

		return comp;
	}

	private static void closeFrame(Window framePanel) {
		Container parent = framePanel.getParent();
		if (parent != null) {
			parent.remove(framePanel);
			parent.revalidate();
			parent.repaint();
		}
	}

	private static void moveFrameToColumn(JPanel targetColumn) {
		if (selectedFrame != null) {
			Container parent = selectedFrame.getParent();

			if (parent != null) {
				parent.remove(selectedFrame); // Remove from old column
			}

			targetColumn.add(selectedFrame); // Add to new column
			selectedFrame.setColumn(targetColumn); // Update column reference

			targetColumn.revalidate();
			targetColumn.repaint();
			selectedFrame.setPreferredSize(targetColumn.getSize());

			if (parent != null) {
				parent.revalidate();
				parent.repaint();
			}

			selectedFrame = null; // Reset selection
		}
	}


	private static String[] makeCommands(String title, String... commands) {
		var list = new ArrayList<String>(List.of(title, "_", "System.close", "System.copy", "System.grow"));
		if (commands.length > 0) {
			list.add("_");
		}

		for (var cmd : commands) {
			list.add(cmd);
		}
		return list.toArray(String[]::new);
	}

	private static void processClickedCommand(String command, Window parent) {
		String[] cmds = command.split(" ");
		var args = cmds.length > 1 ? Arrays.copyOfRange(cmds, 1, cmds.length) : new String[]{};
		String cmd = cmds[0].trim();
		if (cmd.equals("System.close")) {
			closeFrame(parent);
		} else {
			executeCommand(cmd, args, parent);
		}
	}

	private static void executeCommand(String command, String[] args, Window parent) {
		parent.handleCommand(command, args);
	}

	public static Window getMouseEventParent(MouseEvent e) {
			var obj = (Component) e.getSource();
			Component parent = obj.getParent();
			while (parent != null && !(parent instanceof Window)) {
				parent = parent.getParent();
			}
			return (Window) parent;
	}

	public static class CommandClickListener extends MouseAdapter {

		@Override
		public void mousePressed(MouseEvent e) {

			var parent = getMouseEventParent(e);
			if (selectedFrame != null && SwingUtilities.isLeftMouseButton(e)) {
				moveFrameToColumn(parent.getColumn());
				System.out.println("HERE: CommandClick: " + parent.getName());
				return;
			}

			if (!SwingUtilities.isMiddleMouseButton(e)) {
				return;
			}


			var source = e.getSource();
			e.consume();

			if (source instanceof JLabel lbl) {
				processClickedCommand(lbl.getText().trim(), (Window) parent);
			} else if (source instanceof JTextArea textArea) {
				int x = e.getX();
				int y = e.getY();
				int caretPosition = textArea.viewToModel(new Point(x,y));
				String text = textArea.getText();
				String clickedWord = getWordAtPosition(text, caretPosition);
				processClickedCommand(clickedWord, (Window) parent);
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			if (SwingUtilities.isMiddleMouseButton(e)) {
				e.consume(); // Prevent default action
			}
		}

		private String getWordAtPosition(String text, int pos) {
			if (text.isEmpty() || pos < 0 || pos >= text.length()) {
				return "";
			}
			int start = pos, end = pos;
			while (start > 0
				&& (Character.isLetterOrDigit(text.charAt(start - 1))
				|| (text.charAt(start - 1) == '.')
				|| (text.charAt(start - 1) == '/')
				|| (text.charAt(start - 1) == '~')
				|| (text.charAt(start - 1) == ':')
				|| (text.charAt(start - 1) == ' '))) {
				start--;
			}
			while (end < text.length()
				&& (Character.isLetterOrDigit(text.charAt(end))
				|| (text.charAt(end) == '.'))
				|| (text.charAt(end) == ' ')
				|| (text.charAt(end) == '/')
				|| (text.charAt(end) == ':')
				|| (text.charAt(end) == '~')) {
				end++;
			}
			return text.substring(start, end).trim();
		}
	
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			OberonUI ui = new OberonUI();
			var frame = (TextFrame) OberonUI.addTiledFrame("System.log", Frame.Text);
			var te = frame.getTextArea();
			te.setText(
				"""
    
  Edit.open 
  Edit.open ./Welcome.txt
  Edit.open ~/sam.save

  Image.show
  Image.show ~/Pictures/ThunkPow.jpg 
  Image.show ~/Pictures/Odin.png

  Code.edit
  Code.edit Main.java

  Web.browse https://google.com
      """);
		});
	}
}
