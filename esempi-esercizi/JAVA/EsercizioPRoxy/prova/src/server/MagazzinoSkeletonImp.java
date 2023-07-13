package server;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import magazzino.*;


public class MagazzinoSkeletonImp implements IMagazzino{

    private Buffer bufferLaptop;
    private Buffer bufferSmartphone;

    private Lock lock;
    private Condition cv_full_laptop;
    private Condition cv_empty_laptop;
    private Condition cv_full_smartphone;
    private Condition cv_empty_smartphone;

    public MagazzinoSkeletonImp(int size){
        
        bufferLaptop = new Buffer(size);
        bufferSmartphone = new Buffer(size);

        lock = new ReentrantLock();
        cv_full_laptop = lock.newCondition();
        cv_empty_laptop = lock.newCondition();
        cv_full_smartphone = lock.newCondition();
        cv_empty_laptop = lock.newCondition();

    }

    public void deposita(String articolo, int id) {
        lock.lock();

        try {
            if(articolo.compareTo("laptop") == 0){
                try {
                    while(bufferLaptop.isFull()){
                        System.out.println("[MAGAZZINO] buffer " + articolo + " pieno. Sospensione...");
                        cv_full_laptop.await();
                    }

                    bufferLaptop.inserimento(articolo, id);
                    cv_empty_laptop.signal();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else{
                if(articolo.compareTo("smartphone") == 0){
                    try {
                        while(bufferLaptop.isFull()){
                            System.out.println("[MAGAZZINO] buffer " + articolo + " pieno. Sospensione...");
                            cv_full_smartphone.await();
                        }
    
                        bufferSmartphone.inserimento(articolo, id);
                        cv_empty_smartphone.signal();
    
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } finally {
            lock.unlock();
        }
    }

    public int preleva(String articolo) {
        int id = 0;

        lock.lock();

        try {
            if(articolo.compareTo("laptop") == 0){
                try {
                    while(bufferLaptop.isEmpty()){
                        System.out.println("[MAGAZZINO] buffer " + articolo + " vuoto. Sospensione...");
                        cv_empty_laptop.await();
                    }

                    bufferLaptop.preleva(articolo);
                    cv_full_laptop.signal();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else{
                if(articolo.compareTo("smartphone") == 0){
                    try {
                        while(bufferLaptop.isFull()){
                            System.out.println("[MAGAZZINO] buffer " + articolo + " vuoto. Sospensione...");
                            cv_empty_smartphone.await();
                        }
    
                        bufferSmartphone.preleva(articolo);
                        cv_full_smartphone.signal();
    
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } finally {
            lock.unlock();
        }

        return id;
    }

}

    