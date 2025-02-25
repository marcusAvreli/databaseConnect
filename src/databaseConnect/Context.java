package databaseConnect;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.config.properties.PropertiesConfigurationBuilder;

public class Context {
	
	
	
	public void initApp(String inWorkingDirPathName) {
		Properties appProperties = initSettings(inWorkingDirPathName,"/resources/application.properties");	           
		String log4jPahName = appProperties.getProperty("LOG4J");			
		Properties properties = initSettings(inWorkingDirPathName,log4jPahName);
		if (null != properties) {
			LoggerContext context = (LoggerContext) LogManager.getContext(false);
			Configuration config = new PropertiesConfigurationBuilder()
					.setConfigurationSource(ConfigurationSource.NULL_SOURCE).setRootProperties(properties)
					.setLoggerContext(context).build();

			context.setConfiguration(config);
			Configurator.initialize(config);

   		}
	}
	
	
/*
	+-------------------------------------------------------+
	|										======1======	|
	|	LOAD PROPERTIES										|
	|														|
	+-------------------------------------------------------+
*/	

	public static Properties initSettings(String inWorkingDirPathName,String inSettingsFileName) {	           
    	InputStream inputStream = null;
    	Properties result = null;    	
    	try {
			inputStream = new FileInputStream(new File(inWorkingDirPathName+inSettingsFileName));
			result = new Properties();
			result.load(inputStream);
    		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
    	return result;
    	
	}
	
	public static Properties initSettings(String inSettingsFileName) {
		String inWorkingDirPathName = System.getProperty("user.dir");
    	InputStream inputStream = null;
    	Properties result = null;    	
    	try {
			inputStream = new FileInputStream(new File(inWorkingDirPathName+"/"+inSettingsFileName));
			result = new Properties();
			result.load(inputStream);
    		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
    	return result;
    	
	}

	
	
/*
+-------------------------------------------------------+
|										======1======	|
|	SINGLETON											|
|														|
+-------------------------------------------------------+
 */
	public static Context getInstance() {
		return ContextSingleton.INSTANCE;
	}
	
	private static class ContextSingleton{
		private static final Context INSTANCE = new Context(null);
	}
	
	public Context(Object object) {
		
	}
}
