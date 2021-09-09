package com.company.server;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.Panel;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map.Entry;

import static com.googlecode.lanterna.TextColor.ANSI.BLACK;

// Per ogni client che si connetterà al server creo un Thread handlerThread che si occupa
// di gestire singolarmente i client uno a uno
public class ClientHandler implements Runnable {
    public static Socket clientSocket;
    private String username;
    private BufferedReader fromClient;
    private Panel panel;
    private TextColor coloreLabel;
    public HashMap<String, PrintWriter> connectedClients;
    private Boolean  usernameCheck = true;

    public ClientHandler(Socket socket, String userName,Panel panel,TextColor coloreLabel, HashMap connectedClients) {
        this.clientSocket = socket;
        this.username = userName;
        this.panel=panel;
        this.coloreLabel=coloreLabel;
        this.connectedClients=connectedClients;
    }

    public void clientHandler() {
        PrintWriter out;
        try {

            InputStream socketInput = clientSocket.getInputStream();
            InputStreamReader socketReader = new InputStreamReader(socketInput);
            fromClient = new BufferedReader(socketReader);

            out = new PrintWriter(clientSocket.getOutputStream(), true);

            username = fromClient.readLine();

            for(Entry<String, PrintWriter> user : connectedClients.entrySet()){
                if(user.getKey().equals(username)){
                    System.out.println("entrato");
                    out.println("_terminate");
                    usernameCheck = false;
                    clientSocket.close();

                }
            }

            if(usernameCheck) {
                connectedClients.put(username, out);

                for (Entry<String, PrintWriter> e : connectedClients.entrySet()) {

                    if (e.getKey().equals(username)) {

                        e.getValue().println(ServerSender.name);
                        e.getValue().flush();
                    }
                }
                // Appena il seguente client si connette al server lo comunico a tutti nella
                // chat pre-partita
                Label lab_newClient = new Label("[SERVER]: " + "New client connected: " + username + " ["
                        + clientSocket.getInetAddress().getHostAddress()
                        + "]").setBackgroundColor(BLACK).setForegroundColor(coloreLabel);
                panel.addComponent(lab_newClient);

                broadcastServerMessage("[SERVER]: " + "New client connected: " + username + " ["
                        + clientSocket.getInetAddress().getHostAddress()
                        + "]");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public synchronized void run() {
        if (usernameCheck) {
            try {
                String message = "";
                while ((message != null || Server.serverThread.isAlive()) && !Server.gameStarted) {

                    // Quando un client invia un messaggio viene ricevuto dal server qui
                    message = fromClient.readLine();
                    if (message != null && !Server.gameStarted) {

                        // Se il messaggio ricevuto dal client è /quit il server esce dal ciclo e finisce nel "finally"
                        if (message.toLowerCase().equals("/quit")) break;

                        // Il server si occupa poi di trasmettere il messaggio agli altri client
                        Label lab_clientMsg = new Label("[" + username + "]: " + message).setBackgroundColor(BLACK).setForegroundColor(coloreLabel);
                        panel.addComponent(lab_clientMsg);

                        System.out.println("[" + username + "]: " + message);
                        broadcastMessage(String.format("[%s]: %s", username, message), username);
                        System.out.println(Server.gameStarted);
                    }

                }

                while (Server.gameStarted) {

                    message = fromClient.readLine();

                    if (message != null) {

                        if (connectedClients.size() == 1 && Server.gameStarted) {

                            Label lab_clientPerso = new Label("[SERVER]: " + username + " is the winner!").setBackgroundColor(BLACK).setForegroundColor(coloreLabel);
                            panel.addComponent(lab_clientPerso);
                            broadcastServerMessage(connectedClients.keySet() + "-winner");
                            Server.gameStarted = false;
                        }
                        //divido il messaggio, se la prima parola è spazzatura allora la mando al client interessato
                        if (message.contains("spazzatura")) {
                            String arr[] = message.split("-");
                            for (Entry<String, PrintWriter> e : connectedClients.entrySet()) {
                                if (arr[1].equals(connectedClients.get(username))) {
                                    e.getValue().println(message);
                                    e.getValue().flush();
                                    break; //essendoci un solo client con quel nome una volta trovato forzo l'uscita
                                }
                            }
                        }
                        synchronized (this) {
                            for (String i : connectedClients.keySet()) {

                                if (message.equals(i + "-lost")) {

                                    Label lab_clientPerso = new Label("[SERVER]: " + i + " lost!").setBackgroundColor(BLACK).setForegroundColor(coloreLabel);
                                    panel.addComponent(lab_clientPerso);

                                    if ((connectedClients.size() - 1) > 1) {

                                        Label lab_clientPerso2 = new Label("[SERVER]: " + (connectedClients.size() - 1) + " players left!").setBackgroundColor(BLACK).setForegroundColor(coloreLabel);
                                        panel.addComponent(lab_clientPerso2);

                                    } else {

                                        Label lab_clientPerso3 = new Label("[SERVER]: " + (connectedClients.size() - 1) + " player left!").setBackgroundColor(BLACK).setForegroundColor(coloreLabel);
                                        panel.addComponent(lab_clientPerso3);

                                    }
                                    connectedClients.remove(i);
                                }
                            }
                        }
                        broadcastMessage(String.format("%s", message), username);
                    }
                }

                System.out.println(username + " si è spento!");
                clientSocket.close(); //Interrompi la connessione

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (username != null) {
                    // Quando un giocatore invia il comando /quit si disconnette dal server
                    // Viene quindi mandato un messaggio di aggiornamento a tutti i client
                    if (true) System.out.println("[SERVER]: " + username + " is leaving");
                    connectedClients.remove(username);

                    Label lab_clientLeft = new Label("[SERVER]: " + username + " has left").setBackgroundColor(BLACK).setForegroundColor(coloreLabel);
                    panel.addComponent(lab_clientLeft);
                    Label lab_clientTot = new Label("[SERVER]: " + "Connected clients: " + connectedClients.size() + "/4").setBackgroundColor(BLACK).setForegroundColor(coloreLabel);
                    panel.addComponent(lab_clientTot);

                    broadcastServerMessage("[SERVER]: " + username + " has left");
                    broadcastServerMessage("[SERVER]: Connected clients: " + connectedClients.size() + "/4");
                }
            }
        }
    }


    public void broadcastServerMessage(String message) {

        for(Entry<String, PrintWriter> e : connectedClients.entrySet()) {

            e.getValue().println(message);
            e.getValue().flush();
        }
    }

    // Questo metodo serve a trasmettere il messaggio di un client a tutti gli altri tranne a se stesso
    public  void broadcastMessage(String message, String username) {

        for(Entry<String, PrintWriter> e : connectedClients.entrySet()) {

            if(!e.getKey().equals(username)){

                e.getValue().println(message);
                e.getValue().flush();
            }
        }
    }
}
