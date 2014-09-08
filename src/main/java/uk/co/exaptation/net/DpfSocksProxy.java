package uk.co.exaptation.net;

import javax.swing.JOptionPane;

import org.ayal.SPT.DynamicForwarder;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.UserInfo;

/**
 * Example based on
 * 
 * http://code.google.com/p/ssh-persistent-tunnel/
 */
public class DpfSocksProxy {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JSch jsch = new JSch();
		try {
			String host = null;
			host = JOptionPane.showInputDialog("Enter username@hostname", System.getProperty("user.name") + "@localhost");
			String user = host.substring(0, host.indexOf('@'));
			host = host.substring(host.indexOf('@') + 1);
			java.util.Properties config = new java.util.Properties();
			config.put("StrictHostKeyChecking", "no");
			Session session = jsch.getSession(user, host, 22);
			SysTray sysTray = new SysTray(session);
			session.setConfig(config);
			UserInfo ui = new MyUserInfo();
			session.setUserInfo(ui);
			session.connect();
			if (session.isConnected()) {
				sysTray.notifyTrayyMessage("tunnel established");
			}
			String port = JOptionPane.showInputDialog("Enter proxy server port", "9090");
			DynamicForwarder dynamicForwarder = new DynamicForwarder(Integer.parseInt(port), session);
			dynamicForwarder.run();
			sysTray.notifyTrayyMessage("socks proxy listening on localhost:" + port);
		} catch (JSchException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
