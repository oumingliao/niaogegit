package com;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
public class sqlProducer {
	// �����������֮ǰ��classpath�����õ�jdbc����������jar��
		public static final String drive = "oracle.jdbc.driver.OracleDriver";
		/**
		 * 
		 * ���ӵ�ַ
		 * 
		 * 
		 */
		public static final String url = "jdbc:oracle:thin:@10.250.94.254:1521:orcl";
		/**
		 * 
		 * �û� ����
		 */
		public static final String DBUSER = "timetable";
		public static final String password = "unitime";
		
		public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		Connection conn = null;// ��ʾ���ݿ�����
		Statement stmt = null;// ��ʾ���ݿ�ĸ���
		ResultSet result = null;// ��ѯ���ݿ�
		Class.forName(drive);// ʹ��class�������س���
		conn = DriverManager.getConnection(url, DBUSER, password); // �������ݿ�
		// Statement�ӿ�Ҫͨ��connection�ӿ�������ʵ��������
		stmt = conn.createStatement();
		// ִ��SQL�������ѯ���ݿ�
		result = stmt.executeQuery("select t.reference,t.label from DISTRIBUTION_TYPE t");//SELECT name From UNITIMEDEPARTMENT
		
		//"select t.subject,a.subject_area_abbreviation,t.staffid,f.lastname,f.firstname, t.kc_flag,t.courseid,t.xf from unitimetask t,unitimestaff f,timetable.subject_area a where t.subject = a.long_title and t.staffid = f.staffid"
		
		String file = "E:\\sql.txt"; // �ļ����λ��  
		
		FileWriter fw = new FileWriter(file);
			while(result.next()){
				String sql = result.getString(1);
				String sql1 = result.getString(2);
							
				String wholeSql = "UPDATE itype_desc s set s.label = '' WHERE s.reference =" +"'"+ sql +"'" + ";" + "  --"+sql1;
				fw.write(wholeSql+"\r\n");
				
			}
		fw.flush();
		fw.close();
		
		
		 
	    result.close();// ���ݿ��ȿ����
		stmt.close();
		conn.close();// �ر����ݿ�
		System.out.println("success");
	}
}
