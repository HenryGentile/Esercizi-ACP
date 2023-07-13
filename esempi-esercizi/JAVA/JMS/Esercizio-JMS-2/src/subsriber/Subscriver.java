package subsriber;

import java.io.IOException;
import java.util.*;
import javax.jms.*;
import javax.naming.*;

import coda.Icoda;
import codaImp.CodaCircolare;
import codaImp.CodaWrapperLock;

public class Subscriver {

    public static final int D = 5;
    
    public static void main(String[] args) throws JMSException, NamingException,IOException{

        Hashtable<String,String> propieties = new Hashtable<String,String>();
        propieties.put("java.naming.factory.initial", "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
        propieties.put("java.naming.provider.url","tcp://127.0.0.1:61616");
        propieties.put("topic.MyTopic2", "MyTopic2");
        
        Icoda q = new CodaCircolare(D);
        Icoda coda = new CodaWrapperLock(q);
        String FileWriter = args[0];

        Context context = new InitialContext(propieties);

        TopicConnectionFactory topicFactory = (TopicConnectionFactory) context.lookup("TopicConnectionFactory");

        Topic topic = (Topic) context.lookup("MyTopic2");

            
        TopicConnection topicConn = topicFactory.createTopicConnection();
        topicConn.start();

        TopicSession topicSess = topicConn.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);

        TopicSubscriber sub = topicSess.createSubscriber(topic);

        SubscriverListener listerer = new SubscriverListener( coda );
        sub.setMessageListener(listerer);

        System.out.println("[SUBSCRIVER] set avviato");

        TExecutor executor = new TExecutor(coda, FileWriter);
        executor.start();

    }

}
