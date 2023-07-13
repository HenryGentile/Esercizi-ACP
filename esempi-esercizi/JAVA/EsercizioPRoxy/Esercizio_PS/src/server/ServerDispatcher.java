package server;

import dispatcher.*;

public class ServerDispatcher {
    //analogo comportamento del client, in questo caso il server istanzia un dispatcher skeleton
    //lato server, dopo di che lancia il metudo runskeleton che gestirà il tutto
    public static void main(String[] args){
        //in questo caso essendo implementato tramite delega i passaggi sono:
        //creazione di un dispatcehr di tipo DispatcherSkeletonImp
        IDispatcher dispatcher = new DispatcherSkeletonImp(5); //passiamo la dimensione della coda
 
        
        //creazione dello skeleton che si occuperà della connessione con riferimento al dispatcher
        ServerDispatcherSkeleton dispatcher_skeleton = new ServerDispatcherSkeleton(dispatcher, 8000);

        //lanciare il metodo runskeleton(), metodo dello skeleton
        dispatcher_skeleton.runskeleton();

    }
}
