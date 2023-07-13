package client;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

//client listener destruttura i messaggi dell'utente e li invia al magazzino

public class ClientListener implements MessageListener{

	@Override
	public void onMessage(Message message) {
		
		TextMessage msg = (TextMessage) message;
		String text;
		try {
			text = msg.getText();
			String[] textSplit = text.split("#");
			System.out.println("[CLIENT_LISTENER] Arrivo risposta dall coda. Valore Prelevato:" + textSplit[1]);
		} catch (JMSException e) {
			e.printStackTrace();
		}
		
		
		
		
	}
	
}
