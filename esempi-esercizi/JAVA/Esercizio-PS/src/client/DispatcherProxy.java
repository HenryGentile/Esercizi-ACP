package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

import interfacce.IDispatcher;

public class DispatcherProxy implements IDispatcher{

    private int porto;

    public DispatcherProxy(int p){
        porto = p;
    }

    @Override
    public void sendCmd(int command) {
        // TODO Auto-generated method stub
        
        try {


            Socket socket = new Socket("127.0.0.1", porto);

            DataOutputStream output = new DataOutputStream(socket.getOutputStream());

            output.writeUTF("sendCmd");
            output.writeInt(command);

            socket.close();
        
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @Override
    public int getCmd() {
        // TODO Auto-generated method stub

        int valore = 0;

        try {


            Socket socket = new Socket("127.0.0.1", porto);

            DataOutputStream output = new DataOutputStream(socket.getOutputStream());
            DataInputStream input = new DataInputStream(socket.getInputStream());

            output.writeUTF("getCmd");

            //read bloccante
            valore = input.readInt();

            socket.close();

            
        
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return valore;
    }
    
}
