package magazzino;

import javax.jms.*;

import coda.*;

public class MagazzinoThread extends Thread {

	private QueueConnection conn;
	private TextMessage msg;
	private Coda coda;
	
	public MagazzinoThread ( QueueConnection qc, TextMessage message, Coda c){
			conn = qc;
			msg = message;
			coda = c;
	}
	
	public void run (){
		/*
		 * Splitto il messaggio con il carattere # e valuto richiesta e id
		 * deposita faccio inserimento nella coda contenente il lock
		 * prelieva estraggo, apro una sessione, e lo posiziono sulla coda dove il client ha il listener
		 */

		 try {
			String[] text = msg.getText().split("#");
			if (text[0].compareTo("deposita") == 0){
				// metodo deposita. Invochiamo il deposita della queue
				System.out.println("[MAGAZZINO-LISTENER] Operazione:" + text[0] + " valore:" + text[1]);
				coda.inserisci(Integer.valueOf(text[1]));
			}else{

				System.out.println("[MAGAZZINO-LISTENER] Operazione:" + text[0]);

				String value = String.valueOf(coda.preleva());
				QueueSession session = conn.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);

				QueueSender sender = session.createSender((Queue) msg.getJMSReplyTo());

				TextMessage message = session.createTextMessage();

				message.setText(text[0] + "#" + value);

				sender.send(message);

				sender.close();
				session.close();

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	
}