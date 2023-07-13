package publisher;

import java.util.Hashtable;
import javax.jms.*;
import javax.naming.*;

public class ControlStation {

    public static final int N = 20;
    public static String[] methods;
    

    public static void main(String[] args) throws JMSException, NamingException {
        Hashtable<String,String> propieties = new Hashtable<String,String>();
        propieties.put("java.naming.factory.initial", "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
        propieties.put("java.naming.provider.url","tcp://127.0.0.1:61616");
        propieties.put("topic.MyTopic2", "MyTopic2");

        Context context = new InitialContext(propieties);

        TopicConnectionFactory topicFactory = (TopicConnectionFactory) context.lookup("TopicConnectionFactory");

        Topic topic = (Topic) context.lookup("MyTopic2");

        TopicConnection TopicConn = topicFactory.createTopicConnection();
        //non avvio la connessione, a quello deve pensarci il subscriver

        TopicSession TopicSess = TopicConn.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);

        TopicPublisher publisher = TopicSess.createPublisher(topic); //publisher sul topic generato

        TextMessage msg = TopicSess.createTextMessage();

        methods = new String[3];

        methods[0]="startSensor";
        methods[1]="stopSensor";
        methods[2]="read";


        for (int i = 0; i < N; i++){
            int met = (int)((Math.random() * 100) % 3);
            msg.setText(methods[met]);
            System.out.println("[CONTROL-STATION] invio messaggio:" + methods[met]);

            publisher.send(msg);
        }

        publisher.close();
        TopicSess.close();
        TopicConn.close();

    }
        
}
