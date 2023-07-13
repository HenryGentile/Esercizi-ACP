package client;

import dispatcher.*;

public class ClientThread extends Thread{

    private IDispatcher dis;
    int id_thread;
    String[] command = new String[4]; //generiamo l'array delle stringhe comandi per i print

    public ClientThread(IDispatcher dispatcher, int id){
        dis=dispatcher;
        id_thread = id;
        command[0]="leggi"; command[1]="scrivi";
        command[2]="configura"; command[3]="reset";
    }

    public void run(){
        int x = 0;

        //i thread workes aspettano un tempo compreso tra 2 e 4 secondi 
        //succcessivamente inviano al dispatcher il comando usando sendCmd(int x);
        for (int i = 0; i < 3; i++){
            x = (int)(Math.random()*4);
            try {
                Thread.sleep((int)(Math.random()*2 + 2));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("\n[THREAD " + id_thread + "] INVIO " + i + "o COMANDO:" + command[x]);
            dis.sendCmd(x);
        }
    }
}
