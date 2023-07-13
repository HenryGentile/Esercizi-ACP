package server;


import java.io.*;
import java.net.*;

import dispatcher.IDispatcher;

public class ServerThread extends Thread{
    //definiamo le variabili private in questione
    private IDispatcher dispatcher;
    private Socket socket;

    ServerThread(Socket s, IDispatcher d){
        socket=s;
        dispatcher=d;
    }

    public void run(){
        //apriamo i canali di flusso per lettura/scrittura sulla connessione socket
        try {
            DataInputStream datain = new DataInputStream(socket.getInputStream());
            DataOutputStream dataout = new DataOutputStream(socket.getOutputStream());

            String metodo = datain.readUTF();
            System.out.println(metodo + " getCmd" + " sendCmd " + metodo.compareTo("getCmd") + 
                                metodo.compareTo("sendCmd"));

            int x;

            if(metodo.compareTo("getCmd") == 0){

                //se il mmetodo è get allora richiamiamo la get del dispatcher e estraiamo il metodo
                x = dispatcher.getCmd();

                System.out.println("\n[SERVER] Metodo ottenuto:" + metodo + ":" + x);

                //in questo modo passiamo al client il metodo estratto dalla coda
                dataout.writeInt(x);

            } else {
                if (metodo.compareTo("sendCmd") == 0){

                    x = datain.readInt();

                    System.out.println("\n[SERVER] Metodo ottenuro:" + metodo + ":" + x);
                    dispatcher.sendCmd(x);

                    //scriviamo sul flusso in uscita un ack in quanto il sender è in attesa di risposta
                    dataout.writeUTF("ACK");

                } else System.out.println("\n[SERVER] Metodo invalido");
            }

            socket.close();
            
        } catch (IOException e) {
            e.printStackTrace();
        }

        
    } 
}
