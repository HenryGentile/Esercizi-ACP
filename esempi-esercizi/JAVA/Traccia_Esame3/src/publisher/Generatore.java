package publisher;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import Interfaces.IManager;

public class Generatore {
    
    public static void main(String[] args){

        Registry myregistry;

        try {

            

            myregistry = LocateRegistry.getRegistry(1200);
            IManager manager = (IManager) myregistry.lookup("MyManager");

            

            for(int i = 0; i < 3; i++){

                GeneratoreThread thread = new GeneratoreThread(manager);

                thread.start();
            }


        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

    }


}
