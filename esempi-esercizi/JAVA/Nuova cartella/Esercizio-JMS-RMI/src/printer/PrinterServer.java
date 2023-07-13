package printer;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import interfaces.IPrinter;

public class PrinterServer {

    public static void main(String[] args){

        Registry myRegistry;

        try {
            
            myRegistry = LocateRegistry.createRegistry(1289);

            IPrinter printer = new Printer();

            myRegistry.bind(args[0], printer);

            System.out.println("[PRINTER SERVER] impostata stampante con nome printer " + args[0]);
        

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

    }
    
}
