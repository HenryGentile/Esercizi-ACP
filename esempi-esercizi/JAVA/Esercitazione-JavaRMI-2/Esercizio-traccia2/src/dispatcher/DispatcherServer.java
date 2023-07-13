package dispatcher;

import interfaces.*;
import java.rmi.registry.*;

public class DispatcherServer {
    
    //crea l'oggetto remoto dispatcher e lo mette in ascolto
    public static void main(String[] args){
        Registry myregistry;

        try{
            IDispatcher dispatcher = new DispatcherImp();
            IDispatcher rmi_dispatcher = new DispatcherSkeleton(dispatcher);
            myregistry = LocateRegistry.createRegistry(1234);

            myregistry.rebind("Dispatcher", rmi_dispatcher);

            System.out.println("[SYSTEM] Creazione Dispatcher RMI. Attesa...");

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
