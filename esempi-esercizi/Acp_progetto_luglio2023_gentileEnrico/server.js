const express = require('express');
const http = require('http');
const mongoose = require('mongoose');
const db_url = "mongodb://127.0.0.1:27017/";

const PORT = 3000;

var app = express();

/*
    *Creazione server connesso ai nostri file html e istanziamento in ascolto sul porto 3000
*/

console.log("Server connect on port:" + PORT)

app.use(express.static(__dirname + "/client"));
app.use(express.urlencoded({ extended: true }));


/*
    * Creazione della connessione con il database
*/


mongoose.connect(db_url)
    .then( result=> console.log("db conncected"))
    .catch(err => console.log('ERROR: db not connected '));



let NoleggioSchema = mongoose.Schema({

    DataRitiro: Date,
    DataConsegna: Date,
    Prezzo: Number,
    Username: String
})

let autoSchema = mongoose.Schema({

    Modello: String,
    Targa: String,
    prezzoGiornoNoleggio: Number, 
    NumPasseggeri: Number, 
    Alimentazione: String,
    PotenzaMotore: Number,
    Noleggi: [NoleggioSchema]
});

let auto = mongoose.model("carsdocument", autoSchema);

/*
 * Data Model per gli utenti registrati
 */

let URSchema = mongoose.Schema({
    Username: String,
    Password: String 
})


let utente = mongoose.model("userdocument", URSchema);


http.createServer(app).listen(PORT);




app.get("/auto", function(req, res){


    auto.find({})
        .then(Element =>{
  
        console.log(Element);
        res.json(Element);
        res.status(200).send();
        console.log("GET : CORRECT");

    }).catch((err) =>{

        console.log("GET : ERROR");
        res.status(404).send();
    })

});

// post per i noleggi

app.post("/noleggio", function(req, res){

    /*
     * Req passa : Id auto, Data ritiro, data consegna, prezzo totale, username
     */

    let NewNoleggio = {
        "DataRitiro" : req.body.DataRitiro,
        "DataConsegna": req.body.DataConsegna,
        "Prezzo": req.body.Prezzo,
        "Username": req.body.username
    }

    console.log(NewNoleggio);
    

    auto.findByIdAndUpdate(

        req.body.Id_auto,
        { $push: { Noleggi: NewNoleggio } },
        { new: true }

    ).then(Element =>{

        if (Element.length !== 0){
            console.log("Auto Aggiornata:" + Element);
            res.status(200).send("Noleggio Prenotato con successo");
        } else{
            console.log("POST : ERROR");
            res.status(404).send("ERRORE: AUTO NON TROVATA");
        }

    }).catch((err) =>{

        console.log("POST : ERROR");
        res.status(404).send("Errore durante la fase di noleggio");

    })

})





// post utente registrato (con ricerca di esistenza dello username).

app.post("/Register", function(req, res){

    let newUtente = new utente({
        "Username": req.body.username,
        "Password": req.body.password
    })

    console.log(req.body.username + " " + req.body.password);

    utente.find(

        {"Username": req.body.username}, 
        {new: true }

    ).then(Element =>{
        if (Element.length === 0) {
            // Nessun documento trovato
            console.log("USERNAME NOT FOUND. TRY SAVE");

            newUtente.save().then(Element =>{
  
                console.log(Element);
                res.status(200).send();
                console.log("POST USER : CORRECT");
        
            }).catch((err) =>{
        
                console.log("POST USER : ERROR");
                res.status(404);
            })

          } else {
            // Documento trovato nella collection
            console.log("USERNAME ALREADY EXIST: " + Element);
            res.status(404).send("USERNAME ALREADY EXIST");
          }

    }).catch((err) =>{
        console.log("POST : ERROR FIND");
        res.status(404).send("ERROR FIND");
    })

})





// post per il login (in questo caso controlliamo solo se l'utente è giò registrato)



app.post("/login", function(req, res){


    console.log(req.body.username + " " + req.body.password);

    utente.find({ "Username" : req.body.username, "Password": req.body.password}).then(Element =>{

        if (Element.length === 0) {

            // Nessun documento trovato
            console.log("ERROR FIND USER");
            res.status(404).send("ERROR : USER NOT FOUND");

        } else {
            // Documento trovato nella collection
            console.log(" FIND USER");
            res.status(200).send();
        }
        

    }).catch((err) =>{

        console.log("GET : ERROR ");
        res.status(404).send("GET : ERROR ");

    })

})


