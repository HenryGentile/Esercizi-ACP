package client;

import java.util.Hashtable;
import javax.jms.*; 
import javax.naming.*;

/*
 * Client. Ciascun client può inviare N messaggi sulla coda Richiesta. Ogni messaggio contiene 2
 * informazioni: (i) tipo di richiesta (deposita o preleva) e (ii) id_articolo (rappresentato da un intero).
 */

public class Client {
	
	private static final int N = 10;
	private static final String[] richieste = new String[] {"deposita" , "preleva"};
	
	public static void main(String[] args) {
		
		Hashtable<String, String> prt = new Hashtable<>();
		prt.put("java.naming.factory.initial", "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
		prt.put("java.naming.provider.url", "tcp://127.0.0.1:61616");

		prt.put("queue.Richiesta", "Richiesta");
		prt.put("queue.Risposta", "Risposta");

		try {

			Context context = new InitialContext(prt);
			QueueConnectionFactory cntFactory = (QueueConnectionFactory) context.lookup("QueueConnectionFactory");
			Queue queue = (Queue) context.lookup("Richiesta");
			Queue Dqueue = (Queue) context.lookup("Risposta");

			QueueConnection connection = cntFactory.createQueueConnection();
			connection.start();

			QueueSession session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);

			MessageProducer producer = session.createProducer(queue);

			//creiamo un listener per permettere al client l'invio asincrono dei messaggi
			QueueReceiver receiver = session.createReceiver(Dqueue);
			ClientListener listenerC = new ClientListener();
			receiver.setMessageListener(listenerC);

			//FASE DI PRODUZIONE
			TextMessage msg = session.createTextMessage();
			for(int i = 0; i < N; i++){
				int id = (int)(Math.random() * 50);
				String req = richieste[(int)(Math.random() % 2)];
				msg.setText(req + "#" + id);
				msg.setJMSReplyTo(Dqueue); //setto una destination per consentire al Listener Server di ottenerla
				producer.send(msg);
				System.out.println("[CLIENT] invio messaggio:" + msg.getText());
			}

			producer.close();
			session.close();
			connection.close();

		} catch (NamingException e) {
			e.printStackTrace();
		}catch (JMSException j){
			j.printStackTrace();
		}
	}
	
}
