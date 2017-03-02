package server;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

import javax.swing.JOptionPane;
import server.Server;

public class Server_Controller extends Server{ // ���� ��Ʈ�ѷ�
	
	// Net Source
	private ServerSocket serverSocket = null;
	private Socket socket;
	private int port = 7777;
	
	public Server_Controller(){ // ������ �Լ�
		start();
	}
	
	public void start(){
		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "�̹� ������� ��Ʈ", "�˸�", JOptionPane.ERROR_MESSAGE);
		}
		if(serverSocket != null){
			System.out.println("���Ἲ��");
			connect();
		}
	}// start
	
	public class Socket_Thread implements Runnable{

		@Override
		public void run() {
			while(true){
				try {
					textArea.append("����� ���� �����\n");
					socket = serverSocket.accept();
				} catch (Exception e) {
					textArea.append("������ ���� �Ǿ����ϴ�\n");
					break;
				}//try-catch
			}// while
		}//run
		
	} // ������
	
	private void connect() {
		
		Thread thread = new Thread(new Socket_Thread());
		thread.start();
		
	}
	
	
	
}// class end
