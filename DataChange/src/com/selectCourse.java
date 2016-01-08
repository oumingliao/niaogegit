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

public class selectCourse {
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
		public static final String DBUSER = "timetable";
		public static final String password = "unitime";
	/*	public static void main(String[] args) throws Exception {
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
			result = stmt.executeQuery("select t.xh,t.xsxm,t.nj,t.zydm from unitime_selkc_141501 t where t.zydm='0901' group by t.xh,t.xsxm,t.nj,t.zydm");
			
			
			String file = "E:\\xml\\select.xml"; // 锟侥硷拷锟斤拷锟轿伙拷锟� 
			selectCourse dj = new selectCourse();
			 Document document = DocumentHelper.createDocument();  
		        
		       // Element employees = document.addElement("departments");  
		        Element employees = document.addElement("request"); 
		        employees.addAttribute("campus", "CQU");
		        employees.addAttribute("year", "2014");
		        employees.addAttribute("term", "Spring");
		        
		        
		        
		        while (result.next()){// 锟叫讹拷锟斤拷没锟斤拷锟斤拷一锟斤拷
		        	//UNITIMEDEPARTMENT
		        	Element employee = employees.addElement("student");  
					String studentcode = result.getString(1);
					employee.addAttribute("key", studentcode);
					
					Element updateDemo = employee.addElement("updateDemographics");
					
					Element name = updateDemo.addElement("name");
					String lastname = result.getString(2);
					name.addAttribute("first", "");
					name.addAttribute("middle", "");
					name.addAttribute("last", lastname);
					
					Element acadArea = updateDemo.addElement("acadArea");
					String nj = result.getString(3);
					acadArea.addAttribute("abbv", "A");
					if(nj.equals("2014"))
					{
						acadArea.addAttribute("classification", "01");
					}
					if(nj.equals("2013"))
					{
						acadArea.addAttribute("classification", "02");
					}
					if(nj.equals("2012"))
					{
						acadArea.addAttribute("classification", "03");
					}
					if(nj.equals("2011"))
					{
						acadArea.addAttribute("classification", "04");
					}
					
					Element major = acadArea.addElement("major");
					String code = result.getString(4);
					major.addAttribute("code", code);
		           
					
					Element updateCourse = employee.addElement("updateCourseRequests");
					updateCourse.addAttribute("commit", "true");
					
					
					 ResultSet aresult = null;// 锟斤拷询锟斤拷菘锟�
						//conn = DriverManager.getConnection(url, DBUSER, password); // 锟斤拷锟斤拷锟斤拷菘锟�
					    stmt = conn.createStatement();
					    aresult = stmt.executeQuery(

"select c.title || c.xf || '(' || d.abbreviation || ')' as subject, t.xh,\n" +
"          t.xsxm,\n" + 
"          t.nj,\n" + 
"          t.zydm,\n" + 
"          t.zymc,\n" + 
"          t.kcdm,\n" + 
"          t.kcmc\n" + 
"  from Unitime_Selkc_141501 t, Ut_Courseplan c, ut_department d\n" + 
" where t.kcdm = c.coursenumber\n" + 
"   and c.deptcode = d.deptcode\n" + 
"   and t.zydm = '0901'\n" + 
"   and t.xh = "+"'"+result.getString(1)+"'"+"\n" + 
" group by t.xh,\n" + 
"          t.xsxm,\n" + 
"          t.nj,\n" + 
"          t.zydm,\n" + 
"          t.zymc,\n" + 
"          t.kcdm,\n" + 
"          t.kcmc,\n" + 
"          c.title,\n" + 
"          c.xf,\n" + 
"          d.abbreviation");
					    while (aresult.next()){
					    	String subjectArea = aresult.getString(1);
					    	String courseNumber = aresult.getString(7);
					    	Element courseOffering = updateCourse.addElement("courseOffering");
					    	courseOffering.addAttribute("subjectArea", subjectArea);
					    	courseOffering.addAttribute("courseNumber", courseNumber);
					    }
					    aresult.close();// 锟斤拷菘锟斤拷瓤锟斤拷锟斤拷
						stmt.close();
					    
	      
		       }
		        dj.createXml(file,document);  
		        dj.parserXml(file); 
		        
		        result.close();// 锟斤拷菘锟斤拷瓤锟斤拷锟斤拷
		    	stmt.close();
		    	conn.close();// 锟截憋拷锟斤拷菘锟�
		    	System.out.println("success");
		}
		
		public void createXml(String filePath,Document document) throws Exception {  
	       
				
			
	       
	        
	         
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
	    *//** 
	     * 锟斤拷锟斤拷XML 
	     * @param filePath 锟侥硷拷路锟斤拷 
	     *//*  
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
	    }  */
}
