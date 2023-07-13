package client;

import java.rmi.RemoteException;

import interfaces.IDispatcher;

public class NodeThread extends Thread{
    /* istanziamo le variabili private quali:
        IDispatcher disptcher (passato dal Node main tramite parametro del costruttore)
    */
    //creiamo il costruttore
    /*
        *Creiamo il metodo run nel quale aspettando un tempo random invochiamo per 3 volte 
        *il metodo remoto printRequest contattando l'oggetto remoto in questione.
    */ 

    private IDispatcher dispatcher;
    private int ID;

    public NodeThread(int id, IDispatcher dispatcher){
        this.dispatcher = dispatcher;
        ID = id;
    }

    public void run(){
        System.out.println("[THREAD " + ID + "] AVVIO...");
        try {
            for(int i = 0; i < 3; i++){

                Thread.sleep(3000);
                String docName = "doc" + String.valueOf((int) (Math.random() * 50));
                boolean resp = dispatcher.printRequest(docName);
                System.out.println("[THREAD " + ID + "] invio docName:" + docName + " esito:" + resp);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch(RemoteException u){
            u.printStackTrace();
        }
    }
}
