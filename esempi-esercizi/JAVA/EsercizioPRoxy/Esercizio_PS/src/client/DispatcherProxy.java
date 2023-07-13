//proxy dispatcher che serve al client per inoltrare le richieste al dispatcher server
package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.*;

import dispatcher.*;
import java.io.*;

public class DispatcherProxy implements IDispatcher{

    private String addr;
    private int port;
    public DispatcherProxy(String a, int p){
        addr=new String(a);
        port=p;
    }

    public int getCmd(){
        int x = 0;
        //creiamo una socket e un flusso di ricezione/invio dati
        try {
            
            Socket skt = new Socket (addr, port);
            DataOutputStream dataout = new DataOutputStream(skt.getOutputStream());
            DataInputStream datain = new DataInputStream(skt.getInputStream());
            
            //estreaiamo la stringa ricevuta sul flusso e ne estraiamo l'intero per risalire all'itruzione
            dataout.writeUTF("getCmd");
            x = datain.readInt();

            //chiudiamo la socket
            skt.close();

        } catch (UnknownHostException u ){
			u.printStackTrace();
		}catch (IOException e ){
			e.printStackTrace();
		}

        return x;
    }

    public void sendCmd(int cmd){

        try {
            
            Socket skt = new Socket (addr, port);
            DataOutputStream dataout = new DataOutputStream(skt.getOutputStream());
            DataInputStream datain = new DataInputStream(skt.getInputStream());
            
            //estreaiamo la stringa ricevuta sul flusso e ne estraiamo l'intero per risalire all'itruzione
            dataout.writeUTF("sendCmd");
            dataout.writeInt(cmd);

            datain.readUTF();	// forza il proxy ad attendere una risposta dal server
								// nel caso di metodo che restituisce void

            //chiudiamo la socket
            skt.close();

        } catch (UnknownHostException u ){
			u.printStackTrace();
		}catch (IOException e ){
			e.printStackTrace();
		}
    }
}