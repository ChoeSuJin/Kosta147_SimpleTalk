package client;

import java.net.InetAddress;

public class Client_Model {

	private String client_ip;

	// set, get method
	public String getClient_ip() {
		String ip = null;
		try {
			InetAddress local = InetAddress.getLocalHost();
			ip = local.getHostAddress();
		} catch (Exception e) {
			System.out.println("Error : Load Client_IP");
		}
		return ip;
	}
	
	
}
