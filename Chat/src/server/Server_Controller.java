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

import dbConn.util.ConnectionHelper;
import server.Server;

import server.Server_Model;
public class Server_Controller extends Server { // 서버 컨트롤러

	// Net Source
	private ServerSocket serverSocket = null;
	private Socket socket;
	final int port = 7777;
	PreparedStatement pstmt = null;
	String sql = null;
	ResultSet rs = null;
	String temp = "";

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

					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, currentIp);

					rs = pstmt.executeQuery();
				
					
					if (rs.next()) {
						String nickName = rs.getString(4);
						textArea.append(nickName + "님이 입장하셨습니다.\n");
					}
					

				} catch (Exception e) {
					textArea.append("서버가 중지 되었습니다\n");
					break;
				} // try-catch
			} // while
		}// run

	} // 스레드
	
	public void sendToAll(Vector<String> userList) {
		
		String[] list = new String[userList.size()];
		
		for (int i = 0; i < list.length; i++) {
			list[i] = userList.get(i);
		}

		for (int i = 0; i < list.length; i++){
			try {
				//DataOutputStream dos = (DataOutputStream) clients.get(it.next());
				DataOutputStream dos = null;
				dos.writeUTF(list[i]);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} // end while
	} // sendToAll(String msg) end

	class ServerReceiver extends Thread { // inner class
		Socket s;
		DataInputStream dis;
		DataOutputStream dos;

		public ServerReceiver(Socket s) {
			this.s = s;
			try {
				dis = new DataInputStream(s.getInputStream());
				dos = new DataOutputStream(s.getOutputStream());
			} catch (Exception e) {
			}
		} // constructor end

		public void run() {
			String name = "";
			try {
				name = dis.readUTF();
				sendToAll("#" + name + " 님이 입장하셨습니다.");

				clients.put(name, dos);
				System.out.println("현재 서버 접속자 수는 : " + clients.size() + " 입니다");

				while (dis != null) {
					sendToAll(dis.readUTF());
				}
			} catch (Exception e) {
			} finally {
				sendToAll("#" + name + " 님이 나가셨습니다.");
				clients.remove(name);
				System.out.println("[" + s.getInetAddress() + " : " + s.getPort() + "] 에서 접속 종료하였습니다.");
				System.out.println("현재 서버 접속자 수는 : " + clients.size() + " 입니다");
			}
		} // run() end
	} // ServerReceiver class end

	private void connect() {

		Thread thread = new Thread(new Socket_Thread());
		thread.start();

	}

}// class end
