package dispatcher;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import interfaces.IPrinter;

public class DispatcherThread extends Thread{

    private String nomeDocumento; 
    private String nomePrinter;
    private IPrinter printer;

    public DispatcherThread(String nd, String np){

        nomeDocumento = nd;
        nomePrinter = np;

    }

    public void run(){

        System.out.println("[THREAD] nome documento:" + nomeDocumento);

        Registry myRegistry;

        try {

            myRegistry = LocateRegistry.getRegistry(1289);

            try {

                printer = (IPrinter) myRegistry.lookup(nomePrinter);

            } catch (Exception e) {

                System.out.println("non esiste un Remote obj printer con quel nome");

                return;
            };

            printer.printDoc(nomeDocumento);



        } catch (Exception e) {
            // TODO: handle exception
        }

    }
}
