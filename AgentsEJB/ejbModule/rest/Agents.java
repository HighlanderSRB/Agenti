package rest;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import data.AgentManager;
import mdb.TestProducer;
import model.AID;
import model.Agent;
import model.AgentType;
import utility.UtilsMethods;

@LocalBean
@Path("/agents")
@Stateless
public class Agents {

	@EJB
	AgentManager mng;
	
	/**
	 * Vraca listu svih agenata na sistemu
	 * @return
	 */
	
	@GET
	@Path("/classes")
	@Produces(MediaType.APPLICATION_JSON)
	public List<String> classes()
	{
		List<String> retVal = mng.getAllAgentClasses(new File("").getPath()+UtilsMethods.getJarName());
		return retVal;
	}
	
	/**
	 * Vraca listu pokrenutih agenata
	 * @return
	 */
	
	@GET
	@Path("/running")
	@Produces(MediaType.APPLICATION_JSON)
	public List<String> running()
	{
		Collection<Agent> agents = mng.getRunning().values();
		List<String> retVal = new ArrayList<String>();
		for(Agent a: agents)
		{
			retVal.add(a.getClass().getName());
		}
		return retVal;
	}
	
	/**
	 * Dostavlja listu pokrenutih agenata
	 */
	
	@POST
	@Path("/running")
	@Produces(MediaType.APPLICATION_JSON)
	public void postRunning()
	{

	}
	
	/**
	 * Dostavlja listu novih/postojecih agenata
	 */
	
	@POST
	@Path("/classes")
	@Produces(MediaType.APPLICATION_JSON)
	public void postClasses()
	{

	}
	
	/**
	 * pokrece agenta na sistemu
	 * @param type
	 * @param name
	 */
	
//	@PUT
//	@Path("/running/{type}/{name}")
//	@Produces(MediaType.APPLICATION_JSON)
//	public void activate(@PathParam("type") AgentType type, @PathParam("name") String name)
//	{
//		AID ai = new AID();
//		//ai.setHost(); kada implementujem listu hostova onda cu i ovo namestiti
//		ai.setType(type);
//		ai.setName(name);
//		TestProducer.startMsg(type.getName());
//	}
	
//	/**
//	 * zaustavlja pokrenutog agenta i brise ga iz liste
//	 * @param aid
//	 */
//	
//	@DELETE
//	@Path("/running/{aid}")
//	@Produces(MediaType.APPLICATION_JSON)
//	public void delete(@PathParam("aid") AID aid)
//	{
//		mng.deleteAgent(aid);
//	}
}
