package client;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;

import interfacce.IDispatcher;

public class Actuator {
    
    public static void main(String[] args){

        IDispatcher dispatcher = new DispatcherProxy(Integer.valueOf(args[0]));
        FileWriter file;
        int count = 0;

        while(true){

            try {
                
                Thread.sleep(1000);

                int valore = dispatcher.getCmd();

                System.out.println("Comdando ottenuto:" + valore + ". Procedere stampa");

                file = new FileWriter("cmdlog.txt.", true);
                BufferedWriter bw = new BufferedWriter(file);
                PrintWriter pw = new PrintWriter(bw);

                pw.println((count + 1) + "o comdando:" + valore);
                pw.flush();

                pw.close();
                bw.close();
                file.close();

                count++;

            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }

        }

    }

}
