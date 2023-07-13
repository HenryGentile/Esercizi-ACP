package client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import interfaces.IGestoreSportello;

public class Client {
	
	public static void main (String[] args) {
		
		int T = 10;
		int R = 10;
		
		try {
			//rmiregistry = LocateRegistry.getRegistry() ci restituise un riferimento al RMIregistry
			//comando importante in quando il Registry mappa tutti gli oggetti remoti tramite appositi nomi
			//simbolici come in questo caso.
			//la richiesta Registry.lookup ci restituisce il riferimento remoto all'apposito oggetto remoto
			//associato al nome simbolico passato come parametro della lookup
			Registry rmiRegistry = LocateRegistry.getRegistry();
			
			IGestoreSportello gestore = (IGestoreSportello) rmiRegistry.lookup("gestore");
			
			ClientThread[] threads = new ClientThread[T];
			
			for (int i = 0; i < T; i++) {
				threads[i] = new ClientThread(R, gestore);
				threads[i].start();
			}
			
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}		
		
	}

}
