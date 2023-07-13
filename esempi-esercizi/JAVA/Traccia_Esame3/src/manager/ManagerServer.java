package manager;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import Interfaces.IManager;

public class ManagerServer {
    
    public static void main(String[] args){

        Registry myregistry;

        try{

            myregistry = LocateRegistry.createRegistry(1200);

            IManager manager = new Manager();

            myregistry.bind("MyManager", manager);

            System.out.println("[SERVER] Manager in attività...");

        }catch(Exception e){
            e.printStackTrace();
        }

    }

}
