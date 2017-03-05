package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

import dbConn.util.ConnectionHelper;

public class Client_Controller extends Client implements ActionListener {

	// net
	private Socket socket = null;
	private InputStream inputStream;
	private OutputStream outputStream;
	private DataOutputStream dataOutputStream;
	private DataInputStream dataInputStream;

	// jdbc
	private Connection conn = ConnectionHelper.getConnection("oracle");
	private PreparedStatement pstmt = null;
	private String sql = null;
	ResultSet rs = null;
	private String id = null;
	private String pw = null;
	static Vector<String> userList = new Vector<String>();
	
	String ip = "";
	Socket s;

	// dto
	Client_Model dto = new Client_Model();

	final int PORT_NUMBER = 7777;

	public Client_Controller() { // 생성자 함수
		start();
	}

	public void start() {
		Log_btn.addActionListener(this);
		SignUp_btn.addActionListener(this);
		signUp_btn_reg.addActionListener(this); // 회원가입 GUI : 등록
		signUp_btn_exit.addActionListener(this); // 회원가입 GUI : 닫기
		
		
	}

	private void network() {
		try {
			sql = "select * from userlist";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			InetAddress local = InetAddress.getLocalHost();
			ip = local.getHostAddress();

			socket = new Socket(ip, PORT_NUMBER); // IP 와 PORT
			
			if (socket != null) {
				connect(); // 소켓OK
				
				
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "네트워크 연결 실패", "알림", JOptionPane.ERROR_MESSAGE);
		}
	}// network

	private void connect() throws IOException {
		try {
			inputStream = socket.getInputStream();
			dataInputStream = new DataInputStream(inputStream);
			outputStream = socket.getOutputStream();
			dataOutputStream = new DataOutputStream(outputStream);
			
			
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Connect 연결 실패", "알림", JOptionPane.ERROR_MESSAGE);
		}
		System.out.println("연결완료!");
		receive_userList(socket);
		
		
		
		Log_frame.setVisible(false);

	}// connect
	
	public void mouseClicked(MouseEvent e) {
		if (e.getClickCount() == 2) {
			int index = Main_user_list.locationToIndex(e.getPoint());
			super.chatView();
			super.Chat_frame.setVisible(true);
			super.Chat_frame.setTitle((String)Main_user_list.getSelectedValue());
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource(); // Object의 객체 obj를 생성
		try {
			Socket socket = new Socket(ip, PORT_NUMBER);
			
			DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
			DataInputStream dis = new DataInputStream(socket.getInputStream());

			if (obj == Log_btn) { // 버튼이 눌릴때

				id = Log_tf.getText();
				pw = Pw_tf.getText();
				sql = "select * from userlist where ID = ?";
				pstmt = conn.prepareStatement(sql);

				pstmt.setString(1, id);
				rs = pstmt.executeQuery();
				
				String tempIp = "";

				while (rs.next()) {
					tempIp = rs.getString(1);
					String temp = rs.getString(3);
					System.out.println(temp);
					if (temp.equals(pw)) {
						System.out.println(rs.getString(4) + "님 로그인 완료");
						network();
						dos.writeUTF(ip);
						
						super.Main_frame.setVisible(true);
						
						//receive_userList(socket);
						break;
					} else {
						System.out.println("비밀번호가 틀렸습니다.");
						break;
					}

					// 테스트할때는 꼭 commit하고 합시당...
				}	// while end
				
				
			} else if (obj == SignUp_btn) {
				// 회원가입 GUI 띄우기
				super.signUpView();
			} else if (obj == signUp_btn_reg) {
				// ID 와 PW 입력 필수
				if (signUp_id.getText().length() == 0) {
					JOptionPane.showMessageDialog(null, "ID 입력은 필수입니다.", "알림", JOptionPane.ERROR_MESSAGE);
				} else if (signUp_pwd.getText().length() == 0) {
					JOptionPane.showMessageDialog(null, "PW 입력은 필수입니다.", "알림", JOptionPane.ERROR_MESSAGE);
				} else {
					// 회원 정보 DB에 저장
					String ip = dto.getClient_ip(); // client_ip 가져옴
					String id = signUp_id.getText();
					String pwd = signUp_pwd.getText();
					String nicname = signUp_nicname.getText();
					create_userDB(ip, id, pwd, nicname);
				}
			} else if (obj == signUp_btn_exit) {
				// 회원 가입 GUI 닫기
				super.signUp_frame.setVisible(false);
			} else if (obj == Main_user_list) {
				super.Chat_frame.setVisible(true);
				super.chat_ta.setEditable(true);
				super.chatView();
				
			}
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}// actionPerformed

	private void create_userDB(String ip, String id, String pwd, String nicname) {
		String db_table = "ip_mapping_table"; // 자기 서버에 있는 테이블 이름으로 바꾸서 사용.
		// 회원 가입시 쓰레드 생성
		Thread thread = new Thread() {
			@Override
			public void run() {
				try {
					sql = "insert into " + db_table + " values(?,?,?,?,?)";
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, ip);
					pstmt.setString(2, id);
					pstmt.setString(3, pwd);
					pstmt.setString(4, nicname);
					pstmt.setInt(5, 7777);
					if (pstmt.executeUpdate() == 1) {
						JOptionPane.showMessageDialog(null, "저장 완료", "알림", JOptionPane.INFORMATION_MESSAGE);
						signUp_frame.setVisible(false);
					} else {
						JOptionPane.showMessageDialog(null, "저장 실패", "알림", JOptionPane.ERROR_MESSAGE);
					}
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("Error : create_userDB");
				}
			}
		};
		// 쓰레드 실행
		thread.start();
	} // create DB user
	
	public void receive_userList(Socket s) throws IOException {
		DataInputStream dis;
		
		dis = new DataInputStream(s.getInputStream());
		
		Thread thread = new Thread() {
			@Override
			public void run() {
				while ( dis != null ) {
					try {
						System.out.println("receive_userList 진입");
						String temp = dis.readUTF();
						System.out.println(temp);
						int index = -1;
						for (int i = 0; i < listModel.getSize(); i++) {
							System.out.println(listModel.get(i));
							if (temp.equals(listModel.get(i))) {
								index = i;
								break;
							}
						}
						
						if (index != -1) {
							listModel.removeElementAt(index);
						}
						else
							listModel.addElement(temp);
						
					} catch (Exception e) {
						e.printStackTrace();
					}
				}// while end
			}
		};
		
		thread.start();
	} // ClientRecievier end

}
