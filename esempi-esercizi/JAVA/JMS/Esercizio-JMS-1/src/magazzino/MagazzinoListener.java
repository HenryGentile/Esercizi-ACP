package magazzino;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.QueueConnection;
import javax.jms.TextMessage;

import coda.Coda;

public class MagazzinoListener implements MessageListener{

	private Coda coda;
	private QueueConnection connection;

	public MagazzinoListener(Coda b, QueueConnection qc){
		coda = b;
		connection = qc;
	}

	public void onMessage(Message message) {
		
		try {
			TextMessage msg = (TextMessage) message;
			System.out.println("[MAGAZZINO-LISTENER] Ricezione messaggio:" + msg.getText() + ". Elaborazione...");
			MagazzinoThread worker = new MagazzinoThread(connection, msg, coda);
			worker.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
