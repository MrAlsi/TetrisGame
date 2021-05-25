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
                System.out.print("\nInsert username: ");
                username = sc.nextLine();
                System.out.println( "\n" + username + " was set as your username.");
                System.out.println( "\n- - - Options - - -\n1 - Connect to the server \n2 - Change username");
                System.out.print( "1/2: ");
                command = sc.nextInt();
                if(command == 1){
                    usernameCheck = true;
                }           
            }
            
            System.out.println("\nConnecting to the server...");
            Socket socket = new Socket("78.134.45.68", 6789); //Creazione socket, connessione a localhost:1555
            System.out.println("\n- - - - Connected! - - - -\n");
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
            
            while(message != null && !message.toLowerCase().equals("/quit")) { //Finché il server non chiude la connessione o non ricevi un messaggio "quit"...
                message = fromServer.readLine(); //Leggi un messaggio inviato dal server (bloccante!)
                if (message != null){
                    System.out.println(message);
                }               
            }

            System.out.println("\nLeaving the server...");
            senderThread.interrupt(); //Chiedi al senderThread di fermarsi
            socket.close(); //Chiudi la connessione
            System.out.println("\n- - Server left - -");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
