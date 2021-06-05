package com.company;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;

import com.googlecode.lanterna.*;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.GridLayout;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import javax.swing.*;
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


public class Server  implements Runnable{
    private HashMap<String, PrintWriter> connectedClients = new HashMap();
    private Server.ConnectionListener connectionListener = new Server.ConnectionListener();
    private Thread listenerThread = new Thread(connectionListener);
    protected Thread runningThread= null;
    private Boolean gameStart = false;
    public String name;
    public TextColor coloreLabel;
    public Panel panel;

    /*
    private ServerSocket listener = null;
    private PrintWriter out = null;
    int port = 6789;*/


    public Server(String name, Panel panel, TextColor coloreLabel) {
        this.name = name;
        this.panel= panel;
        this.coloreLabel= coloreLabel;

    }

    public void StartServer(Server server) {

        new Thread(server).start();
    }

    public void run(){
        synchronized(this){
            this.runningThread = Thread.currentThread();
        }
        panel.removeAllComponents();
        panel.setFillColorOverride(BLACK);
        panel.setPosition(new TerminalPosition(0,0));
        panel.setPreferredSize(new TerminalSize(100,10));
        Label lab=new Label("\nStarting "+ name + "... ").setBackgroundColor(BLACK).setForegroundColor(
                coloreLabel);

        panel.removeAllComponents();
        panel.setFillColorOverride(BLACK);
        panel.addComponent(lab);

        // System.out.println("\nStarting the server...");

        try {

            listenerThread.start();
            // creo il thread di comunicazione del server
            // e lo avvio
            Server.ServerSender serverSender = new Server.ServerSender();
            Thread senderThread = new Thread(serverSender);
            senderThread.start();

            // ciclo per far connettere più client al server
            Label lab_serveron=new Label("\n- - - Server on - - -").setBackgroundColor(BLACK).setForegroundColor(coloreLabel);
            panel.addComponent(lab_serveron);



            //System.out.println("\n- - - Server on - - -");
            while(true){
                // commenta easterEgg per distrugere il server
                String easterEgg = new String();
                if(gameStart == true || connectedClients.size() > 7){
                    gameStart = true;
                    System.out.println("\n\n- - - THE GAME IS STARTING - - -" );
                    broadcastServerMessage("\n\n- - - THE GAME IS STARTING - - -");

                    System.out.println("\n\n- - - GAME STARTED - - -\n|  Online players: " + connectedClients.size() + "  |");
                    broadcastServerMessage("\n\n- - - GAME STARTED - - -\n|  Online players: " + connectedClients.size() + "  |");
                    while(gameStart){

                    }
                }
            }
        } catch (Exception e) {

            e.printStackTrace();
        }

    }

    // classe per la gestione dei thread dei client
    private class ClientHandler implements Runnable {
        private Socket clientSocket;
        public String username;
        private BufferedReader fromClient;

        // Constructor
        public ClientHandler(Socket socket, String userName)
        {
            PrintWriter out = null;
            try{
                this.clientSocket = socket;
                this.username = userName;
                InputStream socketInput = clientSocket.getInputStream();
                InputStreamReader socketReader = new InputStreamReader(socketInput);
                fromClient = new BufferedReader(socketReader);

                out = new PrintWriter(clientSocket.getOutputStream(), true);

                username = fromClient.readLine();

                Label lab_newClient=new Label( "New client connected: " + username + " ["
                        + clientSocket.getInetAddress().getHostAddress()
                        + "]").setBackgroundColor(BLACK).setForegroundColor(coloreLabel);
                panel.addComponent(lab_newClient);

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
        @Override
        public void run()
        {
            try {
                //LOGICA APPLICATIVA - RICEZIONE MESSAGGI
                String message = "";
                while(message != null) { //Finché il client non chiude la connessione o non ricevi un messaggio "quit"...
                    message = fromClient.readLine(); //Leggi un messaggio inviato dal server (bloccante!)
                    if (message != null) {
                        if (message.toLowerCase().equals("/quit")) break;

                        Label lab_clientMsg=new Label("[" + username + "]: " + message).setBackgroundColor(BLACK).setForegroundColor(coloreLabel);
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

                    Label lab_clientLeft=new Label(username + " has left").setBackgroundColor(BLACK).setForegroundColor(coloreLabel);
                    panel.addComponent(lab_clientLeft);
                    Label lab_clientTot=new Label("Connected clients: " + connectedClients.size() + "/8").setBackgroundColor(BLACK).setForegroundColor(coloreLabel);
                    panel.addComponent(lab_clientTot);

                    System.out.println(username + " has left");
                    System.out.println("Connected clients: " + connectedClients.size() + "/8");
                    broadcastServerMessage(username + " has left");
                    broadcastServerMessage("[SERVER]: Connected clients: " + connectedClients.size() + "/8");
                }
            }
        }
    }

    public class ConnectionListener implements Runnable {

        public ConnectionListener() {

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
                    Server.ClientHandler clientSock = new Server.ClientHandler(socket, "");
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
    }


    // classe per la trasmissione di un messaggio inviato dal server agli altri client
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
    /*
    private Screen screen;
    private TextBox textBox;
    private final String emptyString = "";

    public String getText() throws IOException {
        String result = null;
        KeyStroke key = null;
        while ((key = screen.readInput()).getKeyType() != KeyType.Enter) {
            textBox.handleKeyStroke(key);
            // use only one of handleInput() or handleKeyStroke()
            textBox.setText(textBox.getText());
        }
        result = textBox.getText();
        textBox.setText(emptyString);
        return result;
    }
    */

    // classe per la gestione del thread del server
    public  class ServerSender implements Runnable {

        public ServerSender() {

        }
        @Override
        public void run() {
            TextBox textServer = new TextBox();
            panel.addComponent(textServer);

            Scanner serverInput = new Scanner(System.in);
            String serverMessage = "";
            String messageServer = "";
            while(true) { //Finché non ricevi un comando "quit" dall'utente...
                
                messageServer = textServer.getText();
                serverMessage = serverInput.nextLine(); //... leggi un messaggio da console (bloccante!)...
                if(serverMessage.equals("/start")){
                    listenerThread.interrupt();
                    gameStart = true;
                }
                else if(serverMessage != null && !serverMessage.toLowerCase().equals("/start")){
                    broadcastServerMessage("[SERVER]: " + messageServer);
                    broadcastServerMessage("[SERVER]: " + serverMessage);
                }
            }
        }
    }
}
