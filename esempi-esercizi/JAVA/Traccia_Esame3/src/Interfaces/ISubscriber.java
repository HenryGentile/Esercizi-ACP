package Interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ISubscriber extends Remote{
    
    public void notifyAlert(int criticality) throws RemoteException;

}
