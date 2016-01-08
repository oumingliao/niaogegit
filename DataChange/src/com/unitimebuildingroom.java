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

public class unitimebuildingroom {
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
		result = stmt.executeQuery(
				"SELECT DISTINCT B_NAME,SCHOOLEREA From ut_classroom");//SELECT name From UNITIMEDEPARTMENT
		
		
		
		String file = "E:\\buildingroom1.xml"; // 锟侥硷拷锟斤拷锟轿伙拷锟� 
		unitimebuildingroom dj = new unitimebuildingroom();  
		
        Document document = DocumentHelper.createDocument();  
        
        Element employees = document.addElement("buildingsRooms");  
        employees.addAttribute("campus", "CQU");
        employees.addAttribute("term", "春");
        employees.addAttribute("year", "2015");
        
     	String buildinglocationX = new String();
        String buildinglocationY = new String();
        String externalId1 = "";
        int temp = 1;
        while (result.next()){// 锟叫讹拷锟斤拷没锟斤拷锟斤拷一锟斤拷
   
        	externalId1 =  temp+"";
        	Element employee = employees.addElement("building");  
			String name = result.getString(1);
			String nameare  = new String();
			
			employee.addAttribute("externalId", externalId1);
			
			String schoolArea=result.getString(2);
			if(schoolArea.equals("A区")){
				buildinglocationX="29.568250";
				buildinglocationY="106.466880";
				nameare = name +"-"+ "A区";
				
			}
			else if(schoolArea.equals("B区")){
				buildinglocationX="29.568250";
				buildinglocationY="106.466880";
				nameare = name +"-"+ "B区";
			}
			else if(schoolArea.equals("D区")){
				buildinglocationX="29.598060";
				buildinglocationY="106.298528";
				nameare = name +"-"+ "D区";
			}
			
			
		    employee.addAttribute("locationX", buildinglocationX);
		    employee.addAttribute("locationY", buildinglocationY);
			
			employee.addAttribute("abbreviation", nameare);
		    employee.addAttribute("name", nameare);
		  
		    ResultSet subresult = null;// 锟斤拷询锟斤拷菘锟�
		   // conn = DriverManager.getConnection(url, DBUSER, password); // 锟斤拷锟斤拷锟斤拷菘锟�
			// Statement锟接匡拷要通锟斤拷connection锟接匡拷锟斤拷锟斤拷锟斤拷实锟斤拷锟斤拷锟�
			stmt = conn.createStatement();
			// 执锟斤拷SQL锟斤拷锟斤拷锟斤拷锟窖拷锟捷匡拷
			subresult = stmt.executeQuery("SELECT * From ut_classroom where  B_NAME ="+"'"+result.getString(1)+"'");
			int temp1=1;
			while(subresult.next()){
				Element subemployee = employee.addElement("room");
				String externalId = subresult.getString(2)+"-"+temp1;
				String roomNumber = subresult.getString(7);
				String locationX = buildinglocationX;
				String locationY = buildinglocationY;
				
				String roomClassification = subresult.getString(9);
				String capacity = subresult.getString(12);
				String scheduledRoomType = "";
				if(roomClassification.contains("教室"))
				{
					scheduledRoomType="genClassroom";
				}
				else if(roomClassification.contains("机房"))
				{
					scheduledRoomType="computingLab";
				}
				else if(roomClassification.contains("体育"))
				{
					scheduledRoomType="Stadium";
				}
				else if(roomClassification.contains("实验室"))
				{
					scheduledRoomType="Lab";
				}
				else
				{
					scheduledRoomType="specialUse";
				}
					
				subemployee.addAttribute("externalId", externalId);
								
				subemployee.addAttribute("locationX", locationX);
				subemployee.addAttribute("locationY", locationY);
				
				
				subemployee.addAttribute("roomNumber", roomNumber);
				
				subemployee.addAttribute("roomClassification", roomClassification);
				subemployee.addAttribute("capacity", capacity);
				subemployee.addAttribute("instructional", "T");
				subemployee.addAttribute("scheduledRoomType", scheduledRoomType);
				Element ssubemployee = subemployee.addElement("roomDepartments");
				if(subresult.getString(11)!=null)
				{
					
					Element sssubemployee = ssubemployee.addElement("assigned");
					String departmentNumber = subresult.getString(11);
					sssubemployee.addAttribute("departmentNumber", departmentNumber);
					sssubemployee.addAttribute("percent", "100");
				}
				else
				{
					Element sssubemployee = ssubemployee.addElement("assigned");
					sssubemployee.addAttribute("departmentNumber", "74");
					sssubemployee.addAttribute("percent", "100");
					/*if(subresult.getString(1).equals("D区"))
					{
						for(int i=1;i<38;i++)
						{
							Element sssubemployee = ssubemployee.addElement("assigned");
							String j = "";
							if(i<10)
							{
								j="0"+i;
							}
							else j=i+"";
							sssubemployee.addAttribute("departmentNumber", j);
							sssubemployee.addAttribute("percent", "100");
						}
						Element sssubemployee = ssubemployee.addElement("assigned");
						sssubemployee.addAttribute("departmentNumber", "74");
						sssubemployee.addAttribute("percent", "100");
					}
					else
					{
						for(int i=1;i<38;i++)
						{
							if(i==4||i==5||i==7||i==9||i==10||i==31||i==34||i==30)
							{
								continue;
							}
							Element sssubemployee = ssubemployee.addElement("assigned");
							String j = "";
							if(i<10)
							{
								j="0"+i;
							}
							else j=i+"";
							sssubemployee.addAttribute("departmentNumber", j);
							sssubemployee.addAttribute("percent", "100");
						}
						Element sssubemployee = ssubemployee.addElement("assigned");
						sssubemployee.addAttribute("departmentNumber", "74");
						sssubemployee.addAttribute("percent", "100");
					}*/
					
				}
				temp1++;
				
			}
			
			temp++;
			
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
    /** 
     * 锟斤拷锟斤拷XML 
     * @param filePath 锟侥硷拷路锟斤拷 
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
