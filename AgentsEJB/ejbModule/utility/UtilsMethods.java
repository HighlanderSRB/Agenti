package utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import model.ACLMessage;
import model.Agent;

public class UtilsMethods {

	public static List<String> getAllAgents(String URI)
	{
		ArrayList<String> retVal = new ArrayList<String>();
		File folder = new File(URI);
		File[] listOfFiles = folder.listFiles();

	    for (int i = 0; i < listOfFiles.length; i++) {
	      if (listOfFiles[i].isFile()) {
	        retVal.add(listOfFiles[i].getName());
	      } else if (listOfFiles[i].isDirectory()) {
	    	  retVal.addAll(getAllAgents(URI+"/"+listOfFiles[i].getName()));
	      }
	    }
		return retVal;
	}
	
	
	public static String getJarName()
	{
		Properties prop = new Properties();
		InputStream input = null;

		try {

			input = new FileInputStream("build.properties");

			// load a properties file
			prop.load(input);

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return prop.getProperty("jarname");
	}
	
	public static String getMasterNode()
	{
		Properties prop = new Properties();
		InputStream input = null;

		try {

			input = new FileInputStream("master.properties");

			// load a properties file
			prop.load(input);

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return prop.getProperty("masterNode");
	}
	
	public static String getCurrentIPaddress()
	{
		String address = null;
		try {
			address = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return address;
	}
	
	public static String getPcName()
	{
		String name = null;
		try{
			name = InetAddress.getLocalHost().getHostName();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return name;
	}
	

	public static Agent getAgentClass(String pathToJar, String agentName)
	{
		JarFile jarFile = null;
		try{
			jarFile = new JarFile(pathToJar);
			Enumeration<JarEntry> e = jarFile.entries();
	
			URL[] urls = { new URL("jar:file:" + pathToJar+"!/") };
			URLClassLoader cl = URLClassLoader.newInstance(urls);
	
			while (e.hasMoreElements()) {
			    JarEntry je = e.nextElement();
			    if(je.isDirectory() || !je.getName().endsWith(".class")){
			        continue;
			    }
			    // -6 because of .class
			    String className = je.getName().substring(0,je.getName().length()-6);
			    className = className.replace('/', '.');
			    String[] params = className.split("\\.");
			    if(params[params.length - 1].equals(agentName))
			    {
			    	Class c = cl.loadClass(className);
			    	Agent a = (Agent)c.newInstance();
			    	return a;
			    	//System.out.println(params[params.length - 1]);
			    }
	
			}
			jarFile.close();
		}catch(Exception e)
		{
			e.printStackTrace();
			try {
				jarFile.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return null;
	}
	
	public static void main(String[] args)
	{
		Agent a = getAgentClass("C:/Users/Komp/Desktop/AgentiF2/Workspace/AgentsEJB/dist/Agents.jar","Ping");
		a.handleMessage(new ACLMessage());
	}

}
