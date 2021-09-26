package com.company.client;

import com.company.MainSchermata;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.TextBox;

import java.io.PrintWriter;

import static com.googlecode.lanterna.TextColor.ANSI.BLACK;

// La classe Sender si occupa dell'invio di messaggi verso il server che poi si occuperà di inoltrarli
// agli altri client connessi
public class Sender implements Runnable {
    private String name;
    private Panel panel;
    private TextColor coloreLabel;
    private PrintWriter toOther;
    public Boolean shown = true;

    public Sender(PrintWriter pw,Panel panel,TextColor coloreLabel, String name){
        this.name = name;
        this.toOther = pw;
        this.panel=panel;
        this.coloreLabel=coloreLabel;
    }

    // Inserisco il pulsante "Send" nella schermata pre-partita
    public void inviaMessaggio(final TextBox messaggio) {
        new Button("Send", new Runnable() {
            @Override
            public void run() {
                // Quando premo il pulsante stampo il messaggio e lo invio al server
                if (!messaggio.getText().equals("")) {
                    String messaggioString = messaggio.getText();
                    //se scrivi quit ti scolleghi dal server ma torni alla schermata iniziale con la possibilità di ricollegarti
                    //allo stesso server o uno diverso
                    if(messaggio.getText().equals("/quit")){
                        panel.removeAllComponents();
                        MainSchermata.Schermata(panel);
                    }
                    else {
                        Label lab_clientMsg = new Label("[" + name + "]: " + messaggioString).setBackgroundColor(BLACK).setForegroundColor(coloreLabel);
                        panel.addComponent(lab_clientMsg);
                        messaggio.setText("");
                    }
                    toOther.println(messaggioString);
                    toOther.flush();
                }
            }
        }).addTo(panel);

        //se schiacci close chiudi il tuo client ma anche la possibilità di collegarti a un altro client
        new Button("Close", new Runnable() {
            @Override
            public void run() {
                shown = false;
                try {
                    System.exit(0);
                    panel.removeAllComponents();
                } catch (Exception e) {
                    //e.printStackTrace();
                }
            }
        }).addTo(panel);
    }

    public void run() {
        TextBox messaggio = new TextBox();
        panel.addComponent(messaggio);
        inviaMessaggio(messaggio);
        shown = false;
    }

}
