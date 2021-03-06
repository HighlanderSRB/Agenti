package mdb;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;

import model.ACLMessage;

public class OutOfMainProducer {

	public static void startMsg(ACLMessage message)
	{
		try{
			Context context = new InitialContext();
			ConnectionFactory cf = (ConnectionFactory) context
				.lookup("java:jboss/exported/jms/RemoteConnectionFactory");
			final Queue queue = (Queue) context
				.lookup("java:jboss/exported/jms/queue/agentQueue");
//			ConnectionFactory cf = (ConnectionFactory) context
//					.lookup("jms/RemoteConnectionFactory");
//			final Queue queue = (Queue) context
//					.lookup("jms/queue/agentQueue");
			context.close();
			Connection connection = cf.createConnection();
			final Session session = connection.createSession(false,
				Session.AUTO_ACKNOWLEDGE);
			
			connection.start();
	
			
			ObjectMessage msg = session.createObjectMessage(message);
			
			MessageProducer producer = session.createProducer(queue);
			producer.send(msg);
			
			producer.close();
			connection.stop();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
