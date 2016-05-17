package mdb;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import data.AgentManager;
import model.AID;
import model.AgentType;

@MessageDriven(activationConfig =
{
  @ActivationConfigProperty(propertyName="destinationType",
    propertyValue="javax.jms.Queue"),
  @ActivationConfigProperty(propertyName="destination",
    propertyValue="java:jboss/exported/jms/queue/mojQueue")
})

public class TestConsumer implements MessageListener {

	@EJB
	AgentManager mng;
	
	@Override
	public void onMessage(Message arg0) {
		TextMessage tmsg = (TextMessage) arg0;
		try {
			String text = tmsg.getText();
			AID ai = new AID();
			ai.setType(new AgentType(text,""));
			mng.startAgent(ai, "C:/Users/Komp/Desktop/AgentiF2/Workspace/AgentsEJB/dist/Agents.jar");
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
