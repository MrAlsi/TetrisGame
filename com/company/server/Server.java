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
import java.util.Map.Entry;
import java.util.concurrent.Semaphore;

import static com.googlecode.lanterna.TextColor.ANSI.BLACK;



public class Server  implements Runnable{
    //private HashMap<String, PrintWriter> connectedClients = new HashMap();
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
    public static Semaphore semaforoConnectedClients=new Semaphore(1);


    // Appena creo il sevrer gli passso i seguenti parametri e avvio il thread listenerThread
    // che si occupa di restare in ascolto per le connessioni dei client che vorranno collegarsi al server
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

    // Metodo che richiamo subito e serve per far partire il thread dedicato al server
    public void StartServer(Server server) {
            serverThread = new Thread(server);
            serverThread.start();
            System.out.println("Avvio del Server... ");
    }

    // Inizializzo la schermata del server
    public void run(){
        panel.removeAllComponents();
        panel.setFillColorOverride(BLACK);
        panel.setPosition(new TerminalPosition(0,0));
        panel.setPreferredSize(new TerminalSize(100,10));
        Label lab=new Label("\nStarting "+ name + "... ").setBackgroundColor(BLACK).setForegroundColor(
                coloreLabel);
        panel.addComponent(lab);

        gameStarted = false;

        try{
            URL whatismyip = new URL("http://checkip.amazonaws.com");
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    whatismyip.openStream()));

            String ip = in.readLine(); //you get the IP as a String

            Label myIp = new Label("\nShare your ip address: " + ip).setBackgroundColor(BLACK).setForegroundColor(
                    coloreLabel);
            panel.addComponent(myIp);
        }catch(Exception e){
            System.out.println(e);
        }

        try {

            // Il server si mette in ascolto per eventuali client che tentano di connettersi
            listenerThread.start();
            System.out.println("Server in ascolto... ");
            // Creo il thread di comunicazione del server e lo avvio
            // Questo thread permette al server di mandare messaggi a tutti i client
            // durante il pre-partita
            ServerSender serverSender = new ServerSender(panel, coloreLabel, name);
            senderThread = new Thread(serverSender);
            senderThread.start();

            Label lab_serverOn=new Label("\n- - - Server on - - -").setBackgroundColor(BLACK).setForegroundColor(coloreLabel);
            panel.addComponent(lab_serverOn);
            while(true) {
                while (!gameStarted) {

                }
                while (gameStarted) {

                }
            }


        } catch (Exception e) {

            e.printStackTrace();
        }

    }

    // Metodo per la trasmissione di un messaggio inviato dal server agli altri client
    public static void broadcastServerMessage(String message) {

        for(Entry <String, PrintWriter> e: connectedClients.entrySet()) {

            e.getValue().println(message);
            e.getValue().flush();
        }
    }


}
