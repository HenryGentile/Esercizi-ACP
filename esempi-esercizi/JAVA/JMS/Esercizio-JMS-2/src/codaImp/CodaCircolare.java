package codaImp;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import coda.Icoda;

public class CodaCircolare implements Icoda{

    private int size;
    private int head;
    private String[] methods;
    private int elem;

    public CodaCircolare(int size){
        this.size = size;
        head = 0;
        methods = new String[size];
        elem = 0;
    }

    public void svuota(String fn, int update) {

        
        try {

            System.out.println("[TEXECUTOR] Svuotamento No:" + update + " coda in corso. Salvataggio su file...");

            FileWriter file = new FileWriter(fn, true);
            BufferedWriter bw = new BufferedWriter(file);
            PrintWriter pw = new PrintWriter(bw);

            pw.write("UPDATES N." + String.valueOf(update) + "\n");
            pw.flush();

            for (int i = 0; i < elem; i++){

                pw.write(methods[i] + "\n");
                pw.flush();
                elem = elem - 1;
            }

            pw.close();
            bw.close();
            file.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean empty() {
        if (elem == 0)
            return true;
        return false;
    }

    public boolean full() {
        if (elem == size)
            return true;
        return false;
    }

    public int getsize(){
        return size;
    }

    public void put(String m) {
        
        methods[head % size] = m;
        elem = elem + 1;
        System.out.println("[LISTENER-TMANAGER] Inserimento metodo:" + methods[head % size] + " (tot:" + elem + ")");
        head = head + 1;

    }
    
}
