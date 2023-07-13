package magazzino;

import java.util.Hashtable;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;

import coda.*;
import codaimpl.*;

public class Magazzino {

	public static void main(String[] args) {
		Hashtable<String, String> prt = new Hashtable<>();
		prt.put("java.naming.factory.initial", "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
		prt.put("java.naming.provider.url", "tcp://127.0.0.1:61616");

		prt.put("queue.Richiesta", "Richiesta");

		try {

			Coda buffer = new CodaWrapperSynchr(new CodaCircolare(10));

			Context context = new InitialContext(prt);
			QueueConnectionFactory cntFactory = (QueueConnectionFactory) context.lookup("QueueConnectionFactory");
			Queue queue = (Queue) context.lookup("Richiesta");

			QueueConnection connection = cntFactory.createQueueConnection();
			connection.start();

			QueueSession session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);

			QueueReceiver receiver = session.createReceiver(queue);
			MagazzinoListener listenerM = new MagazzinoListener(buffer, connection);
			receiver.setMessageListener(listenerM);

			System.out.println("[MAGAZZINO] Inizio...");

		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

}