package com.company;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.Panel;



import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.Map.Entry;
import java.util.Arrays;

import static com.googlecode.lanterna.TextColor.ANSI.BLACK;


public  class ClientHandler implements Runnable {
    private Socket clientSocket;
    private String username;
    private BufferedReader fromClient;
    private Panel panel;
    private TextColor coloreLabel;
    private HashMap<String, PrintWriter> connectedClients;

    // Constructor
    public ClientHandler(Socket socket, String userName,Panel panel,TextColor coloreLabel) {
        this.clientSocket = socket;
        this.username = userName;
        this.panel=panel;
        this.coloreLabel=coloreLabel;
    }

    public void clientHandler() {
        PrintWriter out = null;
        try {
            InputStream socketInput = clientSocket.getInputStream();
            InputStreamReader socketReader = new InputStreamReader(socketInput);
            fromClient = new BufferedReader(socketReader);

            out = new PrintWriter(clientSocket.getOutputStream(), true);

            username = fromClient.readLine();

            Label lab_newClient = new Label("New client connected: " + username + " ["
                    + clientSocket.getInetAddress().getHostAddress()
                    + "]").setBackgroundColor(BLACK).setForegroundColor(coloreLabel);
            panel.addComponent(lab_newClient);

            System.out.println("New client connected: " + username + " ["
                    + clientSocket.getInetAddress().getHostAddress()
                    + "]");

            broadcastServerMessage("New client connected: " + username + " ["
                    + clientSocket.getInetAddress().getHostAddress()
                    + "]");
            connectedClients.put(username, out);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        try {
            //LOGICA APPLICATIVA - RICEZIONE MESSAGGI
            String message = "";
            while (message != null) { //Finché il client non chiude la connessione o non ricevi un messaggio "quit"...
                message = fromClient.readLine(); //Leggi un messaggio inviato dal server (bloccante!)
                if (message != null) {
                    if (message.toLowerCase().equals("/quit")) break;

                    Label lab_clientMsg = new Label("[" + username + "]: " + message).setBackgroundColor(BLACK).setForegroundColor(coloreLabel);
                    panel.addComponent(lab_clientMsg);

                    System.out.println("[" + username + "]: " + message);
                    broadcastMessage(String.format("[%s]: %s", username, message), username);

                }
            }

            clientSocket.close(); //Interrompi la connessione

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (username != null) {
                if (true) System.out.println(username + " is leaving");
                connectedClients.remove(username);

                Label lab_clientLeft = new Label(username + " has left").setBackgroundColor(BLACK).setForegroundColor(coloreLabel);
                panel.addComponent(lab_clientLeft);
                Label lab_clientTot = new Label("Connected clients: " + connectedClients.size() + "/8").setBackgroundColor(BLACK).setForegroundColor(coloreLabel);
                panel.addComponent(lab_clientTot);

                System.out.println(username + " has left");
                System.out.println("Connected clients: " + connectedClients.size() + "/8");
                broadcastServerMessage(username + " has left");
                broadcastServerMessage("[SERVER]: Connected clients: " + connectedClients.size() + "/8");
            }
        }
    }
    public void broadcastServerMessage(String message) {

        for(Entry<String, PrintWriter> e : connectedClients.entrySet()) {

            e.getValue().println(message);
            e.getValue().flush();
        }
    }

    public  void broadcastMessage(String message, String username) {

        for(Entry<String, PrintWriter> e : connectedClients.entrySet()) {

            if(!e.getKey().equals(username)){

                e.getValue().println(message);
                e.getValue().flush();
            }
        }
    }
}
