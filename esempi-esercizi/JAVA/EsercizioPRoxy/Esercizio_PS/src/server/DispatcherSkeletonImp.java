package server;

import java.util.concurrent.locks.*;

import dispatcher.IDispatcher;

public class DispatcherSkeletonImp implements IDispatcher{

    //variabili per la sincronizzazione e per la gestione della coda 
    private int buffer[];
    private String buffercmd[];
    private int testa;
    private int coda; 
    private int elem;
    private int size;

    private Lock lock;
    private Condition full;
    private Condition empty;

    

    public DispatcherSkeletonImp(int s){
        size = s;
        buffer = new int[size];
        coda = 0;
        testa = 0; 
        elem = 0;

        buffercmd = new String[4];
        buffercmd[0]="leggi"; buffercmd[1]="scrivi";
        buffercmd[2]="configura"; buffercmd[3]="reset";

        lock = new ReentrantLock();
        full = lock.newCondition(); //sospendiamo i thread che inseriscono se il buffer si riempie
        empty = lock.newCondition(); //sospendiamo i thread che vogliono estrarre se quest'ultimo risulta vuoto
    }

    //introduciamo i metodi full e empty per valutare le condizioni
    public boolean buffer_full(){
        if ( size == elem)
            return true;
        return false;
    }

    public boolean buffer_empty(){
        if (elem == 0)
            return true;
        return false;
    }

    public void sendCmd(int x){
        lock.lock();

        try{
            try{
                while (this.buffer_full()){

                    System.out.println("\n [SERVER] Buffer pieno. Attesa...");
                    full.await();
                }

                buffer[testa % size] = x;
                System.out.println("\n[SERVER] COMANDO " + buffer[testa % size] + "(" + buffercmd[buffer[testa % size]] + ") INSERITO");
                testa += 1;
                elem += 1;
                empty.signal();

            }catch(Exception e){
                e.printStackTrace();
            }
        }finally{
            lock.unlock();
        }
    }

    public int getCmd(){
        lock.lock();

        int x = 0;

        try{
            try{
                while (this.buffer_empty()){

                    System.out.println("\n [SERVER] Buffer vuoto. Attesa...");
                    full.await();
                }

                x = buffer[coda % size];
                System.out.println("\n[SERVER] COMANDO " + x + "(" + buffercmd[x] + ") ESTRATTO");
                coda += 1;
                elem -= 1;
                full.signal();

            }catch(Exception e){
                e.printStackTrace();
            }
        }finally{
            lock.unlock();
        }
        
        return x;
    }
}
