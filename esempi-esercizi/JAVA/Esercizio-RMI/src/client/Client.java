package client;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import interfacce.IGestoreSportello;

public class Client {

    private static int T = 10;
    
    public static void main(String[] args){

        Registry myRegistry;

        

        try {
            myRegistry = LocateRegistry.getRegistry(2345);

            IGestoreSportello gestoreSportello = (IGestoreSportello) myRegistry.lookup("MyGestoreSportello");

            for(int i = 0; i < T; i++){

                ClientThread ct = new ClientThread(gestoreSportello);

                ct.start();

            }

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

    }

}
