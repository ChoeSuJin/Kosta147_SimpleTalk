package dbConn.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//ConnectionHelper�� ����
//�Ź� ����̹� �ε�,,, connection ����

//��¥�� �ϳ��� ���μ������� �ϳ��� ���� ���� �ϸ� ���״�..
//�ذ� ����� > �����ڿ�(�̱���)

public class ConnectionSingletonHeler {
	
	
	//�ϳ��� ���μ������� �������� ����� �� �ִ� �����ڿ�(static)
	private static Connection conn;
	
	private ConnectionSingletonHeler(){
		
	}
	
	public static Connection getConnection(String dsn){
		
		if(conn != null){
			return conn;
		}
		

		try {
			if(dsn.equals("mysql")){
				Class.forName("com.mysql.jdbc.Driver");
				conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sampledb","kingsmile","oracle");
			}
			else if(dsn.equals("oracle")){
				Class.forName("oracle.jdbc.OracleDriver");
				conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","kingsmile","oracle");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			
			return conn;
			
		}
	}
	
	public static void close() throws SQLException{
		if(conn!=null){
			if(!conn.isClosed()){
				conn.close();
			}
		}
	}

}
