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

public class ConnectionListener implements Runnable {
    private HashMap<String, PrintWriter> connectedClients;
    private Panel panel;
    private TextColor coloreLabel;

    public ConnectionListener(Panel panel,TextColor coloreLabel, HashMap connectedClients) {
        this.panel=panel;
        this.coloreLabel=coloreLabel;
        this.connectedClients=connectedClients;
    }
    @Override
    public void run() {
        try{
            ServerSocket listener = null;
            int port = 6789;
            listener = new ServerSocket(port);
            listener.setReuseAddress(true);
            while(true){
                Socket socket = listener.accept();
                // creo un thread per ogni client così
                // da essere gestiti singolarmente
                ClientHandler clientSock = new ClientHandler(socket, "",panel,coloreLabel,connectedClients);
                clientSock.clientHandler();
                new Thread(clientSock).start();


                Label lab_clientJoin=new Label("Connected clients: " + connectedClients.size() + "/8").setBackgroundColor(BLACK).setForegroundColor(coloreLabel);
                panel.addComponent(lab_clientJoin);

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
