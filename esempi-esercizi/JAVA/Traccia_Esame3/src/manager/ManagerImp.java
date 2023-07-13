package manager;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import Interfaces.IAlertNotification;
import Interfaces.IManager;

public class ManagerImp extends UnicastRemoteObject implements IManager{

    protected ManagerImp() throws RemoteException {
        super();
        //TODO Auto-generated constructor stub
    }

    @Override
    public void sendNotification(IAlertNotification notification) {
        // TODO Auto-generated method stub
    }

    @Override
    public void subscribe() {
        // TODO Auto-generated method stub
    }

    @Override
    public void notifyAlert(int criticality) {
        // TODO Auto-generated method stub
    }
    
}
