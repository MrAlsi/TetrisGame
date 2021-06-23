package com.company;

import com.googlecode.lanterna.gui2.Label;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.TextBox;

import static com.company.MainSchermata.Schermata;
import static com.googlecode.lanterna.TextColor.ANSI.BLACK;

import static com.googlecode.lanterna.TextColor.ANSI.BLACK;


public class ServerSender implements Runnable{

    public HashMap<String ,PrintWriter> connectedClients;
    private Panel panel;
    private TextColor coloreLabel;


    public ServerSender(Panel panel, TextColor coloreLabel, HashMap connectedClients) {

        this.panel=panel;
        this.coloreLabel=coloreLabel;
        this.connectedClients=connectedClients;

    }

    // Aggiungo la textbox al pannello pre-partita del server
    public void run() {

        TextBox messaggio = new TextBox();
        panel.addComponent(messaggio);
        inviaMessaggio(messaggio);

    }

    // Aggiungo il pulsante "Send" al pannello pre-partita del server
    public void inviaMessaggio(final TextBox messaggio){
        new Button("Send",new Runnable(){
            @Override
            public void run(){

                // Posso inviare il messaggio solo nel caso non sia "vuoto"
                if(!messaggio.getText().equals("")) {
                    String messaggioString = messaggio.getText();

                    // Se il server invia il messaggio "/start" il gioco parte
                    if(messaggioString.equals("/start")){
                        panel.removeAllComponents();
                        panel.setVisible(false);
                        broadcastServerMessage(messaggioString);

                    } else{

                        // In tutti gli altri casi trasmetto il messaggio del server a tutti i client connessi
                        Label lab_serverMsg = new Label("[SERVER]: " + messaggioString).setBackgroundColor(BLACK).setForegroundColor(coloreLabel);
                        panel.addComponent(lab_serverMsg);

                        broadcastServerMessage("[SERVER]: " + messaggioString);
                    }

                    // Una volta inviato il messaggio pulisco la textbox
                    messaggio.setText("");
                }
            }
        }).addTo(panel);

        // Aggiungo il pulsante "indietro" alla schermata pre-partita del server
        new Button("Close",new Runnable(){
            @Override
            public void run(){

                // Una volta premuto spengo il server e torno alla home
                // Da sistemare
                try {

                    System.exit(0);

                } catch (Exception e) {

                    e.printStackTrace();

                }
            }
        }).addTo(panel);
    }

    // Metodo per inoltrare il messaggio del server a tutti i client
    public void broadcastServerMessage(String message) {

        for(Map.Entry<String, PrintWriter> e : connectedClients.entrySet()) {

            e.getValue().println(message);
            e.getValue().flush();
        }
    }
}

