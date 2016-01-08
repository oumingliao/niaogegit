package com;
import java.io.File;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
public class course {
	// ��������֮ǰ��classpath�����õ�jdbc�������jar��
	public static final String drive = "oracle.jdbc.driver.OracleDriver";
	/**
	 * 
	 * ���ӵ�ַ
	 * 
	 * 
	 */
	public static final String url = "jdbc:oracle:thin:@10.250.94.254:1521:ORCL";
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
	result = stmt.executeQuery(
	"select t.title as subject,t.deptcode," +
	"t.xf,REGEXP_REPLACE(t.coursenumber,'[A-Za-z]'),t.coursenumber,t.discipline," +
	"t.title from (select cp.*, d.abbreviation  from Ut_Courseplan cp, Ut_Department d where cp.deptcode = d.deptcode)" +
	" t group by t.title, t.deptcode, t.xf, t.abbreviation,t.coursenumber,t.discipline");//SELECT name From UNITIMEDEPARTMENT
	
	//"select t.subject,a.subject_area_abbreviation,t.staffid,f.lastname,f.firstname, t.kc_flag,t.courseid,t.xf from unitimetask t,unitimestaff f,timetable.subject_area a where t.subject = a.long_title and t.staffid = f.staffid"
	
	String file = "E:\\course.xml"; // �ļ����λ��  
	course dj = new course();  
	
    Document document = DocumentHelper.createDocument();  
    
    Element employees = document.addElement("offerings");  
    employees.addAttribute("campus", "CQU");
    employees.addAttribute("term", "秋");
    employees.addAttribute("year", "2015");
    
    ResultSet subresult = null;// ��ѯ��ݿ�
    ResultSet aresult = null;// ��ѯ��ݿ�
	stmt = conn.createStatement();
    
    while (result.next()){// �ж���û����һ��
    	Element employee = employees.addElement("offering");  
		//int add = 1;
		//String id = add+"";
		employee.addAttribute("id","");
		//add++;
	    employee.addAttribute("offered", "true");
	    employee.addAttribute("action", "insert");
	  
	    Element consent = employee.addElement("consent");
	    consent.addAttribute("type","department");
	    Element courseCredit = employee.addElement("courseCredit");
	    String fixedCredit = result.getString(3);
	    courseCredit.addAttribute("creditType","collegiate");
	    courseCredit.addAttribute("creditUnitType","semesterHours");
	    courseCredit.addAttribute("creditFormat","fixedUnit");
	    courseCredit.addAttribute("fixedCredit",fixedCredit);
	    Element course = employee.addElement("course");
	    String subject = result.getString(6);
	    String courseNbr = result.getString(4);
	    String courseNbrAll = result.getString(5);
	    String title = result.getString(1);
	    
	    //String chineseCourseNbr=courseNbr+"-"+title;
	    
	    course.addAttribute("subject",subject);
	    course.addAttribute("courseNbr",courseNbr);
	    course.addAttribute("controlling","true");
	    course.addAttribute("title",title);
	    course.addAttribute("scheduleBookNote","");
	   
	   
	    
	    aresult = stmt.executeQuery("select max(t.xkrslimit) from ut_course t where t.coursenumber="+"'" + courseNbrAll+"'");
	    while(aresult.next()){
	    	if(aresult.getString(1)!=null && aresult.getString(1)!="")
	    	{
	    	Element config = employee.addElement("config");
		    config.addAttribute("name","1");
	    	String limit = aresult.getString(1);
	    	config.addAttribute("limit",limit);
	    
	    
	    
	    
			// ִ��SQL�������ѯ��ݿ�
		subresult = stmt.executeQuery(
				"select t.subpart,\n" +
						"        case\n" + 
						"          when t.zzxs01 != '0' then\n" + 
						"           t.zzxs01 * 45\n" + 
						"          when t.zzxs02 != '0' then\n" + 
						"           t.zzxs02 * 45\n" + 
						"          when t.zzxs03 != '0' then\n" + 
						"           t.zzxs03 * 45\n" + 
						"          when t.zzxs04 != '0' then\n" + 
						"           t.zzxs04 * 45\n" + 
						"          when t.zzxs05 != '0' then\n" + 
						"           t.zzxs05 * 45\n" + 
						"          when t.zzxs06 != '0' then\n" + 
						"           t.zzxs06 * 45\n" + 
						"          when t.zzxs07 != '0' then\n" + 
						"           t.zzxs07 * 45\n" + 
						"          when t.zzxs08 != '0' then\n" + 
						"           t.zzxs08 * 45\n" + 
						"          when t.zzxs09 != '0' then\n" + 
						"           t.zzxs09 * 45\n" + 
						"          when t.zzxs10 != '0' then\n" + 
						"           t.zzxs10 * 45\n" + 
						"          when t.zzxs11 != '0' then\n" + 
						"           t.zzxs11 * 45\n" + 
						"          when t.zzxs12 != '0' then\n" + 
						"           t.zzxs12 * 45\n" + 
						"          when t.zzxs13 != '0' then\n" + 
						"           t.zzxs13 * 45\n" + 
						"          when t.zzxs14 != '0' then\n" + 
						"           t.zzxs14 * 45\n" + 
						"          when t.zzxs15 != '0' then\n" + 
						"           t.zzxs15 * 45\n" + 
						"          when t.zzxs16 != '0' then\n" + 
						"           t.zzxs16 * 45\n" + 
						"          when t.zzxs17 != '0' then\n" + 
						"           t.zzxs17 * 45\n" + 
						"          when t.zzxs18 != '0' then\n" + 
						"           t.zzxs18 * 45\n" + 
						"          when t.zzxs19 != '0' then\n" + 
						"           t.zzxs19 * 45\n" + 
						"          when t.zzxs20 != '0' then\n" + 
						"           t.zzxs20 * 45\n" + 
						"          when t.zzxs21 != '0' then\n" + 
						"           t.zzxs21 * 45\n" + 
						"          when t.zzxs22 != '0' then\n" + 
						"           t.zzxs22 * 45\n" + 
						"          when t.zzxs23 != '0' then\n" + 
						"           t.zzxs23 * 45\n" + 
						"          when t.zzxs24 != '0' then\n" + 
						"           t.zzxs24 * 45\n" + 
						"          else\n" + 
						"           45\n" + 
						"        end as newminperweek\n" + 
						"   from ut_course t\n" + 
						"  where t.coursenumber="+"'"+courseNbrAll+"'"+"\n" + 
						"  group by t.subpart,\n" + 
						"           t.zzxs01,\n" + 
						"           t.zzxs02,\n" + 
						"           t.zzxs03,\n" + 
						"           t.zzxs04,\n" + 
						"           t.zzxs05,\n" + 
						"           t.zzxs06,\n" + 
						"           t.zzxs07,\n" + 
						"           t.zzxs08,\n" + 
						"           t.zzxs09,\n" + 
						"           t.zzxs10,\n" + 
						"           t.zzxs11,\n" + 
						"           t.zzxs12,\n" + 
						"           t.zzxs13,\n" + 
						"           t.zzxs14,\n" + 
						"           t.zzxs15,\n" + 
						"           t.zzxs16,\n" + 
						"           t.zzxs17,\n" + 
						"           t.zzxs18,\n" + 
						"           t.zzxs19,\n" + 
						"           t.zzxs20,\n" + 
						"           t.zzxs21,\n" + 
						"           t.zzxs22,\n" + 
						"           t.zzxs23,\n" + 
						"           t.zzxs24");
		
		while(subresult.next()){
			Element subpart = config.addElement("subpart");
			String type = "";
			if(subresult.getString(1).equals("理论"))
				type="Lec";
			else if(subresult.getString(1).equals("上机"))
				type="Com";
			else if(subresult.getString(1).equals("实验"))
				type="Lab";
			else if(subresult.getString(1).equals("实践"))
				type="Pra";
			subpart.addAttribute("type",type);
			subpart.addAttribute("suffix","");
			String minPerWeek = subresult.getString(2);
			subpart.addAttribute("minPerWeek",minPerWeek);
		}
		
		subresult = stmt.executeQuery("select t.* from ut_course t where t.coursenumber="+"'" + courseNbrAll+"'");
		
		while(subresult.next())
		{
			Element aclass = config.addElement("class");
			String idc = subresult.getString(5);
			aclass.addAttribute("id",idc);
			String type = "";
			if(subresult.getString(3).equals("理论"))
				type="Lec";
			else if(subresult.getString(3).equals("上机"))
				type="Com";
			else if(subresult.getString(3).equals("实验"))
				type="Lab";
			else if(subresult.getString(3).equals("实践"))
				type="Pra";
			aclass.addAttribute("type",type);
			aclass.addAttribute("suffix","1");
			String limita = subresult.getString(15);
			aclass.addAttribute("limit",limita);
			aclass.addAttribute("scheduleNote","");
			aclass.addAttribute("studentScheduling","true");
			
			Element date = aclass.addElement("date");
			
			String str=subresult.getString(12);
			String[] strs = str.split(",");
			ArrayList<String> result1 = new ArrayList();
			for(int i=0;i<strs.length;i++){
				String[] subresult1 = strs[i].split("-");
				for(int j=0;j<subresult1.length;j++){
					//System.out.println(subresult[j]);
					result1.add(subresult1[j]);
				}
			}
			String startString = result1.get(0);
			String endString = result1.get(result1.size()-1);
			//System.out.println(startString);
			//System.out.println(endString);
			String startDate="";
			String endDate="";
			if(startString.equals("1"))
			{
				startDate="3/2";
			}
			else if(startString.equals("2"))
			{
				startDate="3/9";
			}
			else if(startString.equals("3"))
			{
				startDate="3/16";
			}
			else if(startString.equals("4"))
			{
				startDate="3/23";
			}
			else if(startString.equals("5"))
			{
				startDate="3/30";
			}
			else if(startString.equals("6"))
			{
				startDate="4/6";
			}
			else if(startString.equals("7"))
			{
				startDate="4/13";
			}
			else if(startString.equals("8"))
			{
				startDate="4/20";
			}
			else if(startString.equals("9"))
			{
				startDate="4/27";
			}
			else if(startString.equals("10"))
			{
				startDate="5/4";
			}
			else if(startString.equals("11"))
			{
				startDate="5/11";
			}
			else if(startString.equals("12"))
			{
				startDate="5/18";
			}
			else if(startString.equals("13"))
			{
				startDate="5/25";
			}
			else if(startString.equals("14"))
			{
				startDate="6/1";
			}
			else if(startString.equals("15"))
			{
				startDate="6/8";
			}
			else if(startString.equals("16"))
			{
				startDate="6/15";
			}
			else if(startString.equals("17"))
			{
				startDate="6/22";
			}
			else if(startString.equals("18"))
			{
				startDate="6/29";
			}
			else if(startString.equals("19"))
			{
				startDate="7/6";
			}
			else if(startString.equals("20"))
			{
				startDate="7/13";
			}
			else if(startString.equals("21"))
			{
				startDate="7/20";
			}
			///判断结束时间
			if(endString.equals("1"))
			{
				endDate="3/8";
			}
			else if(endString.equals("2"))
			{
				endDate="3/15";
			}
			else if(endString.equals("3"))
			{
				endDate="3/22";
			}
			else if(endString.equals("4"))
			{
				endDate="3/29";
			}
			else if(endString.equals("5"))
			{
				endDate="4/5";
			}
			else if(endString.equals("6"))
			{
				endDate="4/12";
			}
			else if(endString.equals("7"))
			{
				endDate="4/19";
			}
			else if(endString.equals("8"))
			{
				endDate="4/26";
			}
			else if(endString.equals("9"))
			{
				endDate="5/3";
			}
			else if(endString.equals("10"))
			{
				endDate="5/10";
			}
			else if(endString.equals("11"))
			{
				endDate="5/17";
			}
			else if(endString.equals("12"))
			{
				endDate="5/24";
			}
			else if(endString.equals("13"))
			{
				endDate="5/31";
			}
			else if(endString.equals("14"))
			{
				endDate="6/7";
			}
			else if(endString.equals("15"))
			{
				endDate="6/14";
			}
			else if(endString.equals("16"))
			{
				endDate="6/21";
			}
			else if(endString.equals("17"))
			{
				endDate="6/28";
			}
			else if(endString.equals("18"))
			{
				endDate="7/5";
			}
			else if(endString.equals("19"))
			{
				endDate="7/12";
			}
			else if(endString.equals("20"))
			{
				endDate="7/19";
			}
			else if(endString.equals("21"))
			{
				endDate="7/26";
			}
			
			
			date.addAttribute("startDate",startDate);
			date.addAttribute("endDate",endDate);
			
			Element instructor = aclass.addElement("instructor");
			String ida = subresult.getString(6);
			String lname = subresult.getString(7);
			instructor.addAttribute("id",ida);
			instructor.addAttribute("lname",lname);
			instructor.addAttribute("fname","");
			instructor.addAttribute("share","100");
			instructor.addAttribute("lead","true");
		}
		
	}
	    }
    }
	
    dj.createXml(file,document);  
    dj.parserXml(file); 
    
    result.close();// ��ݿ��ȿ����
	stmt.close();
	conn.close();// �ر���ݿ�
	System.out.println("success");
}

public void createXml(String filePath,Document document) throws Exception {  
    
    
     
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
