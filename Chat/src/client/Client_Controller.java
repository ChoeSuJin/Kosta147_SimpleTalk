package client;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JOptionPane;

import dbConn.util.*;

public class Client_Controller extends Client implements ActionListener{
	
	//net
	private Socket socket = null;
	private InputStream inputStream;
	private OutputStream outputStream;
	private DataOutputStream dataOutputStream;
	private DataInputStream dataInputStream;
	
	//jdbc
	private Connection conn = ConnectionHelper.getConnection("oracle");
	private PreparedStatement pstmt = null;
	private String sql = null;
	ResultSet rs = null;
	private String id = null;
	private String pw = null;
	
	public Client_Controller(){ //������ �Լ�
		start();
	}
	
	public void start(){
		Log_btn.addActionListener(this);
	}
	
	private void network(){
		try {
			sql = "select * from userlist";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			socket = new Socket("127.0.0.1",7777); //IP �� PORT
			if(socket != null){
				connect(); // ����OK
			}
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "��Ʈ��ũ ���� ����", "�˸�", JOptionPane.ERROR_MESSAGE);
		}
	}// network
	
	private void connect(){
		try {
			inputStream = socket.getInputStream();
			dataInputStream = new DataInputStream(inputStream);
			outputStream = socket.getOutputStream();
			dataOutputStream = new DataOutputStream(outputStream);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Connect ���� ����", "�˸�", JOptionPane.ERROR_MESSAGE);
		}
		System.out.println("����Ϸ�!");
		Log_frame.setVisible(false);
		
	}// connect
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		Object obj = e.getSource(); //Object�� ��ü obj�� ����
		try {
			sql = "select * from userlist where ID = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			
			rs = pstmt.executeQuery();
			if(obj==Log_btn){ //��ư�� ������
				id = Log_tf.getText();
				pw = Pw_tf.getText();
				
				if(true==rs.next()){
					String temp = rs.getString(3);
					if(temp.equals(pw)){
						System.out.println(rs.getString(4) + "�� �α��� �Ϸ�");
						network();
					} else System.out.println("��й�ȣ ����!");
				}
				else System.out.println("�������� �ʴ� ���̵��Դϴ�.");
			}
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		
	}// actionPerformed
}


