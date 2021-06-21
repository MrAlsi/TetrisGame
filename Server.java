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
    private ConnectionListener connectionListener;
    protected Thread runningThread= null;
    private Boolean gameStart = false;
    public String name;
    public TextColor coloreLabel;
    public Panel panel;
    public static Thread serverThread;
    public static Thread senderThread;
    public static Thread listenerThread;

    /*
    private ServerSocket listener = null;
    private PrintWriter out = null;
    int port = 6789;*/


    public Server(String name, Panel panel, TextColor coloreLabel) {
        this.name = name;
        this.panel= panel;
        this.coloreLabel= coloreLabel;
        connectionListener = new ConnectionListener(panel,coloreLabel,connectedClients);
        listenerThread = new Thread(connectionListener);

    }

    public void StartServer(Server server) {
        serverThread = new Thread(server);
        serverThread.start();
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
            ServerSender serverSender = new ServerSender(panel, coloreLabel, connectedClients);
            senderThread = new Thread(serverSender);
            senderThread.start();

            // ciclo per far connettere più client al server
            Label lab_serverOn=new Label("\n- - - Server on - - -").setBackgroundColor(BLACK).setForegroundColor(coloreLabel);
            panel.addComponent(lab_serverOn);

            //System.out.println("\n- - - Server on - - -");
            while(true){
                // commenta easterEgg per distrugere il server
                String easterEgg = new String();
                if(gameStart == true || connectedClients.size() > 7){
                    panel.setVisible(true);
                    gameStart = true;
                    Label lab_gameStarting=new Label("\n\n- - - THE GAME IS STARTING - - -").setBackgroundColor(BLACK).setForegroundColor(coloreLabel);
                    panel.addComponent(lab_gameStarting);

                    System.out.println("\n\n- - - THE GAME IS STARTING - - -" );
                    broadcastServerMessage("\n\n- - - THE GAME IS STARTING - - -");

                    Label lab_gameStarted=new Label("\n\n- - - GAME STARTED - - -\n|  Online players: " + connectedClients.size() + "  |").setBackgroundColor(BLACK).setForegroundColor(coloreLabel);
                    panel.addComponent(lab_gameStarted);

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





    // classe per la trasmissione di un messaggio inviato dal server agli altri client
    public void broadcastServerMessage(String message) {

        for(Entry<String, PrintWriter> e : connectedClients.entrySet()) {

            e.getValue().println(message);
            e.getValue().flush();
        }
    }
/*
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


}
