package server;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.xml.crypto.Data;

import dbConn.util.ConnectionHelper;
import server.Server;

import server.Server_Model;
public class Server_Controller extends Server { // ���� ��Ʈ�ѷ�

	// Net Source
	private ServerSocket serverSocket = null;
	private Socket socket;
	final int port = 7777;
	PreparedStatement pstmt = null;
	String sql = null;
	ResultSet rs = null;
	String temp = "";
	Vector<Object> userList = new Vector<>();
	String nickName = "";
	String id = "";
	
	public Server_Controller() { // ������ �Լ�
		start();
	}

	public void start() {
		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "�̹� ������� ��Ʈ", "�˸�", JOptionPane.ERROR_MESSAGE);
		}
		if (serverSocket != null) {
			System.out.println("���Ἲ��");
			connect();
		}
	}// start

	public class Socket_Thread implements Runnable {

		@Override
		public void run() {
			while (true) {
				try {
					
					textArea.append("����� ���� �����\n");
					socket = serverSocket.accept();
					
					Connection conn = ConnectionHelper.getConnection("oracle");
					
					sql = "select * from userlist where ip = ?";

					
					temp = socket.getInetAddress().toString();
					
					String currentIp = "";
					for (int i = 1; i < temp.length(); i++) {
						currentIp += String.valueOf(temp.charAt(i));
					}
					
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, currentIp);

					rs = pstmt.executeQuery();
				
					
					if (rs.next()) {
						nickName = rs.getString(4);
						id = rs.getString(2);
						textArea.append(nickName + "���� �����ϼ̽��ϴ�.\n");
						
						userList.add(nickName + "(" + id + ")");
						
						//Thread thread = new ServerReceiver(socket);
						sendToAll(userList, socket);
						
					}
					
				} catch (Exception e) {
					textArea.append("������ ���� �Ǿ����ϴ�\n");
					break;
				} // try-catch
				finally {
				}
			} // while
		}// run

	} // ������
	
	public void sendToAll(Vector<Object> userList, Socket s) {
		
		String[] list = new String[userList.size()];
		System.out.println(list.length);
		
		try {
			System.out.println("sendToAll ����");
			
			DataOutputStream dos = new DataOutputStream(s.getOutputStream());
			for (int i = 0; i < list.length; i++) {
				list[i] = (String) userList.get(i);
				System.out.println(list[i]);
				dos.writeUTF(list[i]);
			} // end while
			dos.flush();
			dos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		
	} // sendToAll(String msg) end

	class ServerReceiver extends Thread { // inner class
		Socket s;
		DataInputStream dis;
		DataOutputStream dos;

		public ServerReceiver(Socket s) {
			this.s = s;
			try {
				System.out.println("ServerReceiver ����");
				dis = new DataInputStream(s.getInputStream());
				dos = new DataOutputStream(s.getOutputStream());
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		} // constructor end

		public void run() {
			String name = "";
			try {
				
				while (dis != null) {
					sendToAll(userList, s);
				}
			} catch (Exception e) {
			} finally {
				textArea.append("���� �����ϼ̽��ϴ�.\n");
				textArea.append("���� ���� ������ ���� : " + userList.size() + " �Դϴ�.\n");
			}
		} // run() end
	} // ServerReceiver class end

	private void connect() {

		Thread thread = new Thread(new Socket_Thread());
		thread.start();

	}

}// class end
