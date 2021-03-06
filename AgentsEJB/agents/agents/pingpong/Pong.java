package agents.pingpong;

import mdb.OutOfMainProducer;
import model.ACLMessage;
import model.AID;
import model.AbstractAgent;
import model.AgentType;
import model.Performative;

public class Pong extends AbstractAgent {

	@Override
	protected void onMessage(ACLMessage msg)  {
		if(msg.getPerformative().equals(Performative.REQUEST))
		{
			System.out.println("PONG: Answering ping");
			AID a = new AID();
			a.setType(new AgentType("Ping",""));
			ACLMessage acl = new ACLMessage();
			acl.addReceiver(a);
			acl.setPerformative(Performative.AGREE);
			OutOfMainProducer.startMsg(acl);
		}else if(msg.getPerformative().equals(Performative.AGREE))
		{
		}
		
	}

}
