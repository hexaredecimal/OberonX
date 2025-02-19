package digitalclock;

/**
 *
 * @author hexaredecimal
 */

import frames.BasicFrame;
import frames.Frame;
import frames.FrameFactory;
import java.awt.Container;
import java.awt.Font;
import java.util.Calendar;
import javax.swing.JLabel;
import javax.swing.Timer;
import oberonui.Window;

/**
 *
 * @author hexaredecimal
 */
public class DigitalClock extends Window {
	
	private BasicFrame frame;
	public DigitalClock() {
		super(FrameFactory.getFrame(Frame.Basic), "DigitalClock.app");
		frame = (BasicFrame) this.getFrame();
		var comp = (Container) frame.getCenterComponent();

		var calender = Calendar.getInstance();
		
		var _h = calender.get(Calendar.HOUR_OF_DAY);
		var _m = calender.get(Calendar.MINUTE);
		var _s = calender.get(Calendar.SECOND);
		
		var hour = _h < 10 ? "0" + _h : "" + _h;
		var min = _m < 10 ? "0" + _m : "" + _m;
		var sec = _s < 10 ? "0" + _s : "" + _s;

		var amPm = _h < 12 ? "AM" : "PM";
		JLabel time = new JLabel(String.format("%s:%s:%s %s", hour, min, sec, amPm));
		var font = time.getFont(); 
		time.setFont(new Font(font.getName(), font.getStyle(), 20));
		comp.add(time);

		Timer t = new Timer(1000, frame);
		t.start();

		frame.onUpdate((gfx) -> {
			var c = Calendar.getInstance();
			var  h = c.get(Calendar.HOUR_OF_DAY);
			var  m = c.get(Calendar.MINUTE);
			var  s = c.get(Calendar.SECOND);
			var _hour = h < 10 ? "0" + h : "" + h;
			var _min = m < 10 ? "0" + m : "" + m;
			var _sec = s < 10 ? "0" + s : "" + s;
			var _amPm = h < 12 ? "AM" : "PM";
			time.setText(String.format("%s:%s:%s %s", _hour, _min, _sec, _amPm));
		});
	}


	@Override
	public void handleCommand(String command, String[] args) {
		super.handleCommand(command, args);
	}

	@Override
	public void processArgs(String ... args) {
		super.processArgs(args);
	}

	@Override
	public String[] getCommands() {
		return new String[]{};
	}

}

