import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;


public class Client {
    public static void main (String [] args) {
        try {
            //SETUP
            Boolean usernameCheck = false;
            String username = "";
            int command = 0;
            while(usernameCheck == false){
                Scanner sc = new Scanner(System.in);
                System.out.print("\nInserisci username: ");
                username = sc.nextLine();
                System.out.println( "\n" + username + " e' stato impostato come username");
                System.out.println( "\nCosa vuoi fare? \n1 - Accedere al server \n2 - Modificare l'username");
                System.out.print( "1/2: ");
                command = sc.nextInt();
                if(command == 1){
                    usernameCheck = true;
                }           
            }
            
            System.out.println("\nConnessione al server in corso...");
            Socket socket = new Socket("212.124.174.25", 6789); //Creazione socket, connessione a localhost:1555
            System.out.println("\n- - - - Connesso alla chat - - - -\n");
            //Otteniamo gli stream
            InputStream socketInput = socket.getInputStream();
            OutputStream socketOutput = socket.getOutputStream();
            
            InputStreamReader socketReader = new InputStreamReader(socketInput);
            OutputStreamWriter socketWriter = new OutputStreamWriter(socketOutput);

            BufferedReader fromServer = new BufferedReader(socketReader); //Legge stringhe dal socket
            PrintWriter toServer = new PrintWriter(socketWriter); //Scrive stringhe sul socket

            //Creazione del thread di invio messaggi
            Sender clientSender = new Sender(toServer);
            Thread senderThread = new Thread(clientSender);

            String message = username;
            toServer.println(message);
            toServer.flush();
            senderThread.start();

            //LOGICA APPLICATIVA - RICEZIONE MESSAGGI
            
            while(message != null && !message.equals("quit")) { //Finché il server non chiude la connessione o non ricevi un messaggio "quit"...
                message = fromServer.readLine(); //Leggi un messaggio inviato dal server (bloccante!)
                    if (message != null) {
                        System.out.println(message);
                    }
            }

            senderThread.interrupt(); //Chiedi al senderThread di fermarsi
            socket.close(); //Chiudi la connessione

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}