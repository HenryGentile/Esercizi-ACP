package subsriber;


import coda.Icoda;

public class TExecutor extends Thread{
    private Icoda coda;
    private String fileWrite;
    private int update = 0;

    public TExecutor (Icoda q, String f){
        coda = q;
        fileWrite = f;
    }

    public void run(){
        System.out.println("[TEXECUTOR] Inizio processo");

        while(true){
            try {
                Thread.sleep(10000);
                update++;
                coda.svuota(fileWrite, update);
            } catch (Exception e) {
                e.printStackTrace();
            }
            
        }
    }


}
