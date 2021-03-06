package mdb;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import data.AgentManager;
import model.ACLMessage;
import model.AID;
import model.Agent;

@MessageDriven(activationConfig =
{
  @ActivationConfigProperty(propertyName="destinationType",
    propertyValue="javax.jms.Queue"),
  @ActivationConfigProperty(propertyName="destination",
    propertyValue="java:jboss/exported/jms/queue/agentQueue")
})

public class AgentMsgConsumer implements MessageListener {

	@EJB
	AgentManager mng;
	
	@Override
	public void onMessage(Message arg0) {
		try {
			ACLMessage acl = (ACLMessage) ((ObjectMessage) arg0).getObject();
			for(AID a:acl.getReceivers())
			{
				Agent ag = mng.getRunningAgent(a);
				ag.handleMessage(acl);
			}
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
