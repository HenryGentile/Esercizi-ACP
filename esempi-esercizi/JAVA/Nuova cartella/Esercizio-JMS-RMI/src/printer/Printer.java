package printer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import interfaces.IPrinter;

public class Printer extends UnicastRemoteObject implements IPrinter{

    private String nomefile;
    private FileWriter file;

    public Printer() throws RemoteException {
        nomefile = "Doc.txt";
    }

    @Override
    public void printDoc(String nomeDocumento) throws RemoteException {

        // TODO Auto-generated method stub

        synchronized(this){

            System.out.println("Stampa del nome documento " + nomeDocumento + " in corso...");

            try {
                Thread.sleep(5000);

                file = new FileWriter(nomefile, true);
                BufferedWriter bw = new BufferedWriter(file);
                PrintWriter pw = new PrintWriter(bw);

                pw.println(nomeDocumento);
                pw.flush();

                bw.close();
                pw.close();
                file.close();

            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }

        }
        
    }
    
}
