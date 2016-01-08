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
	// 驱动程序就是之前在classpath中配置的jdbc的驱动程序jar中
		public static final String drive = "oracle.jdbc.driver.OracleDriver";
		/**
		 * 
		 * 连接地址
		 * 
		 * 
		 */
		public static final String url = "jdbc:oracle:thin:@10.250.94.254:1521:orcl";
		/**
		 * 
		 * 用户 密码
		 */
		public static final String DBUSER = "timetable";
		public static final String password = "unitime";
		
		public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		Connection conn = null;// 表示数据库连接
		Statement stmt = null;// 表示数据库的更新
		ResultSet result = null;// 查询数据库
		Class.forName(drive);// 使用class类来加载程序
		conn = DriverManager.getConnection(url, DBUSER, password); // 连接数据库
		// Statement接口要通过connection接口来进行实例化操作
		stmt = conn.createStatement();
		// 执行SQL语句来查询数据库
		result = stmt.executeQuery("select t.reference,t.label from DISTRIBUTION_TYPE t");//SELECT name From UNITIMEDEPARTMENT
		
		//"select t.subject,a.subject_area_abbreviation,t.staffid,f.lastname,f.firstname, t.kc_flag,t.courseid,t.xf from unitimetask t,unitimestaff f,timetable.subject_area a where t.subject = a.long_title and t.staffid = f.staffid"
		
		String file = "E:\\sql.txt"; // 文件存放位置  
		
		FileWriter fw = new FileWriter(file);
			while(result.next()){
				String sql = result.getString(1);
				String sql1 = result.getString(2);
							
				String wholeSql = "UPDATE itype_desc s set s.label = '' WHERE s.reference =" +"'"+ sql +"'" + ";" + "  --"+sql1;
				fw.write(wholeSql+"\r\n");
				
			}
		fw.flush();
		fw.close();
		
		
		 
	    result.close();// 数据库先开后关
		stmt.close();
		conn.close();// 关闭数据库
		System.out.println("success");
	}
}
