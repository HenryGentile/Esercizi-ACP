package client;

import java.util.Hashtable;
import java.util.Random;

import javax.jms.MapMessage;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;

public class Client{

    public static void main(String[] args){

        Hashtable<String, String> prt = new Hashtable<String, String>();

        prt.put("java.naming.factory.initial", "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
        prt.put("java.naming.provider.url", "tcp://127.0.0.1:61616");

        prt.put("queue.PrintRequest", "PrintRequest");

        System.out.println("[CLIENT] inizializzato con nome Printer " + args[0]);

        try {

            Context context = new InitialContext(prt);

            QueueConnectionFactory factory = (QueueConnectionFactory) context.lookup("QueueConnectionFactory");

            QueueConnection connection = factory.createQueueConnection();
            connection.start();

            QueueSession session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);

            Queue queue = (Queue) context.lookup("PrintRequest");

            QueueSender sender = session.createSender(queue);

            MapMessage msg = session.createMapMessage();

            msg.setString("nomePrinter", args[0]);
            
            
            for (int i = 0; i < 5; i++){
                
                int valore = new Random().nextInt(41);

                msg.setString("nomeDocumento", "doc" + String.valueOf(valore));

                sender.send(msg);

                System.out.println("[CLIENT] " + (i+1) + "o messaggio inviato:" + msg.getString("nomeDocumento"));

            }

            sender.close();
            session.close();
            connection.close();


        }catch(Exception e){

            e.printStackTrace();

        }

    }


}