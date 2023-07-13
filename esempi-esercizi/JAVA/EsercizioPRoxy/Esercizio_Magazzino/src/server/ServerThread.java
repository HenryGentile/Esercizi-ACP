package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;


import magazzino.*;

public class ServerThread extends Thread{

    private Socket socket;
    private IMagazzino magazzino;

    public ServerThread(Socket s, IMagazzino m){
        socket = s;
        magazzino = m;
    }

    public void run(){

        try {

            DataInputStream dataIn = new DataInputStream(socket.getInputStream());
            DataOutputStream dataOut = new DataOutputStream(socket.getOutputStream());

            String str = dataIn.readUTF();
            String[] str_split = str.split("#"); //sapendo il formato del messaggio sfrutto split

            if (str_split[0] == "preleva"){

                System.out.println("[SERVER THREAD] prelievo articolo:" + str_split[1]);

                int x = magazzino.preleva(str_split[1]);

                dataOut.writeInt(x);
            }else{
                if(str_split[0] == "deposita"){

                    int x = dataIn.readInt();
    
                    System.out.println("[SERVER THREAD] prelievo articolo:" + str_split[1] + " id:" + x);
    
                    magazzino.deposita(str_split[1], x);
    
                    dataOut.writeUTF("ACK");
                }else{
                    System.out.println("Composizione errata del messaggio (operazione#articolo)");
                }
            }

            

            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
}
