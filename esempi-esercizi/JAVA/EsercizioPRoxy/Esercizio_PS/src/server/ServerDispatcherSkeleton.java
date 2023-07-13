package server;

import java.net.*;
import java.io.*;

import dispatcher.*;

public class ServerDispatcherSkeleton implements IDispatcher{
    
    private IDispatcher dispatcher;
    int port;

    public ServerDispatcherSkeleton(IDispatcher d, int p){
        dispatcher=d;
        port=p;
    }

    // il dispatcher ha il solo modulo usabile runskeleton() che si occupa 
    // di attivare una socket e passarla agli apposidi thread del server per gestirle cont.
    public void runskeleton(){
        try{
            //la socket non bisogna chiuderla in quanto ci penserà il thread generato successivamente a chiuderla
            // try with resource ()
            try (ServerSocket socket = new ServerSocket(port)) {
                System.out.println("\n[SERVER] ATTESA CREAZIONE CONNESSIONE...");
                while(true){
                    Socket s = socket.accept(); //accept istanzia una socket e restituisce il riferimento della socket
                    
                    //this in quanto passiamo il riferimento di questo Dispatcher
                    ServerThread workers = new ServerThread(s,this);

                    workers.run();

                }
            }


        }catch(IOException e){
            e.printStackTrace();
        }
    }

    // l'implementazione dei due metodi richiesti viene rimandata ad un altra classe 
    // che implementerà la gestione della coda con politica prod/cons
    public void sendCmd(int cmd){
        dispatcher.sendCmd(cmd);
    }

    public int getCmd(){
        return dispatcher.getCmd();
    }
}