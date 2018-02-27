package generic;

import java.io.FileInputStream;
import java.util.Properties;

public class Lib {
	
	public static String pptFile(String path,String key)
	{
		String v="";		
		try {
			Properties p = new Properties();
			p.load(new FileInputStream(path));
			v = p.getProperty(key);
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 		
		return v;
		
	}

}
