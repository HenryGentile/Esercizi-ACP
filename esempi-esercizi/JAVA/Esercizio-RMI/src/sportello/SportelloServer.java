package sportello;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import interfacce.IGestoreSportello;
import interfacce.ISportello;

public class SportelloServer {
    
    public static void main(String[] args){

        Registry myRegistry;

        try {
            
            myRegistry = LocateRegistry.getRegistry(2345);
            IGestoreSportello gs = (IGestoreSportello) myRegistry.lookup("MyGestoreSportello");

            ISportello s = new SportelloImp();
            gs.sottoscrivi(s);

            System.out.println("Sportello sottoscritto al gestore");

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

    }

}
