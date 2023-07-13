package dispatcher;

import java.nio.channels.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import interfaces.IDispatcher;

public class DispatcherServer {
    
    public static void main(String[] args) throws RemoteException, AlreadyBoundException{

        IDispatcher dispatcher = new Dispatcher();

        Registry registry = LocateRegistry.createRegistry(1099);

        registry.rebind("MyDispatcher", dispatcher);

        System.out.println("Server: Dispatcher istanziato sul registry col nome MyDispatcher");

    }

}
