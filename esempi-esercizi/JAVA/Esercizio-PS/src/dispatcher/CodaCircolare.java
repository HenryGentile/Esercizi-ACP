package dispatcher;

import interfacce.ICoda;

public class CodaCircolare implements ICoda{
    
    private int size;
    private int head;
    private int tail;
    private int buffer[];
    private int elem;

    public CodaCircolare(int s){
        size = s;
        buffer = new int[size];
        head = 0;
        tail = 0;
        elem = 0;
    }

    @Override
    public void produci(int v) {
        // TODO Auto-generated method stub

        buffer[head] = v;

        System.out.println("[DISPATCHER] prodotto valore:" + v + " posizione " + head);

        head = ((head + 1) % size);
        elem ++;
    }

    @Override
    public int consuma() {
        // TODO Auto-generated method stub

        int valore = buffer[tail];

        System.out.println("[DISPATCHER] Consumato valore:" + valore + " posizione " + tail);

        tail = (tail + 1) % size;
        elem --;

        return valore;

    }

    @Override
    public int getSize() {
        // TODO Auto-generated method stub
        return size;
    }

    @Override
    public boolean full() {
        // TODO Auto-generated method stub

        if(elem == size)
            return true;
        return false;

    }

    @Override
    public boolean empty() {
        // TODO Auto-generated method stub
        if(elem == 0)
            return true;
        return false;
    }

}
