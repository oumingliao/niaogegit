package com;

	import java.io.BufferedInputStream;
	import java.io.File;
	import java.io.FileInputStream;
	import java.io.FileOutputStream;
	import java.io.IOException;
	import java.io.InputStream;
	import java.io.OutputStream;
	import java.util.Properties;
	/**
	 * 
	 */

	/**
	 * @author lenovo
	 *
	 */
	public class AcessTest {
	 
	 private static final String PROPERTY_FILE = "F:/program/data.properties";   
	 
	 /**  
	 25.     * 根据Key 读取Value  
	 26.     *   
	 27.     * @param key  
	 28.     * @return  
	 29.     */  
	  public String readData(String key) {   
	         Properties props = new Properties();   
	         try {   
	             InputStream in = new BufferedInputStream(new FileInputStream(   
	                     PROPERTY_FILE));   
	             props.load(in);   
	             in.close();   
	             String value = props.getProperty(key);   
	             return value;   
	         } catch (Exception e) {   
	             e.printStackTrace();   
	             return null;   
	         }   
	     }   
	  
	        
	    /**  
	 .     * 修改或添加键值对 如果key存在，修改 反之，添加。  
	 48.     *   
	 49.     * @param key  
	 50.     * @param value  
	 51.     */  
	    public void writeData(String key, String value) {   
	        Properties prop = new Properties();   
	        try {   
	            File file = new File(PROPERTY_FILE);   
	            if (!file.exists())   
	                file.createNewFile();   
	            InputStream fis = new FileInputStream(file);   
	            prop.load(fis);   
	            fis.close();//一定要在修改值之前关闭fis   
	            OutputStream fos = new FileOutputStream(PROPERTY_FILE);   
	            prop.setProperty(key, value);   
	            prop.store(fos, "Update '" + key + "' value");   
	            prop.store(fos, "just for test");   
	            
	            fos.close();   
	        } catch (IOException e) {   
	            System.err.println("Visit " + PROPERTY_FILE + " for updating "  
	                    + value + " value error");   
	        }
	    }

	 /**
	  * @param args
	  */
	 public static void main(String[] args) {
	  // TODO Auto-generated method stub
	  AcessTest test = new AcessTest();
	  test.writeData("name", "xiaozhang");
	  test.writeData("sex", "male");
	  
	//  test.writeData("name", "xiaoyang");
	  
	  String name = test.readData("name");
	  String sex = test.readData("sex");
	  System.out.println("The name of the person is:" + name + ", and the sex is:" + sex);
	 }

	}

