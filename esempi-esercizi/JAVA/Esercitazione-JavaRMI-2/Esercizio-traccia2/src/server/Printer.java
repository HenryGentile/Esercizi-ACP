package server;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.Semaphore;

import interfaces.*;

public class Printer extends UnicastRemoteObject implements IPrinter{

    private FileWriter filetext;
    private static final long serialVersionUID = 1121423512352335L;
    private Semaphore permits;
    private String file;

    public Printer(String filename) throws RemoteException{
        permits = new Semaphore(1);
        file = filename;
    }

    public boolean print(String docName) throws RemoteException {

        boolean resp = false;

        try {

            if (! permits.tryAcquire()){
                System.out.println("[PRINTER] in funzione. Impossibile soddisfare richiesta:" + docName);
                return resp;
            }

            Thread.sleep(1000 * (int)(Math.random() * 5 + 5));
            filetext = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(filetext);
            PrintWriter pw = new PrintWriter(bw);

            pw.println(docName + "\n");
            pw.flush();

            pw.close();
            bw.close();
            filetext.close();

            resp = true;

        } catch (Exception e) {
            e.printStackTrace();
            resp = false;
        }finally{
            permits.release();
        }
        return resp;
    }
}
