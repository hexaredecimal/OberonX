package frames;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.concurrent.atomic.AtomicReference;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.event.EventType;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class WebViewer extends Frame {

	private JTextField address_;
	// private volatile WebEngine webEngine;
	private JPanel frame;
	private WebEngine webEngine;

	public WebViewer() {
		frame = new JPanel(new BorderLayout());

		address_ = new JTextField(40);
		SwingUtilities.invokeLater(() -> {
			// Create JFXPanel to hold JavaFX content
			JFXPanel jfxPanel = new JFXPanel();
			frame.add(jfxPanel, BorderLayout.CENTER);

			// Initialize JavaFX content on JavaFX thread
			Platform.runLater(() -> initWebView(jfxPanel));

		});
		
		frame.add(address_, BorderLayout.NORTH);
		// frame.add(browserUI_, BorderLayout.CENTER);
	}

	private void initWebView(JFXPanel jfxPanel) {
		WebView webView = new WebView();
		synchronized (WebViewer.class) {
			webEngine  = webView.getEngine();
			// Load a webpage
			String url = "https://google.com";
			webEngine.load(url);
			address_.setText(url);
			address_.addKeyListener(new KeyListener() {
				@Override
				public void keyTyped(KeyEvent ke) {
				}

				@Override
				public void keyPressed(KeyEvent ke) {
				}

				@Override
				public void keyReleased(KeyEvent ke) {
					if (ke.getKeyCode() == KeyEvent.VK_ENTER) {
						var new_url = address_.getText();
						Platform.runLater(() -> {
							webEngine.load(new_url);
						});
					}
				}
			});
		}
		// Create a JavaFX scene and set it on JFXPanel
		Scene scene = new Scene(webView);
		jfxPanel.setScene(scene);
	}
	
	public void loadUrl(String url) {
		synchronized (WebViewer.class) {
		}
	}

	@Override
	public Component getCenterComponent() {
		return frame;
	}

	@Override
	public void processArgs(String... args) {
	}
}
