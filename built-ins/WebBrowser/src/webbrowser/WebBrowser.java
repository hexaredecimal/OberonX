package webbrowser;

import frames.Frame;
import frames.FrameFactory;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import oberonui.Window;

/**
 *
 * @author hexaredecimal
 */
public class WebBrowser extends Window {

	public WebBrowser() {
		super(FrameFactory.getFrame(Frame.Basic), "Web.app");

		var panel = ((Container) this.getFrame().getCenterComponent());
		panel.setLayout(new BorderLayout());

		JTextField address = new JTextField(40);
		SwingUtilities.invokeLater(() -> {
			// Create JFXPanel to hold JavaFX content
			JFXPanel jfxPanel = new JFXPanel();
			panel.add(jfxPanel, BorderLayout.CENTER);

			// Initialize JavaFX content on JavaFX thread
			Platform.runLater(() -> initWebView(jfxPanel, address));

		});

		panel.add(address, BorderLayout.NORTH);
	}

	private void initWebView(JFXPanel jfxPanel, JTextField address) {
		WebView webView = new WebView();
		synchronized (WebBrowser.class) {
		WebEngine webEngine = webView.getEngine();
			// Load a webpage
			String url = "https://google.com";
			webEngine.load(url);
			address.setText(url);
			address.addKeyListener(new KeyListener() {
				@Override
				public void keyTyped(KeyEvent ke) {
				}

				@Override
				public void keyPressed(KeyEvent ke) {
				}

				@Override
				public void keyReleased(KeyEvent ke) {
					if (ke.getKeyCode() == KeyEvent.VK_ENTER) {
						var new_url = address.getText();
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

}
