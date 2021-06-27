package com.company;

import com.googlecode.lanterna.gui2.Label;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.TextBox;
import static com.googlecode.lanterna.TextColor.ANSI.BLACK;


import static com.googlecode.lanterna.TextColor.ANSI.BLACK;

public class ServerSender implements Runnable{

    public HashMap<String ,PrintWriter> connectedClients;
    private Panel panel;
    private TextColor coloreLabel;
    private String name;

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
                //toOther.println(userMessage); //... e invialo al server


                if(!messaggio.getText().equals("")) {
                    String messaggioString = messaggio.getText();

                    // Se il server invia il messaggio "/start" il gioco ccerca di partire
                    if(messaggioString.equals("/start")){
                        //controllo il numero dei giocatori
                        if (connectedClients.size()+1<2) {
                            // se con il server (quindi +1) siamo meno di 2 non può iniziare il gioco
                            Label lab_serverMsg = new Label("[SERVER]: Non siete abbastanza giocatori." ).setBackgroundColor(BLACK).setForegroundColor(coloreLabel);
                            panel.addComponent(lab_serverMsg);

                        }else {
                            //svuoto il pannello e avverto i client
                            panel.removeAllComponents();
                            panel.setVisible(false);
                            broadcastServerMessage(messaggioString);


                        }


                    } else{
                        // In tutti gli altri casi trasmetto il messaggio del server a tutti i client connessi
                        Label lab_serverMsg = new Label("["+name+"]: " + messaggioString).setBackgroundColor(BLACK).setForegroundColor(coloreLabel);
                        panel.addComponent(lab_serverMsg);

                        broadcastServerMessage("["+name+"]: " + messaggioString);

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

