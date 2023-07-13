package gestoreSportello;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import interfacce.IGestoreSportello;

public class GestoreSportelloServer {
    
    public static void main(String[] args){

        Registry myRegistry;

        try {

            myRegistry = LocateRegistry.createRegistry(2345);
            IGestoreSportello gestoreSportello = new GestoreSportelloImp();
            myRegistry.bind("MyGestoreSportello", gestoreSportello);

            System.out.println("[GESTORE SPORTELLO] Aggiunto Remote obj con nome MyGestoreSportello...");

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

    }

}
