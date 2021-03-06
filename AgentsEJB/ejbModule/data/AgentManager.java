package data;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import model.AID;
import model.AbstractAgent;
import model.Agent;

@Singleton
@SuppressWarnings({"unused","rawtypes"})
public class AgentManager {
	
	private HashMap<AID,Agent> running;
	
	@PostConstruct
	void init()
	{
		running = new HashMap<AID,Agent>();
	}

//	static{
//		running = new HashMap<AID,Agent>();
//	}
	
	public HashMap<AID, Agent> getRunning() {
		return running;
	}
	
	public void addAgent(AID aid, AbstractAgent a)
	{
		running.put(aid,a);
	}
	
	public void deleteAgent(AID a)
	{
		running.remove(a);
	}
	
	public void setRunning(HashMap<AID, Agent> running) {
		this.running = running;
	}
	
	public Agent getRunningAgent(AID aid)
	{ 
		// TODO za trenutne potrebe nabudzeno
		for(AID a:running.keySet())
		{
			if(a.getType().getName().equals(aid.getType().getName()))
				return running.get(a);
		}
		return null;
	}
	
	public void startAgent(AID aid, String path)
	{
		if(!running.containsKey(aid))
		{
			Object a = getAgentClass(path, aid.getType().getName());
			Agent ag = (Agent)a;
			ag.init(aid);
			running.put(aid,ag);
		}
	}
	
	public List<String> getAllAgentClasses(String pathToJar){
		ArrayList<String> retVal = new ArrayList<String>();
		JarFile jarFile = null;
		try {
			jarFile = new JarFile(pathToJar);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Enumeration<JarEntry> e = jarFile.entries();
	
		URL[] urls = new URL[1];
		try {
			urls[0] = new URL("jar:file:" + pathToJar+"!/");
		} catch (MalformedURLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		URLClassLoader cl = URLClassLoader.newInstance(urls);
	
		while (e.hasMoreElements()) {
		    JarEntry je = e.nextElement();
		    if(je.isDirectory() || !je.getName().endsWith(".class")){
		        continue;
		    }
		    // -6 because of .class
		    String className = je.getName().substring(0,je.getName().length()-6);
		    className = className.replace('/', '.');
		    String[] classInfo = className.split("\\.");
		    if(classInfo[0].equals("agents"))
		    {
		    	retVal.add(className);
		    }
	
		}
		try {
			jarFile.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return retVal;
	}
	
	public Object getAgentClass(String pathToJar, String agentName)
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
			    	Class<?> loadedClass = Class.forName(className);
			    	Agent ag = (Agent)loadedClass.newInstance();
			    	return ag;
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
	
}
