package com;
import java.io.File;
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

public class unitimestaff {
	// 锟斤拷锟斤拷锟斤拷锟斤拷之前锟斤拷classpath锟斤拷锟斤拷锟矫碉拷jdbc锟斤拷锟斤拷锟斤拷锟絡ar锟斤拷
	public static final String drive = "oracle.jdbc.driver.OracleDriver";
	/**
	 * 
	 * 锟斤拷锟接碉拷址
	 * 
	 * 
	 */
	public static final String url = "jdbc:oracle:thin:@10.250.94.254:1521:ORCL";
	/**
	 * 
	 * 锟矫伙拷 锟斤拷锟斤拷
	 */
	public static final String DBUSER = "cqunitime";
	public static final String password = "cqunitime";
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		Connection conn = null;// 锟斤拷示锟斤拷菘锟斤拷锟斤拷锟�
		Statement stmt = null;// 锟斤拷示锟斤拷菘锟侥革拷锟斤拷
		ResultSet result = null;// 锟斤拷询锟斤拷菘锟�
		Class.forName(drive);// 使锟斤拷class锟斤拷锟斤拷锟斤拷锟截筹拷锟斤拷
		conn = DriverManager.getConnection(url, DBUSER, password); // 锟斤拷锟斤拷锟斤拷菘锟�
		// Statement锟接匡拷要通锟斤拷connection锟接匡拷锟斤拷锟斤拷锟斤拷实锟斤拷锟斤拷锟�
		stmt = conn.createStatement();
		// 执锟斤拷SQL锟斤拷锟斤拷锟斤拷锟窖拷锟捷匡拷
		//result = stmt.executeQuery("SELECT * From UNITIMEDEPARTMENT");//SELECT name From UNITIMEDEPARTMENT
		result = stmt.executeQuery("SELECT * From ut_teacher");
		
		
		String file = "E:\\teacher.xml"; // 锟侥硷拷锟斤拷锟轿伙拷锟� 
		unitimestaff dj = new unitimestaff();  
        dj.createXml(file,result);  
        dj.parserXml(file); 
        
        result.close();// 锟斤拷菘锟斤拷瓤锟斤拷锟斤拷
		stmt.close();
		conn.close();// 锟截憋拷锟斤拷菘锟�
	}
	
	public void createXml(String filePath,ResultSet result) throws Exception {  
        Document document = DocumentHelper.createDocument();  
        
       // Element employees = document.addElement("departments");  
        Element employees = document.addElement("staff"); 
        employees.addAttribute("campus", "CQU");
        employees.addAttribute("term", "秋");
        employees.addAttribute("year", "2015");
        
        
        while (result.next()){// 锟叫讹拷锟斤拷没锟斤拷锟斤拷一锟斤拷
        	//UNITIMEDEPARTMENT
        	Element employee = employees.addElement("staffMember");  
        	String externalId = result.getString(1);
			//String firstName = result.getString(3);
        	String positionType = "INSTRUCTOR";
			String lastName = result.getString(2);
			String department = result.getString(4);
			employee.addAttribute("externalId", externalId);
			employee.addAttribute("firstName", "");
		    employee.addAttribute("lastName", lastName);
		    employee.addAttribute("positionType", positionType);
		    employee.addAttribute("department", department);
           
		}
       
        
         
        try {  
            // PrintWriter锟斤拷锟叫憋拷锟斤拷锟绞斤拷墓锟斤拷旆斤拷锟斤拷锟斤拷锟斤拷诮锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷  
            PrintWriter pw = new PrintWriter(filePath, "utf-8");  
            XMLWriter xmlWriter = new XMLWriter(pw);  
            xmlWriter.write(document);  
            xmlWriter.close();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
    //** 

     //*  
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
