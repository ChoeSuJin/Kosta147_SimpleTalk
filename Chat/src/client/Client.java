package client;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import client.Client_Controller;

public class Client extends JFrame{ //View

	//Login
	
	protected JFrame Log_frame = new JFrame();
	protected JPanel Log_panel = new JPanel();
	protected JTextField Log_tf = new JTextField();
	protected JTextField Pw_tf = new JTextField();
	protected JLabel Log_lb = new JLabel("I D");
	protected JLabel Pw_lb = new JLabel("P W");
	protected JButton Log_btn = new JButton("·Î ±× ÀÎ");
	protected JButton SignUp_btn = new JButton("È¸¿ø°¡ÀÔ");
	
	//Main
	
	private JFrame Main_frame = new JFrame();
	private JPanel Main_panel = new JPanel();
	private JList Main_user_list = new JList();
	
	//Chat
	
	private JFrame Chat_frame = new JFrame();
	private JPanel Chat_panel = new JPanel();
	private JTextField chat_tf = new JTextField();
	private JButton chat_send = new JButton("Àü ¼Û");
	private JTextArea chat_ta = new JTextArea();
	
	public Client(){ //»ý¼ºÀÚ ÇÔ¼ö
		
		login();
		mainView();
		chatView();
		
	}
	
	private void login() {
		
		Log_frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		Log_frame.setVisible(true);
		Log_frame.setBounds(500,170,318,250);
		Log_frame.setResizable(false);
		
		Log_frame.setContentPane(Log_panel);
		Log_panel.setLayout(null);
		
		Font font = new Font("³ª´®°íµñ",Font.BOLD,14);		
		Log_lb.setBounds(30, 57, 77, 15);
		Log_lb.setFont(font);
		Log_panel.add(Log_lb);
		
		Log_tf.setBounds(70,49,210,30);
		Log_panel.add(Log_tf);
		
		Pw_lb.setBounds(30, 97, 77, 15);
		Pw_lb.setFont(font);
		Log_panel.add(Pw_lb);
		
		Pw_tf.setBounds(70,89,210,30);
		Log_panel.add(Pw_tf);
		
		Font font1 = new Font("³ª´®°íµñ",Font.BOLD,12);
		Log_btn.setBounds(177,140,80,30);
		Log_btn.setFont(font1);
		Log_panel.add(Log_btn);
		
		SignUp_btn.setBounds(57,140,80,30);
		SignUp_btn.setFont(font1);
		Log_panel.add(SignUp_btn);
		
	}
	
	private void mainView() {
		
		Main_frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		Main_frame.setVisible(true);
		Main_frame.setBounds(500,170,400,500);
		Main_frame.setResizable(false);
		
		Main_panel.setLayout(null);
		Main_frame.setContentPane(Main_panel);
		
		JLabel list_lb = new JLabel("»ç ¿ë ÀÚ ¸ñ ·Ï");
		list_lb.setBounds(150,25,90,30);
		Font font = new Font("³ª´®°íµñ",Font.BOLD,14);
		Main_panel.add(list_lb);
		list_lb.setFont(font);
		
		Main_user_list.setBounds(25,80,350,370);
		Main_panel.add(Main_user_list);
		
	}

	private void chatView() {
		
		Chat_frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		Chat_frame.setVisible(false);
		Chat_frame.setBounds(500,170,400,500);
		Chat_frame.setResizable(false);
		
		Chat_panel.setLayout(null);
		Chat_frame.setContentPane(Chat_panel);
		
		Font font = new Font("³ª´®°íµñ",Font.PLAIN,12);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(25, 25, 350, 370);
		scrollPane.setFont(font);
		Chat_panel.add(scrollPane);
		
		scrollPane.setViewportView(chat_ta);
		chat_ta.setEditable(false);
		
		chat_tf.setBounds(25,420,280,30);
		chat_tf.setFont(font);
		Chat_panel.add(chat_tf);
		
		chat_send.setBounds(315,420,60,30);
		chat_send.setFont(font);
		Chat_panel.add(chat_send);
		
	}


	public static void main(String[] args) {
		
		Client_Controller controller = new Client_Controller();	
		
	}
	
	

}
