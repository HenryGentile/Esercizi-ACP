package client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import interfaces.IDispatcher;

//otteniamo il riferimento del registry
//estraiamo da li il riferimento al RMI dispatcher
//generiamo i thread e passiamo loro tale riferimento per la connessione

public class Node {

    public static void main(String[] args){
        
        Registry myRegistry;

        try{
            myRegistry = LocateRegistry.getRegistry(1234);
            IDispatcher dispatcher = (IDispatcher) myRegistry.lookup("Dispatcher");
            System.out.println("[CLIENT] ottenuto RMI Dispatcher"); 

            NodeThread[] nodes = new NodeThread[5];

            for (int i = 0; i < 5 ; i++){
                nodes[i] = new NodeThread(i, dispatcher);
                nodes[i].start();
            }


        }catch(RemoteException e){
            e.printStackTrace();
        }catch(NotBoundException e){
            e.printStackTrace();
        }
    }
}
