package codaImp;

import java.util.concurrent.locks.*;

import coda.CodaWrapper;
import coda.Icoda;

public class CodaWrapperLock extends CodaWrapper{

    private Lock lock;
    private Condition Consumercv;
    private Condition ProducerCV;

    public CodaWrapperLock(Icoda q){
        super(q);

        lock = new ReentrantLock();

        Consumercv = lock.newCondition();
        ProducerCV = lock.newCondition();

    }

    public void svuota(String file, int update) {

        lock.lock();

        try {
            while ( queue.empty()){

                try {

                    
                    ProducerCV.await();

                } catch (Exception e) {

                    e.printStackTrace();
                }
            }
            queue.svuota(file, update);

            Consumercv.signalAll(); //una volta svuotati li possiamo richiamare tutti
            
        } finally{

            lock.unlock();

        }
    }

    public void put(String m) {
        lock.lock();

        try {
            while ( queue.full()){

                try {

                    System.out.println("Coda piena. Sospensione...");
                    Consumercv.await();

                } catch (Exception e) {

                    e.printStackTrace();
                }
            }

            queue.put(m);
            ProducerCV.signal();

        } finally{
            lock.unlock();
        }
    }
    
}
