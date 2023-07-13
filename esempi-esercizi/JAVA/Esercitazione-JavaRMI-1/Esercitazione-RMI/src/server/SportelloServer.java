package server;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import interfaces.IGestoreSportello;
import interfaces.ISportello;

//in questo caso il server, una volta avviato,genererà un solo sportello da sottoscrivere
//direttamente al gestore, ottenumo come oggetto remoto
public class SportelloServer {
	
	public static void main (String[] args) {
		
		try {
			
			//anche il server ottiene il riferimento remoto a tale oggetto
			Registry rmiRegistry = LocateRegistry.getRegistry();
			IGestoreSportello gestore = (IGestoreSportello) rmiRegistry.lookup("gestore");
			
			ISportello sportello = new SportelloImpl();
			gestore.sottoscrivi(sportello);
			
			System.out.println("[SportelloServer] Sottoscritto sportello al gestore");
			
			
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
		
	}

}
