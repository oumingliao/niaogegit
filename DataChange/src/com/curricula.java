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

public class curricula {
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
			"select l.majorcode,l.majorname,r.deptcode from unitimemajor r,(select t.majorcode, t.majorname from ut_courseplan t group by t.majorcode, t.majorname)l where l.majorcode=r.code group by l.majorcode,l.majorname,r.deptcode ");//SELECT name From UNITIMEDEPARTMENT
	
	//"select t.subject,a.subject_area_abbreviation,t.staffid,f.lastname,f.firstname, t.kc_flag,t.courseid,t.xf from unitimetask t,unitimestaff f,timetable.subject_area a where t.subject = a.long_title and t.staffid = f.staffid"
	
	String file = "E:\\curricula.xml"; // 锟侥硷拷锟斤拷锟轿伙拷锟� 
	curricula dj = new curricula();  
	
    Document document = DocumentHelper.createDocument();  
    
    Element employees = document.addElement("curricula");  
    employees.addAttribute("campus", "CQU");
    employees.addAttribute("term", "春");
    employees.addAttribute("year", "2015");
    
   
    
    while (result.next()){// 锟叫讹拷锟斤拷没锟斤拷锟斤拷一锟斤拷
    	Element employee = employees.addElement("curriculum");  
		
    	String abbreviation = "A/"+result.getString(1);
    	String name = "A区/" +result.getString(2);
		employee.addAttribute("abbreviation",abbreviation);
	    employee.addAttribute("name", name);
	    
	  
	    Element academicArea = employee.addElement("academicArea");
	    academicArea.addAttribute("abbreviation","A");
	    
	    Element department = employee.addElement("department");
	    String code = result.getString(3);
	    department.addAttribute("code",code);
	   
	    Element major = employee.addElement("major");
	    String mcode = result.getString(1);
	    major.addAttribute("code",mcode);
	   
	    //Connection conn1 = null;// 锟斤拷示锟斤拷菘锟斤拷锟斤拷锟�
	    ResultSet aresult = null;// 锟斤拷询锟斤拷菘锟�
		//conn = DriverManager.getConnection(url, DBUSER, password); // 锟斤拷锟斤拷锟斤拷菘锟�
	    stmt = conn.createStatement();
	    aresult = stmt.executeQuery("select t.nj,t.entrollment from ut_courseplan t where  t.majorcode="+"'"+result.getString(1)+"'"+"group by t.nj,t.entrollment");
	    while (aresult.next()){
	    	Element classification = employee.addElement("classification");
		    classification.addAttribute("name","01");
	    	String nj= aresult.getString(1);
	    	

	    	 ResultSet subresult = null;// 锟斤拷询锟斤拷菘锟�
	 		stmt = conn.createStatement();
	 		subresult = stmt.executeQuery(

"select REGEXP_REPLACE(t.coursenumber,'[a-zA-Z]'), t.title, t.majorcode, t.nj, t.entrollment,t.DISCIPLINE" +
"  from UT_COURSEPLAN t,ut_department d\n" + 
" where t.majorcode = "+"'"+result.getString(1)+"'"+"\n" + 
"   and t.nj = "+"'"+nj+"'"+" and t.deptcode=d.deptcode\n" );
	 		
	 		String enrollment = aresult.getString(2);
	    	classification.addAttribute("enrollment",enrollment);
	    	
	 		Element academicClassification = classification.addElement("academicClassification");
	 		//String externalId = subresult.getString(4);
	 		//String acode = subresult.getString(4);
	 		academicClassification.addAttribute("externalId",nj);
	 		academicClassification.addAttribute("code",nj);
	 		
	 		
	 		while(subresult.next()){
	 			Element course = classification.addElement("course");
	 			String subject = subresult.getString(6);
	 			String courseNbr = subresult.getString(1);
	 			course.addAttribute("subject",subject);
	 			course.addAttribute("courseNbr",courseNbr);
	 			course.addAttribute("share","1.0000");
	 		}
	 		
	 		subresult.close();// 锟斤拷菘锟斤拷瓤锟斤拷锟斤拷
	 		stmt.close();
	    	
	    }
	    	
	    
	   // conn1.close();// 锟截憋拷锟斤拷菘锟�
	    aresult.close();// 锟斤拷菘锟斤拷瓤锟斤拷锟斤拷
		stmt.close();
	    
	   // String deptcode = result.getString(2);
			// 执锟斤拷SQL锟斤拷锟斤拷锟斤拷锟窖拷锟捷匡拷
	   
		
		
		
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
