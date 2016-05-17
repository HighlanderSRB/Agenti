package agents.pingpong;

import mdb.AgentMsgProducer;
import mdb.TestProducer;
import model.ACLMessage;
import model.AID;
import model.AgentType;
import model.Performative;

public class PingPongTest {

	public static void main(String[] args)
	{
		AID ping = new AID();
		ping.setType(new AgentType("Ping",""));
		AID pong = new AID();
		pong.setType(new AgentType("Pong",""));
		
		TestProducer.startMsg("Ping");
		TestProducer.startMsg("Pong");
		
		ACLMessage acl = new ACLMessage();
		acl.addReceiver(ping);
		acl.setPerformative(Performative.REQUEST);
		AgentMsgProducer.startMsg(acl);
	}
}
