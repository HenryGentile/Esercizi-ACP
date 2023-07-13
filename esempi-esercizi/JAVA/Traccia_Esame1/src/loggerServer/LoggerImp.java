package loggerServer;

import java.io.FileWriter;
import java.io.PrintWriter;

/*
 * in questa classe implementiamo la memorizzazione fu file del dato inviato dall'utente
 * Il tutto deve avvenire in mutua esclusione (blocchi synchronized)
 */

public class LoggerImp implements ILogger{

    private FileWriter filetext;
    private int count;

    @Override
    public void registraDato(int dato) {
        // TODO Auto-generated method stub

        synchronized(this){
            try {
                System.out.println("Logger: valore ricevuto:" + dato +". Inizio stampa su file...");     
            
                Thread.sleep(4000);

                filetext = new FileWriter("logger.txt", false);

                PrintWriter writer = new PrintWriter(filetext);

                writer.println("Valore:" + dato + "(" + count + ")");

                writer.close();
                filetext.close();

                //apertura del flusso di scrittura
                /* 
                BufferedWriter bw = new BufferedWriter(filetext);
                PrintWriter writer = new PrintWriter(bw);

                writer.println("Valore:" + dato + "(" + count + ")");
                writer.flush(); //svuotiamolo. Scriverà sul file

                bw.close();
                writer.close();
                filetext.close();
                */

                System.out.println("Stampa terminata...");

                count ++;

            }catch (Exception e) {
                // TODO: handle exception
            }

        }
    }
    
}
