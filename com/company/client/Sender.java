package com.company.client;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.TextBox;

import java.io.PrintWriter;

import static com.googlecode.lanterna.TextColor.ANSI.BLACK;

// La classe Sender si occupa dell'invio di messaggi verso il com.company.server che poi si occuperà di inoltrarli
// agli altri com.company.client connessi
public class Sender implements Runnable {
    private String name;
    private Panel panel;
    private TextColor coloreLabel;
    private PrintWriter toOther;

    public Sender(PrintWriter pw,Panel panel,TextColor coloreLabel, String name){
        this.name = name;
        this.toOther = pw;
        this.panel=panel;
        this.coloreLabel=coloreLabel;
    }

    public void run() {
        TextBox messaggio=new TextBox();
        panel.addComponent(messaggio);
        inviaMessaggio(messaggio);
    }

    // Inserisco il pulsante "Send" nella schermata pre-partita
    public void inviaMessaggio(final TextBox messaggio){
        new Button("Send",new Runnable(){
            @Override
            public void run(){

                // Quando premo il pulsante stampo il messaggio e lo invio al com.company.server
                if(!messaggio.getText().equals("")) {
                    String messaggioString = messaggio.getText();

                    Label lab_clientMsg = new Label("[" + name + "]: " + messaggioString).setBackgroundColor(BLACK).setForegroundColor(coloreLabel);
                    panel.addComponent(lab_clientMsg);

                    toOther.println(messaggioString);
                    toOther.flush();
                    messaggio.setText("");

                }
            }
        }).addTo(panel);

        new Button("Close",new Runnable(){
            @Override
            public void run(){

                // Una volta premuto spengo il com.company.server e torno alla home
                // Da sistemare
                try {

                    System.exit(0);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).addTo(panel);
    }
}
