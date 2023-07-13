package client;

import interfacce.IDispatcher;

public class Client{

    public static void main(String[] args){

        IDispatcher dispatcher = new DispatcherProxy(Integer.valueOf(args[0]));

        for (int i = 0; i < 5; i++){
            WorkerThread thread = new WorkerThread(dispatcher);

            thread.start();
        }

    }

}