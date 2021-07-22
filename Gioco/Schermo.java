package com.company.Gioco;

import com.company.client.Client;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.graphics.TextGraphics;
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

public class Schermo implements Runnable{

    public int delay = 1000 / 60;

    private Griglia campo;
    private TextGraphics schermo;
    private Pezzo pezzoScelto;
    Random sceltaPezzo = new Random();
    private String azione;
    public static boolean gameOver;
    public static String datas;
    private String username;

    public PrintWriter pw;
    private final int brickDropDelay = 1000;
    private Screen screen;

    public Schermo(PrintWriter toServer, String name) throws IOException {

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
        screen.refresh();
    }

    public synchronized void run(){
        try {
            //Creazione terminale con dimensioni già fisse

            //Gioco

            // start brick move treads
            CadutaBlocco brickDropTimer = new CadutaBlocco();
            brickDropTimer.setDelay(brickDropDelay);
            brickDropTimer.start();

            InputKey keyInput = new InputKey((TerminalScreen) screen);
            keyInput.start();

            pezzoScelto=prossimoPezzo(schermo);

            gameOver = false;
            // run game loop
            while(!gameOver) {

                if(Client.nick.length == 1 && Client.nick[0].equals(username)){
                    System.out.println("Partita finita");
                    YouWin vittoria = new YouWin();
                    vittoria.run();
                    gameOver=true;
                    screen.stopScreen();
                }

                Thread.sleep(delay);
                screen.refresh();
                List<KeyStroke> keyStrokes = keyInput.getKeyStrokes();

                if(pezzoScelto.collisioneSotto()){               //Avviene una collisione: TRUE
                    pezzoScelto.setStruttura();                  //Il pezzo diventa parte della struttura
                    screen.refresh();                            //Refresh dello schermo
                    int combo = campo.controlloRighe();          //Controllo se ci sono righe piene
                    if(combo > 1)                                //Combo serve per vedere se si sono liberate più righe
                        righeSpazzatura(combo);                  //Richiamo il metodo per mandare le righe spaz. in base alla combo
                    screen.refresh();                            //Refresh dello schermo
                    if(campo.sconfitta()){
                        System.out.println("Partita finita");
                        String msg_sconfitta = username + "-lost";
                        invia(msg_sconfitta, pw);
                        GameOver sconfitta = new GameOver();
                        sconfitta.run();
                        gameOver=true;
                        screen.stopScreen();
                    }
                    pezzoScelto=prossimoPezzo(schermo);        //Nuovo pezzo inizia a scendere
                }

                for(KeyStroke key : keyStrokes) {
                    if(!pezzoScelto.collisioneSotto()) {
                        screen.refresh();
                        processKeyInput(key);
                    }
                }

                if(brickDropTimer.getDropBrick()) {
                    screen.refresh();
                    pezzoScelto.scendi(campo);
                    datas = username + "/" + pezzoScelto.tipoPezzo + pezzoScelto.getCoord();
                    invia(datas, pw);
                }
            }

        } catch (IOException | InterruptedException e) {
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
            case 2 -> System.out.println("1 Riga spazzatura");
            case 3 -> System.out.println("2 Riga spazzatura");
            case 4 -> System.out.println("4 Riga spazzatura");
        }
    }
}
