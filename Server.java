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

public class Server implements Runnable {
  
    private static HashMap<String, PrintWriter> connectedClients = new HashMap<>();
    private static ConnectionListener connectionListener = new ConnectionListener();
    private static Thread listenerThread = new Thread(connectionListener);
    private static Boolean gameStart = false;
    protected Thread runningThread= null;

    public static void main(String[] args){
        Server server = new Server();
        new Thread(server).start();

    }

    public void run() {
        
        synchronized(this){
            this.runningThread = Thread.currentThread();
        }
        
        System.out.println("\nStarting the server...");

        try {
            
            listenerThread.start();
            // creo il thread di comunicazione del server
            // e lo avvio 
            ServerSender serverSender = new ServerSender();
            Thread senderThread = new Thread(serverSender);
            senderThread.start();

            // ciclo per far connettere più client al server
            System.out.println("\n- - - Server on - - -");
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


    // classe per la trasmissione di un messaggio inviato da un client agli altri client
    public static void broadcastMessage(String message, String username) {

		for(Entry<String, PrintWriter> e : connectedClients.entrySet()) {

			if(!e.getKey().equals(username)){

                e.getValue().println(message);
                e.getValue().flush();
            }
		}
	}


    // classe per la trasmissione di un messaggio inviato dal server agli altri client
    public static void broadcastServerMessage(String message) {	

        for(Entry<String, PrintWriter> e : connectedClients.entrySet()) {

                e.getValue().println(message);
                e.getValue().flush();
        }
	}


    public static class ConnectionListener implements Runnable {

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
                    ClientHandler clientSock = new ClientHandler(socket, "");
                    new Thread(clientSock).start();
                    System.out.println("Connected clients: " + connectedClients.size() + "/8");
                    broadcastServerMessage("[SERVER]: Connected clients: " + connectedClients.size() + "/8"); 
                }
            } catch(Exception e) {
                e.printStackTrace();
            }     
        }
    }



    
    // classe per la gestione del thread del server
    public static class ServerSender implements Runnable {

        public ServerSender() {

        }
        @Override
        public void run() {
            Scanner serverInput = new Scanner(System.in);
            String serverMessage = "";
            while(true) { //Finché non ricevi un comando "quit" dall'utente...
                serverMessage = serverInput.nextLine(); //... leggi un messaggio da console (bloccante!)...
                if(serverMessage.equals("/start")){ 
                    listenerThread.interrupt();
                    gameStart = true;
                }
                else if(serverMessage != null && !serverMessage.toLowerCase().equals("/start")){
                    broadcastServerMessage("[SERVER]: " + serverMessage);
                }
            }
        }
    }


    // classe per la gestione dei thread dei client
    private static class ClientHandler implements Runnable {
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
    }
}
