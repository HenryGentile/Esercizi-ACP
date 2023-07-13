package subsriber;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import coda.Icoda;

public class SubscriverListener implements MessageListener{

    private Icoda coda;

    public SubscriverListener(Icoda queue){
        coda = queue;
    }

    public void onMessage(Message message) {

        try {

            TextMessage msg = (TextMessage) message;
            String msgText = msg.getText();
            System.out.println("[SUBSCRIVER-LISTENER] Ricezione messaggio contenuto:" + msgText);
        
            TMagazine magazine = new TMagazine(coda, msgText);
            magazine.start();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
