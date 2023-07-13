package server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import interfaces.IGestoreSportello;


public class GestoreServer {
	
	public static void main (String[] args) {
		
		Registry rmiRegistry;
		try {
			//riceviamo il riferimento al rmiRegistry
			rmiRegistry = LocateRegistry.getRegistry();
			
			// creiamo una nuova classe e tale istanza diventa un oggetto remoto associato al nome simbolito
			// "gestore" tramite il metodo rebind, che consente associazione riferimento oggetto - nome simbolico
			IGestoreSportello gestore = new GestoreSportelloImpl();
			rmiRegistry.rebind("gestore", gestore);
			
			System.out.println("[GestoreServer] Gestore avviato");
			
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		
		
	}

}
