package observer;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import interfaces.IDispatcher;
import interfaces.IObserver;

public class ObserverServer {
    
    public static void main(String[] args) throws RemoteException, NotBoundException{

        // Observer server chrea un observer e lo attacca al dispatcher remoto

        if (args.length != 2){
            System.out.println("Numero di parametri inseriti non validi");

            return;
        }

        Registry myRegistry = LocateRegistry.getRegistry();
        
        IDispatcher dispatcher = (IDispatcher) myRegistry.lookup("MyDispatcher");

        IObserver observer = new ObserverImp(args[1], dispatcher);

        dispatcher.attachObserver(args[0], observer);

        System.out.println("Observer Attached complete");


    }


}