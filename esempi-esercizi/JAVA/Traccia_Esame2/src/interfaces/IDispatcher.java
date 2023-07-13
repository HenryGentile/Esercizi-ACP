package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IDispatcher extends Remote{
    
    public void setReading(IReading reading) throws RemoteException;
    public IReading getReading() throws RemoteException;
    public void attachObserver(String tipo, IObserver observer) throws RemoteException;

}
