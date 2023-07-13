package observer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import interfaces.IDispatcher;
import interfaces.IObserver;
import interfaces.IReading;

public class ObserverImp extends UnicastRemoteObject implements IObserver{
    
    private String filename;
    private IDispatcher dispatcher;

    public ObserverImp(String filename, IDispatcher d) throws RemoteException{

        this.filename = filename;
        dispatcher = d;

    }

    @Override
    public void notifyReading() throws RemoteException {
        // TODO Auto-generated method stub

        IReading reading = dispatcher.getReading();

        System.out.println("Observer: valore letto:" + reading.getValue() + ". Inizio stampa su file");

        try {

            FileWriter fileWriter = new FileWriter(filename, true);

            BufferedWriter bw = new BufferedWriter(fileWriter);

            PrintWriter pw = new PrintWriter(bw);

            pw.println(reading.getType() + ":" + Integer.toString(reading.getValue()));

            pw.close();
            bw.close();
            fileWriter.close();


        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        
    }



}
