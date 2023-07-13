package client;

import java.io.*;

import dispatcher.*;

public class Actuator{
    public static void main(String[] args){
        //l'attuatore deve estrarre dal dispatcher un comando e printarlo su un file txt
        IDispatcher dispatcher = new DispatcherProxy("127.0.0.1", 8000);
        int cmd=0;
        String[] command = new String[4];
        command[0]="leggi"; command[1]="scrivi";
        command[2]="configura"; command[3]="reset";

        try ( //generiamo un flusso file e un flusso out
            FileOutputStream fileOut = new FileOutputStream ( "./cmdlog.txt")) {
            PrintStream outStream = new PrintStream ( fileOut );
            
            cmd=dispatcher.getCmd();
            System.out.println("\n[ACTUATOR] COMANDO RICEVUTO:" + command[cmd]);
            outStream.println("comando=" + command[cmd]);

            Thread.sleep(1000);

        } catch (IOException e) {
            e.printStackTrace();
        }catch(InterruptedException u){
            u.printStackTrace();
        }


    }
}