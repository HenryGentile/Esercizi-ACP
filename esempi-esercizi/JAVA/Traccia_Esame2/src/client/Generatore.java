package client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import interfaces.IDispatcher;

public class Generatore {
    
    public static void main(String[] args) throws RemoteException, NotBoundException{

        Registry myRegistry = LocateRegistry.getRegistry();

        IDispatcher dispatcher = (IDispatcher )myRegistry.lookup("MyDispatcher");

        int numThread = 3;

        for(int i = 0; i < numThread; i++){

            GeneratoreThread worker = new GeneratoreThread(dispatcher);

            worker.start();

        }

    }

}
