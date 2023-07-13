package dispatcher;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import interfacce.ICoda;
import interfacce.IDispatcher;

public class DispatcherImp implements IDispatcher{
    
    private ICoda coda;
    private Lock lock;
    private Condition full;
    private Condition empty;

    public DispatcherImp(ICoda coda){

        this.coda = coda;

        lock = new ReentrantLock();
        full = lock.newCondition();
        empty = lock.newCondition();

    }

    @Override
    public void sendCmd(int command) {
        // TODO Auto-generated method stub

        lock.lock();

        try{

            try {

                while (coda.full()){
                    System.out.println("[DISPATCHER PRODUZIONE] coda piena. Attesa...");
                    full.await();
                }

            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }

            coda.produci(command);

            empty.signal();

        }catch(Exception e){

            e.printStackTrace();

        }finally{

            lock.unlock();

        }

    }

    @Override
    public int getCmd() {
        // TODO Auto-generated method stub

        lock.lock();

        int valore = -1;

        try{

            try {

                while (coda.empty()){
                    System.out.println("[DISPATCHER CONSUMO] coda vuota. Attesa...");
                    empty.await();
                }

            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }

            valore = coda.consuma();

            full.signal();

        }catch(Exception e){

            e.printStackTrace();

        }finally{

            lock.unlock();

        }

        return valore;

    }

    

}
