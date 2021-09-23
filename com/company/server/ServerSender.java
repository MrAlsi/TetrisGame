package com.company.server;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.TextBox;

import java.io.PrintWriter;
import java.util.Map;

import static com.googlecode.lanterna.TextColor.ANSI.BLACK;

public class ServerSender implements Runnable{

    private Panel panel;
    private TextColor coloreLabel;
    public static String name;
    public String ip;

    public ServerSender(Panel panel, TextColor coloreLabel, String name) {
        this.name=name;
        this.panel=panel;
        this.coloreLabel=coloreLabel;
    }

    // Mostro i componenti
    public synchronized void run() {
        TextBox messaggio = new TextBox();
        panel.addComponent(messaggio);
        inviaMessaggio(messaggio);
    }

    /**
     * Metodo che crea il pulsante "Invia".
     */
    public synchronized void inviaMessaggio(final TextBox messaggio){
        new Button("Invia",new Runnable(){
            @Override
            public void run(){

                // Se la TextBox non è vuota...
                if (!messaggio.getText().equals("")) {

                    String messaggioString = messaggio.getText();

                    // Se il server invia il messaggio "/start" si verifica che la partita possa venire inizializzata.
                    if (messaggioString.equals("/start")) {
                        System.out.println("Il Server ha dato il comando START : Verifica dei giocatori in corso...");

                        // Controllo il numero dei giocatori.
                        // Una partita può iniziare solo se i giocatori sono almeno 2.
                        if (Server.connectedClients.size() < 2) {
                            System.out.println("Numero di giocatori insufficiente.");
                            Label lab_serverMsg = new Label("[SERVER]: Numero di giocatori insufficiente.")
                                    .setBackgroundColor(BLACK).setForegroundColor(coloreLabel);
                            panel.addComponent(lab_serverMsg);
                            broadcastServerMessage("[" + name + "]: " + messaggioString);

                        } else {

                            // Se i giocatori sono sufficenti inizializzo la partita.
                            System.out.println("Numero di giocatori sufficiente : Inizializzando la partita...");

                            // Invio ai client il messaggio "/start"
                            broadcastServerMessage(messaggioString);

                            // Resetto le variabili utilizzate per gestire i giocatori
                            // che parteciperanno alla partita.
                            ConnectionListener.players = "";
                            if(Server.giocatori != null) {
                                Server.giocatori.clear();
                            }
                            for (Map.Entry<String, PrintWriter> pair : Server.connectedClients.entrySet()) {
                                ConnectionListener.players = ConnectionListener.players + pair.getKey() + "-";
                                Server.giocatori.add(pair.getKey());
                            }

                            // Invio ai client una stringa contenente tutti i nomi dei giocatori
                            broadcastServerMessage(ConnectionListener.players);
                            Server.gameStarted = true;
                            System.out.println("- - - PARTITA INIZIATA - - -");
                            Label lab_serverMsg = new Label("[SERVER]: Partita iniziata").setBackgroundColor(BLACK).setForegroundColor(coloreLabel);
                            panel.addComponent(lab_serverMsg);
                            broadcastServerMessage("[" + name + "]: " + messaggioString);
                        }

                        // Se il server invia il messaggio "/quit"...
                    } else if (messaggioString.equals("/quit")) {
                        System.out.println("Chiusura del server in corso...");

                        // Se non ci sono client connessi chiudo il programma.
                        if(Server.connectedClients.size()==0){
                            Server.serverThread.stop();
                            System.out.println("Server spento.");
                            System.exit(0);
                        }

                        // Se ci sono client connessi invio il messaggio ai client
                        // e chiudo il programma.
                        else {
                            broadcastServerMessage(messaggioString);
                            Server.connectedClients.clear();
                            Server.senderThread.stop();
                            Server.serverThread.stop();
                            System.out.println("Server spento.");
                            System.exit(0);

                        }

                        // Se il server invia il messaggio "/restart"...
                    }else if(messaggioString.equals("/restart")) {

                        // Pulisco i messaggi sulllo schermo e invio il messaggio ai client.
                        clear(messaggio);
                        System.out.println("Il Server ha dato il comando RESTART : Termino la partita in corso.");
                        broadcastServerMessage(messaggioString);
                        Label restart= new Label("[SERVER]: Partita annullata.\n[SERVER]: Client disconnessi.")
                                .setBackgroundColor(BLACK).setForegroundColor(coloreLabel);
                        panel.addComponent(restart);
                        Server.connectedClients.clear();

                        // Se il server invia il messaggio "/startagain"...
                    }else if(messaggioString.equals("/startagain")&& Server.gameStarted==true) {
                        if(Server.connectedClients.size()>=2) {

                            // Pulisco i messaggi sulllo schermo e invio il messaggio ai client.
                            clear(messaggio);
                            System.out.println("Il Server ha dato il comando STARTAGAIN : Restart immediato della partita.");
                            Label lab_serverMsg = new Label("[SERVER]: Resettando la partita...").setBackgroundColor(BLACK).setForegroundColor(coloreLabel);
                            panel.addComponent(lab_serverMsg);
                            broadcastServerMessage(messaggioString);
                            Label inizio = new Label("[SERVER]: Partita iniziata...").setBackgroundColor(BLACK).setForegroundColor(coloreLabel);
                            panel.addComponent(inizio);
                        }

                        // Se il server invia il messaggio "/init" resetto forzatamente le variabili.
                    }else if(messaggioString.equals("/init")) {
                        Server.giocatori.clear();
                        Server.connectedClients.clear();
                        Server.gameStarted = false;

                        // Se il server invia il messaggio "/clear" pulisco lo schermo dai messaggi.
                    }else if(messaggioString.equals("/clear")){
                        clear(messaggio);
                    } else {
                        if(messaggioString.equals("/startagain")){
                            System.out.println("Numero di giocatori insufficiente");
                            Label lab_serverMsg = new Label("[SERVER]: Numero di giocatori insufficiente.")
                                    .setBackgroundColor(BLACK).setForegroundColor(coloreLabel);
                            panel.addComponent(lab_serverMsg);
                        }else{

                            // In tutti gli altri casi trasmetto il messaggio del server a tutti i client connessi.
                            System.out.println("trasmetto il messaggio del server a tutti i client connessi");
                            Label lab_serverMsg = new Label("[" + name + "]: " + messaggioString).setBackgroundColor(BLACK).setForegroundColor(coloreLabel);
                            panel.addComponent(lab_serverMsg);
                            broadcastServerMessage("[" + name + "]: " + messaggioString);
                        }
                    }

                    // Una volta inviato il messaggio pulisco la textbox.
                    messaggio.setText("");
                }
            }
        }).addTo(panel);
    }

    /**
     * Metodo che permette al server di inviare un proprio messaggio
     * a tutti i client.
     */
    public void broadcastServerMessage(String message) {

        for(Map.Entry<String, PrintWriter> e : Server.connectedClients.entrySet()) {

            e.getValue().println(message);
            e.getValue().flush();
        }
    }

    /**
     * Metodo di utility che permette al server di eliminare
     * tutti i messaggi presenti sullo schermo.
     */
    public void clear( TextBox messaggio){
        System.out.println("Pulisci schermo...");
        panel.removeAllComponents();
        Label lab=new Label("\nStarting "+ name + "... ").setBackgroundColor(BLACK).setForegroundColor(
                coloreLabel);
        panel.addComponent(lab);
        Label myIp = new Label("\nShare your ip address: " + Server.ip).setBackgroundColor(BLACK).setForegroundColor(
                coloreLabel);
        panel.addComponent(myIp);
        Label lab_serverOn=new Label("\n- - - Server on - - -").setBackgroundColor(BLACK).setForegroundColor(coloreLabel);
        panel.addComponent(lab_serverOn);
        panel.addComponent(messaggio);
        inviaMessaggio(messaggio);
    }
}
