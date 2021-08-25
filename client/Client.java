package com.company.client;

import com.company.Gioco.GameOver;
import com.company.Gioco.Restart;
import com.company.Gioco.Schermo;
import com.company.Gioco.YouWin;
import com.company.MainSchermata;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.Panel;

import java.io.*;
import java.net.Socket;

import static com.googlecode.lanterna.TextColor.ANSI.BLACK;

public class Client implements Runnable {
    private String name;
    private String IP;
    private int PORT;
    private Panel panel;
    private TextColor coloreLabel;
    public static Socket socket;
    private String serverName;
    private String playersData;
    public static Boolean winner = false;
    public static Thread gameThread;
    private String message = "";
    private Boolean pause = false;

    // Reperisco dal form di "find game" i vari dati che mi interessano
    public Client(String name, String IP, String PORT, Panel panel, TextColor coloreLabel) {
        this.name = name;
        this.IP = IP;
        this.PORT = Integer.parseInt(PORT);
        this.panel = panel;
        this.coloreLabel = coloreLabel;
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
        socket = null; //Creazione socket, connessione a localhost:1555
        InputStream socketInput = null;
        OutputStream socketOutput = null;
        try {
            System.out.println("Connessione alla socket: " + IP + PORT);
            socket = new Socket(IP, PORT);
            System.out.println("Connessione eseguita!");
            Label connesso = new Label("\n- - - - Connected - - - -\n").setBackgroundColor(BLACK)
                    .setForegroundColor(coloreLabel);
            panel.addComponent(connesso);

            socketInput = socket.getInputStream();

            socketOutput = socket.getOutputStream();

        } catch (IOException ex) {
            ex.printStackTrace();
            Label nonconnesso = new Label("\n- - - - Connection failed try again - - - -\n").setBackgroundColor(BLACK)
                    .setForegroundColor(coloreLabel);
            panel.addComponent(nonconnesso);
            panel.removeAllComponents();
            panel.setFillColorOverride(BLACK);

            MainSchermata.Schermata(panel);

        }


        InputStreamReader socketReader = new InputStreamReader(socketInput);
        OutputStreamWriter socketWriter = new OutputStreamWriter(socketOutput);

        BufferedReader fromServer = new BufferedReader(socketReader); //Legge stringhe dal socket
        PrintWriter toServer = new PrintWriter(socketWriter); //Scrive stringhe sul socket

        //Creazione del thread di invio messaggi
        Sender clientSender = new Sender(toServer,panel,coloreLabel,name);
        Thread senderThread = new Thread(clientSender);

        message = name;
        toServer.println(message);
        toServer.flush();
        senderThread.start();

        try {
            serverName = fromServer.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Finché il server non chiude la connessione o non ricevi un messaggio "/quit"...
        while (message != null && !message.equals("/quit")) {
            try {

                // Leggi un messaggio inviato dal server
                message = fromServer.readLine();

            } catch (IOException ex) {
                ex.printStackTrace();
            }
            // Se il server invia un comando /quit mi disconnetto dal server
            if(message.equals("/quit")){

                Label successo = new Label("\n- - - Server left - - -").setBackgroundColor(BLACK)
                        .setForegroundColor(coloreLabel);
                panel.addComponent(successo);
                break;

            }else if(message.equals("/start")){
                try {

                    panel.removeAllComponents();
                    panel.setFillColorOverride(BLACK);
                    // Leggo i nick di tutti i giocatori
                    MainSchermata.screen.close();
                    if(YouWin.nextGame){
                        YouWin.screen.close();
                    }
                    if(GameOver.nextGame){
                        GameOver.screen.close();
                    }
                    if(Restart.nextGame){
                        Restart.screen.close();
                    }
                    Schermo schermo = new Schermo(toServer, name, IP, PORT, panel, coloreLabel);
                    gameThread = new Thread(schermo);
                    gameThread.start();

                    Schermo.gameOver = false;

                    while(!Schermo.gameOver){
                        try {

                            playersData = fromServer.readLine();

                            if(playersData.contains("/pause") && !pause){
                                pause = true;
                                gameThread.suspend();
                            }

                            if(playersData.contains("/resume") && pause){
                                pause = false;
                                gameThread.resume();
                            }

                            if(playersData.contains("/restart") && !pause){
                                Schermo.gameOver = true;
                                Client.winner = false;
                                System.out.println("Partita ricominciata");
                                Restart ricomincia = new Restart(name, IP, PORT, panel, coloreLabel);
                                Thread ricominciaThread = new Thread(ricomincia);
                                ricominciaThread.start();
                            }

                            if(playersData.equals("[" + name + "]-winner")){

                                winner = true;
                                Schermo.gameOver = true;

                            }

                            if(playersData.equals("spazzatura-"  + name + "-2")) {
                                Schermo.aggiungiSpazzatura = 1;
                                System.out.println("Spazzatura aggiunta: " + Schermo.aggiungiSpazzatura);
                            }
                            if(playersData.equals("spazzatura-"  + name + "-3")) {
                                Schermo.aggiungiSpazzatura = 2;
                                System.out.println("Spazzatura aggiunta: " + Schermo.aggiungiSpazzatura);
                            }
                            if(playersData.equals("spazzatura-"  + name + "-4")) {
                                Schermo.aggiungiSpazzatura = 4;
                                System.out.println("Spazzatura aggiunta: " + Schermo.aggiungiSpazzatura);
                            }
                        } catch (IOException e) {

                            e.printStackTrace();

                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }else{
                //Se il messaggio non è nullo lo stampo
                Label lab_clientMsg = new Label(message).setBackgroundColor(BLACK).setForegroundColor(coloreLabel);
                panel.addComponent(lab_clientMsg);
            }
        }
        try {
            socket.close(); //Chiudi la connessione
            senderThread.interrupt();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}