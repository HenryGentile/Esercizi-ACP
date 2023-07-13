package client;

import magazzino.IMagazzino;

public class Worker{
    public static void main(String[] args){
        //il client definisce un magazzino che poi cederà come riferimento ai 10 thread generati
        IMagazzino mgz = new WorkerMagazzinoProxy(args[0], Integer.valueOf(args[1]));

        int nthread = 10;
        ThreadWorker[] workers = new ThreadWorker[nthread];

        for (int i = 0; i < nthread ; i ++){

            workers[i] = new ThreadWorker(i, mgz);

            workers[i].run();
        }
    }
}