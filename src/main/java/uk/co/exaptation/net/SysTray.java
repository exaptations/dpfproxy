package uk.co.exaptation.net;

import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.Panel;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.Icon;
import javax.swing.JOptionPane;
import javax.swing.plaf.metal.MetalIconFactory;

import com.jcraft.jsch.Session;

public class SysTray {

	private Session session;
	private TrayIcon icon;

	public SysTray(Session session) {
		this.session = session;
		try {
			installSystemTray();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void installSystemTray() throws Exception {
		PopupMenu menu = new PopupMenu();
		MenuItem exit = new MenuItem("Exit");
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				session.disconnect();
				System.exit(0);
			}
		});
		menu.add(exit);

		icon = new TrayIcon(getImage(), "Java application as a tray icon", menu);
		SystemTray.getSystemTray().add(icon);
	}

	private Image getImage() throws HeadlessException {
		Icon defaultIcon = MetalIconFactory.getTreeHardDriveIcon();
		Image img = new BufferedImage(defaultIcon.getIconWidth(), defaultIcon.getIconHeight(), BufferedImage.TYPE_4BYTE_ABGR);
		defaultIcon.paintIcon(new Panel(), img.getGraphics(), 0, 0);
		return img;
	}

	public void notifyTrayyMessage(String message) {
		icon.displayMessage("Proxy Info", message, TrayIcon.MessageType.INFO);
	}

}