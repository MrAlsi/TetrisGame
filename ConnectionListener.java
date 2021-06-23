package com.company;

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

    public ConnectionListener(Panel panel,TextColor coloreLabel, HashMap connectedClients) {
        this.panel=panel;
        this.coloreLabel=coloreLabel;
        this.connectedClients = connectedClients;
    }
    @Override
    public void run() {
        try{

            ServerSocket listener;
            int port = 6789;
            listener = new ServerSocket(port);
            listener.setReuseAddress(true);
            
            while(Server.serverThread.isAlive()){

                Socket socket = listener.accept();

                // Per ogni client che si connette al server creo un thread handlerThread,  
                // a lato server, per ogni client così che possano essere gestiti singolarmente dal server
                ClientHandler clientSock = new ClientHandler(socket, "",panel,coloreLabel,connectedClients);
                clientSock.clientHandler();
                handlerThread = new Thread(clientSock);
                handlerThread.start();

                Label lab_clientJoin=new Label("Connected clients: " + connectedClients.size() + "/8").setBackgroundColor(BLACK).setForegroundColor(coloreLabel);
                panel.addComponent(lab_clientJoin);

                // Aggiorno i vari client che un nuovo giocatore si è connesso al server
                System.out.println("Connected clients: " + connectedClients.size() + "/8");
                broadcastServerMessage("[SERVER]: Connected clients: " + connectedClients.size() + "/8");
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
