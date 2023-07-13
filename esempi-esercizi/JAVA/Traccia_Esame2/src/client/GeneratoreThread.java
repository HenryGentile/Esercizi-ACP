package client;

import java.util.Random;

import interfaces.IDispatcher;

public class GeneratoreThread extends Thread{
    
    private IDispatcher dispatcher;

    public GeneratoreThread(IDispatcher d){
        dispatcher = d;
    }

    public void run(){

        for(int i = 0; i < 3; i++){

            Random r = new Random();
            String type;

            if(r.nextInt(10) % 2 == 0){

                type = "temperatura";

            }else{

                type = "pressione";

            }

            int value = r.nextInt(51);

            System.out.println("[THREAD] " + (i+1) + "o invio reading tipo " + type + " con valore=" + value);

            try {
                dispatcher.setReading(new Reading(type, value));

                if (i == 2){
                    Thread.sleep(5000);
                }
            

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

    }

}
