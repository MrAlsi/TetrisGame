package com.company;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ServerSender implements Runnable{
    public Boolean gameStarted;
    HashMap<String ,PrintWriter> connectedClients;
    public ServerSender(Boolean gameStarted ) {
        this.gameStarted=gameStarted;

    }

    public void run() {
        Scanner userInput = new Scanner(System.in);
        String userMessage = "";
        while(!Thread.interrupted()) { //Finché non ricevi un comando "quit" dall'utente...
            userMessage = userInput.nextLine(); //... leggi un messaggio da console (bloccante!)...
            if(userMessage.toLowerCase().equals("/start")){
                System.out.println("sono qui");
                gameStarted = true;
            }
            else if(userMessage != null && !userMessage.toLowerCase().equals("/start")){
                broadcastServerMessage("[SERVER]: " + userMessage);
            }

        }

        userInput.close();
    }

    public void broadcastServerMessage(String message) {

        for(Map.Entry<String, PrintWriter> e : connectedClients.entrySet()) {

            e.getValue().println(message);
            e.getValue().flush();
        }
    }
}

