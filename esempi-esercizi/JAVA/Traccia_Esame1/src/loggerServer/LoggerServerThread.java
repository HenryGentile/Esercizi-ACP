package loggerServer;


import java.net.DatagramPacket;

public class LoggerServerThread extends Thread{
 
    private ILogger logger;
    private DatagramPacket packet; 

    public LoggerServerThread(DatagramPacket pkt, ILogger logger){

        this.logger = logger;

        packet = pkt;

    }   

    public void run() {
        // il thread deve estrarre il contenuto dal pacchetto, ossia il dato in questione
        // e deve richiamare il metodo ILogger per farlo scrivere sul file

        try {

            System.out.println("Avvio Thread");

            String payloadString= new String(packet.getData(),0,packet.getLength());

            int dato = Integer.parseInt(payloadString);

            System.out.println(dato);


            logger.registraDato(dato);

        } catch (Exception e) {
            // TODO: handle exception
        }
    }

}
