package queueapp;

import java.util.Hashtable;

import javax.jms.*;
import javax.naming.*;

public class Receiver {

	public static void main(String[] args) {

		System.out.println("Receiver : inizio...");
		
		Hashtable<String, String> prop = new Hashtable<String, String> ();
		
		prop.put( "java.naming.factory.initial", "org.apache.activemq.jndi.ActiveMQInitialContextFactory" );
		prop.put( "java.naming.provider.url", "tcp://127.0.0.1:61616" );
		
		//		jndi queue name   queue-name
		prop.put( "queue.test", "mytestqueue" );
		
		try{
			//inizailiziamo context, un interfaccia che ci consente di usare metodi per gestire oggetti con JMS
			Context jndiContext = new InitialContext(prop);
			
			// Lookup administered objects 
			QueueConnectionFactory queueConnFactory = (QueueConnectionFactory)jndiContext.lookup("QueueConnectionFactory");
			Queue queue = (Queue)jndiContext.lookup("test"); // il prefisso "queue." non fa parte del nome jndi
			
			//creazione connessione
    		QueueConnection queueConn = queueConnFactory.createQueueConnection();
    		queueConn.start();
    		
    	    QueueSession queueSession = queueConn.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
    	    QueueReceiver receiver = queueSession.createReceiver(queue);
    	   
           
    	    
    	    TextMessage message; 
            
            do{
            	System.out.println ("In attesa di messaggi!");
            	message = (TextMessage)receiver.receive(); //bloccante
            	System.out.println ("	+ messaggio ricevuto: " + message.getText());
            }while (message.getText().compareTo("fine") != 0); 
              
            //fase di clean-up: pulire tutti gli strumenti JNDI ottenuti          
            receiver.close();
            queueSession.close();
            queueConn.close();
            
               
            
		}catch(Exception e ){
			e.printStackTrace();
		}

	}

}