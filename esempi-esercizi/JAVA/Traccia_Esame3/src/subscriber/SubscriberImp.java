package subscriber;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.rmi.RemoteException;

import Interfaces.ISubscriber;

public class SubscriberImp implements ISubscriber{

    String filename;
    FileWriter file;

    public SubscriberImp(String filename){
        this.filename = filename;
    }

    @Override
    public void notifyAlert(int criticality) throws RemoteException {
        // TODO Auto-generated method stub

        System.out.println("Subsciber: Valore Ricevuto:" + criticality + ". Inizio stampa...");

        try {   

            Thread.sleep(1000);

            file = new FileWriter(filename, true);
            BufferedWriter bw = new BufferedWriter(file);
            PrintWriter pw = new PrintWriter(bw);

            pw.println("Valore:" + criticality);

            pw.close();
            bw.close();
            file.close();


            
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }


    }
    
}
