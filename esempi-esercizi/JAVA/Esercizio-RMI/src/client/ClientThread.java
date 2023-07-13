package client;

import java.util.Random;

import interfacce.IGestoreSportello;

public class ClientThread extends Thread{

    private IGestoreSportello gs;
    private static int R = 10;
    private Random r;

    public ClientThread(IGestoreSportello gestoreSportello){
        gs = gestoreSportello;
        r = new Random();
    }

    public void run(){

        System.out.println("[THREAD] inizializatto");

        int id;

        try {

            for (int i = 0; i < R; i++){
                
                Thread.sleep(1000 * (r.nextInt(3) + 1));

                id = r.nextInt(100) + 1;

                boolean risultato = gs.sottoponiRichiesta(id);

                System.out.println("[THREAD] Sottopposta richiesta " + (i+1) + " (id" + id + "). Status:" + risultato);

            }

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }


    }
    
}
