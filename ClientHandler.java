package com.company;

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


public  class ClientHandler implements Runnable{
    private Socket clientSocket;
    public String username;
    private BufferedReader fromClient;
    HashMap<String ,PrintWriter> connectedClients;
    // Constructor
    public ClientHandler(Socket socket, String userName )
    {
        this.clientSocket = socket;
        this.username = userName;
    }
    private void ClientHandler(){
        PrintWriter out = null;
        try{


            InputStream socketInput = clientSocket.getInputStream();
            InputStreamReader socketReader = new InputStreamReader(socketInput);
            fromClient = new BufferedReader(socketReader);

            out = new PrintWriter(clientSocket.getOutputStream(), true);

            username = fromClient.readLine();
            System.out.println("New client connected: " + username + " ["
                    + clientSocket.getInetAddress().getHostAddress()
                    + "]" );
            broadcastServerMessage("New client connected: " + username + " ["
                    + clientSocket.getInetAddress().getHostAddress()
                    + "]" );
            connectedClients.put(username, out);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void run()
    {
        try {
            //LOGICA APPLICATIVA - RICEZIONE MESSAGGI
            String message = "";
            while(message != null) { //Finché il client non chiude la connessione o non ricevi un messaggio "quit"...
                message = fromClient.readLine(); //Leggi un messaggio inviato dal server (bloccante!)
                if (message != null) {
                    if (message.toLowerCase().equals("/quit")) break;
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
                System.out.println(username + " has left");
                System.out.println("Connected clients: " + connectedClients.size() + "/8");
                broadcastServerMessage(username + " has left");
                broadcastServerMessage("[SERVER]: Connected clients: " + connectedClients.size() + "/8");
            }
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


    // classe per la trasmissione di un messaggio inviato dal server agli altri client
    public void broadcastServerMessage(String message) {

        for(Entry<String, PrintWriter> e : connectedClients.entrySet()) {

            e.getValue().println(message);
            e.getValue().flush();
        }
    }
}
