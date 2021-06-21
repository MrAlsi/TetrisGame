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

                    if(messaggioString.equals("/start")){
                        panel.removeAllComponents();
                        panel.setVisible(false);
                        broadcastServerMessage(messaggioString);

                    } else{
                        Label lab_serverMsg = new Label("[SERVER]: " + messaggioString).setBackgroundColor(BLACK).setForegroundColor(coloreLabel);
                        panel.addComponent(lab_serverMsg);

                        broadcastServerMessage("[SERVER]: " + messaggioString);
                    }
                    messaggio.setText("");
                }
            }
        }).addTo(panel);
        new Button("Indietro",new Runnable(){
            @Override
            public void run(){
                ConnectionListener.handlerThread.interrupt();
                Server.listenerThread.interrupt();
                Server.serverThread.interrupt();
                Server.senderThread.interrupt();
                panel.removeAllComponents();
                panel.setFillColorOverride(BLACK);
                Schermata(panel);

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

