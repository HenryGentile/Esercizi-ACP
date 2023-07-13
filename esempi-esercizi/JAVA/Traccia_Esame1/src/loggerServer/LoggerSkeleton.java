package loggerServer;

import java.net.DatagramPacket;
import java.net.DatagramSocket;


public class LoggerSkeleton implements ILogger{

    private ILogger logger;
    private int porto; 

    public LoggerSkeleton(ILogger logger, int porto){

        this.logger = logger;

        this.porto = porto;
    }

    public void runSkeleton(){

        try {

            DatagramSocket socket = new DatagramSocket(porto);

            byte[] buffer = new byte[65508];

            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

            System.out.println("Logger Server in azione sul porto " + porto + "...");

            //loop infinito per mettere il server sempre in ascolto
            while(true){

                socket.receive(packet);

                System.out.println("Logger Server: Messaggio ricevuto.");

                //invochiamo un thread che si occupi del pacchetto appena ottenuto
                //this ci vuole per 
                LoggerServerThread thread = new LoggerServerThread(packet, this);

                thread.start();

            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

        public void registraDato(int dato)  {
        // TODO Auto-generated method stub
        logger.registraDato(dato);
    }
    
}
