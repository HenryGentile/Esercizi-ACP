package Interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IManager extends Remote{
    
    public void sendNotification(IAlertNotification notification) throws RemoteException;
    public void subscribe(int ID, int port) throws RemoteException;

}
