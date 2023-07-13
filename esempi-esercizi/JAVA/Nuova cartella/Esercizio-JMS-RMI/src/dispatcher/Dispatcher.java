package dispatcher;

import java.util.Hashtable;

import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.Queue;
import javax.naming.Context;
import javax.naming.InitialContext;


public class Dispatcher {
    
    public static void main(String[] args){

        Hashtable<String, String> prt = new Hashtable<String, String>();

        prt.put("java.naming.factory.initial", "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
        prt.put("java.naming.provider.url", "tcp://127.0.0.1:61616");

        prt.put("queue.PrintRequest", "PrintRequest");

        try {

            Context context = new InitialContext(prt);

            QueueConnectionFactory factory = (QueueConnectionFactory) context.lookup("QueueConnectionFactory");

            QueueConnection connection = factory.createQueueConnection();
            connection.start();

            QueueSession session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);

            Queue queue = (Queue) context.lookup("PrintRequest");

            QueueReceiver receiver = session.createReceiver(queue);

            DispatcherListener listener = new DispatcherListener();

            receiver.setMessageListener(listener);

            System.out.println("[DISPATCHER] Listener settato...");


        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

    }

}
