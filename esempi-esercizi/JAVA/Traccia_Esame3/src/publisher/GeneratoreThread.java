package publisher;

import java.rmi.RemoteException;
import java.util.Random;

import Interfaces.IManager;

public class GeneratoreThread extends Thread{

    private IManager manager;

    public GeneratoreThread(IManager manager){
        this.manager = manager;
    }

    public void run(){

        for(int i = 0; i < 3; i++){
            try {

                Random r = new Random();

                int crit = r.nextInt(3) + 1;

                int ID = r.nextInt(5) + 1;
            
            
                manager.sendNotification(new AlertNotificationImp(ID, crit));

                System.out.println("[THREAD]" + (i+1) + "o invio messaggio ID:" + ID + " valore:" + crit);

            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

    }
    
}
