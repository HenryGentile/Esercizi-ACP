package dispatcher;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Random;

import interfaces.IDispatcher;
import interfaces.IObserver;
import interfaces.IReading;

public class Dispatcher extends UnicastRemoteObject implements IDispatcher {

    private IReading reading;
    private Hashtable <String, ArrayList<IObserver>> observers;

    public Dispatcher() throws RemoteException{

        observers = new Hashtable<>();

        observers.put("temperatura", new ArrayList<IObserver>());
        observers.put("pressione", new ArrayList<IObserver>());
                 
    }



    @Override
    public void setReading(IReading reading) throws RemoteException {
        // TODO Auto-generated method stub

        //Mutua esclusione con 1 permesso
        synchronized(this){

            this.reading = reading;

            for(IObserver observer : observers.get(reading.getType())){
                observer.notifyReading();
            };

            try {
                Random n = new Random();
                Thread.sleep(1000 * (n.nextInt(5) + 1));

            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }

            System.out.println("Dispatcher: Stampa terminata");

        }
    }

    @Override
    public IReading getReading() throws RemoteException {
        // TODO Auto-generated method stub

        return reading;
    }

    @Override
    public void attachObserver(String tipo, IObserver observer) throws RemoteException {
        // TODO Auto-generated method stub

        (observers.get(tipo)).add(observer);

        System.out.println("Observer " + tipo + " attached status: completata");
        
    }
    
}
