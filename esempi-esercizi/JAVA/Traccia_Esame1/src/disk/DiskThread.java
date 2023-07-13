package disk;

import loggerServer.ILogger;

public class DiskThread extends Thread{

    private int valore;
    private int porto;
    private ILogger logger;
    
    public DiskThread( int v, int p){
        valore = v;
        porto = p;

        logger = new LoggerProxy(porto);

    }


    public void run(){

        logger.registraDato(valore);

    }

}
