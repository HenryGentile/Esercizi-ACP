package disk;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import loggerServer.ILogger;

public class LoggerProxy implements ILogger{

    private int porto;

    public LoggerProxy(int p){
        porto = p;
    }

    @Override
    public void registraDato(int dato) {

        // TODO Auto-generated method stub
        // Metodo che tramite una socket UDP il valore al ServerSkeleton

        try {

            DatagramSocket socket = new DatagramSocket();

            //costruzione dell'array di byte da inserire come valore del packet UDP

            byte[] data = String.valueOf(dato).getBytes();

            try {

                DatagramPacket packet = new DatagramPacket(data, data.length, InetAddress.getByName("localhost"),porto );

                System.out.println("Thread Disk: Pacchetto formato. Invio al porto " + porto + "...");

                socket.send(packet);
            

                socket.close();
                
            } catch (Exception e) {
                // TODO: handle exception
            }
        } catch (SocketException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }

    
}
