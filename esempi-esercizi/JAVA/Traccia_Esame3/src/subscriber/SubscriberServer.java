package subscriber;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import Interfaces.IManager;
import Interfaces.ISubscriber;

public class SubscriberServer {

    public static void main(String[] args){

        if (args.length != 3){

            System.out.println("Errore numero parametri inseriti");

            return;
        }


        Registry myRegistry;

        try {
            
            myRegistry = LocateRegistry.getRegistry(1200);
            IManager manager = (IManager) myRegistry.lookup("MyManager");

            ISubscriber subImp = new SubscriberImp(args[2]);
            SubscriberSkeleton sub = new SubscriberSkeleton(Integer.valueOf(args[0]), Integer.valueOf(args[1]),subImp, manager);

            sub.runSkeleton();

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }


    }

    
}
