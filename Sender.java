package com.company;


import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.TextBox;

import java.io.PrintWriter;
import java.util.Scanner;

import static com.googlecode.lanterna.TextColor.ANSI.BLACK;

public class Sender implements Runnable {
        private Panel panel;
        private TextColor coloreLabel;
        private PrintWriter toOther; //Stream su cui inviare stringhe
    /* Può essere verso il client o verso il server, a seconda di come viene inizializzato
    a Sender non importa: riusabilità del codice */

        public Sender(PrintWriter pw,Panel panel,TextColor coloreLabel){
            this.toOther = pw;
            this.panel=panel;
            this.coloreLabel=coloreLabel;
        }

        public void run() {
            TextBox messaggio=new TextBox();
            panel.addComponent(messaggio);
            inviaMessaggio(messaggio);

            /*Scanner userInput = new Scanner(System.in);
            String userMessage = "";*/


            //userInput.close()
        }
        public void inviaMessaggio(final TextBox messaggio){
            new Button("Invia",new Runnable(){
                @Override
                public void run(){
                    String messaggioString = messaggio.getText();
                    while(!Thread.interrupted()) { //Finché non ricevi un comando "quit" dall'utente...
                        //userMessage = userInput.nextLine(); //... leggi un messaggio da console (bloccante!)...
                        //toOther.println(userMessage); //... e invialo al server
                        toOther.println(messaggioString);
                        toOther.flush();
                    }
                    panel.removeAllComponents();
                    panel.setFillColorOverride(BLACK);



                }
            }).addTo(panel);
        }


    }


