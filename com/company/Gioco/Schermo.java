package com.company.Gioco;

import com.company.Gioco.Mini.Mini_Griglia;
import com.company.Gioco.Pezzi.*;
import com.company.client.Client;
import com.googlecode.lanterna.Symbols;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.awt.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Semaphore;

import static com.googlecode.lanterna.TextColor.ANSI.*;
import static com.googlecode.lanterna.input.KeyType.EOF;

public class Schermo implements Runnable{

    //Variabili
    public static Pezzo pezzoScelto;                    //Pezzo che sta cadendo
    private CadutaBlocco brickDropTimer;                //Timer per la caduta del blocco
    private Boolean barra = false;                      //Se è true il pezzo cadrà tutto
    private static List<String> connectedClients;       //Lista dei client connessi

    private static String username;
    private static String IP;
    private static int PORT;
    private static Panel panel;
    private static TextColor coloreLabel;
    private final int brickDropDelay = 1000;
    public static Screen screen;                        //Oggetto schermo, è la finestra di gioco
    private Random sceltaPezzo = new Random();          //Variabile randomica per scegliere il prossimo pezzo che cadrà
    private int selezionato;                           //servirà come variabile d'appoggio per controllare quale sia il campo evidenziato


    //Variabili publiche
    public int delay = 1000 / 60;
    public Griglia campo;
    public static TextGraphics schermo;
    public static boolean gameOver = false;
    public static String datas = "e";                   //Conterrà la varibile che manderemo al server
    public static String usernameDestinatario = "";     //Username a chi voglio inviare la spazzatura
    public static int aggiungiSpazzatura = 0;           //Righe spazzatura che devo aggiungere al mio campo

    public static PrintWriter pw = null;
    public int dim;
    public static Mini_Griglia[] miniCampo = new Mini_Griglia[3];   //Campi degli avversari, max 3
    public static Semaphore semaforoColore=new Semaphore(1); // serve per gestire l'accesso a "schermo.setForegroundColor"
    public Semaphore semaforoSpazzatura = new Semaphore(1);  // serve per evitare la concorrenza tra il pezzo che scende
    // e l'arrivo di una riga spazzatura
    //Variabili per i tasti per il processKeyInput
    Character c1 = ' '; //Fai cadere il pezzo fino a giù
    Character c2 = 'x'; //Ruota
    Character c3 = 's'; //Eventuale per aggiungersi la spazzatura

    Character uno = '1'; //Evidenziare campo 1 (in realtà lo 0)
    Character due = '2'; //Evidenziare campo 2 (in realtà lo 1)
    Character tre = '3'; //Evidenziare campo 3 (in realtà lo 2)


    public Schermo(PrintWriter toServer, String name, String IP, int PORT, Panel panel, TextColor coloreLabel, List<String> connectedClients) throws IOException {
        this.IP = IP;
        this.PORT = PORT;
        this.panel = panel;
        this.coloreLabel = coloreLabel;
        this.connectedClients=connectedClients;
        pw = toServer;
        username = name;


        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();

        System.out.println(size);
        System.out.println(size.getWidth());
        System.out.println(size.getHeight());

        // width will store the width of the screen
        int width = (int)size.getWidth();

        // height will store the height of the screen
        int height = (int)size.getHeight();

        int leftMargin = width/19;
        int topMargin = height/50;

        //codice per avere uno schermo
        final int COLS = width/8;
        final int ROWS = height/16;
        DefaultTerminalFactory defaultTerminalFactory = new DefaultTerminalFactory();
        defaultTerminalFactory.setInitialTerminalSize(new TerminalSize(COLS,ROWS));
        Terminal terminal = defaultTerminalFactory.createTerminalEmulator();

        screen = new TerminalScreen(terminal);
        screen.startScreen();

        schermo = screen.newTextGraphics();
        //Creazione del campo da gioco
        campo = new Griglia(schermo);
        campo.creaCampo();
        //creo minicampi

        //prendo la dimensione per sapere i client connessi
        dim = connectedClients.size();
        int posverticale=height/25;

        int pos1;
        int pos2;
        int pos3;

        //posizioni per mettere i nomi sotto ai campi
        //variano in base alla dimensione dello schermo
        if(width<=1280) {
            pos1 = width / 19;
            pos2 = width / 12;
            pos3 = width / 9;
        } else {
            pos1 = width / 29;
            pos2 = width / 17;
            pos3 = width / 13;
        }

        //for in base al numero di giocatori
        int j = 0;
        System.out.println("Client connessi: " + connectedClients);
        for (String nome : connectedClients) {
            //controllo che il nome sia diverso dal mio
            if (!nome.equals(name)) {
                miniCampo[j] = new Mini_Griglia(schermo, j, nome);
                miniCampo[j].creaCampo();
                //assegno un numero ad ogni giocatore
                String stampanome = " " + (j+1) + "-->'" + nome + "'";
                screen.refresh();
                if (j == 0) {
                    //quando disegno il primo campo lo evidenzio sempre per avere fin da subito in maniera automatica un
                    //campo a cui inviare le righe spazzatura
                    schermo.putString(pos1, posverticale, stampanome).setBackgroundColor(BLACK);
                    evidenzia(0);
                    selezionato=0;
                    usernameDestinatario=nome;
                } else if (j == 1) {
                    schermo.putString(pos2, posverticale, stampanome).setBackgroundColor(BLACK);
                } else {
                    schermo.putString(pos3, posverticale, stampanome).setBackgroundColor(BLACK);
                }
                j++;
            }

        }
        screen.refresh();
    }

    public synchronized void run(){
        //Gioco
        try {
            //Creazione terminale con dimensioni già fisse
            //Gioco

            // start brick move treads

            brickDropTimer = new CadutaBlocco();
            brickDropTimer.setDelay(brickDropDelay);
            brickDropTimer.start();

            InputKey keyInput = new InputKey((TerminalScreen) screen);
            keyInput.start();

            pezzoScelto = prossimoPezzo(schermo);
            InviaStato IS = new InviaStato(username, pw);
            IS.run(campo);


            // run game loop
            while(!gameOver) {

                if(Client.winner){          //Controlla se si è rimasti gli ultimi in vita, nel caso HAI VINTO!!!
                    gameOver = true;
                    Client.winner = false;
                    System.out.println("Partita finita");
                    YouWin vittoria = new YouWin(username, IP, PORT, panel, coloreLabel, connectedClients);
                    Thread vittoriaThread = new Thread(vittoria);
                    vittoriaThread.start();
                    break;
                }

                List<KeyStroke> keyStrokes = keyInput.getKeyStrokes();  //Lista per prendere gli input da tastiera

                for(KeyStroke key : keyStrokes) {               //Esegue gli input presi da tastiera

                    processKeyInput(key);
                }

                if((pezzoScelto.collisioneSotto() && brickDropTimer.getDropBrick()) || barra){
                    barra = false;                               //Avviene una collisione: TRUE
                    pezzoScelto.setStruttura();                  //Il pezzo diventa parte della struttura
                    int combo = campo.controlloRighe();          //Controlla se ci sono righe piene e le elimina
                    if(combo > 1){                               //Combo serve per vedere se si sono liberate più righe
                        //Mando delle righe spaz. in base alla combo
                        datas = "spazzatura-" + usernameDestinatario + "-" + combo;
                        invia(datas, pw);
                    }
                    //Richiamo il metodo per mandare le righe spaz. in base alla combo
                    //screen.refresh();                            //Refresh dello schermo
                    for(int i=4; i<8; i++) {                     //Controllo se la prima riga al centro è occupata da dei
                        if(campo.griglia[i][0].stato>1){        // blocchi della struttura o spazzatura, in tal caso ho perso
                            haiPerso();
                            System.out.println("1");
                            gameOver=true;
                            break;
                        }
                    }
                    pezzoScelto = prossimoPezzo(schermo);        //Nuovo pezzo inizia a scendere

                }

                //Aggiungi la spazzatura
                if(aggiungiSpazzatura != 0){            //Qualcuno mi ha inviato una riga spazzatura
                    try {
                        semaforoSpazzatura.acquire();       //Semaforo per evitare problemi di concorrenza
                    } catch (InterruptedException e) {      //se finisse il brickDropTimer

                        e.printStackTrace();
                    }
                    campo.aggiungiSpazzatura(aggiungiSpazzatura);
                    aggiungiSpazzatura = 0;
                    semaforoSpazzatura.release();
                }
                //Finito il tempo, deve cadere il pezzo
                if(brickDropTimer.getDropBrick()) {
                    try {
                        semaforoSpazzatura.acquire();       //Semaforo per evitare problemi di concorrenza
                    } catch (InterruptedException e) {      // se arrivasse della spazzatura adesso

                        e.printStackTrace();
                    }
                    pezzoScelto.scendi(campo);              //Il pezzo scende
                    IS.run(campo);                          //Manda l'aggioramento del proprio stato agli altri

                    semaforoSpazzatura.release();
                }
                try {
                    RiceviStato.traduzione.acquire();       //Semaforo per evitare problemi di concorrenza
                } catch (InterruptedException e) {          //con l'arrivo dello stato della griglia degli altri campi
                    e.printStackTrace();
                }
                screen.refresh();                       //Refresho lo schermo
                RiceviStato.traduzione.release();

            }
            semaforoColore.release();
            screen.close();                                 //Chiude lo schermo
            Thread.currentThread().interrupt();             //Interrompe il thread

        } catch (IOException e) {
            System.out.println("Problema con il terminale");
            e.printStackTrace();
        }
    }

    public void stampaGriglia(Griglia campo) {
        System.out.println("-------------------------------------");
        for (int i = 0; i < 12; i++) {
            for (int e = 0; e < 24; e++) {
                System.out.print(miniCampo[0].griglia[i][e].stato);
            }
            System.out.println("");
        }
        System.out.println("-------------------------------------");
    }

    /**
     * Creatore di pezzi randomici
     * @return il prossimo pezzo che cadrà
     */
    public Pezzo prossimoPezzo(TextGraphics schermo){
        //Restituisce il prossimo pezzo che cadrà
        switch(sceltaPezzo.nextInt(7)) {
            case 0: return new PezzoLungo(schermo, campo);
            case 1: return new PezzoT(schermo, campo);
            case 2: return new PezzoL(schermo, campo);
            case 3: return new PezzoJ(schermo, campo);
            case 4: return new PezzoS(schermo, campo);
            case 5: return new PezzoZ(schermo, campo);
            case 6: return new PezzoQuadrato(schermo, campo);
        }
        return null;
    }

    /**
     * Traduttore da KeyInput ad evento del pezzo
     */
    private void processKeyInput(KeyStroke key) throws IOException {
        //down totale
        if(c1.equals(key.getCharacter())) {
            while(!pezzoScelto.collisioneSotto()){
                try {
                    semaforoSpazzatura.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                pezzoScelto.scendi(campo);
                semaforoSpazzatura.release();
            }
            barra = true;
        }


        // down
        if(key.getKeyType().equals(KeyType.ArrowDown)) {
            try {
                semaforoSpazzatura.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(!pezzoScelto.collisioneSotto()){
                pezzoScelto.scendi(campo);
            }
            semaforoSpazzatura.release();

        }

        // left
        if(key.getKeyType().equals(KeyType.ArrowLeft)) {
            if(!pezzoScelto.collisioneMovimento(-1)) {
                pezzoScelto.muovi(campo, -1, 0);
            }
        }

        // right
        if(key.getKeyType().equals(KeyType.ArrowRight)) {
            if(!pezzoScelto.collisioneMovimento(1)) {
                pezzoScelto.muovi(campo, 1, 0);
            }
        }

        // rotate right
        if(c2.equals(key.getCharacter())) {
            if(!pezzoScelto.collisioneRotazione())
                pezzoScelto.ruota(campo, pezzoScelto.rotazione, 1);
        }
        //1
        //Evidenziare il campo 1
        if (uno.equals(key.getCharacter())&& dim>2) {
            if(selezionato!=0) {
                evidenzia(0);
                selezionato=0;
                for (int i = 1; i < dim - 1; i++) {
                    noEvidenzia(i);
                }
                usernameDestinatario = miniCampo[0].nome;
            }
        }
        //2
        //Evidenziare il campo 2
        if((due.equals(key.getCharacter()))&& dim>2) {
            if(selezionato!=1) {
                selezionato=1;
                evidenzia(1);
                for (int i = 0; i < dim - 1; i++) {
                    if (i != 1) {
                        noEvidenzia(i);
                    }
                }
                usernameDestinatario = miniCampo[1].nome;
            }
        }
        //3
        //Evidenziare il campo 3
        if((tre.equals(key.getCharacter()))&& dim>2) {
            if (selezionato != 2) {
                if (dim == 4) {
                    evidenzia(2);
                    selezionato=2;
                    for (int i = 0; i < dim - 2; i++) {
                        noEvidenzia(i);
                    }
                    usernameDestinatario = miniCampo[2].nome;
                }
            }
        }
        //Se si preme la 'X' in alto nella finestra interrompe anche l'esecuzione del programma
        if(EOF.equals(key.getKeyType())){
            invia(username+"-lost",pw);
            System.exit(0);
        }

        //per provare righe spazzatura
        if(c3.equals(key.getCharacter())) {

            datas = "spazzatura-" + usernameDestinatario + "-" + 3;
            invia(datas, pw);

        }
    }
    /**
     * Metodo per inviare messaggi
     * @param s Stringa che si vuole inviare
     */
    public static void invia(String s, PrintWriter pw){
        pw.println(s);
        pw.flush();
    }

    /**
     * Il metodo Evidenzia serve per creare un rettangolo giallo attorno al campi selezionato, in questo modo quando
     * si cambia persona a cui inviare le righe spazzatura si crea un rettangolo giallo attorno al nuovo campo a cui
     * si vuole inviare le righe spazzatura.
     * Al metodo viene passato come parametro il campo da "colorare".
     * Il semaforo semaforoColore viene acquisito per evitare che qualche altro pezzo di codice acceda al colore
     * contemporaneamente a questo metodo cambiando il colore richiesto, quindi acquisendo e rilasciando il semaforo
     * dopo aver disegnato il rettangolo siamo tranquilli che verrà disegnato con il colore richiesto dal metodo e non
     * di altri colori.
     */
    public void evidenzia(int campo){
        try {
            semaforoColore.acquire();
            schermo.setForegroundColor(YELLOW_BRIGHT);
            campo=campo*40+60-1;
            schermo.drawRectangle(new TerminalPosition(campo, 2), new TerminalSize(26, 26),
                    Symbols.BLOCK_SOLID);
            semaforoColore.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Il metodo noEvidenzia serve per creare un rettangolo nero attorno ai campi non selezionati, in questo modo quando
     * si cambia persona a cui inviare le righe spazzatura si "cancellerà" il rettangolo giallo precedentemente creato.
     * Al metodo viene passato come parametro il campo da "decolorare".
     * Il semaforo semaforoColore viene acquisito per evitare che qualche altro pezzo di codice acceda al colore
     * contemporaneamente a questo metodo cambiando il colore richiesto, quindi acquisendo e rilasciando il semaforo
     * dopo aver disegnato il rettangolo siamo tranquilli che verrà disegnato con il colore richiesto dal metodo e non
     * di altri colori.
     */
    public void noEvidenzia(int campo){
        try {
            semaforoColore.acquire();
            schermo.setForegroundColor(BLACK);
            campo=campo*40+60-1;
            schermo.drawRectangle(new TerminalPosition(campo, 2), new TerminalSize(26, 26),
                    Symbols.BLOCK_SOLID);
            semaforoColore.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    /**
     * Metodo richiamato quando si perde
     * manda a tutti il messaggio proprio username-lost e fà partire il thread della classe GameOver
     */
    public static void haiPerso() {
        if(!gameOver) {
            System.out.println("Partita finita");
            String msg_sconfitta = username + "-lost";
            invia(msg_sconfitta, pw);
            GameOver sconfitta = new GameOver(username, IP, PORT, panel, coloreLabel, connectedClients);
            Thread sconfittaThread = new Thread(sconfitta);
            sconfittaThread.start();
            gameOver=true;
        }
    }
    public static void lost(String name){
        int i;
        for(i=0;i<miniCampo.length;i++){
            if(name.equals(miniCampo[i].nome)){
                break;
            }
        }
        if(i==0){
            String stampalost = "Ha perso";
            try {
                semaforoColore.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            schermo.setForegroundColor(RED_BRIGHT);
            schermo.putString(1280/19,720/40 , stampalost).setBackgroundColor(BLACK);
            semaforoColore.release();
        }
        else if(i==1){
            try {
                semaforoColore.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            schermo.setForegroundColor(RED_BRIGHT);
            String stampalost = "Ha perso";
            schermo.putString(1280/12,720/40 , stampalost).setBackgroundColor(BLACK);
            semaforoColore.release();
        }else{
            try {
                semaforoColore.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            schermo.setForegroundColor(RED_BRIGHT);
            String stampalost = "Ha perso";
            schermo.putString(1280/9,720/ 40, stampalost).setBackgroundColor(BLACK);
            semaforoColore.release();
        }
    }
}
