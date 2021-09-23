package com.company.server;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.Panel;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Map.Entry;

import static com.googlecode.lanterna.TextColor.ANSI.BLACK;

/**
 * Per ogni client che si connetterà al server creo un Thread handlerThread che si occupa
 * di gestire singolarmente i client uno a uno.
 */
public class ClientHandler implements Runnable {
    public static Socket clientSocket;
    private String username;
    private BufferedReader fromClient;
    private Panel panel;
    private TextColor coloreLabel;
    private Boolean  usernameCheck = true;
    private int check = 0;

    public ClientHandler(Socket socket, String userName,Panel panel,TextColor coloreLabel) {
        this.clientSocket = socket;
        this.username = userName;
        this.panel=panel;
        this.coloreLabel=coloreLabel;
    }

    public synchronized void clientHandler() {

        // Come prima cosa inizializzo PrintWriter e BufferReader
        PrintWriter out;
        try {
            InputStream socketInput = clientSocket.getInputStream();
            InputStreamReader socketReader = new InputStreamReader(socketInput);
            fromClient = new BufferedReader(socketReader);

            out = new PrintWriter(clientSocket.getOutputStream(), true);

            username = fromClient.readLine();

            // Funzione di controllo sull'username.
            // Verifica che non sia già connesso un client con lo stesso nome.
            for(Entry<String, PrintWriter> user : Server.connectedClients.entrySet()){
                if(user.getKey().equals(username)){

                    // Se trovo un duplicato invio al client il messaggio "_terminate"
                    // e chiudo la connessione.
                    out.println("_terminate");
                    usernameCheck = false;
                    clientSocket.close();
                }
            }

            // Se il nickname non è duplicato...
            if(usernameCheck) {

                // Aggiungo il giocatore alla lista di client connessi.
                Server.connectedClients.put(username, out);

                // Appena il seguente client si connette al server lo comunico a tutti nella
                // chat pre-partita.
                for (Entry<String, PrintWriter> e : Server.connectedClients.entrySet()) {

                    if (e.getKey().equals(username)) {

                        e.getValue().println(ServerSender.name);
                        e.getValue().flush();
                    }
                }
                System.out.println("Chat pre partita -> [SERVER]: New client connected:  " + username );
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

        // Se il nickname non è duplicato...
        if (usernameCheck) {
            try {
                String message = "";
                while ((Server.serverThread.isAlive()) && !Server.gameStarted) {

                    // Leggo i messaggi provenienti dal client.
                    if (((message = fromClient.readLine()) != null)){
                        // Quando un client invia un messaggio viene ricevuto dal server qui.
                        try {

                            // Se il messaggio ricevuto dal client è /quit il server esce dal ciclo e finisce nel "finally".
                            if (message.equals("/quit")) break;

                            // Il server si occupa poi di trasmettere il messaggio agli altri client.
                            System.out.println("[" + username + "]: " + message );
                            Label lab_clientMsg = new Label("[" + username + "]: " + message).setBackgroundColor(BLACK).setForegroundColor(coloreLabel);
                            panel.addComponent(lab_clientMsg);
                            if(!Server.gameStarted){
                                System.out.println("[" + username + "]: " + message);
                                broadcastMessage(String.format("[%s]: %s", username, message), username);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                // Una volta iniziata la partita...
                while (Server.gameStarted) {

                    // Continuo a leggere i dati ricevuti dal client.
                    message = fromClient.readLine();

                    if (message != null) {

                        // Se è rimasto solo questo client in partita significa che è il vincitore.
                        if (Server.giocatori.size() == 1 && Server.gameStarted) {

                            System.out.println(username + " è il vincitore!");
                            Label lab_clientVinto = new Label("[SERVER]: " + username + " is the winner!").setBackgroundColor(BLACK).setForegroundColor(coloreLabel);
                            panel.addComponent(lab_clientVinto);

                            // Lo comunico al client e termino la partita.
                            broadcastServerMessage("[" + username + "]" + "-winner");
                            Server.gameStarted = false;
                        }
                        synchronized (this) {
                            try {
                                Server.semaforoConnectedClients.acquire();
                                for (String i : Server.giocatori) {

                                    if (message.equals(i + "-lost")) {

                                        // Se ricevo dal client il messaggio di sconfitta
                                        // lo trasmetto a tutti gli altri client e mi disconnetto.
                                        broadcastMessage(message,username);
                                        System.out.println(i + " ha perso");
                                        Label lab_clientPerso = new Label("[SERVER]: " + i + " lost!").setBackgroundColor(BLACK).setForegroundColor(coloreLabel);
                                        panel.addComponent(lab_clientPerso);
                                        System.out.println((Server.giocatori.size() - 1)+ "  giocatori rimasti");
                                        Server.giocatori.remove(i);
                                        Server.connectedClients.remove(i);
                                        Thread.currentThread().interrupt();
                                    }
                                }

                                // In tutti gli altri casi trasmetto il messaggio/coordinate a tutti
                                // gli altri client.
                                broadcastMessage(String.format("%s", message), username);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } finally {
                                Server.semaforoConnectedClients.release();
                            }
                        }
                    }
                }

                // Terminata la partita interrompo la connessione col client.
                System.out.println(username + " si è spento!");
                clientSocket.close();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {

                // Quando un giocatore invia il comando /quit o si disconnette dal server
                // Viene quindi mandato un messaggio di aggiornamento a tutti i client
                System.out.println("[SERVER]: " + username + " is leaving");
                Server.connectedClients.remove(username);
                Server.giocatori.remove(username);
                System.out.println(("[SERVER]: " + username + "  si è disconnesso"));
                Label lab_clientLeft = new Label("[SERVER]: " + username + " has left").setBackgroundColor(BLACK)
                        .setForegroundColor(coloreLabel);
                panel.addComponent(lab_clientLeft);

                System.out.println("[SERVER]: " + "Connected clients: " + Server.connectedClients.size() + "/4");
                Label lab_clientTot = new Label("[SERVER]: " + "Connected clients: " + Server.connectedClients
                        .size() + "/4").setBackgroundColor(BLACK).setForegroundColor(coloreLabel);
                panel.addComponent(lab_clientTot);

                broadcastServerMessage("[SERVER]: " + username + " has left");
                broadcastServerMessage("[SERVER]: Connected clients: " + Server.connectedClients.size() + "/4");
            }
        }
    }


    /**
     * Metodo che permette al server di inviare un proprio messaggio
     * a tutti i client.
     */
    public void broadcastServerMessage(String message) {

        for(Entry<String, PrintWriter> e : Server.connectedClients.entrySet()) {

            e.getValue().println(message);
            e.getValue().flush();
        }
    }


    /**
     * Metodo per trasmettere il messaggio di un client
     * a tutti gli altri tranne che a se stesso.
     */
    public  void broadcastMessage(String message, String username) {

        for(Entry<String, PrintWriter> e : Server.connectedClients.entrySet()) {

            if(!e.getKey().equals(username)){

                e.getValue().println(message);
                e.getValue().flush();
            }
        }
    }


}
