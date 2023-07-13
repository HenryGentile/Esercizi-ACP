package manager;

import java.io.DataOutputStream;
import java.net.Socket;
import java.rmi.RemoteException;

import Interfaces.*;

public class SubscriberProxy implements ISubscriber{

    private int porto;

    public SubscriberProxy(int p){
        porto = p;
    }

    @Override
    public void notifyAlert(int criticality) throws RemoteException {
        // TODO Auto-generated method stub

        Socket socket;
        try {

            socket = new Socket("127.0.0.1", porto);

            DataOutputStream output = new DataOutputStream(socket.getOutputStream());

            output.writeInt(criticality);

            System.out.println("Messaggio con valore " + criticality + " inviato sul porto " + porto);
            
            socket.close();


        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }
    
}
