package client;

import java.net.InetAddress;
import java.util.Vector;

public class Client_Model {

	private String client_ip;
	private String client_id;
	private String client_nick;
	private String client_port;
	

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


	public String getClient_id() {
		return client_id;
	}


	public void setClient_id(String client_id) {
		this.client_id = client_id;
	}


	public String getClient_nick() {
		return client_nick;
	}


	public void setClient_nick(String client_nick) {
		this.client_nick = client_nick;
	}


	public String getClient_port() {
		return client_port;
	}


	public void setClient_port(String client_port) {
		this.client_port = client_port;
	}
	
	
	
	
}
