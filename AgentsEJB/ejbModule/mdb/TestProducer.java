package mdb;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;

public class TestProducer {

	public static void startMsg(String message)
	{
		try{
			Context context = new InitialContext();
//			ConnectionFactory cf = (ConnectionFactory) context
//				.lookup("java:jboss/exported/jms/RemoteConnectionFactory");
//			final Queue queue = (Queue) context
//				.lookup("java:jboss/exported/jms/queue/agentQueue");
			ConnectionFactory cf = (ConnectionFactory) context
					.lookup("jms/RemoteConnectionFactory");
//			ConnectionFactory cf = (ConnectionFactory) context
//					.lookup("java:/ConnectionFactory");
			final Queue queue = (Queue) context
					.lookup("jms/queue/mojQueue");
			context.close();
			Connection connection = cf.createConnection();
			final Session session = connection.createSession(false,
				Session.AUTO_ACKNOWLEDGE);
			
			connection.start();
	
			
			TextMessage msg = session.createTextMessage(message);
			
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
