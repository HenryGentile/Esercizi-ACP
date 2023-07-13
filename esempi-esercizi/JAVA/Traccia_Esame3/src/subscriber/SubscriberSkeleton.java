package subscriber;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.RemoteException;
import java.rmi.server.RemoteObject;
import java.rmi.server.UnicastRemoteObject;

import Interfaces.IManager;
import Interfaces.ISubscriber;

public class SubscriberSkeleton extends UnicastRemoteObject implements ISubscriber{

    private int ID;
    private int porto;
    private ISubscriber subscriber;
    private IManager manager;

    public SubscriberSkeleton(int ID, int porto, ISubscriber sub, IManager m) throws RemoteException {

        this.ID = ID;
        this.porto = porto;
        subscriber = sub;
        manager = m;

    }


    public void runSkeleton() throws RemoteException{

        System.out.println("Observer istanziato...");

        manager.subscribe(ID, porto);

        try {

            ServerSocket socket = new ServerSocket(porto);

            while(true){

                // Converrebbe passarlo ad un thread ma nella traccia non è specificato

                Socket newSocket = socket.accept();

                DataInputStream input = new DataInputStream(newSocket.getInputStream());

                int crit = input.readInt();

                subscriber.notifyAlert(crit);
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @Override
    public void notifyAlert(int criticality) throws RemoteException {
        // TODO Auto-generated method stub
        subscriber.notifyAlert(criticality);
    }
    
}
