package sportello;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Random;
import java.util.concurrent.Semaphore;

import interfacce.ISportello;

/*
 * Meccanismo doppio semaforo:
 * 1. Tenti di metterti in coda (fino ad un max di 2, perciò max 5 possono tentare di ottenere il lock)
 * 2. Ottieni il controllo per poter scrivere sul file in questione.
 */

public class SportelloImp extends UnicastRemoteObject implements ISportello{

    private static final long serialVersionUID= 1L;
    private Semaphore semaphore;
    private Semaphore semaphoreCoda;
    private FileWriter file;


   public SportelloImp() throws RemoteException {
        semaphore = new Semaphore(3);
        semaphoreCoda = new Semaphore(5);
    }

    @Override
    public boolean serviRichiesta(int idCliente) throws RemoteException {
        
        boolean result = false;

        try {
            
            if(!semaphoreCoda.tryAcquire()){
                System.out.println("[SPORTELLO] impossibile soddisfare la richiesta");
                return false;
            }

            semaphore.acquire();

            Thread.sleep(1000 * (new Random().nextInt(5) + 1));

            file = new FileWriter("IDclient.txt", true);
            BufferedWriter bw = new BufferedWriter(file);
            PrintWriter pw = new PrintWriter(bw);

            pw.println("ID cliente:" + idCliente);
            pw.flush();

            pw.close();
            bw.close();
            file.close();

            
            result = true;
            semaphore.release();

        } catch (Exception e) {

            // TODO: handle exception
            e.printStackTrace();
            return false;

        }finally{
           
            semaphoreCoda.release();
        }
        

        return result;
    }
    
}
