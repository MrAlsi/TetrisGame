import java.io.PrintWriter;
import java.util.Scanner;

public class Sender implements Runnable {
    private PrintWriter toOther; //Stream su cui inviare stringhe
    /* Può essere verso il client o verso il server, a seconda di come viene inizializzato
    a Sender non importa: riusabilità del codice */

    public Sender(PrintWriter pw) {
        this.toOther = pw;
    }

    public void run() {
        Scanner userInput = new Scanner(System.in);
        String userMessage = "";
        while(!Thread.interrupted()) { //Finché non ricevi un comando "quit" dall'utente...
            userMessage = userInput.nextLine(); //... leggi un messaggio da console (bloccante!)...
            if (userMessage.toLowerCase().equals("/help")){
                System.out.println("\n- - - Here is the command list - - -\n|     /quit to exit\n|     /players to check online players\n|     /nick to change nickname");
            }
            else if (userMessage != null && !userMessage.toLowerCase().equals("/commands" )){    
                toOther.println(userMessage); //... e invialo al server
                toOther.flush();
            }
        }

        userInput.close();
    }
}
