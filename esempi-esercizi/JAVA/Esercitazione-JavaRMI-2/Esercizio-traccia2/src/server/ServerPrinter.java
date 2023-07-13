package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import interfaces.IDispatcher;

/*
 * definiamo l'oggetto remoto Printer e poi successivamente 
 * contattiamo il dispatcher server (ottenendone il riferimento)
 * per usare il suo metodo addPrinter(int port). 
 * Atttendiamo l'esito corretto dell'inserimento aprendo una ServerSocket e
 * ponendoci in attesa, sul porto comunicatogli, della risposta di conferma. 
*/ 

/*
 * 1a soluzione : concediamo ad un thread l'invio del metodo addPrinter(a) mentre il principale
 * lo poniamo in attesa sulla socket
 */

public class ServerPrinter {
    public static void main(String[] args) {

        try {

            int port = Integer.valueOf(args[0]);
            String fileName = args[1];

            ServerSocket socket = new ServerSocket(port);
            Registry myregistry = LocateRegistry.getRegistry(1234);
            IDispatcher dispatcher = (IDispatcher) myregistry.lookup("Dispatcher");

            Printer printer = new Printer(fileName);

            ServerThread workers = new ServerThread(dispatcher, printer , port);
            workers.start();
            
            System.out.println("[SERVER PORT:" + port + "] ATTESA ACK...");
            Socket s = socket.accept();

            DataInputStream dataIn = new DataInputStream(s.getInputStream());

            String resp = dataIn.readUTF();
            System.out.println("[SERVER PORT:" + port + "] ricevuta rispota:" + resp);

            socket.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }

    }   
}
