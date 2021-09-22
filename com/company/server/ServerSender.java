package com.company.server;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.TextBox;

import java.io.PrintWriter;
import java.util.Map;

import static com.googlecode.lanterna.TextColor.ANSI.BLACK;

public class ServerSender implements Runnable{

    private Panel panel;
    private TextColor coloreLabel;
    public static String name;
    public String ip;

    public ServerSender(Panel panel, TextColor coloreLabel, String name) {
        this.name=name;
        this.panel=panel;
        this.coloreLabel=coloreLabel;
    }

    public synchronized void run() {
        TextBox messaggio = new TextBox();
        panel.addComponent(messaggio);
        inviaMessaggio(messaggio);
    }

    public synchronized void inviaMessaggio(final TextBox messaggio){
        new Button("Invia",new Runnable(){
            @Override
            public void run(){
                //flag=false;
                //Finché non ricevi un comando "quit" dall'utente...
                //userMessage = userInput.nextLine(); //... leggi un messaggio da console (bloccante!)...
                //toOther.println(userMessage); //... e invialo al server
                if (!messaggio.getText().equals("")) {

                    String messaggioString = messaggio.getText();

                    // Se il server invia il messaggio "/start" il gioco ccerca di partire

                    if (messaggioString.equals("/start")) {
                        System.out.println("Il Server ha dato il comando START : il gioco cerca di partire...");
                        //controllo il numero dei giocatori
                        if (Server.connectedClients.size() < 2) {
                            System.out.println("Numero di giocatori insufficiente");
                            Label lab_serverMsg = new Label("[SERVER]: Numero di giocatori insufficiente.")
                                    .setBackgroundColor(BLACK).setForegroundColor(coloreLabel);
                            panel.addComponent(lab_serverMsg);
                            broadcastServerMessage("[" + name + "]: " + messaggioString);


                        } else {
                            //svuoto il pannello e avverto i client
                            System.out.println("Partita iniziata!");
                            broadcastServerMessage(messaggioString);
                            if(Server.giocatori != null) {
                                Server.giocatori.clear();
                            }
                            for (Map.Entry<String, PrintWriter> pair : Server.connectedClients.entrySet()) {
                                ConnectionListener.players = ConnectionListener.players + pair.getKey() + "-";
                                Server.giocatori.add(pair.getKey());
                            }

                            broadcastServerMessage(ConnectionListener.players);
                            ConnectionListener.players = "";
                            Server.gameStarted = true;
                            Label lab_serverMsg = new Label("[SERVER]: Partita iniziata").setBackgroundColor(BLACK).setForegroundColor(coloreLabel);
                            panel.addComponent(lab_serverMsg);
                            broadcastServerMessage("[" + name + "]: " + messaggioString);

                        }
                    } else if (messaggioString.equals("/quit")) {
                        System.out.println("Uscita dal gioco");
                        if(Server.connectedClients.size()==0){
                            Server.serverThread.stop();
                            System.exit(0);
                        }
                        else {
                            broadcastServerMessage(messaggioString);
                            Server.connectedClients.clear();
                            Server.senderThread.stop();
                            Server.serverThread.stop();
                            System.exit(0);

                        }
                    }else if(messaggioString.equals("/restart")) {
                        clear(messaggio);
                        //Server.connectedClients.clear();
                        System.out.println("Restart ...");
                        broadcastServerMessage(messaggioString);
                        Label restart= new Label("[SERVER]: Resettando la partita...\n[SERVER]: nuova partita avviata")
                                .setBackgroundColor(BLACK).setForegroundColor(coloreLabel);
                        panel.addComponent(restart);

                    }else if(messaggioString.equals("/startagain")&& Server.gameStarted==true) {
                        if(Server.connectedClients.size()>=2) {
                            clear(messaggio);
                            System.out.println("Restart immediato...");
                            Label lab_serverMsg = new Label("[SERVER]: Resettando la partita...").setBackgroundColor(BLACK).setForegroundColor(coloreLabel);
                            panel.addComponent(lab_serverMsg);
                            broadcastServerMessage(messaggioString);
                            Label inizio = new Label("[SERVER]: Partita iniziata...").setBackgroundColor(BLACK).setForegroundColor(coloreLabel);
                            panel.addComponent(inizio);
                        }

                    }else if(messaggioString.equals("/clear")){
                        clear(messaggio);
                    } else {
                        if(messaggioString.equals("/startagain")){
                            // if(Server.connectedClients.size()>=2) {
                            System.out.println("Numero di giocatori insufficiente");
                            Label lab_serverMsg = new Label("[SERVER]: Numero di giocatori insufficiente.")
                                    .setBackgroundColor(BLACK).setForegroundColor(coloreLabel);
                            panel.addComponent(lab_serverMsg);
                            //}
                           /* System.out.println("La partita non é ancora iniziata non puoi reiniziarla ");
                            Label lab_serverMsg = new Label("[SERVER]: Numero di giocatori insufficiente.")
                                    .setBackgroundColor(BLACK).setForegroundColor(coloreLabel);
                            panel.addComponent(lab_serverMsg);*/
                        }else{
                            // In tutti gli altri casi trasmetto il messaggio del server a tutti i client connessi
                            System.out.println("trasmetto il messaggio del server a tutti i client connessi");
                            Label lab_serverMsg = new Label("[" + name + "]: " + messaggioString).setBackgroundColor(BLACK).setForegroundColor(coloreLabel);
                            panel.addComponent(lab_serverMsg);
                            broadcastServerMessage("[" + name + "]: " + messaggioString);
                        }
                    }

                    // Una volta inviato il messaggio pulisco la textbox
                    messaggio.setText("");
                }
            }
        }).addTo(panel);
    }

    public void broadcastServerMessage(String message) {

        for(Map.Entry<String, PrintWriter> e : Server.connectedClients.entrySet()) {

            e.getValue().println(message);
            e.getValue().flush();
        }
    }
    public void clear( TextBox messaggio){
        System.out.println("Pulisci schermo...");
        panel.removeAllComponents();
        Label lab=new Label("\nStarting "+ name + "... ").setBackgroundColor(BLACK).setForegroundColor(
                coloreLabel);
        panel.addComponent(lab);
        Label myIp = new Label("\nShare your ip address: " + Server.ip).setBackgroundColor(BLACK).setForegroundColor(
                coloreLabel);
        panel.addComponent(myIp);
        Label lab_serverOn=new Label("\n- - - Server on - - -").setBackgroundColor(BLACK).setForegroundColor(coloreLabel);
        panel.addComponent(lab_serverOn);
        panel.addComponent(messaggio);
        inviaMessaggio(messaggio);
    }
}
