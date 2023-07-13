package disk;

import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

/*
 * Listener si mette in ascolto, riceve i messaggi, istanzia un nuovo thread 
 * e ne delega il compito ad un thread per non bloccare le operazioni
 */

public class DiskListener implements MessageListener{

    @Override
    public void onMessage(Message arg0) {
        
        try {

            MapMessage message = (MapMessage) arg0;

            int valore = message.getInt("valore");
            int porto = message.getInt("porto");

            System.out.println("Listener: Messaggio Ricevuto. Avvio thread...");

            DiskThread diskThread = new DiskThread( valore, porto);

            diskThread.start();


        } catch (Exception e) {

            // TODO: handle exception
            e.printStackTrace();
        }

    }
    
}
