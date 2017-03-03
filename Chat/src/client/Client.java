package client;

import java.awt.Choice;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Client extends JFrame { // View

	// Login
	protected JFrame Log_frame = new JFrame();
	protected JPanel Log_panel = new JPanel();
	protected JTextField Log_tf = new JTextField();
	protected JTextField Pw_tf = new JTextField();
	protected JLabel Log_lb = new JLabel("I D");
	protected JLabel Pw_lb = new JLabel("P W");
	protected JButton Log_btn = new JButton("�� �� ��");
	protected JButton SignUp_btn = new JButton("ȸ������");

	// SignUp
	protected JFrame signUp_frame = new JFrame("ȸ������");
	protected JPanel signUp_panel = new JPanel();
	protected JTextField signUp_id = new JTextField();
	protected JTextField signUp_pwd = new JTextField();
	protected JTextField signUp_nicname = new JTextField();
	protected JLabel signUp_label_id = new JLabel("I D");
	protected JLabel signUp_label_pwd = new JLabel("P W");
	protected JLabel signUp_label_nicname = new JLabel("Nicname");
	protected JLabel signUp_label_sex = new JLabel("Sex");
	protected JLabel signUp_label_job = new JLabel("Job");
	protected JButton signUp_btn_reg = new JButton("�� ��");
	protected JButton signUp_btn_exit = new JButton("�� ��");
	protected JPanel signup_check_panel = new JPanel();
	protected JCheckBox check_woman = new JCheckBox("Woman");
	protected JCheckBox check_man = new JCheckBox("Man");
	protected Choice choice = new Choice();

	// Main
	private JFrame Main_frame = new JFrame();
	private JPanel Main_panel = new JPanel();
	private JList Main_user_list = new JList();

	// Chat
	private JFrame Chat_frame = new JFrame();
	private JPanel Chat_panel = new JPanel();
	private JTextField chat_tf = new JTextField();
	private JButton chat_send = new JButton("�� ��");
	private JTextArea chat_ta = new JTextArea();

	public Client() { // ������ �Լ�

		login();
		chatView();

	}

	private void login() {

		Log_frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		Log_frame.setVisible(true);
		Log_frame.setBounds(500, 170, 318, 250);
		Log_frame.setResizable(false);

		Log_frame.setContentPane(Log_panel);
		Log_panel.setLayout(null);

		Font font = new Font("��������", Font.BOLD, 14);
		Log_lb.setBounds(30, 57, 77, 15);
		Log_lb.setFont(font);
		Log_panel.add(Log_lb);

		Log_tf.setBounds(70, 49, 210, 30);
		Log_panel.add(Log_tf);

		Pw_lb.setBounds(30, 97, 77, 15);
		Pw_lb.setFont(font);
		Log_panel.add(Pw_lb);

		Pw_tf.setBounds(70, 89, 210, 30);
		Log_panel.add(Pw_tf);

		Font font1 = new Font("��������", Font.BOLD, 12);
		Log_btn.setBounds(177, 140, 80, 30);
		Log_btn.setFont(font1);
		Log_panel.add(Log_btn);

		SignUp_btn.setBounds(57, 140, 80, 30);
		SignUp_btn.setFont(font1);
		Log_panel.add(SignUp_btn);

	}

	protected void signUpView() {
		// frame GUI
		signUp_frame.setVisible(true);
		signUp_frame.setBounds(700, 170, 318, 300);
		signUp_frame.setResizable(false);

		// panel
		signUp_frame.setContentPane(signUp_panel);
		signUp_panel.setLayout(null);

		// text
		Font font = new Font("��������", Font.BOLD, 14);
		signUp_id.setBounds(110, 30, 170, 30);
		signUp_id.setFont(font);
		signUp_panel.add(signUp_id);

		signUp_pwd.setBounds(110, 70, 170, 30);
		signUp_pwd.setFont(font);
		signUp_panel.add(signUp_pwd);

		signUp_nicname.setBounds(110, 110, 170, 30);
		signUp_nicname.setFont(font);
		signUp_panel.add(signUp_nicname);

		// Label
		signUp_label_id.setFont(font);
		signUp_label_id.setBounds(30, 30, 80, 30);
		signUp_panel.add(signUp_label_id);

		signUp_label_pwd.setFont(font);
		signUp_label_pwd.setBounds(30, 70, 80, 30);
		signUp_panel.add(signUp_label_pwd);

		signUp_label_nicname.setFont(font);
		signUp_label_nicname.setBounds(30, 110, 80, 30);
		signUp_panel.add(signUp_label_nicname);
		
		signUp_label_sex.setFont(font);
		signUp_label_sex.setBounds(30, 150, 80, 30);
		signUp_panel.add(signUp_label_sex);
		
		signUp_label_job.setFont(font);
		signUp_label_job.setBounds(30, 190, 80, 30);
		signUp_panel.add(signUp_label_job);

		// chekButton
		Font font_check = new Font("��������", Font.BOLD, 12);
		signup_check_panel.setBounds(110, 150, 170, 30);
		signup_check_panel.setLayout(new BoxLayout(signup_check_panel, BoxLayout.X_AXIS));
		check_man.setFont(font_check);
		signup_check_panel.add(check_man);
		check_woman.setFont(font_check);
		signup_check_panel.add(check_woman);
		signUp_panel.add(signup_check_panel);
		
		// Choice
		choice.setBounds(110, 190, 170, 30);
		choice.add("�л�");
		choice.add("ȸ���");
		choice.add("�ֺ�");
		choice.add("�׿�");
		choice.add("��Ÿ");
		signUp_panel.add(choice);

		// button
		signUp_btn_reg.setBounds(110, 230, 80, 30);
		signUp_panel.add(signUp_btn_reg);

		signUp_btn_exit.setBounds(200, 230, 80, 30);
		signUp_panel.add(signUp_btn_exit);

	}

	protected void mainView() {

		Main_frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		Main_frame.setVisible(true);
		Main_frame.setBounds(500, 170, 400, 500);
		Main_frame.setResizable(false);

		Main_panel.setLayout(null);
		Main_frame.setContentPane(Main_panel);

		JLabel list_lb = new JLabel("�� �� �� �� ��");
		list_lb.setBounds(150, 25, 90, 30);
		Font font = new Font("��������", Font.BOLD, 14);
		Main_panel.add(list_lb);
		list_lb.setFont(font);

		Main_user_list.setBounds(25, 80, 350, 370);
		Main_panel.add(Main_user_list);

	}

	private void chatView() {

		Chat_frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		Chat_frame.setVisible(false);
		Chat_frame.setBounds(500, 170, 400, 500);
		Chat_frame.setResizable(false);

		Chat_panel.setLayout(null);
		Chat_frame.setContentPane(Chat_panel);

		Font font = new Font("��������", Font.PLAIN, 12);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(25, 25, 350, 370);
		scrollPane.setFont(font);
		Chat_panel.add(scrollPane);

		scrollPane.setViewportView(chat_ta);
		chat_ta.setEditable(false);

		chat_tf.setBounds(25, 420, 280, 30);
		chat_tf.setFont(font);
		Chat_panel.add(chat_tf);

		chat_send.setBounds(315, 420, 60, 30);
		chat_send.setFont(font);
		Chat_panel.add(chat_send);

	}

	public static void main(String[] args) {

		Client_Controller controller = new Client_Controller();

	}

}
