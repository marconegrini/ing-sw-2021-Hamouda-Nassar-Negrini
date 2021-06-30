Implementazione del gioco da tavolo [Maestri del Rinascimento](http://www.craniocreations.it/prodotto/masters-of-renaissance/)

Interazione e gameplay da linea di comando (CLI) e grafica (GUI)

##Documentazione
E' possibile visualizzare la documentazione java all'interno del percorso deliverables\final\javadoc.

##Coverage Report
I test sono stati effettuati con junit e sono consultabili all'interno del percorso deliverables\final\report.

##Librerie e plugin
|__Maven__| 
|__JavaFx__|
|__JUnit__|
|__Gson__|

##Funzionalità
Regole complete + CLI + GUI + Socket + 1 Funzionalità aggiuntiva (partite multiple)

##Jar ed esecuzione
I file jar del progetto sono posizionati all'interno del percorso deliverables\final\jar. Posizionandosi con il terminale
windows sul jar del server, digitando 'java -jar PSP32-server', verrà eseguito il server. 
Allo stesso modo, avviando PSP32-client inizierà di default il gioco in GUI.
Per avviare il gioco da linea di comando è necessario installare WSL (Windows Subsystem for Linux), posizionarsi sulla 
cartella di PSP32-client, ed eseguire il jar (sempre con 'java -jar PSP32-client') digitando come opzione di esecuzione -c o --cli.

##Componenti del gruppo
Adel Nassar
Marco Negrini
Amir Hamouda