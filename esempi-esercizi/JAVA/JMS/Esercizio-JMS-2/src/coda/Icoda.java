package coda;

public interface Icoda {

    public void svuota(String file, int update);
    public boolean empty();
    public boolean full();
    public void put(String m);
    public int getsize();

}
