var main = function(){


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


    $("input").empty();

    $("#login").on("click", function(){
        login();
    });

    $("#registrazione").on("click", function(){
        registrazione();
    });

    $(".cerca").on("click", function(){

        ricerca();
    });


}

// Bottone Login

function login(){

    // 
    /**
     * estrarre dalle caselle di input i valori
     * Controllo se
     * sono validi (get) (credenziali errate)
     * non sono vuoti (alert inserire valori)
     * se condizioni falliscono return false
     */

    if($("#UsernameInput").val() === "" || $("#PasswordInput").val() === ""){

        window.alert("Inserire credenziali");

        return;

    }else{

        let login = {
            username: $("#UsernameInput").val(),
            password: $("#PasswordInput").val()
        };


        $.post('/login', login)
        .then((result) => {

            console.log(result);
            //window.location.href = "http://localhost:3000/htmlpages/indexAccess.html";

            // Passiamo I parametri all'url usanto encodeURIComponent per settarli bene.
            // Successivamente apriamo la pagina del login. Usando '_blank' come parametro di
            // window.open(url, '_blank') possiamo aprirla in un altra scheda 

            const url = `http://localhost:3000/htmlpages/indexAccess.html?parametro1=${encodeURIComponent($("#UsernameInput").val())}&parametro2=${encodeURIComponent($("#PasswordInput").val())}`;

            window.open(url);

            $("#UsernameInput").val("");
            $("#PasswordInput").val("");

          }).catch((err) => {

            window.alert(err.message);

          });
    }

    $("#UsernameInput").empty();
    $("#PasswordInput").empty();

}




// Bottone Registrazione

function registrazione(){

    // 
    /**
     * estrarre dalle caselle di input i valori
     * Controllo se
     * sono validi (get e controllo se non esistono altri utenti con lo stesso username) (credenziali errate)
     * non sono vuoti (alert inserire valori)
     * se condizioni falliscono return false
     */

    if($("#UsernameInput").val() === "" || $("#PasswordInput").val() === ""){

        window.alert("Inserire credenziali");

        return;

    }else{
        let newUser = {
            username: $("#UsernameInput").val(),
            password: $("#PasswordInput").val()
        };

        console.log(newUser);

        $.post('/Register', newUser)
            .then((res) => {

                const url = `http://localhost:3000/htmlpages/indexAccess.html?parametro1=${encodeURIComponent(newUser.username)}&parametro2=${encodeURIComponent(newUser.password)}`;

                window.open(url);
                $("#UsernameInput").val("");
                $("#PasswordInput").val("");


            }).catch((err) =>{
                window.alert("ERROR");
            })
    }

    $("#UsernameInput").empty();
    $("#PasswordInput").empty();

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


    $TabellaAuto.append($RigaHeader);

    // Get per ottenere le auto dal server

    $.getJSON("/auto").then(Element =>{

        // filtrare le auto in base a quelle non noleggiate già nella fascia temporale scelta
        Element.forEach(auto => {
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

                $TabellaAuto.append(InsertAuto(auto));

            }

        });

    }).catch((err) =>{

        window.alert("ERROR:" + err);

        return;
    })


    $(".elencoAuto").append($TabellaAuto);
        
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

$(document).ready(main());




