package dispatcher;

import interfacce.ICoda;
import interfacce.IDispatcher;

public class DispatcherServer {
    
    public static void main(String[] args){

        ICoda coda = new CodaCircolare(5);
        IDispatcher dispatcher = new DispatcherImp(coda);
        DispatcherSkeleton dispatcherSkel = new DispatcherSkeleton(dispatcher, Integer.valueOf(args[0]));

        dispatcherSkel.run();

    }

}
