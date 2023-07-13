package client;

import java.net.*;
import java.io.*;

import magazzino.*;

public class WorkerMagazzinoProxy implements IMagazzino {

    private String addr;
    private int port;
    
    public WorkerMagazzinoProxy(String a, int p){
        addr = a;
        port = p;
    }

    public void deposita(String articolo, int id) {
        //metodo per depositare sul flusso della socket aperta
        try{

            Socket socket = new Socket (addr, port);

            //datain e dataout servono per leggere/scrivere dati primitivi sui flussi di byte dell conn. socket
            DataInputStream dataIn = new DataInputStream(socket.getInputStream());
            DataOutputStream dataOut = new DataOutputStream(socket.getOutputStream());

            System.out.println("\n[CLIENT] Deposito articolo:" + articolo + " id:" + id);
            dataOut.writeUTF("deposito#" + articolo );
            dataOut.writeInt(id);

            dataIn.readUTF(); 

            socket.close();

        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public int preleva(String articolo) {
    

        int x = 0; //id da restituire associato al prodotto

        try{

            Socket socket = new Socket (addr, port);

            //datain e dataout servono per leggere/scrivere dati primitivi sui flussi di byte dell conn. socket
            DataInputStream dataIn = new DataInputStream(socket.getInputStream());
            DataOutputStream dataOut = new DataOutputStream(socket.getOutputStream());

            dataOut.writeUTF("prelievo#" + articolo);
            x = dataIn.readInt(); //bloccante per cui ok

            System.out.println("\n[CLIENT] Prelievo articolo:" + articolo + " id:" + x);

            socket.close();

        }catch(IOException e){
            e.printStackTrace();
        }

        return x;
    }
    
}
