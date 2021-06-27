package com.company;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;

import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.Panel;
//import com.sun.tools.javac.Main;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

import static com.googlecode.lanterna.TextColor.ANSI.BLACK;

public class Client implements Runnable {
    private String name;
    private String IP;
    private Panel panel;
    private TextColor coloreLabel;
    private Thread runningThread;
    public static Socket socket;

    // Reperisco dal form di "find game" i vari dati che mi interessano
    public Client(String name, String IP, Panel panel, TextColor coloreLabel) {
        this.name = name;
        this.IP = IP;
        this.panel = panel;
        this.coloreLabel = coloreLabel;
    }

    public void StartClient(Client client){
        new Thread(client).start();
    }

    public void run() {

        // Inizializzo la chat pre-partita del client
        panel.removeAllComponents();
        panel.setFillColorOverride(BLACK);
        panel.setPosition(new TerminalPosition(0, 0));
        panel.setPreferredSize(new TerminalSize(100, 10));
        panel.removeAllComponents();
        panel.setFillColorOverride(BLACK);
        Label lab = new Label("\nYour nickname: " + name).setBackgroundColor(BLACK)
                .setForegroundColor(coloreLabel);
        Label connessione = new Label("\nConnecting to the server...").setBackgroundColor(BLACK)
                .setForegroundColor(coloreLabel);
        panel.addComponent(lab);
        panel.addComponent(connessione);
        Socket socket = null; //Creazione socket, connessione a localhost:1555

        try {
            socket = new Socket(IP, 6789);
            Label connesso = new Label("\n- - - - Connected - - - -\n").setBackgroundColor(BLACK)
                    .setForegroundColor(coloreLabel);
            panel.addComponent(connesso);
        } catch (IOException ex) {
            ex.printStackTrace();
            Label nonconnesso = new Label("\n- - - - Connection failed try again - - - -\n").setBackgroundColor(BLACK)
                    .setForegroundColor(coloreLabel);
            panel.addComponent(nonconnesso);
            panel.removeAllComponents();
            panel.setFillColorOverride(BLACK);

            MainSchermata.Schermata(panel);

        }

        InputStream socketInput = null;

        try {
            socketInput = socket.getInputStream();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        OutputStream socketOutput = null;

        try {
            socketOutput = socket.getOutputStream();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        InputStreamReader socketReader = new InputStreamReader(socketInput);
        OutputStreamWriter socketWriter = new OutputStreamWriter(socketOutput);

        BufferedReader fromServer = new BufferedReader(socketReader); //Legge stringhe dal socket
        PrintWriter toServer = new PrintWriter(socketWriter); //Scrive stringhe sul socket

        //Creazione del thread di invio messaggi
        Sender clientSender = new Sender(toServer,panel,coloreLabel,name);
        Thread senderThread = new Thread(clientSender);

        String message = name;
        toServer.println(message);
        toServer.flush();
        senderThread.start();

        // Finché il server non chiude la connessione o non ricevi un messaggio "/quit"...
        while (message != null && !message.equals("/quit")) {
            try {

                // Leggi un messaggio inviato dal server
                message = fromServer.readLine();

            } catch (IOException ex) {
                ex.printStackTrace();
            }
            // Se il server invia un comando /quit mi disconnetto dal server
            if(message.equals("/quit)")){
                /*Label serverClosed = new Label("\n- - SERVER CLOSED - -").setBackgroundColor(BLACK)
                        .setForegroundColor(coloreLabel);
                panel.addComponent(serverClosed);
                Label uscita = new Label("\nLeaving the server...").setBackgroundColor(BLACK)
                        .setForegroundColor(coloreLabel);
                panel.addComponent(uscita);*/
                Label successo = new Label("\n- - - Server left - - -").setBackgroundColor(BLACK)
                        .setForegroundColor(coloreLabel);
                panel.addComponent(successo);
                break;
            }else if(message.equals("/start")){
                panel.removeAllComponents();
                panel.setVisible(false);
                //break;
                //qui parte il gioco

            }else{
                //Se il messaggio non è nullo lo stampo
                Label lab_clientMsg = new Label(message).setBackgroundColor(BLACK).setForegroundColor(coloreLabel);
                panel.addComponent(lab_clientMsg);
                System.out.println(message);
                /*System.out.println("break");
                break;*/
            }
          /*  if (message != null && !message.equals("/start")) {

                //Se il messaggio non è nullo lo stampo
                Label lab_clientMsg = new Label(message).setBackgroundColor(BLACK).setForegroundColor(coloreLabel);
                panel.addComponent(lab_clientMsg);
                System.out.println(message);*/


        }



        try {
            socket.close(); //Chiudi la connessione
            senderThread.interrupt();

        } catch (IOException ex) {
            ex.printStackTrace();
        }




    }
}
