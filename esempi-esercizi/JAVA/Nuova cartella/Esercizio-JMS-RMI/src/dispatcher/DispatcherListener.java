package dispatcher;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

public class DispatcherListener implements MessageListener{

    @Override
    public void onMessage(Message arg0) {
        // TODO Auto-generated method stub
        MapMessage message = (MapMessage) arg0;
        try {

            String nomeDocumento = message.getString("nomeDocumento");
            String nomePrinter = message.getString("nomePrinter");

            DispatcherThread worker = new DispatcherThread(nomeDocumento, nomePrinter);

            System.out.println("[SERVER LISTENER] Ricezione meessaggio. Avvio Thread...");
            worker.start();
        } catch (JMSException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        

    }
    
}
