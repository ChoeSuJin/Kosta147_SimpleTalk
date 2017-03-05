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
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JOptionPane;

import dbConn.util.ConnectionHelper;
import server.Server;

import server.Server_Model;
import client.Client_Model;

public class Server_Controller extends Server { // 서버 컨트롤러

	// Net Source
	private ServerSocket serverSocket = null;
	private Socket socket;
	final int port = 7777;
	PreparedStatement pstmt = null;
	String sql = null;
	ResultSet rs = null;
	String temp = "";
	Vector<Client_Model> userList = new Vector<Client_Model>();
	String nickName = "";
	String id = "";
	String ip = "";
	Client_Model client = new Client_Model();

	public Server_Controller() { // 생성자 함수
		start();
	}

	public void start() {
		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "이미 사용중인 포트", "알림", JOptionPane.ERROR_MESSAGE);
		}
		if (serverSocket != null) {
			System.out.println("연결성공");
			connect();
		}
	}// start

	public class Socket_Thread implements Runnable {

		@Override
		public void run() {
			while (true) {
				try {

					textArea.append("사용자 접속 대기중\n");
					socket = serverSocket.accept();

					Connection conn = ConnectionHelper.getConnection("oracle");

					sql = "select * from userlist where ip = ?";

					temp = socket.getInetAddress().toString();

					String currentIp = "";
					for (int i = 1; i < temp.length(); i++) {
						currentIp += String.valueOf(temp.charAt(i));
					}
					
					System.out.println(currentIp);

					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, currentIp);
					rs = pstmt.executeQuery();

					if (rs.next()) {
						nickName = rs.getString(4);
						id = rs.getString(2);
						
						client.setClient_id(id);
						client.setClient_nick(nickName);
						client.setClient_ip(currentIp);
						
						System.out.println(nickName);

						textArea.append(nickName + "님이 입장하셨습니다.\n");

						userList.add(client);
						send_userList(socket, userList);

					}

				} catch (Exception e) {
					textArea.append("서버가 중지 되었습니다\n");
					break;
				} // try-catch

			} // while
		}// run

	} // 스레드

	public void sendToAll(Vector<Client_Model> userList, Socket s) throws SQLException {

		System.out.println(userList.size());

		try {
			Connection conn = ConnectionHelper.getConnection("oracle");

			sql = "select * from userlist where ip = ?";

			DataOutputStream dos = new DataOutputStream(s.getOutputStream());
			for (int i = 0; i < userList.size(); i++) {
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, userList.get(i).getClient_ip());
				System.out.println(userList.get(i).getClient_ip());
				rs = pstmt.executeQuery();

				while (rs.next()) {
					String ip = rs.getString(1);
					String id = rs.getString(2);
					String nick = rs.getString(4);
					System.out.println(ip + "   " + id + "   " + nick);
					dos.writeUTF(ip);
					dos.writeUTF(id);
					dos.writeUTF(nick);
				}
				

			} // end while
		} catch (IOException e) {
			e.printStackTrace();
		}

	} // sendToAll(String msg) end

	public void ServerReceiver(Socket s) throws IOException { // inner class
		DataInputStream dis;

		System.out.println("ServerReceiver 진입");
		dis = new DataInputStream(s.getInputStream());

		Thread thread = new Thread() {
			public void run() {
				try {
					while (dis != null) {
						String tempIp = dis.readUTF();
						send_userList(s, userList);
					}
				} catch (Exception e) {
				} /*finally {
					textArea.append("님이 퇴장하셨습니다.\n");
					textArea.append("현재 서버 접속자 수는 : " + userList.size() + " 입니다.\n");
				}*/
			} // run() end
		};

		thread.start();

	} // ServerReceiver class end

	public void send_userList(Socket s, Vector<Client_Model> userList) throws IOException {

		System.out.println("ServerSender 진입");
		Thread thread = new Thread() {
			@Override
			public void run() {
				try {
					sendToAll(userList, s);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		thread.start();

	}

	private void connect() {

		Thread thread = new Thread(new Socket_Thread());
		thread.start();

	}

}// class end
