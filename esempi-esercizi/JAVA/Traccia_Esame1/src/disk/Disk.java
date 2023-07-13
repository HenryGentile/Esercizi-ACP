package disk;

import java.util.Hashtable;

import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;
import javax.naming.Context;
import javax.naming.InitialContext;

public class Disk {
    /*
     * Disk crea il topic, apre la connessione, e si mette in recezione
     * asincrona (creazione Listener) sul topi in questione
     */

    public static void main(String[] args){
        Hashtable <String, String> prt = new Hashtable<String, String>();

        prt.put("java.naming.factory.initial", "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
        prt.put("java.naming.provider.url", "tcp://127.0.0.1:61616");

        prt.put("topic.Esame1", "TopicTracciaEsame1");

        try {

            Context context = new InitialContext(prt);

            TopicConnectionFactory topicConnectionFactory = (TopicConnectionFactory) context.lookup("TopicConnectionFactory");

            Topic topic = (Topic) context.lookup("Esame1");

            TopicConnection connection = topicConnectionFactory.createTopicConnection();
            connection.start();

            TopicSession session = connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);

            TopicSubscriber subscriber = session.createSubscriber(topic);

            DiskListener listener = new DiskListener();
            subscriber.setMessageListener(listener);

            System.out.println("Disk avviato. Listener settato...");

        }catch(Exception e){
            
            e.printStackTrace();

        }

    }
}
