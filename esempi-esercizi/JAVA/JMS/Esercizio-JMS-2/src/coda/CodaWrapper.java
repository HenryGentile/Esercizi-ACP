package coda;

public abstract class CodaWrapper implements Icoda{

    protected Icoda queue; // per consentire alle classi che la ereditano di accedervi alla coda.

    public CodaWrapper(Icoda q){
        queue = q;
    }

    public boolean empty(){
        return queue.empty();
    }

    public boolean full(){
        return queue.full();
    }

    public int getsize(){
        return queue.getsize();
    }
}
