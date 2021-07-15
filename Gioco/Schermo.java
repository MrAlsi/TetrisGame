package com.company.Gioco;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;
import java.util.List;
import java.util.Random;

public class Schermo {

    public int delay = 1000 / 60;
    private Griglia campo;
    private Pezzo pezzo[] = new Pezzo[7];
    private Pezzo pezzoScelto;
    private int index = 0;
    Random sceltaPezzo = new Random();

    private int brickDropDelay = 1000;
    private Screen screen;

    public Schermo() throws IOException {

    }

    public void run() throws IOException {
        try {

            final int COLS = 50;
            final int ROWS = 50;

            DefaultTerminalFactory defaultTerminalFactory = new DefaultTerminalFactory();
            defaultTerminalFactory.setInitialTerminalSize(new TerminalSize(COLS, ROWS));
            Terminal terminal = defaultTerminalFactory.createTerminal();
            BasicWindow window = new BasicWindow();
            window.setFixedSize((new TerminalSize(100, 50)));

            screen = new TerminalScreen(terminal);
            screen.startScreen();

            //Creazione del campo da gioco
            TextGraphics schermo = screen.newTextGraphics();

            campo = new Griglia(schermo);
            campo.creaCampo();
            screen.refresh();

            //Gioco

            //Cordinate nella griglia
            int x = 4;    //Colonne  |||
            int y = 0;    //Righe    ===

            // start brick move treads
            CadutaBlocco brickDropTimer = new CadutaBlocco();
            brickDropTimer.setDelay(brickDropDelay);
            brickDropTimer.start();

            InputKey keyInput = new InputKey((TerminalScreen) screen);
            keyInput.start();

            pezzoScelto=prossimoPezzo(schermo);

            boolean gameOver = false;
            // run game loop
            while(!gameOver) {

                Thread.sleep(delay);
                campo.eliminaRiga();
                screen.refresh();
                List<KeyStroke> keyStrokes = keyInput.getKeyStrokes();

                if(pezzoScelto.collisioneSotto()){
                    pezzoScelto.setStruttura();
                    screen.refresh();
                    pezzoScelto=prossimoPezzo(schermo);
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
                }
            }
        //screen.stopScreen();*/
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

        //down totale
        if(c1.equals(key.getCharacter())) {
            while(!pezzoScelto.collisioneSotto()){
                System.out.println("In fondo");
                pezzoScelto.scendi(campo);
            }
            screen.refresh();
            return;
        }

        // down
        if(key.getKeyType().equals(KeyType.ArrowDown)) {
            if(!pezzoScelto.collisioneSotto()){
                System.out.println("Scendi");
                pezzoScelto.scendi(campo);
                screen.refresh();
            }

            return;
        }

        // left
        if(key.getKeyType().equals(KeyType.ArrowLeft)) {
            //if(p1.getRiga())
            if(!pezzoScelto.collisioneLaterale(-1)) {
                System.out.println("Vai a sinistra");
                pezzoScelto.muovi(campo, -1);
            }
            return;
        }

        // right
        if(key.getKeyType().equals(KeyType.ArrowRight)) {
            if(!pezzoScelto.collisioneLaterale(1)) {
                System.out.println("Vai a destra");
                pezzoScelto.muovi(campo, 1);
            }

            return;
        }

        // rotate left
        if(c2.equals(key.getCharacter())) {

            return;
        }

        // rotate right
        if(c3.equals(key.getCharacter())) {
            pezzoScelto.ruota(campo);
            return;
        }
    }

}
