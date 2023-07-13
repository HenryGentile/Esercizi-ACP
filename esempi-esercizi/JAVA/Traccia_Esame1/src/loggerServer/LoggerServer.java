package loggerServer;

/*
 * Funzione del logger server è quella di inizializzare 
 * uno skeleton Logger implementato con delega 
 */

public class LoggerServer {
    
    public static void main(String[] args){

        ILogger logger = new LoggerImp();

        LoggerSkeleton loggerSkeleton = new LoggerSkeleton(logger, Integer.valueOf(args[0]));

        loggerSkeleton.runSkeleton();


    }


}
