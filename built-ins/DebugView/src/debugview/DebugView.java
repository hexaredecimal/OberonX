package debugview;

/**
 *
 * @author hexaredecimal
 */

import frames.BasicFrame;
import frames.Frame;
import frames.FrameFactory;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.util.Calendar;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.Timer;
import oberonui.Window;

/**
 *
 * @author hexaredecimal
 */
public class DebugView extends Window {

	private BasicFrame frame;

	public DebugView() {
		super(FrameFactory.getFrame(Frame.Basic), "DebugView.app");
		frame = (BasicFrame) this.getFrame();
		var comp = (Container) frame.getCenterComponent();

		var lbl = new JLabel();

		var debug_lbl = new JTextArea(debugText(comp));
		debug_lbl.setEditable(false);
		comp.add(debug_lbl);
		Timer t = new Timer(1000, frame);
		t.start();

		frame.onUpdate((gfx) -> {
			debug_lbl.setText(debugText(comp));
		});
	}

	private String debugText(Component comp) {
		var dim = comp.getSize();

		Runtime runtime = Runtime.getRuntime();

		long totalMemory = runtime.totalMemory(); // Total memory allocated to JVM
		long freeMemory = runtime.freeMemory();   // Free memory within the JVM
		long usedMemory = totalMemory - freeMemory; // Memory currently in use
		long maxMemory = runtime.maxMemory(); // Maximum memory JVM can use

		var total_used = usedMemory / (1024 * 1024);
		var total_free = freeMemory / (1024 * 1024);
		var total_mem = totalMemory / (1024 * 1024);
		var total_max = maxMemory / (1024 * 1024); 

		int barSize = 50; // Width of the progress bar
		int fill = (int) ((usedMemory / (double) totalMemory) * barSize);
		int space = barSize - fill; // Remaining space in the bar

		String bar = String.format("[%s%s] %.2f%% Used",
			"#".repeat(fill),
			" ".repeat(space),
			(usedMemory / (double) totalMemory) * 100
		);		

		
		return String.format(
			"""
   	App Stats:
   	App name: %s
   	Width: %f
   	Height: %f
   	
   	System Memory Usage:
   	Used  Memory (by OberonX): %d MB
   	Free  Memory (in the JVM): %d MB
   	Total Memory (in the JVM): %d MB
   	Max   Memory (in the JVM): %d MB
		\t%s
    	""",
			this.getName(), dim.getWidth(), dim.getHeight(),
			total_used, total_free, total_mem, total_max, bar
		);
	}

	@Override
	public void handleCommand(String command, String[] args) {
		super.handleCommand(command, args);
	}

	@Override
	public void processArgs(String... args) {
		super.processArgs(args);
	}

	@Override
	public String[] getCommands() {
		return new String[]{};
	}

}

