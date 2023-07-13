package manager;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Hashtable;

import Interfaces.IAlertNotification;
import Interfaces.IManager;

public class ManagerProxy extends UnicastRemoteObject implements IManager{

    //lista delle associazioni ID e porto dei rispettivi observer
    private Hashtable<Integer, ArrayList<Integer>> observers;
    private IAlertNotification alertNotification ;

    public ManagerProxy() throws RemoteException {

        observers = new Hashtable<Integer , ArrayList<Integer>>();
    }

    @Override
    public void sendNotification(IAlertNotification notification) {
        // TODO Auto-generated method stub

        synchronized(this){

            alertNotification = notification;

            for(Integer porto : observers.get(alertNotification.getID())){

                notifyAlert(alertNotification.getCriticality());

            }

        }

    }

    @Override
    public void subscribe() {
        // TODO Auto-generated method stub

    }


    public void notifyAlert(int criticality){

    }
    
}
