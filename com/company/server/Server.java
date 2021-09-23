package com.company.server;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.Panel;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.concurrent.Semaphore;

import static com.googlecode.lanterna.TextColor.ANSI.BLACK;

public class Server  implements Runnable{
    private ConnectionListener connectionListener;
    public String name;
    private int SERVERPORT;
    public TextColor coloreLabel;
    public Panel panel;
    public static Thread serverThread;
    public static Thread senderThread;
    public static Thread listenerThread;
    public static Boolean gameStarted = false;
    public static HashMap<String, PrintWriter> connectedClients;
    public static LinkedList<String> giocatori;
    public static Semaphore semaforoConnectedClients=new Semaphore(1);
    public static String ip="";

    /**
     * Classe principale del server,
     * si occupa di inizializzare tutti i componenti
     * le variabili pubbliche e i Thread necessari
     * al funzionamento.
     */
    public Server(String name, String SERVERPORT, Panel panel, TextColor coloreLabel) {
        this.name = name;
        this.SERVERPORT = Integer.parseInt(SERVERPORT);
        this.panel= panel;
        this.coloreLabel= coloreLabel;
        this.connectedClients= new HashMap<String, PrintWriter>();
        connectionListener = new ConnectionListener(panel, this.SERVERPORT, coloreLabel);
        listenerThread = new Thread(connectionListener);
        System.out.println("Creazione del Server...");

    }

    /**
     * Metodo che richiamo subito e che serve a
     * far inizializzare il thread dedicato al server
     */
    public void StartServer(Server server) {
        serverThread = new Thread(server);
        serverThread.start();
        System.out.println("Avvio del Server... ");

    }

    // Inizializzo la schermata del server.
    public void run(){
        giocatori = new LinkedList<String>();
        panel.removeAllComponents();
        panel.setFillColorOverride(BLACK);
        panel.setPosition(new TerminalPosition(0,0));
        panel.setPreferredSize(new TerminalSize(100,10));
        Label lab=new Label("\nStarting "+ name + "... ").setBackgroundColor(BLACK).setForegroundColor(
                coloreLabel);
        panel.addComponent(lab);

        gameStarted = false;

        // Funzione che restituisce e stampa l'indirizzo
        // IP pubblico del server.
        try{
            URL whatismyip = new URL("http://checkip.amazonaws.com");
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    whatismyip.openStream()));
            ip = in.readLine();

            Label myIp = new Label("\nShare your ip address: " + ip).setBackgroundColor(BLACK).setForegroundColor(
                    coloreLabel);
            panel.addComponent(myIp);
        }catch(Exception e){
            System.out.println("Errore nell'acquisizione dell'ip.");
        }

        try {

            // Faccio partire il Thread dedicato all'ascolto di connessioni
            // in entrata da parte dei client.
            listenerThread.start();
            System.out.println("Server in ascolto... ");

            // Creo il thread di comunicazione del server e lo avvio.
            // Questo thread permette al server di mandare messaggi a tutti i client.
            ServerSender serverSender = new ServerSender(panel, coloreLabel, name);
            senderThread = new Thread(serverSender);
            senderThread.start();

            Label lab_serverOn=new Label("\n- - - Server on - - -").setBackgroundColor(BLACK).setForegroundColor(coloreLabel);
            panel.addComponent(lab_serverOn);
            while(true) {

            }
        } catch (Exception e) {
            System.out.println("Errore durante l'inizializzazione dei Thread.");
        }
    }
}
