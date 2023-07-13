package client;

import java.util.Random;

import interfacce.IDispatcher;

public class WorkerThread extends Thread{
    
    IDispatcher dispatcher;

    public WorkerThread(IDispatcher d){
        dispatcher = d;
    }

    public void run(){

        System.out.println("[THREAD] Attivato");

        try {
            
            for(int i = 0; i < 3; i++){

                Thread.sleep(1000* (new Random().nextInt(3) + 2));

                int valore = new Random().nextInt(4);

                System.out.println("Invio messaggio al dispatcher con valore " + valore);

                dispatcher.sendCmd(valore);

            }

        } catch (Exception e) {
            // TODO: handle exception
        }

    }

}
