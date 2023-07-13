package subsriber;

import coda.Icoda;

public class TMagazine extends Thread{
    private Icoda coda;
    private String message;
    
    public TMagazine(Icoda q, String m ){
        coda = q;
        message = m;
    }

    public void run(){
        coda.put(message);
        System.out.println("[TMAGAZINE] messaggio:" + message + " depositato");
    }
}
