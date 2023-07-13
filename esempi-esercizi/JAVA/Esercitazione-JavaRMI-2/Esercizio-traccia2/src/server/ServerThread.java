package server;

import java.rmi.RemoteException;

import interfaces.IDispatcher;
import interfaces.IPrinter;

public class ServerThread extends Thread{

    private IDispatcher dispatcher;
    private IPrinter printer;
    private int port;

    public ServerThread(IDispatcher d, IPrinter prt, int p){
        dispatcher = d;
        port = p;
        printer = prt;
    }

    public void run(){
        try {
            dispatcher.addPrinter(port, printer);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
