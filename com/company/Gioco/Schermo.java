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

import static com.googlecode.lanterna.TextColor.ANSI.BLACK;
import static com.googlecode.lanterna.TextColor.ANSI.YELLOW_BRIGHT;

public class Schermo implements Runnable{


    //Variabili private
    private int numeroDiStampa = 0;
    private Pezzo pezzoScelto;
    private CadutaBlocco brickDropTimer;
    private Boolean barra = false;
    private List<String> connectedClients;
    private static String username;
    private String IP;
    private int PORT;
    private Panel panel;
    private TextColor coloreLabel;
    private final int brickDropDelay = 1000;
    public static Screen screen;
    private Random sceltaPezzo = new Random();
    private int selezionato; //servirà come variabile d'appoggio per controllare quale sia il campo evidenziato

    //Variabili publiche
    public int delay = 1000 / 60;
    public Griglia campo;
    public static TextGraphics schermo;
    public static boolean gameOver = false;
    public static String datas = "e";
    public static String usernameDestinatario = "";
    public static int aggiungiSpazzatura = 0;
    public PrintWriter pw = null;
    public int dim;
    public static Mini_Griglia[] miniCampo = new Mini_Griglia[3];
    public static Semaphore semaforoColore=new Semaphore(1); // servirà per gestire l'accesso a "schermo.setForegroundColor"

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

        //Creazione del campo da gioco
        schermo = screen.newTextGraphics();

        campo = new Griglia(schermo);
        campo.creaCampo();
        //creo minicampi

        //prendo la dimensione per sapere i client connessi
        dim = connectedClients.size();
        int posverticale=height/25;

        //posizioni per mettere i nomi sotto ai campi
        int pos1=width/19;
        int pos2=width/12;
        int pos3=width/9;

        //for in base al numero di giocatori
        int j = 0;
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

            // run game loop
            while(!gameOver) {

                if(Client.winner){
                    gameOver = true;
                    Client.winner = false;
                    System.out.println("Partita finita");
                    YouWin vittoria = new YouWin(username, IP, PORT, panel, coloreLabel, connectedClients);
                    Thread vittoriaThread = new Thread(vittoria);
                    vittoriaThread.start();
                    break;
                }

                /*if(Client.restart){
                    gameOver = true;
                    Client.winner = false;
                    System.out.println("Partita resettata");
                    Restart ricomincia = new Restart(username, IP, PORT, panel, coloreLabel, connectedClients);
                    Thread restartThread = new Thread(ricomincia);
                    restartThread.start();
                    break;
                }*/

                List<KeyStroke> keyStrokes = keyInput.getKeyStrokes();

                for(KeyStroke key : keyStrokes) {
                    //screen.refresh();
                    processKeyInput(key);
                    //IS.run(campo);
                }

                if((pezzoScelto.collisioneSotto() && brickDropTimer.getDropBrick()) || barra){
                    barra = false;                               //Avviene una collisione: TRUE
                    pezzoScelto.setStruttura();                  //Il pezzo diventa parte della struttura
                    int combo = campo.controlloRighe();          //Controlla se ci sono righe piene e le elimina
                    // screen.refresh();                            //Refresh dello schermo
                    if(combo > 1){                               //Combo serve per vedere se si sono liberate più righe
                        //righeSpazzatura(combo);
                        datas = "spazzatura-" + usernameDestinatario + "-" + combo;
                        invia(datas, pw);
                    }
                    //Richiamo il metodo per mandare le righe spaz. in base alla combo
                    //screen.refresh();                            //Refresh dello schermo
                    if(campo.sconfitta()){
                        System.out.println("Partita finita");
                        String msg_sconfitta = username + "-lost";
                        invia(msg_sconfitta, pw);
                        GameOver sconfitta = new GameOver(username, IP, PORT, panel, coloreLabel, connectedClients);
                        Thread sconfittaThread = new Thread(sconfitta);
                        sconfittaThread.start();
                        gameOver = true;
                        break;
                    }
                    pezzoScelto = prossimoPezzo(schermo);        //Nuovo pezzo inizia a scendere

                }

                if(aggiungiSpazzatura != 0){
                    campo.aggiungiSpazzatura(aggiungiSpazzatura);
                    aggiungiSpazzatura = 0;
                }

                if(brickDropTimer.getDropBrick()) {
                    pezzoScelto.scendi(campo);
                    IS.run(campo);

                }

                //Semaforo
                try {
                    RiceviStato.traduzione.acquire();
                    screen.refresh();
                    RiceviStato.traduzione.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            screen.close();
            Thread.currentThread().interrupt();
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

    //Creatore di pezzi randomici
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

    //Thread per i pulsati e movimento pezzo
    private void processKeyInput(KeyStroke key) throws IOException {
        // drop
        Character c1 = ' '; //Fai cadere
        Character c2 = 'x'; //Ruota

        Character uno = '1'; //Evidenziare campo 1 (in realtà lo 0)
        Character due = '2'; //Evidenziare campo 2 (in realtà lo 1)
        Character tre = '3'; //Evidenziare campo 3 (in realtà lo 2)

        //down totale
        if(c1.equals(key.getCharacter())) {
            while(!pezzoScelto.collisioneSotto()){
                pezzoScelto.scendi(campo);
            }
            barra = true;
        }

        // down
        if(key.getKeyType().equals(KeyType.ArrowDown)) {
            if(!pezzoScelto.collisioneSotto()){
                pezzoScelto.scendi(campo);
            }
        }

        // left
        if(key.getKeyType().equals(KeyType.ArrowLeft)) {
            if(!pezzoScelto.collisioneLaterale(-1)) {
                pezzoScelto.muovi(campo, -1);
            }
        }

        // right
        if(key.getKeyType().equals(KeyType.ArrowRight)) {
            if(!pezzoScelto.collisioneLaterale(1)) {
                pezzoScelto.muovi(campo, 1);
            }
        }

        // rotate right
        if(c2.equals(key.getCharacter())) {
            if(!pezzoScelto.collisioneLateraleRotazione(pezzoScelto.spostamentoOrizzontale[pezzoScelto.rotazione], pezzoScelto.rotazione))
                pezzoScelto.ruota(campo, pezzoScelto.rotazione, 1);
        }

        //1
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
    }

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
}
