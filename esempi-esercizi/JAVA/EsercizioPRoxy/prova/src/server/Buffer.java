package server;

public class Buffer {

    private int[] buff;
    int testa;
    int coda;
    int elem;
    int size;
     
    
    public Buffer(int s){
        size = s;
        elem = 0;
        coda = 0;
        testa = 0;
    }

    public boolean isEmpty(){
        if (elem == 0)
            return true;
        return false;
    }

    public boolean isFull(){
        if (elem == size)
            return true;
        return false;
    }

    public void inserimento(String articolo, int x){
        buff[testa % size] = x;
        elem ++;
        System.out.println("[M] inserito id:" +  buff[testa % size] + " articolo:" + articolo + "(" + elem + ")");
        testa ++;
    }

    public int preleva(String articolo){
        int x = buff[coda % size];
        elem --;
        System.out.println("[M] prelevato id:" +  buff[coda % size] + " articolo:" + articolo + "(" + elem + ")");
        coda ++;
        
        return x;
    }
}
