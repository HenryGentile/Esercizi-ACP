package server;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Random;
import java.util.concurrent.Semaphore;

import interfaces.ISportello;


public class SportelloImpl extends UnicastRemoteObject implements ISportello{
	
	private static final long serialVersionUID = -5457541672194646702L;
	
	private Semaphore maxReqs;
	private Semaphore maxConcurrentReqs;
	
	
	protected SportelloImpl() throws RemoteException {
		// TODO Auto-generated constructor stub
		
		maxReqs = new Semaphore(5);
		maxConcurrentReqs = new Semaphore(3);
		
	}

	@Override
	public boolean serviRichiesta(int idCliente) throws RemoteException {
		// TODO Auto-generated method stub
		
		boolean result = false;
		//tryacquire() restituisce true se e solo se al momento del richiamo del metodo
		//c'è un permesso disponibile. Altrimenti conviene usare acquire che mette il 
		//thread in attesa che si liberi un permesso, ossia che venga fatta una release();

		if (!maxReqs.tryAcquire()) { 
			
			System.out.println("[Sportello] Raggiunto limite richieste");
			System.out.println("[Sportello] Richiesta da " + idCliente + " non servita");
			return result;
		}
		
		try {
			
			maxConcurrentReqs.acquire();
			
			Random rand = new Random();
			
			Thread.sleep((rand.nextInt(5) + 1) * 1000);
			
			//una classe che gesstisce un file di testo (lo crea nel caso in cui non c'è)
			//bufferWriter serve per accelerare la scrittura
			//PrintWriter invece converte ogni tipo di dato scritto in un dato di testo
			//flush() costringe a svuotare il buffer e a scrivere in output i dati sopra(che in questo
			//caso sarà sul file di testo). Usato per migliorare le performance di I/O
			
			FileWriter fw = new FileWriter("requests.txt", true);
			BufferedWriter bw = new BufferedWriter(fw); 
			PrintWriter pw = new PrintWriter(bw); 
			
			pw.println(idCliente);
			pw.flush();
			
			pw.close();
			bw.close();
			fw.close();
			
			result = true;
			
			System.out.println("[Sportello] Servita richiesta da " + idCliente);
			
		} catch (InterruptedException e) {
			e.printStackTrace();
			result = false;
			
		} catch (IOException e) {
			e.printStackTrace();
			result = false;
			
		} finally {
			//a prescindere da tutto rilasciamo i due mutex, anche se le date procedure hanno fallito
			maxConcurrentReqs.release();
			maxReqs.release();
		}
		
		return result;
	}

}
