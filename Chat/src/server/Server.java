package server;

import java.awt.Font;

import javax.swing.*;

public class Server extends JFrame { // vi//ew
	
	private JFrame Frame = new JFrame();
	private JPanel panel = new JPanel();
	protected JTextArea textArea = new JTextArea();
	//테스트
	public Server(){
		mainView();
	}

	private void mainView() {
		
		Frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		Frame.setVisible(true);
		Frame.setBounds(500,150,400,500);
		Frame.setResizable(false);
		
		Frame.setContentPane(panel);
		panel.setLayout(null);
		
		JLabel title = new JLabel("�� �� V I E W");
		Font font = new Font("�������",Font.BOLD,14);
		title.setFont(font);
		title.setBounds(150,25,100,30);
		panel.add(title);
		
		JScrollPane scrollPanel = new JScrollPane();
		scrollPanel.setBounds(25,80,350,370);
		Font font1 = new Font("�������",Font.PLAIN,12);
		scrollPanel.setFont(font1);
		panel.add(scrollPanel);
		
		scrollPanel.setViewportView(textArea);
		textArea.setEditable(false);
		
	}

	public static void main(String[] args) {
		
		Server_Controller controller = new Server_Controller();
		
	}

}
