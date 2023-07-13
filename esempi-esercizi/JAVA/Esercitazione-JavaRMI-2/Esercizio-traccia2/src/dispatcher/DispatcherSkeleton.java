package dispatcher;

import java.io.DataOutputStream;
import java.net.Socket;
import java.rmi.*;
import java.rmi.server.*;

import interfaces.*;

/*
        *nel dispatcehr skeleton creiamo le variabili private quali:
        *variabile che riporta un riferimento al dispatcherImp
    */
    /*
        *i metodi della classi IDispatcher vengono risolti invocando quelli del disptcher 
        *di cui abbiamo il riferimento. Unico metodo addPrinter nel quale invece gestiamo
        *dopo la chiamata di aggiunta il meccanismo di connessione tramite socket per comunicare
        *il corretto inserimento del valore all'interno della lista
    */

public class DispatcherSkeleton extends UnicastRemoteObject implements IDispatcher{

    private IDispatcher dispatcher;
    private static final long serialVersionUID =  2725189131126219696L;

    public DispatcherSkeleton(IDispatcher d) throws RemoteException{
        dispatcher = d; // riferimento del dispatcherImp contenente la gestione delle funzionalità.
    }

    public void addPrinter(int port, IPrinter printer) throws RemoteException {
        try {

            //aggiungere il DataInputStream per leggere un eventuale ricezione dell'ack da parte della Printer
            dispatcher.addPrinter(port, printer);
            System.out.println("[DISPATCHER] Printer aggiunta. Invio ACK al porto " + port);
            Socket socket = new Socket("127.0.0.1", port);
            DataOutputStream dataout = new DataOutputStream(socket.getOutputStream());

            dataout.writeUTF("ACK");

            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public boolean printRequest(String docName) throws RemoteException {

        boolean risp = dispatcher.printRequest(docName);
        return risp;
    }
    
}
