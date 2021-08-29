package com.company.server;

import com.company.server.Server;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.TextBox;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import static com.googlecode.lanterna.TextColor.ANSI.BLACK;

public class ServerSender implements Runnable{

    public HashMap<String ,PrintWriter> connectedClients;
    private Panel panel;
    private TextColor coloreLabel;
    public static String name;
    private String players = "";

    public ServerSender(Panel panel, TextColor coloreLabel, HashMap connectedClients, String name) {
        this.name=name;
        this.panel=panel;
        this.coloreLabel=coloreLabel;
        this.connectedClients=connectedClients;

    }

    public void run() {

        TextBox messaggio=new TextBox();
        panel.addComponent(messaggio);
        inviaMessaggio(messaggio);

    }

    public void inviaMessaggio(final TextBox messaggio){
        new Button("Invia",new Runnable(){
            @Override
            public void run(){
                //Finché non ricevi un comando "quit" dall'utente...
                //userMessage = userInput.nextLine(); //... leggi un messaggio da console (bloccante!)...
                //toOther.println(userMessage); //... e invialo al com.company.server
                if (!messaggio.getText().equals("")) {

                    String messaggioString = messaggio.getText();

                    // Se il com.company.server invia il messaggio "/start" il gioco ccerca di partire

                    if (messaggioString.equals("/start")) {
                        //controllo il numero dei giocatori
                        if (connectedClients.size() < 2) {
                            Label lab_serverMsg = new Label("[SERVER]: Numero di giocatori insufficiente.").setBackgroundColor(BLACK).setForegroundColor(coloreLabel);
                            panel.addComponent(lab_serverMsg);

                        } else {
                            //svuoto il pannello e avverto i com.company.client
                            broadcastServerMessage(messaggioString);
                            for (Map.Entry<String, PrintWriter> pair : connectedClients.entrySet()) {
                                players = players + pair.getKey() + "-";
                            }
                            broadcastServerMessage(players);
                            Server.gameStarted = true;
                            Label lab_serverMsg = new Label("[SERVER]: Partita iniziata").setBackgroundColor(BLACK).setForegroundColor(coloreLabel);
                            panel.addComponent(lab_serverMsg);
                        }
                    } else if (messaggioString.equals("/quit")) {
                        Label serverClosed = new Label("\n- - SERVER CLOSED - -").setBackgroundColor(BLACK)
                                .setForegroundColor(coloreLabel);
                        broadcastServerMessage("[SERVER]: " + serverClosed);
                        panel.addComponent(serverClosed);
                        Label uscita = new Label("\nLeaving the com.company.server...").setBackgroundColor(BLACK)
                                .setForegroundColor(coloreLabel);
                        broadcastServerMessage("[SERVER]: " + uscita);
                        panel.addComponent(uscita);

                    } else {
                        // In tutti gli altri casi trasmetto il messaggio del com.company.server a tutti i com.company.client connessi
                        Label lab_serverMsg = new Label("[" + name + "]: " + messaggioString).setBackgroundColor(BLACK).setForegroundColor(coloreLabel);
                        panel.addComponent(lab_serverMsg);

                        broadcastServerMessage("[" + name + "]: " + messaggioString);
                    }

                    // Una volta inviato il messaggio pulisco la textbox
                    messaggio.setText("");
                }
            }
        }).addTo(panel);
    }

    public void broadcastServerMessage(String message) {

        for(Map.Entry<String, PrintWriter> e : connectedClients.entrySet()) {

            e.getValue().println(message);
            e.getValue().flush();
        }
    }
}

