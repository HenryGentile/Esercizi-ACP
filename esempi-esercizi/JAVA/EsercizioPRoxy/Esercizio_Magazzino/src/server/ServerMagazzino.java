package server;

//Questa classe è FONDAMENTALE nel modello proxy/skeleton. Infatti instanzia i due magazzini
//passando il riferimento di quello implementato al magazzino skeleton in questione 

public class ServerMagazzino {

    public static void main(String[] args){
        MagazzinoSkeletonImp mgz = new MagazzinoSkeletonImp(5);
        ServerMagazzinoSkt magazzino = new ServerMagazzinoSkt(mgz, Integer.valueOf(args[0]));
        magazzino.runskeleton();
        
    }

}
