package server;

import java.net.*;
import java.io.*;

import magazzino.*;

public class ServerMagazzinoSkt implements IMagazzino{

    private IMagazzino magazzino;
    int port;
    

    public ServerMagazzinoSkt(IMagazzino m, int p){
        magazzino = m;
        port = p;
    }

    public void runskeleton(){
        try {
            try (ServerSocket socket = new ServerSocket(port)) {
                System.out.println("[MAGAZZINO] in ascolto sul porto " + port + "...");

                while(true){
                    Socket s = socket.accept();

                    ServerThread serverWorkers = new ServerThread(s, this); // per ottenere le info del magazzino se no amen
                    serverWorkers.run();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deposita(String articolo, int id) {
        magazzino.deposita(articolo, id);
    }

    public int preleva(String articolo) {
        return magazzino.preleva(articolo);
    }
    
}
