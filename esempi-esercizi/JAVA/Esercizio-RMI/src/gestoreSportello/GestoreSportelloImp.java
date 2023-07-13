package gestoreSportello;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;

import interfacce.IGestoreSportello;
import interfacce.ISportello;

public class GestoreSportelloImp extends UnicastRemoteObject implements IGestoreSportello{

    private Vector<ISportello> sportelli;

    public GestoreSportelloImp() throws RemoteException {
        
        sportelli = new Vector<>();

    }

    @Override
    public void sottoscrivi(ISportello sportello) throws RemoteException {

        sportelli.add(sportello);

    }

    @Override
    public boolean sottoponiRichiesta(int idCliente) throws RemoteException {

        boolean flag = false;
        int i = 0;

        while(!flag && i < sportelli.size()){

            flag = sportelli.get(i).serviRichiesta(idCliente);

            i++;

            System.out.println("[GESTORE SPORTELLI] Richiesta sottoposta al " + i + "o sportello. Esito:" + flag);

        }

        return flag;
    }

    
}
