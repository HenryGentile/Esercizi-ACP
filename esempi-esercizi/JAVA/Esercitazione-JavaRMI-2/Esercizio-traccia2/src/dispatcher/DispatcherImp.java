package dispatcher;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;

import interfaces.*;

/*
     * implementiamo le variabili private quali:
     * lista delle printer
     */
    /*
     * definiamo il funzionamento di tutti i metodi utilizzati nell'interfaccia IDispatcher
     * in questione, in quanto il funzionamento di questi ultimi e compito di questa classe
     */

public class DispatcherImp extends UnicastRemoteObject implements IDispatcher{

    private Vector<IPrinter> printers;

    protected DispatcherImp() throws RemoteException{
        printers = new Vector<>();
    }

    public void addPrinter(int port, IPrinter printer) throws RemoteException {

        // la printer non può essere rimossa dall'elenco, ne tantomento ha altre finestre oltre all'avvio
        // in cui farsi aggiungere nel dispatcher. Per questo motivo va bene non effettuare check di una
        // eventuale presenza all'interno del vector<IPrinter>
        printers.add(printer);

    }

    public boolean printRequest(String docName) throws RemoteException {
        boolean resp = false;
        int i = 0;

        while ( (!resp) && (i < printers.size())){
            resp=printers.get(i).print(docName);
            i++;
        }

        System.out.println("[DISPATCHER] risposta richiesta:" + docName + " printer:" + i + " esito:" + resp);

        return resp;
    }

    
}
