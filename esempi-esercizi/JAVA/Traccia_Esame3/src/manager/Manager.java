package manager;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Hashtable;

import Interfaces.IAlertNotification;
import Interfaces.IManager;

public class Manager extends UnicastRemoteObject implements IManager{

    Hashtable <Integer, ArrayList<SubscriberProxy>> observers;

    public Manager() throws RemoteException {

        observers = new Hashtable<>();
       
        observers.put(1, new ArrayList<SubscriberProxy>());
        observers.put(2, new ArrayList<SubscriberProxy>());
        observers.put(3, new ArrayList<SubscriberProxy>());
        observers.put(4, new ArrayList<SubscriberProxy>());
        observers.put(5, new ArrayList<SubscriberProxy>());

    }

    @Override
    public void sendNotification(IAlertNotification notification) throws RemoteException {
        // TODO Auto-generated method stub

        synchronized(this){

            for (SubscriberProxy sub : observers.get(notification.getID())){
                sub.notifyAlert(notification.getCriticality());
            }

        }

        System.out.println("Notificati tutti i observers tipo " + notification.getID());

    }

    @Override
    public void subscribe(int ID, int port) throws RemoteException {
        // TODO Auto-generated method stub

        System.out.println("Nuovo Observer con ID " + ID + " aggiunto");

        (observers.get(ID)).add(new SubscriberProxy(port));


    }
    
}
