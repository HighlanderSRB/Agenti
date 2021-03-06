package rest;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import model.ACLMessage;
import model.Performative;

@LocalBean
@Path("/messages")
@Stateless
public class Messages {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Performative> performatives()
	{
		List<Performative> performatives = new ArrayList<Performative>(EnumSet.allOf(Performative.class));
		return performatives;
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void message(ACLMessage msg)
	{

	}
}
