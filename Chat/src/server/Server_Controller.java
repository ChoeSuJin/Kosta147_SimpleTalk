package server;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import javax.swing.JOptionPane;

import dbConn.util.ConnectionHelper;
import server.Server;

public class Server_Controller extends Server { // ���� ��Ʈ�ѷ�

	// Net Source
	private ServerSocket serverSocket = null;
	private Socket socket;
	final int port = 7777;

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
					PreparedStatement pstmt = null;
					String sql = null;
					ResultSet rs = null;

					sql = "select * from userlist where ip = ?";

					String temp = socket.getInetAddress().toString();
					String currentIp = "";
					for (int i = 1; i < temp.length(); i++) {
						currentIp += String.valueOf(temp.charAt(i));
					}

					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, currentIp);

					rs = pstmt.executeQuery();
					
					System.out.println(currentIp);
					
					if (rs.next()) {
						String nickName = rs.getString(4);
						textArea.append(nickName + "���� �����ϼ̽��ϴ�.\n");
					}
					
					System.out.println("if �������");

				} catch (Exception e) {
					textArea.append("������ ���� �Ǿ����ϴ�\n");
					break;
				} // try-catch
			} // while
		}// run

	} // ������

	private void connect() {

		Thread thread = new Thread(new Socket_Thread());
		thread.start();

	}

}// class end
