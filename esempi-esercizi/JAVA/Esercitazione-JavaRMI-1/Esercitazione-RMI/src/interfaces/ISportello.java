package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

/*le interfaccie che gestiscono servizi remoti devono essere caratterizate da:
  -public interface Iname extends Remote
  inoltre i loro metodi devono essere seguiti subito da throws RemoteException per gestire già durante l'invocazione
  i possibili problemi legati alla connessione o ai servizi lato server
*/

public interface ISportello extends Remote {
	
	boolean serviRichiesta(int idCliente) throws RemoteException;

}
