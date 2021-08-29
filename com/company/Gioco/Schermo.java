package com.company.Gioco;

import com.company.client.Client;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.GridLayout;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.Window;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import javax.management.StringValueExp;
import javax.swing.plaf.basic.BasicOptionPaneUI;
import java.awt.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.lang.String;

import static com.googlecode.lanterna.TextColor.ANSI.BLACK;

public class Schermo implements Runnable{

    public int delay = 1000 / 60;

    Mini_Griglia[] miniCampo = new Mini_Griglia[3];

    private Griglia campo;
    private TextGraphics schermo;
    private Pezzo pezzoScelto;
    Random sceltaPezzo = new Random();
    private String azione;
    public static boolean gameOver = false;
    public static String datas;
    private CadutaBlocco brickDropTimer;
    private Boolean barra = false;
    public static String usernameDestinatario = "palma";
    public static int aggiungiSpazzatura = 0;
    private List<String> connectedClients;

    public PrintWriter pw;
    private String username;
    private String IP;
    private int PORT;
    private Panel panel;
    private TextColor coloreLabel;
    private final int brickDropDelay = 1000;
    private Screen screen;

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

        //codice per avere uno chermo
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
        //for in base al numero di giocatori
        //prendo la dimensione per sapere i com.company.client connessi
        int dim = connectedClients.size();
        //creo bottoni in base al numero dei giocatori
        int posverticale=height/27;
        int pos1=width/19;
        int pos2=width/12;
        int pos3=width/9;
        String dimm= String.valueOf(dim);
        schermo.putString(pos1, posverticale,dimm).setBackgroundColor(BLACK);
        int j = 0;
        for (String nome : connectedClients) {
            //controllo che il nome sia diverso dal mio
            if (!nome.equals(name)) {
                miniCampo[j] = new Mini_Griglia(schermo, j, nome);
                miniCampo[j].creaCampo();
                String stampanome = " " + (j+1) + "-->'" + nome + "'";
                screen.refresh();
                if (j == 0) {
                    schermo.putString(pos1, posverticale, stampanome).setBackgroundColor(BLACK);
                } else if (j == 1) {
                    schermo.putString(pos2, posverticale, stampanome).setBackgroundColor(BLACK);
                } else {
                    schermo.putString(pos3, posverticale, stampanome).setBackgroundColor(BLACK);
                }
                j++;
            }

        }
/*
        miniCampo[0]=new Mini_Griglia(schermo,0,name);
        miniCampo[0].creaCampo();
        String nome1="1-->'alsi'";

        schermo.putString(width/19,height/27,nome1).setBackgroundColor(BLACK);
        miniCampo[1]=new Mini_Griglia(schermo,1,name);
        miniCampo[1].creaCampo();
        String nome2="2-->'totta'";
        schermo.putString(width/12,height/27,nome2).setBackgroundColor(BLACK);
        miniCampo[2]=new Mini_Griglia(schermo,2,name);
        miniCampo[2].creaCampo();
        String nome3="3-->'gabri'";
        schermo.putString(width/9,height/27,nome3).setBackgroundColor(BLACK);
*/
        screen.refresh();
    }


    public synchronized void run(){
        try {
            //Creazione terminale con dimensioni già fisse

            //com.company.Gioco

            // start brick move treads
            brickDropTimer = new CadutaBlocco();
            brickDropTimer.setDelay(brickDropDelay);
            brickDropTimer.start();

            InputKey keyInput = new InputKey((TerminalScreen) screen);
            keyInput.start();

            pezzoScelto = prossimoPezzo(schermo);
            // run game loop
            while(!gameOver) {

                screen.refresh();

                if(Client.winner == true){
                    Client.winner = false;
                    System.out.println("Partita finita");
                    YouWin vittoria = new YouWin(username, IP, PORT, panel, coloreLabel,connectedClients);
                    Thread vittoriaThread = new Thread(vittoria);
                    vittoriaThread.start();
                    break;
                }

                List<KeyStroke> keyStrokes = keyInput.getKeyStrokes();

                for(KeyStroke key : keyStrokes) {
                    screen.refresh();
                    processKeyInput(key);
                }

                if((pezzoScelto.collisioneSotto() && brickDropTimer.getDropBrick()) || barra){
                    barra = false;                               //Avviene una collisione: TRUE
                    pezzoScelto.setStruttura();                  //Il pezzo diventa parte della struttura
                    screen.refresh();                            //Refresh dello schermo
                    int combo = campo.controlloRighe();          //Controllo se ci sono righe piene
                    if(combo > 1){                               //Combo serve per vedere se si sono liberate più righe
                        righeSpazzatura(combo);
                        datas = "spazzatura" + "-" + usernameDestinatario + "-" + combo;
                        invia(datas, pw);
                    }
                    //Richiamo il metodo per mandare le righe spaz. in base alla combo
                    screen.refresh();                            //Refresh dello schermo
                    if(campo.sconfitta()){
                        System.out.println("Partita finita");
                        String msg_sconfitta = username + "-lost";
                        invia(msg_sconfitta, pw);
                        GameOver sconfitta = new GameOver(username, IP, PORT, panel, coloreLabel,connectedClients);
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
                    screen.refresh();
                    pezzoScelto.scendi(campo);
                    datas = username + "/" + pezzoScelto.tipoPezzo + pezzoScelto.getCoord();
                    invia(datas, pw);
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
        for (int i = 0; i < 12; i++) {
            for (int e = 0; e < 24; e++) {
                System.out.print(campo.griglia[i][e].getStato() + "  ");
            }
            System.out.println("");
        }
    }

    //Restituisce il prossimo pezzo che cadrà
    public Pezzo prossimoPezzo(TextGraphics schermo){
        Pezzo pezzo=null;

        //Creatore di pezzi randomici
        switch(sceltaPezzo.nextInt(7)) {
            case 0:
                pezzo = new PezzoLungo(schermo, campo);
                break;
            case 1:
                pezzo = new PezzoT(schermo, campo);
                break;
            case 2:
                pezzo = new PezzoL(schermo, campo);
                break;
            case 3:
                pezzo = new PezzoJ(schermo, campo);
                break;
            case 4:
                pezzo = new PezzoS(schermo, campo);
                break;
            case 5:
                pezzo = new PezzoZ(schermo, campo);
                break;
            case 6:
                pezzo = new PezzoQuadrato(schermo, campo);
                break;
        }
        return pezzo;
    }

    //Thread per i pulsati e movimento pezzo
    private void processKeyInput(KeyStroke key) throws IOException {
        // drop
        Character c1 = ' ';
        Character c2 = 'z';
        Character c3 = 'x';
        datas = "";

        //down totale
        if(c1.equals(key.getCharacter())) {
            while(!pezzoScelto.collisioneSotto()){
                //System.out.println("In fondo");
                pezzoScelto.scendi(campo);
            }
            barra = true;
            datas = username + "/" + pezzoScelto.tipoPezzo + pezzoScelto.getCoord();
            invia(datas, pw);
            screen.refresh();
        }

        // down
        if(key.getKeyType().equals(KeyType.ArrowDown)) {
            if(!pezzoScelto.collisioneSotto()){
                pezzoScelto.scendi(campo);
                datas = username + "/" + pezzoScelto.tipoPezzo + pezzoScelto.getCoord();
                invia(datas, pw);
                screen.refresh();
            }
        }

        // left
        if(key.getKeyType().equals(KeyType.ArrowLeft)) {
            //if(p1.getRiga())
            if(!pezzoScelto.collisioneLaterale(-1)) {
                pezzoScelto.muovi(campo, -1);
                datas = username + "/" + pezzoScelto.tipoPezzo + pezzoScelto.getCoord();
                invia(datas, pw);
                screen.refresh();
            }
        }

        // right
        if(key.getKeyType().equals(KeyType.ArrowRight)) {
            if(!pezzoScelto.collisioneLaterale(1)) {
                pezzoScelto.muovi(campo, 1);
                datas = username + "/" + pezzoScelto.tipoPezzo + pezzoScelto.getCoord();
                invia(datas, pw);
                screen.refresh();
            }
        }

        // rotate left
        if(c2.equals(key.getCharacter())) {
            //Aggiungere rotazione
        }

        // rotate right
        if(c3.equals(key.getCharacter())) {
            pezzoScelto.ruota(campo);
            datas = username + "/" + pezzoScelto.tipoPezzo + pezzoScelto.getCoord();
            invia(datas, pw);
            screen.refresh();
        }
    }

    public void invia(String s, PrintWriter pw){
        pw.println(s);
        pw.flush();
    }

    public void righeSpazzatura(int combo){
        switch(combo){
            //Prova per vedere se funziona, quando mettiamo le righe spazzatura useremo questo metodo
            case 2 : System.out.println("1 Riga spazzatura");
            case 3 : System.out.println("2 Riga spazzatura");
            case 4 : System.out.println("4 Riga spazzatura");
            break;
        }
    }
}
