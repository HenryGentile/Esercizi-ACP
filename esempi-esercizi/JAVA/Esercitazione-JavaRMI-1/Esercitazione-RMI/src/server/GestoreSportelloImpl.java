package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;

import interfaces.IGestoreSportello;
import interfaces.ISportello;

//le classi che gestiscono (o che sono) oggetti remoti derivano da RemoteObject. In questo caso
//la derivazione è da UnicastRemoteObjecct in quanto le classi derivate da essa gestiscono 
//comunicazioni del tipo point-to-point (come suggerito dal nome unicast) e non invece 
//connessioni multicast o broadcast

public class GestoreSportelloImpl extends UnicastRemoteObject implements IGestoreSportello {

	//version number della classe. Definito costante e differente per ogni classe
	private static final long serialVersionUID = 2725155631126219696L;
	
	private Vector<ISportello> sportelli;
	
	//metodi e variabili protected sono accessibili solo all'interno dello stessa package
	protected GestoreSportelloImpl() throws RemoteException {
		// TODO Auto-generated constructor stub
		
		sportelli = new Vector<ISportello>();
		
	}

	@Override
	public boolean sottoponiRichiesta(int idCliente) throws RemoteException {
		// TODO Auto-generated method stub
		
		boolean result = false;
		int i = 0;
		
		while ((!result) && (i < sportelli.size())) {
			result = sportelli.get(i).serviRichiesta(idCliente);
			i++;
		}
		
		System.out.println("[Gestore] Richiesta da " + idCliente + " terminata con esito " + result);
		
		return result;
	}

	@Override
	public void sottoscrivi(ISportello sportello) throws RemoteException {
		// TODO Auto-generated method stub
		
		sportelli.add(sportello);
		
		System.out.println("[Gestore] Ricevuta nuova sottoscrizione");
		
	}

}
