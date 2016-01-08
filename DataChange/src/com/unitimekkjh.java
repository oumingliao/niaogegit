package com;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.dom4j.*;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.xml.*;
public class unitimekkjh {

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
	public static final String DBUSER = "cqunitime";
	public static final String password = "cqunitime";
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
		result = stmt.executeQuery(
				"SELECT DISTINCT REGEXP_REPLACE(t.DISCIPLINE, '\\d+'),t.DEPTCODE from UT_COURSEPLAN t where regexp_like(t.DISCIPLINE,'^[a-zA-Z]')");
		
		
		
		String file = "E:\\unitimekkjh.xml"; // 文件存放位置  
		unitimekkjh dj = new unitimekkjh();  
        dj.createXml(file,result);  
        dj.parserXml(file); 
        
        result.close();// 数据库先开后关
		stmt.close();
		conn.close();// 关闭数据库
		System.out.println("success");
	}
	
	public void createXml(String filePath,ResultSet result) throws Exception {  
        Document document = DocumentHelper.createDocument();  
        
        Element employees = document.addElement("subjectAreas");  
        employees.addAttribute("campus", "CQU");
        employees.addAttribute("term", "秋");
        employees.addAttribute("year", "2015");
        
        //数组a用于存longTitle数据，处理为没有重复数据
//        ArrayList a = new ArrayList();
//        while (result.next()){// 判断有没有下一行 
//        	a.add(result.getString(1).replaceAll("[^a-zA-Z]", ""));
//        }
//    	HashSet hs = new HashSet();
//    	hs.addAll(a);
//    	Iterator i = hs.iterator();
//    	while(i.hasNext())
//    	{
//
//        	System.out.println(i.next());
//    	}
        
        while (result.next()){
        	Element employee = employees.addElement("subjectArea");  
			
			String longTitle = result.getString(1);
			String department = result.getString(2);
			System.out.println("longTitle="+longTitle);
			
			employee.addAttribute("abbreviation", longTitle);
		    //employee.addAttribute("shortTitle", "");
		    employee.addAttribute("longTitle", longTitle);
		    employee.addAttribute("schedBookOnly", "true");
		    employee.addAttribute("pseudoSubjArea", "true");
		    employee.addAttribute("department", department);
		    //abb++;
				    
		}
       
        
         
        try {  
            // PrintWriter带有编码格式的构造方法有助于解决乱码问题  
            PrintWriter pw = new PrintWriter(filePath, "utf-8");  
            XMLWriter xmlWriter = new XMLWriter(pw);  
            xmlWriter.write(document);  
            xmlWriter.close();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
	
	//去除数组中重复的记录  
	public static String[] array_unique(String[] a) {  
	    // array_unique  
	    List<String> list = new LinkedList<String>();  
	    for(int i = 0; i < a.length; i++) {  
	        if(!list.contains(a[i])) {  
	            list.add(a[i]);  
	        }  
	    }  
	    return (String[])list.toArray(new String[list.size()]);  
	} 
	
    /** 
     * 解析XML 
     * @param filePath 文件路径 
     */  
    public void parserXml(String filePath) {  
        File inputXml = new File(filePath);  
        SAXReader saxReader = new SAXReader();  
        try {  
            Document document = saxReader.read(inputXml);  
            Element root = document.getRootElement();  
            for (Iterator persons = root.elementIterator(); persons.hasNext();) {  
                Element person = (Element) persons.next();  
                for (Iterator pro = person.elementIterator(); pro.hasNext();) {  
                    Element node = (Element) pro.next();  
                    System.out.print(node.getName() + ":" + node.getText() + "\t");  
                }  
                System.out.println();  
            }  
        } catch (DocumentException e) {  
            e.printStackTrace();  
        }  
    }  
}
