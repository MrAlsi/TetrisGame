package com.company.server;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.Panel;

import java.awt.*;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;

import static com.googlecode.lanterna.TextColor.ANSI.BLACK;


/**
 * Questa classe si occupa di rimanere in attesa di nuove connessioni da parte dei client.
 * Possono connettersi fino a 4 client contemporaneamente.
 * La connessione al server può avvenire sia durante il pre-partita che durante una partita in corso.
 */
public class ConnectionListener implements Runnable {
    private Panel panel;
    private TextColor coloreLabel;
    public static Thread handlerThread;
    private int SERVERPORT;
    public static String players = "";
    private int dimension;

    public  ConnectionListener(Panel panel, int SERVERPORT, TextColor coloreLabel) {
        this.panel=panel;
        this.SERVERPORT = SERVERPORT;
        this.coloreLabel=coloreLabel;
    }

    @Override
    public synchronized void run() {
        try{
            ServerSocket listener = null;
            listener = new ServerSocket(SERVERPORT);
            listener.setReuseAddress(true);

            while(true) {

                Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
                dimension = size.height * size.width;
                while (Server.connectedClients.size() < 4) {
                    System.out.println("Client connessi" + Server.connectedClients.size() + "/4");
                    Socket socket = listener.accept();

                    // Per ogni client che si connette al server creo un thread handlerThread
                    // a lato server, per ogni client così che possano essere gestiti singolarmente.
                    ClientHandler clientSock = new ClientHandler(socket, "", panel, coloreLabel);
                    clientSock.clientHandler();
                    handlerThread = new Thread(clientSock);
                    handlerThread.start();

                    Label lab_clientJoin = new Label("[SERVER]: Connected clients: " + (Server.connectedClients.size()) + "/4").setBackgroundColor(BLACK).setForegroundColor(coloreLabel);
                    panel.addComponent(lab_clientJoin);

                    // Aggiorno i vari client che un nuovo giocatore si è connesso al server
                    System.out.println("Aggiornamento Client quando un nuovo giocatore si connette al Server: " + "Client connessi" + Server.connectedClients.size() + "/4");
                    broadcastServerMessage("[SERVER]: Connected clients: " + (Server.connectedClients.size()) + "/4");

                    // Una volta connesso il quarto giocatore comunico
                    // ai client di inizializzare la partita.
                    if (Server.connectedClients.size() == 4 && !Server.gameStarted) {
                        Server.gameStarted = true;

                        // Un client inizializza una partita quando riceve il comando
                        // "/start" dal server.
                        broadcastServerMessage("/start");

                        // Resetto le variabili utilizzate per gestire i giocatori
                        // che parteciperanno alla partita.
                        players = "";
                        if(Server.giocatori != null) {
                            Server.giocatori.clear();
                        }
                        for (Map.Entry<String, PrintWriter> pair : Server.connectedClients.entrySet()) {
                            players = players + pair.getKey() + "-";
                            Server.giocatori.add(pair.getKey());
                        }

                        // Invio ai client una stringa contenente tutti i nomi dei giocatori
                        broadcastServerMessage(players);
                        System.out.println("Il gioco è iniziato!");
                        Label lab_serverMsg = new Label("[SERVER]: The game has started!").setBackgroundColor(BLACK).setForegroundColor(coloreLabel);
                        panel.addComponent(lab_serverMsg);
                    }
                }
            }
        } catch(Exception e) {
            System.out.println("Errore durante l'inizializzazione del listenerThread.");
        }
    }

    /**
     * Metodo che permette al server di inviare un proprio messaggio
     * a tutti i client.
     */
    public void broadcastServerMessage(String message) {

        for(Map.Entry<String, PrintWriter> e : Server.connectedClients.entrySet()) {

            e.getValue().println(message);
            e.getValue().flush();
        }
    }
}
