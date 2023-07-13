package dispatcher;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import interfacce.IDispatcher;

public class DispatcherWorker extends Thread{

    private IDispatcher dispatcher;
    private Socket socket;

    public DispatcherWorker(IDispatcher d, Socket s){
        dispatcher = d;
        socket = s;
    }

    public void run(){

        try {

            DataInputStream input = new DataInputStream(socket.getInputStream());

            String comando = input.readUTF();

            if( comando.compareTo("sendCmd") == 0) {
                System.out.println("[THREAD] " + comando + " ricevuto. Avvio procedura.");

                int command = input.readInt();

                dispatcher.sendCmd(command);

                input.close();

            } else{
                if( comando.compareTo("getCmd") == 0) {

                    System.out.println("[THREAD] " + comando + " ricevuto. Avvio procedura.");

                    DataOutputStream output = new DataOutputStream(socket.getOutputStream());
                    output.writeInt(dispatcher.getCmd());

                    output.close();

                } else{
                    System.out.println("[THTREAD] " + comando + " non valido");
                }
            }

            socket.close();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }


    
}
