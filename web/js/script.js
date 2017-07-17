/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function() {
    // Individuo l'elemento del DOM con id Ricerca non appena il documento è stato interamente caricato.
    $("#Ricerca").keyup(function() {
       // Ogni volta che un tasto viene rilasciato (perciò premuto) estraggo il valore dalla barra di ricerca
       var text = $("#Ricerca").val();
       
       // Strutturo la richiesta ajax
       $.ajax({
          url: "filter.json", // URL in cui la servlet Filter è in ascolto
          data: {
              cmd: "search", // mi consente di capire, dalla servlet, in che modo gestire la richiesta
              text: text
          },
          dataType: 'json', // Specifico il tipo di formato utilizzato per la risposta
          success: function(data, state) { // Funzione da eseguire nel caso di corretta gestione della richiesta del server
              aggiornaListaOggetti(data); //chiamo la funzione di aggiornamento listaOggetti (quindi della tabella)
          },
          error: function(data, state) {
              
          }        
       });
       
       // Definisco la funzione che aggiorna la lista oggetti (listaOggetti è l'array di oggetti Json!)
       function aggiornaListaOggetti(wallsList) {
           // Svuoto la tabella
           $("#items").empty();
           // Controllo che la listaOggetti filtrata contenga almeno un elemento, altrimenti stampo un errore
           if(wallsList.length < 1) {
                 $("#searchError").show(); // mostro il paragrafo con id searchError, di default nascosto
           } else { // Ricreo l'intestazione
               $("#searchError").hide(); // nascondo in caso precedentemente abbia mostrato il paragrafo errore
               
               // Definisco thead (intestazione della tabella), tr e td
               /* INIZIO TABLE HEAD *******************************************/
               /* 
               var newThead = document.createElement("thead");
               var trIntestazione = document.createElement("tr");
               
               var th1 = document.createElement("th");
               var text = document.createTextNode("ID ");
               th1.appendChild(text);
               trIntestazione.appendChild(th1);
               
               var th2 = document.createElement("th");
               var text = document.createTextNode("OWNER ");
               th2.appendChild(text);
               trIntestazione.appendChild(th2);
               
               var th3 = document.createElement("th");
               var text = document.createTextNode("NAME ");
               th3.appendChild(text);
               trIntestazione.appendChild(th3);
               
               var th4 = document.createElement("th");
               trIntestazione.appendChild(th4);
               
               newThead.appendChild(trIntestazione); //aggiungo al thead il tr con i vari td
               
               $("#items").append(newThead); // aggiungo alla tabella con id items il thead
               */
            /* FINE TABLE HEAD - INIZIO TABLE BODY ****************************/
            
           var tbody = document.createElement("tbody"); // definisco il tbody (il corpo della tabella)
           
           for(var oggetto in wallsList) // Per ogni oggetto trovato nel database
           {
               // Creo un nuovo tag tr
               var newtr = document.createElement("tr"); // Creo un nuovo tag tr
               /*
               // Campo wallid
               var tdWallId = document.createElement("td");
               tdWallId.setAttribute("class","wallid");
               var text = document.createTextNode(wallsList[oggetto].wallid);
               tdWallId.appendChild(text);
               newtr.appendChild(tdWallId);
               */
               // Campo owner
               var tdOwner = document.createElement("td");
               tdOwner.setAttribute("class","owner");
               var text = document.createTextNode(wallsList[oggetto].owner);
               tdOwner.appendChild(text);
               newtr.appendChild(tdOwner);
               // Campo nome (con indicazione su bacheca utente o gruppo)
               var tdName = document.createElement("td");
               tdName.setAttribute("class","name");
               var text;
               if(wallsList[oggetto].name.length>0)
                 text = document.createTextNode("Gruppo "+wallsList[oggetto].name);
               else
                 text = document.createTextNode("Bacheca Utente");
               tdName.appendChild(text);
               newtr.appendChild(tdName);
               
               //************************* Bottone con form nascosto incorporato
               var form = document.createElement("form");
               form.id="LoadWall";
               form.name="Submit";
               form.method="POST";
               form.action="loadwall.html";
               
               var hiddenwallid = document.createElement("input");
               hiddenwallid.setAttribute("type", "hidden");
               hiddenwallid.setAttribute("name", "wallid");
               hiddenwallid.setAttribute("value", wallsList[oggetto].wallid);
               
               var hiddenowner = document.createElement("input");
               hiddenowner.setAttribute("type", "hidden");
               hiddenowner.setAttribute("name", "owner");
               hiddenowner.setAttribute("value", wallsList[oggetto].owner);
               
               var hiddenname = document.createElement("input");
               hiddenname.setAttribute("type", "hidden");
               hiddenname.setAttribute("name", "name");
               hiddenname.setAttribute("value", wallsList[oggetto].name);
               
               var hiddenisgroup = document.createElement("input");
               hiddenisgroup.setAttribute("type", "hidden");
               hiddenisgroup.setAttribute("name", "isgroup");
               if(wallsList[oggetto].name.length>0)
                   hiddenisgroup.setAttribute("value",true);
               else
                   hiddenisgroup.setAttribute("value",false);
               
               var s = document.createElement("input");
               s.type = "submit";
               s.name= "Submit";
               s.value = "Vai";
               
               form.appendChild(hiddenwallid);
               form.appendChild(hiddenowner);
               form.appendChild(hiddenname);
               form.appendChild(hiddenisgroup);
               form.appendChild(s);
               newtr.appendChild(form);
               
               tbody.appendChild(newtr); // collego al tbody il tr creato con i vari td, questo per ogni oggetto nella lista
               
           }
           // FINE TABLE BODY
           $("#items").append(tbody); // aggiungo il tbody alla tabella, una solta volta fuori dal ciclo
         }
       }
    });
});
