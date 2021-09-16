package com.company.client;

import com.company.Gioco.*;
import com.company.MainSchermata;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.Panel;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.Arrays;
import java.util.List;

import static com.googlecode.lanterna.TextColor.ANSI.BLACK;

public class Client implements Runnable {
    private String name;
    private String IP;
    private int PORT;
    private Panel panel;
    private TextColor coloreLabel;
    public static Socket socket;
    private String serverName;
    private String playersData;
    public static Boolean winner = false;
    public static Boolean restart = false;
    public static Boolean winCheck = false;
    public static Boolean loseCheck = false;
    public static Thread gameThread;
    private String message = "";
    private Boolean pause = false;
    private List<String> connectedClients;
    private Boolean terminate = false;


    // Reperisco dal form di "find game" i vari dati che mi interessano
    public Client(String name, String IP, String PORT, Panel panel, TextColor coloreLabel, List<String> connectedClients) {
        this.name = name;
        this.IP = IP;
        this.PORT = Integer.parseInt(PORT);
        this.panel = panel;
        this.coloreLabel = coloreLabel;
        this.connectedClients=connectedClients;
    }

    public void run() {

        // Inizializzo la chat pre-partita del client
        panel.removeAllComponents();
        panel.setFillColorOverride(BLACK);
        panel.setPosition(new TerminalPosition(0, 0));
        panel.setPreferredSize(new TerminalSize(100, 10));
        panel.removeAllComponents();
        panel.setFillColorOverride(BLACK);
        Label lab = new Label("\nYour nickname: " + name).setBackgroundColor(BLACK)
                .setForegroundColor(coloreLabel);
        Label connessione = new Label("\nConnecting to the server...").setBackgroundColor(BLACK)
                .setForegroundColor(coloreLabel);
        panel.addComponent(lab);
        panel.addComponent(connessione);
        socket = null; //Creazione socket, connessione a localhost:1555
        InputStream socketInput = null;
        OutputStream socketOutput = null;
        try {
            System.out.println("Connessione alla socket: " + IP + PORT);
            socket = new Socket(IP, PORT);
            socketInput = socket.getInputStream();

            socketOutput = socket.getOutputStream();

        } catch (IOException ex) {
            ex.printStackTrace();
            Label nonconnesso = new Label("\n- - - - Connection failed try again - - - -\n").setBackgroundColor(BLACK)
                    .setForegroundColor(coloreLabel);
            panel.addComponent(nonconnesso);
            panel.removeAllComponents();
            panel.setFillColorOverride(BLACK);

            MainSchermata.Schermata(panel);

        }

        InputStreamReader socketReader = new InputStreamReader(socketInput);
        OutputStreamWriter socketWriter = new OutputStreamWriter(socketOutput);

        BufferedReader fromServer = new BufferedReader(socketReader); //Legge stringhe dal socket
        PrintWriter toServer = new PrintWriter(socketWriter); //Scrive stringhe sul socket
        System.out.println("Connessione eseguita!");
        Label connesso = new Label("\n- - - - Connected - - - -\n").setBackgroundColor(BLACK)
                .setForegroundColor(coloreLabel);
        panel.addComponent(connesso);
        //Creazione del thread di invio messaggi
        Sender clientSender = new Sender(toServer,panel,coloreLabel,name);
        Thread senderThread = new Thread(clientSender);
        senderThread.start();

        message = name;
        toServer.println(message);
        toServer.flush();

        try {
            serverName = fromServer.readLine();
            if(serverName.equals("_terminate")){
                terminate = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        RiceviStato rs = new RiceviStato();
        while(clientSender.shown){

        }
        if(!terminate) {
            // Finché il server non chiude la connessione o non ricevi un messaggio "/quit"...
            while (message != null || message.equals("/quit")) {

                try {

                    // Leggi un messaggio inviato dal server
                    message = fromServer.readLine();
                    System.out.println(message);

                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                // Se il server invia un comando /quit mi disconnetto dal server
                if (message.equals("/quit")) {
                    /*
                    Label successo = new Label("\n- - - Server left - - -").setBackgroundColor(BLACK)
                            .setForegroundColor(coloreLabel);
                    panel.addComponent(successo);
                  */
                    try {
                        connectedClients.remove(name);
                        Client.socket.close();

                    } catch (SocketException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    panel.removeAllComponents();
                    MainSchermata.Schermata(panel);
                    break;

                } else if (message.equals("/start")) {
                    try {

                        panel.removeAllComponents();
                        panel.setFillColorOverride(BLACK);
                        // Leggo i nick di tutti i giocatori
                        MainSchermata.screen.close();

                        connectedClients.clear();

                        if (YouWin.nextGame) {
                            YouWin.screen.close();
                        }
                        if (GameOver.nextGame) {
                            GameOver.screen.close();
                        }
                        if (Restart.nextGame) {
                            Restart.screen.close();
                        }
                        String playersStringMessage = fromServer.readLine();
                        System.out.println("Nomi: " + playersStringMessage);
                        connectedClients.addAll(Arrays.asList(playersStringMessage.split("-")));
                        Schermo schermo = new Schermo(toServer, name, IP, PORT, panel, coloreLabel, connectedClients);
                        gameThread = new Thread(schermo);
                        gameThread.start();

                        Schermo.gameOver = false;

                        while (!Schermo.gameOver) {
                            try {
                                playersData = fromServer.readLine();
                                //System.out.println(playersData);
                                System.out.println("Dati:" + playersData);
                                if (playersData.contains(":0")) {
                                    //Schermo.campoAvv=playersData;
                                    rs.run(playersData);

                                    //Schermo.traduciStringToInt(playersData);
                                }

                                //se il messaggio contiene la parola "spazzatura" so che dovrò aggiungere righe spazzatura
                                // in base al numero finale del messaggio
                                if (playersData.contains("spazzatura")) {

                                    //quindi divido il messaggio e aggiungo righe spazzatura in base a quanto dice il
                                    //messaggio ricevuto
                                    String arr[] = playersData.split("-");
                                    //controllo che le righe spazzatura siano indirizzate a me
                                    if(arr[1].equals(name)){
                                        if (arr[2].equals("2")) {
                                            Schermo.aggiungiSpazzatura = 1;
                                            System.out.println("Spazzatura aggiunta: " + Schermo.aggiungiSpazzatura);
                                        } else if (arr[2].equals("3")) {
                                            Schermo.aggiungiSpazzatura = 2;
                                            System.out.println("Spazzatura aggiunta: " + Schermo.aggiungiSpazzatura);

                                            //se non è ne 1 ne 2 ne 3 allora invierò 4 righe perché tanto meno non possono essere
                                            //se no sarebbe ricaduto in uno dei casi precedenti
                                        } else {
                                            Schermo.aggiungiSpazzatura = 4;
                                            System.out.println("Spazzatura aggiunta: " + Schermo.aggiungiSpazzatura);
                                        }
                                    }
                                }
                                if (playersData.contains("/pause") && !pause) {
                                    pause = true;
                                    gameThread.suspend();
                                } else if (playersData.contains("/resume") && pause) {
                                    pause = false;
                                    gameThread.resume();
                                } else if (playersData.contains("/restart") && !pause) {
                                    winner = false;
                                    restart = true;
                                    while(restart){

                                    }
                                    restart = true;
                                    connectedClients.clear();
                                    Thread.currentThread().interrupt();
                                    break;

                                } else if (playersData.equals("[" + name + "]-winner")) {
                                    winner = true;
                                    connectedClients.clear();
                                    winCheck = true;
                                    while(winCheck){

                                    }
                                    Thread.currentThread().interrupt();
                                    break;

                                }
                                if (Schermo.gameOver) {
                                    connectedClients.clear();
                                    loseCheck = true;
                                    while(loseCheck){

                                    }
                                    Thread.currentThread().interrupt();
                                    break;

                                }
                            } catch (IOException e) {

                                e.printStackTrace();

                            }
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else {
                    //Se il messaggio non è nullo lo stampo
                    Label lab_clientMsg = new Label(message).setBackgroundColor(BLACK).setForegroundColor(coloreLabel);
                    panel.addComponent(lab_clientMsg);
                }
            }
        }
        if(terminate) {
            try {
                Terminate termina = new Terminate(panel, coloreLabel);
                Thread terminateThread = new Thread(termina);
                terminateThread.start();
                socket.close(); //Chiudi la connessione
                senderThread.interrupt();
                try {
                    MainSchermata.screen.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            } finally {
                try {
                    socket.close(); //Chiudi la connessione
                } catch (IOException e) {
                    e.printStackTrace();
                }
                senderThread.interrupt();
            }

        }
    }
}
