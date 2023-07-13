package client;

public class Client{

   public static void main(String[] args){
    //generiamo 5 thread client, li runniamo, e poi attendiamo la loro fine
    //generiamo inoltre l'istanza dispatcher proxy che tutti dovranno usare
        int nthreads=5;
        ClientThread[] workers = new ClientThread[nthreads];

        System.out.println("[CLIENT] inizio istanziamento dei threads...");

        DispatcherProxy dispatcher = new DispatcherProxy("127.0.0.1", 8000);
        for (int i = 0 ; i < nthreads ; i++){
            workers[i] = new ClientThread(dispatcher, i);

            workers[i].run();
        }
    }

}