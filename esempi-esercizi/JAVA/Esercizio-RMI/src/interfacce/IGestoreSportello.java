package interfacce;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IGestoreSportello extends Remote{
    
    public void  sottoscrivi(ISportello sportello) throws RemoteException;
    boolean sottoponiRichiesta( int idCliente ) throws RemoteException; 

}
