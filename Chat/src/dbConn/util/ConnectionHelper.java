package dbConn.util;

import java.sql.Connection;
import java.sql.DriverManager;

//DB �������� �ݺ������� �ڵ��ذ�
	//�ٸ� Ŭ�������� �Ʒ� �ڵ� ������ ���� �ʵ��� ����
	//Class.forName("oracle.jdbc.OracleDriver");
	//conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe","Kingsmile","oracle");
	
	
	//�̷������� ���
	//ConnectionHelper.getConnection("mysql") or ("oracle");
	//dsn ==> data source name
	

public class ConnectionHelper {
	
	//�Լ�(������ : public , static)
	public static Connection getConnection(String dsn){
		
		Connection conn = null;
		
		try {
			if(dsn.equals("mysql")){
				Class.forName("com.mysql.jdbc.Driver");
				conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sampledb","kingsmile","mysql");
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
		
	} //
	
	public static Connection getConnection(String dsn , String userid , String pwd){
		
		Connection conn = null;
		
		try {
			if(dsn.equals("mysql")){
				Class.forName("com.mysql.jdbc.Driver");
				conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sampledb",userid,pwd);
			}
			else if(dsn.equals("oracle")){
				Class.forName("oracle.jdbc.OracleDriver");
				conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe",userid,pwd);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			
			return conn;
			
		}
		
	}
	
	
}
