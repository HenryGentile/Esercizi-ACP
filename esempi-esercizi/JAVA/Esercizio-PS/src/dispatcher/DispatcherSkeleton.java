package dispatcher;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import interfacce.IDispatcher;

public class DispatcherSkeleton implements IDispatcher{

    private IDispatcher dispatcher;
    private int porto;

    public DispatcherSkeleton(IDispatcher d, int porto){
        dispatcher = d;
        this.porto = porto;
    }

    public void run(){

        try {

            ServerSocket socket = new ServerSocket(porto);

            System.out.println("[DISPATCHER] in attesa sul porto " +  porto + "...");
            
            while(true){

                Socket s = socket.accept();

                System.out.println("[DISPATCHER] Messaggio recepito. avvio Thread...");

                DispatcherWorker worker = new DispatcherWorker(this, s);

                worker.start();

            }

            
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        

    }

    @Override
    public void sendCmd(int command) {
        // TODO Auto-generated method stub

        dispatcher.sendCmd(command);

    }

    @Override
    public int getCmd() {
        // TODO Auto-generated method stub

        return dispatcher.getCmd();
        
    }
    
}
