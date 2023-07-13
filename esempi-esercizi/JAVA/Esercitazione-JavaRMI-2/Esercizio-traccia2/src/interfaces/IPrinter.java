package interfaces;

import java.rmi.*;

public interface IPrinter extends Remote {

    public boolean print(String docName) throws RemoteException;

}
