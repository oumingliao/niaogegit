package com;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;

import org.dom4j.*;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.xml.*;

public class Academicareas {
	// ��������֮ǰ��classpath�����õ�jdbc�������jar��
	public static final String drive = "oracle.jdbc.driver.OracleDriver";
	/**
	 * 
	 * ���ӵ�ַ
	 * 
	 * 
	 */
	public static final String url = "jdbc:oracle:thin:@10.250.94.30:1521:ORCL";
	/**
	 * 
	 * �û� ����
	 */
	public static final String DBUSER = "cqunitime";
	public static final String password = "cqunitime";
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		Connection conn = null;// ��ʾ��ݿ�����
		Statement stmt = null;// ��ʾ��ݿ�ĸ���
		ResultSet result = null;// ��ѯ��ݿ�
		Class.forName(drive);// ʹ��class�������س���
		conn = DriverManager.getConnection(url, DBUSER, password); // ������ݿ�
		// Statement�ӿ�Ҫͨ��connection�ӿ�������ʵ�����
		stmt = conn.createStatement();
		// ִ��SQL�������ѯ��ݿ�
		//result = stmt.executeQuery("SELECT * From UNITIMEDEPARTMENT");//SELECT name From UNITIMEDEPARTMENT
		result = stmt.executeQuery("SELECT * From unitimeacademicareas");
		
		
		String file = "E:\\unitimeacademicareas.xml"; // �ļ����λ��  
		Academicareas dj = new Academicareas();  
        dj.createXml(file,result);  
        dj.parserXml(file); 
        
        result.close();// ��ݿ��ȿ����
		stmt.close();
		conn.close();// �ر���ݿ�
	}
	
	public void createXml(String filePath,ResultSet result) throws Exception {  
        Document document = DocumentHelper.createDocument();  
        
       // Element employees = document.addElement("departments");  
        Element employees = document.addElement("academicAreas"); 
        employees.addAttribute("campus", "CQU");
        employees.addAttribute("term", "Spring");
        employees.addAttribute("year", "2014");
        
        
        while (result.next()){// �ж���û����һ��
        	//UNITIMEDEPARTMENT
        	Element employee = employees.addElement("academicArea");  
			
			String shortTitle = result.getString(2);
			String abbreviation = shortTitle.substring(0,1);
			//String longTitle = result.getString(2);
			employee.addAttribute("abbreviation", abbreviation);
		    employee.addAttribute("shortTitle", shortTitle);
		    employee.addAttribute("longTitle", shortTitle);
        	
           
		}
       
        
         
        try {  
            // PrintWriter���б����ʽ�Ĺ��췽�������ڽ����������  
            PrintWriter pw = new PrintWriter(filePath, "utf-8");  
            XMLWriter xmlWriter = new XMLWriter(pw);  
            xmlWriter.write(document);  
            xmlWriter.close();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
    /** 
     * ����XML 
     * @param filePath �ļ�·�� 
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
