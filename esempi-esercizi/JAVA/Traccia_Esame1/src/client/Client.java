package client;

import java.util.Hashtable;

import javax.naming.*;
import javax.jms.*;



public class Client {
    
    public static void main(String[] args){
        /*
         * il client ha come compiti
         * prende in ingresso 1.valore da inviare 2.porto della socket UDP
         * crea connnessione al topic, e tutti gli elementi ad esso collegati
         * invia il mapMessagge e termina (no Listener)
         */

         Hashtable <String, String> prt = new Hashtable<String, String>();

         prt.put("java.naming.factory.initial", "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
         prt.put("java.naming.provider.url", "tcp://127.0.0.1:61616");

         prt.put("topic.Esame1", "TopicTracciaEsame1");

         try {

            Context context = new InitialContext(prt);

            TopicConnectionFactory topicConnectionFactory = (TopicConnectionFactory) context.lookup("TopicConnectionFactory");

            Topic topic = (Topic) context.lookup("Esame1");

            TopicConnection connection = topicConnectionFactory.createTopicConnection();

            TopicSession session = connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);

            TopicPublisher publisher = session.createPublisher(topic);

            MapMessage message = session.createMapMessage();

            message.setInt("valore", Integer.valueOf(args[0]));
            message.setInt("porto", Integer.valueOf(args[1]));

            publisher.send(message);

            System.out.println("Messaggio inviato:" + message.getInt("valore") + 
                                "-porto:" + message.getInt("porto"));

            //clean-up delle risorse. Fondamentale per chiudere il programma
            publisher.close();
            session.close();
            connection.close();

         } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
         }



    }

}
