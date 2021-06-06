package com.company;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;

import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.Panel;

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

    //costruttore
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

        panel.removeAllComponents();
        panel.setFillColorOverride(BLACK);
        panel.setPosition(new TerminalPosition(0, 0));
        panel.setPreferredSize(new TerminalSize(100, 10));
        panel.removeAllComponents();
        panel.setFillColorOverride(BLACK);
        Label lab = new Label("\nti sei registrato con il nome: " + name + "... ").setBackgroundColor(BLACK)
                .setForegroundColor(coloreLabel);
        Label connessione = new Label("\nConnessione al server in corso...").setBackgroundColor(BLACK)
                .setForegroundColor(coloreLabel);
        panel.addComponent(lab);
        panel.addComponent(connessione);
        Socket socket = null; //Creazione socket, connessione a localhost:1555
        try {
            socket = new Socket(IP, 6789);
            Label connesso = new Label("\n- - - - Connesso alla chat - - - -\n").setBackgroundColor(BLACK)
                    .setForegroundColor(coloreLabel);
            panel.addComponent(connesso);
        } catch (IOException ex) {
            ex.printStackTrace();
            Label nonconnesso = new Label("\n- - - - non siamo riusciti a connetterti al server - - - -\nriprova").setBackgroundColor(BLACK)
                    .setForegroundColor(coloreLabel);
            panel.addComponent(nonconnesso);
            panel.removeAllComponents();
            panel.setFillColorOverride(BLACK);

            MainSchermata.Schermata(panel);

        }


        //System.out.println("\n- - - - Connesso alla chat - - - -\n");
        //Otteniamo gli stream
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
        Sender clientSender = new Sender(toServer,panel,coloreLabel);
        Thread senderThread = new Thread(clientSender);

        String message = name;
        toServer.println(message);
        toServer.flush();
        senderThread.start();

        //LOGICA APPLICATIVA - RICEZIONE MESSAGGI

        while (message != null && !message.equals("quit")) { //Finché il server non chiude la connessione o non ricevi un messaggio "quit"...
            try {
                message = fromServer.readLine(); //Leggi un messaggio inviato dal server (bloccante!)
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            if (message != null) {
                System.out.println(message);
            }
        }
        Label uscita = new Label("\\nUscita dal server in corso...").setBackgroundColor(BLACK)
                .setForegroundColor(coloreLabel);
        panel.addComponent(uscita);
        //ystem.out.println("\nUscita dal server in corso...");
        senderThread.interrupt(); //Chiedi al senderThread di fermarsi
        try {
            socket.close(); //Chiudi la connessione
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        Label successo = new Label("\nUscita eseguita con successo").setBackgroundColor(BLACK)
                .setForegroundColor(coloreLabel);
        panel.addComponent(successo);
        //System.out.println("\nUscita eseguita con successo");
        panel.removeAllComponents();

    }


    /*public void run() {
        {
            synchronized (this) {
                this.runningThread = Thread.currentThread();
            }
            //SETUP

        /*
            Boolean usernameCheck = false;
            String username = "";
            int command = 0;
            while(usernameCheck == false){
                Scanner sc = new Scanner(System.in);
                System.out.print("\nInserisci username: ");
                username = sc.nextLine();
                System.out.println( "\n" + username + " e' stato impostato come username");
                System.out.println( "\nCosa vuoi fare? \n1 - Accedere al server \n2 - Modificare l'username");
                System.out.print( "1/2: ");
                command = sc.nextInt();
                if(command == 1){
                    usernameCheck = true;
                }
            }
*/
            //System.out.println("\nConnessione al server in corso...");

        }
