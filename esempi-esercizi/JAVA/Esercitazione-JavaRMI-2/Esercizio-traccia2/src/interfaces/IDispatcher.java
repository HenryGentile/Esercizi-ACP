package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IDispatcher extends Remote{

    public void addPrinter(int port, IPrinter printer) throws RemoteException;

    public boolean printRequest(String docName) throws RemoteException;


}