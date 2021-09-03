package com.company.server;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.Panel;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import static com.googlecode.lanterna.TextColor.ANSI.BLACK;

// Questa classe si occupa di rimanere in attesa di nuove connessioni da parte dei client
public class ConnectionListener implements Runnable {
    private HashMap<String, PrintWriter> connectedClients;
    private Panel panel;
    private TextColor coloreLabel;
    public static Thread handlerThread;
    private int SERVERPORT;
    private String players = "";

    public ConnectionListener(Panel panel, int SERVERPORT, TextColor coloreLabel, HashMap connectedClients) {
        this.panel=panel;
        this.SERVERPORT = SERVERPORT;
        this.coloreLabel=coloreLabel;
        this.connectedClients=connectedClients;
    }

    @Override
    public void run() {
        try{
            ServerSocket listener = null;
            listener = new ServerSocket(SERVERPORT);
            listener.setReuseAddress(true);

            while(connectedClients.size() <= 4){
                System.out.println(connectedClients.size());
                Socket socket = listener.accept();
                // Per ogni client che si connette al server creo un thread handlerThread,
                // a lato server, per ogni client così che possano essere gestiti singolarmente dal server
                ClientHandler clientSock = new ClientHandler(socket, "",panel,coloreLabel,connectedClients);
                clientSock.clientHandler();
                handlerThread = new Thread(clientSock);
                handlerThread.start();

                Label lab_clientJoin=new Label("[SERVER]: Connected clients: " + (connectedClients.size())+ "/4").setBackgroundColor(BLACK).setForegroundColor(coloreLabel);
                panel.addComponent(lab_clientJoin);

                // Aggiorno i vari client che un nuovo giocatore si è connesso al server
                broadcastServerMessage("[SERVER]: Connected clients: " + (connectedClients.size()) + "/4");
                if(connectedClients.size() == 4){
                    Server.gameStarted = true;
                    broadcastServerMessage("/start");
                    for (Map.Entry<String, PrintWriter> pair : connectedClients.entrySet()) {
                        players = players + pair.getKey() + "-";
                    }
                    broadcastServerMessage(players);
                    Label lab_serverMsg = new Label("[SERVER]: The game has started!").setBackgroundColor(BLACK).setForegroundColor(coloreLabel);
                    panel.addComponent(lab_serverMsg);
                    while(Server.gameStarted){

                    }
                    break;
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void broadcastServerMessage(String message) {

        for(Map.Entry<String, PrintWriter> e : connectedClients.entrySet()) {

            e.getValue().println(message);
            e.getValue().flush();
        }
    }
}
