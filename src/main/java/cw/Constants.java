package cw;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Constants {
    /*
     * Default resource name that will be used to load properties.
     */
    private static String defaultResourceName = "/cw.properties";
	
	public static String DB_DRIVER;
	public static String DB_URL;
	
	public static String NULL_STRING = "N/A";
	
	public static String CW_URL;
	public static String CW_MASTER_CHANNEL_URL;
	public static String CW_SUB_CHANNEL_URL;
	public static String CW_ARTICLE_URL;
	
	static{
		Properties prop = new Properties();
		
		try {
		    //load a properties file
			InputStream inStream = Constants.class.getResourceAsStream(defaultResourceName);
			prop.load(inStream);
			
			//get the property value
			DB_DRIVER = prop.getProperty("jdbc.driverClassName");
			DB_URL = prop.getProperty("jdbc.url");
			
			//GET CW WEB URL
			CW_URL = prop.getProperty("cw.url");
			CW_MASTER_CHANNEL_URL = prop.getProperty("cw.master.channel.url");
			CW_SUB_CHANNEL_URL = prop.getProperty("cw.sub.channel.url");
			CW_ARTICLE_URL = prop.getProperty("cw.article.url");
			
		} catch (IOException ex) {
    		ex.printStackTrace();
        }
	}
}
