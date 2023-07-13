package client;

import magazzino.*;

public class ThreadWorker extends Thread {
    

    private int id;
    private String[] obj;
    private IMagazzino magazzino;

    public ThreadWorker (int num, IMagazzino m){
        magazzino = m;
        id = num;
        obj = new String[2];
        obj[0]="laptop";
        obj[1] = "smartphone";
    }

    public void run(){
        int x;
        String articolo;
        if(id % 2 == 0){
            System.out.println("[THREAD " + id + "] B generato");

            for(int i = 0; i < 5; i++){

                articolo = obj[(int)(Math.random()*2)];

                x = magazzino.preleva(articolo);

                System.out.println("[THREAD " + id + "] B prelevato articolo:" + articolo + " id:" + x);

                try {
                    Thread.sleep((int)(Math.random()*2 + 2));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        } else{
            System.out.println("[THREAD " + id + "] A generato");

            for(int i = 0; i < 5; i++){

                articolo = obj[(int)(Math.random()*2)];

                x = (int) (Math.random() * 100 );

                magazzino.deposita(articolo,x);

                System.out.println("[THREAD " + id + "] B deposito articolo:" + articolo + " id:" + x);


                try {
                    Thread.sleep((int)(Math.random()*2 + 2));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
