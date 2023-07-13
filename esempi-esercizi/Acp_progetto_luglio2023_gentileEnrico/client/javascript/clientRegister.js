var main = function(login){

    "use strict";

    console.log("Caricamento completato");

    // Setto il minimo al calendario partendo dalla data odierna
    // usando min nell'html devo predisporre la data scritta come il formato richiesto yyyy-mm-dd
    let oggi = new Date();

    let giorno = oggi.getDate();
    let mese = oggi.getMonth() + 1; //L'insieme parte da 0=Gennaio;
    let anno = oggi.getFullYear();

    if (giorno < 10){
        giorno = '0' + giorno;
    }

    if(mese < 10){
        mese = '0' + mese;
    }
    

    oggi = anno + "-" + mese + "-" + giorno;

    console.log(oggi);
    
    document.getElementById("DataRitiro").setAttribute("min", oggi);
    document.getElementById("DataConsegna").setAttribute("min", oggi);

    // Creazione degli elementi per l'area personale
    var $newArea = $("<div>").addClass('InformazioniUtente');
    var $newAreaLista = $("<ul>").addClass("ListaUsernameBase");
    var $newAreaOverlay = $("<div>").addClass('overlay');
    var $NewLiPassword = $("<li>").addClass("ListaText").text("PASSWORD:   ");



    $newAreaLista.append($("<li>").append($("<button>").addClass("ChiudiArea").text("X")));
    $newAreaLista.append($("<li>").addClass("ListaText").text("USERNAME:   " + login.username));
    $NewLiPassword.append(($("<p>").addClass("PasswordTesto").text(login.password)));
    $NewLiPassword.append(($("<button>")).addClass("VisionePassword"));
    $newAreaLista.append($NewLiPassword);
    $newAreaLista.append($("<li>").append($("<button>").addClass("BottoneListaNoleggi").text("Elenco Noleggi")));

    $newArea.append($newAreaLista);

    $newAreaOverlay.append($newArea);

    $("main").prepend($newAreaOverlay);

    $(".cerca").on("click", function(){

        ricerca();

    });


    $(".UserInfo").on("click", function(){

        setTimeout( function(){
            var $overlayNuovoAttr = document.querySelector(".overlay");
            $overlayNuovoAttr.style.left = "0px";
        }, 1000);


        $(".BottoneListaNoleggi").one("click", function(){

            $(".InformazioniUtente .ListaNoleggiUsername").remove();
            ListaNoleggi(login.username);
        });

        $(".VisionePassword").one("click", function(){
            Visione($NewLiPassword);
        });        

        $(".ChiudiArea").one("click", function(){

            setTimeout( function(){

                var $overlayNuovoAttr = document.querySelector(".overlay");
                $overlayNuovoAttr.style.left = "-9999px";
                $(".InformazioniUtente .ListaNoleggiUsername").remove();

            }, 1000);
        });



    })
}


function Visione($NewLiPassword){

    var passwordText = document.querySelector('.PasswordTesto');
    passwordText.style.webkitTextSecurity = 'none';

    $NewLiPassword.append(($("<button>")).addClass("NascondiPassword"));
    
    $(".InformazioniUtente .VisionePassword").remove();

    $(".NascondiPassword").one("click", function(){
        Nascondi($NewLiPassword);
    });

}





function Nascondi($NewLiPassword){

    var passwordText = document.querySelector('.PasswordTesto');
    passwordText.style.webkitTextSecurity = 'disc';

    $NewLiPassword.append(($("<button>")).addClass("VisionePassword"));

    $(".InformazioniUtente .NascondiPassword").remove(); 

    $(".VisionePassword").one("click", function(){
        Visione($NewLiPassword);
    }); 
    
}




// Bottone Ricerca
var count = 0; //definito quì sarà utile per la creazione di elementi in html

function ricerca(){

    /**
     * La funzione di ricerca ci consente di ottenere dal server le informazioni su tutte
     * le vetture ricercate. 
     */

    // filtri sulle due stringe di date inserite(Data ritiro < Data Consegna)

    if ($("#DataRitiro").val() >= $("#DataConsegna").val()){

        window.alert("Data Ritiro e Data Consegna non valide");

        return;
    }

    $(".elencoAuto table").empty() //Ripuliamo il div dalle precendenti tabelle se il controllo è OK

    var $TabellaAuto = $("<table>").addClass('ParcoAuto');
    var $RigaHeader = $("<tr>").addClass("IntestazioneColonne");
    $RigaHeader.append(($("<th>")).text("Modello"));
    $RigaHeader.append(($("<th>")).text("Targa"));
    $RigaHeader.append(($("<th>")).text("Prezzo al giorno"));
    $RigaHeader.append(($("<th>")).text("Passeggeri"));
    $RigaHeader.append(($("<th>")).text("Alimentazione"));
    $RigaHeader.append(($("<th>")).text("Potenza Motore"));
    $RigaHeader.append(($("<th>")).text("Noleggia"));

    $TabellaAuto.append($RigaHeader);

    // Get per ottenere le auto dal server

    $.getJSON("/auto").then(Element =>{

        // filtrare le auto in base a quelle non noleggiate già nella fascia temporale scelta
        Element.forEach(auto => {

            
            var $NoleggioBottone = $("<button>").addClass("BottoneNoleggia").text("Noleggia");
            
            // sfrutto l'index del bottone per memorizare l'indice della vettura
            $NoleggioBottone.attr('data-index', auto._id);

            // sfrutto invece il valore per settare il prezzo giorno-noleggio
            $NoleggioBottone.attr("data-valore", auto.prezzoGiornoNoleggio);


            let flag = true;
            let i = 0; 

            // se un auto no ha noleggi non entra nel ciclo e flag resta true
            while((flag) && i < auto.Noleggi.length){

                // se uno dei noleggi associati all'auto matcha con le date da noi scelti
                // settiamo il flag a false


                if(!(($("#DataRitiro").val() > auto.Noleggi[i].DataConsegna) || 
                    ($("#DataConsegna").val() < auto.Noleggi[i].DataRitiro))){

                        console.log("la data non va bene");
                        console.log($("#DataRitiro").val() + " " + $("#DataConsegna").val());
                        console.log(auto.Noleggi[i].DataRitiro + " " + auto.Noleggi[i].DataConsegna);
                        flag = false;

                } else{

                    i++;
                }

            }
            
            if (flag){

                // Creiamo la tabella dove settiamo i valori in questione

                $TabellaAuto.append(InsertAuto(auto).append($NoleggioBottone));

            }

        });

    }).catch((err) =>{

        window.alert("ERROR:" + err);

        return;
    })


    $(".elencoAuto").append($TabellaAuto);

    // creiamo un listener dinamico ad ogni ricerca. One() ci garantisce che una volta effettuato
    // il primo click il listener è rimosso
    $(".elencoAuto").one('click', '.BottoneNoleggia', NoleggiaAuto);

}




function InsertAuto(auto){

    let $newRiga = $("<tr>");

    if (count % 2 === 0){

        $newRiga.addClass("EvenRow");

        $newRiga.append($("<td>").text(auto.Modello));
        $newRiga.append($("<td>").text(auto.Targa));
        $newRiga.append($("<td>").text(auto.prezzoGiornoNoleggio + "€"));
        $newRiga.append($("<td>").text(auto.NumPasseggeri));
        $newRiga.append($("<td>").text(auto.Alimentazione));
        $newRiga.append($("<td>").text(auto.PotenzaMotore + " cavalli"));

    } else{


        $newRiga.addClass("OddRow");

        $newRiga.append($("<td>").text(auto.Modello));
        $newRiga.append($("<td>").text(auto.Targa));
        $newRiga.append($("<td>").text(auto.prezzoGiornoNoleggio + "€"));
        $newRiga.append($("<td>").text(auto.NumPasseggeri));
        $newRiga.append($("<td>").text(auto.Alimentazione));
        $newRiga.append($("<td>").text(auto.PotenzaMotore + " cavalli"));

    }

    count = count + 1;

    /**
     * adattiamo la dimensione del main quando la tabella cresce 
     */

    if(count === 7){
        var $body = document.querySelector('.Body');
        $body.style.height = '100%';
    }

    return $newRiga;

}




// POST del noleggio dell'auto
function NoleggiaAuto(){

    // Construiamo il nostro nuovo Noleggio Auto

    const urlParams = new URLSearchParams(window.location.search);
    const parametro1 = urlParams.get('parametro1');

    // calcoliamo la distanza in giorni tra le due date 

    // il divisore ci serve perchè la distanza tra due date viene fatta in millisecondi
    // quindi per ottenerla in giorni dobbiamo dividerla per questa costante
    const Divisore = 1000 * 60 * 60 * 24;

    let DiffDate = new Date($("#DataConsegna").val()).getTime() - new Date($("#DataRitiro").val()).getTime();

    let GiorniDiff = Math.floor(DiffDate / Divisore);

    console.log($("#DataRitiro").val() + " " + $("#DataConsegna").val());
    console.log(DiffDate);
    console.log(GiorniDiff);

    // ora possiamo calcolare il prezzo finale della prenotazione


    let NewNoleggio = {
        "Id_auto": $(this).data('index'),
        "DataRitiro": $("#DataRitiro").val(),
        "DataConsegna": $("#DataConsegna").val(),
        "Prezzo": $(this).data('valore') * GiorniDiff,
        "username": parametro1
    }

    console.log(NewNoleggio);

    $.post("/noleggio", NewNoleggio).then(result =>{

        window.alert("Auto Noleggiata con successo");

        $(".ParcoAuto").empty();

        var $body = document.querySelector('.Body');
        $body.style.height = '550px';

    }).catch((err) =>{
        window.alert("Errore durante la fase di noleggio");
    })


}   





// funzione che fa una get al database, ottiene la lista dei noleggi e li filtra per lo username
// avendo bisogno delle informazioni anche dalle auto uso la stessa rotta della get della ricerca
// e ottengo l'insieme delle autovetture

function ListaNoleggi(username){

    var $listaNoleggiFiltrata = [];

    $.getJSON("/auto").then(Element => {

        //controllo se il noleggio ha lo stesso username e prendo le seguenti informazioni

        Element.forEach(auto => {
            
            for(let i = 0; i < auto.Noleggi.length; i++){
                if(auto.Noleggi[i].Username === username){

                    // costruiamo le due date per facilitare la visualizzazione all'utente
                    let dataCaratteri = 10;

                    let $NoleggioUtente = {
                        "Modello" : auto.Modello,
                        "DataRitiro" : auto.Noleggi[i].DataRitiro.slice(0,dataCaratteri),
                        "DataConsegna": auto.Noleggi[i].DataConsegna.slice(0,dataCaratteri),
                        "Prezzo" : auto.Noleggi[i].Prezzo,
                    }

                    $listaNoleggiFiltrata.push($NoleggioUtente);
                }
            }

        })

        if ($listaNoleggiFiltrata.length === 0){

            window.alert("Non co sono noleggi effettuati");

        } else{

            


            $listaNoleggiFiltrata.forEach(element =>{

                let $newNoleggiLista = $("<ul>").addClass("ListaNoleggiUsername");

                $newNoleggiLista.append($("<li>").text("MODELLO: " + element.Modello));
                $newNoleggiLista.append($("<li>").text("DATA RITIRO: " + element.DataRitiro));
                $newNoleggiLista.append($("<li>").text("DATA CONSEGNA: " + element.DataConsegna));
                $newNoleggiLista.append($("<li>").text("PREZZO NOLEGGIO: " + element.Prezzo + "€"));

                $(".InformazioniUtente").append($newNoleggiLista);

            })

        }

    }).catch((err) => {

        window.alert("ERROR:" + err);

        return; 
    });

}




$(document).ready(function(){

    /*
     * Cerco i parametri attraverso window.location.search.length. Se nonn ci sono restituisce ""
     * se non ci sono i parametri vuol dire tentare di accedere alla pagina senza avere l'autenticazione
     * di utente registrato. Per questo chiudiamo la pagina.
    */

    if (window.location.search.length > 0) {

        const urlParams = new URLSearchParams(window.location.search);
        const parametro1 = urlParams.get('parametro1');
        const parametro2 = urlParams.get('parametro2');

        let login = {
            username: parametro1,
            password: parametro2
        };


        $.post('/login', login)
        .then((result) => {

            //controlliamo se effettivamente è un utente registrato al sito
            //window.location.href = "http://localhost:3000/htmlpages/indexAccess.html";

            main(login);

          }).catch((err) => {

            // Se non è presente allora torniamo indietro alla pagina home
            window.alert("ERROR : USERNAME/PASSWORD");

            window.location.href = "http://localhost:3000/htmlpages/index.html";

          });

    } else {
        // Sezione con parametri vuoti. Torniamo alla home page 
        window.location.href = "http://localhost:3000/htmlpages/index.html";
    }
    



});


