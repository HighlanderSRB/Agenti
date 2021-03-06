package agents.pingpong;

import mdb.OutOfMainProducer;
import model.ACLMessage;
import model.AID;
import model.AbstractAgent;
import model.AgentType;
import model.Performative;

public class Ping extends AbstractAgent {

	@Override
	protected void onMessage(ACLMessage msg) {
		if(msg.getPerformative().equals(Performative.REQUEST))
		{
			System.out.println("PING: Im in ping");
			System.out.println("PING: Calling pong");
			AID a = new AID();
			a.setType(new AgentType("Pong",""));
			ACLMessage acl = new ACLMessage();
			acl.addReceiver(a);
			acl.setPerformative(Performative.REQUEST);
			OutOfMainProducer.startMsg(acl);
		}else if(msg.getPerformative().equals(Performative.AGREE))
		{
			System.out.println("PING: I got answer from pong");
		}
		
	}

}
