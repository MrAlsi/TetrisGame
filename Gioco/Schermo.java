package com.company.Gioco;

import com.company.Tetris.shape.util.CollisionException;
import com.company.Tetris.shape.util.ShapeDrawer;
import com.company.Tetris.shape.util.ShapeFactory;
import com.company.Tetris.shapes.Shape;
import com.company.Tetris.thread.BrickDropper;
import com.company.Tetris.thread.KeyInputThread;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.util.Random;

public class Schermo {

    private int delay = 0;
    private Griglia campo;
    //private TextGraphics schermo;
    private PezzoLungo p1;
    private int xPos;
    private int yPos;



    private int brickDropDelay = 0;
    private Screen screen;
    //private ShapeFactory shapeFactory;
    //private Shape shape;


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

            //Ho creato un blocco chiamato Mago (il mago del blocco) per testare il codice
            //Ovviamente andrà tolto
            //BloccoPieno mago = (BloccoPieno) campo.griglia[x][y];
            //screen.refresh();

            KeyStroke keyStroke = screen.pollInput();
            //Sostituisco le decisioni dell'utente con un numero random
            Random rand = new Random();
            int scelta;

            boolean gameOver = false;

            // run game loop
            while(!gameOver) {

                    p1 = (PezzoLungo) creaPezzo(schermo, campo);
                    screen.refresh();

                    for (int i = 0; i < 24; i++) {
                        List<KeyStroke> keyStrokes = keyInput.getKeyStrokes();

                        for(KeyStroke key : keyStrokes) {
                            if(!p1.collisioneSotto()) {
                                screen.refresh();
                                processKeyInput(key);
                            }
                        }


                        if(brickDropTimer.getDropBrick()) {
                            p1.scendi(campo);
                            screen.refresh();
                            System.out.println("Scendi");
                        }
                    }

            }


                    //campo.cancellaRiga();



/*
            PROVA GIOCO
            while(true){
                x=4;
                y=0;
                BloccoPieno mago = new BloccoPieno(schermo, campo.griglia[x][y].getPosizioneColonna(), campo.griglia[x][y].getPosizioneRiga());
                System.out.println("Nuovo ciclo");
                while(y<7){
                    scelta = rand.nextInt(6);
                    System.out.println(scelta);
                    switch(scelta){
                        case 0: //Vai a destra
                            if(mago.collisioneLaterale(campo, x,y,1)==true){
                                mago.muovi(campo, x, y, 1, 0);
                                screen.refresh();
                                x++;
                                System.out.println("Destra");
                                break;
                            } else {
                                break;
                            }

                        case 1: //Vai a sinistra
                            if(mago.collisioneLaterale(campo, x,y,1)==true){
                                mago.muovi(campo, x, y, -1, 0);
                                screen.refresh();
                                x--;
                                System.out.println("Sinistra");
                                break;
                            } else {
                                break;
                            }

                        default: //Vai giù
                            if(mago.collisioneSotto(campo, x, y)==true) {
                                mago.muovi(campo, x, y, 0, 1);
                                screen.refresh();
                                y++;
                                System.out.println("Giù");
                                break;
                            } else {
                                campo.griglia[x][y]=new BloccoStruttura(schermo, campo.griglia[x][y].getPosizioneColonna(), campo.griglia[x][y].getPosizioneRiga());
                                screen.refresh();
                                System.out.println("Y. " + y);
                                y++;
                                break;
                            }
                    }
                    Thread.sleep(600);
                }
            }


        //screen.stopScreen();*/
        } catch (IOException | CollisionException e) {
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

    public static Pezzo creaPezzo(TextGraphics schermo, Griglia campo) {
        PezzoLungo p1 = new PezzoLungo(
                campo,
                new BloccoPieno(schermo, 3, 0),
                new BloccoPieno(schermo, 4, 0),
                new BloccoPieno(schermo, 5, 0),
                new BloccoPieno(schermo, 6, 0)
        );

        return p1;
    }

    private void processKeyInput(KeyStroke key) throws CollisionException,
            IOException {
        // drop
        Character c1 = ' ';
        Character c2 = 'z';
        Character c3 = 'x';

        if(c1.equals(key.getCharacter())) {
            while(!p1.collisioneSotto()){
                BloccoPieno.velocita = 0;
                System.out.println("In fondo");
                p1.scendi(campo);
            }
            BloccoPieno.velocita = 100;
            screen.refresh();
            return;
        }

        // down
        if(key.getKeyType().equals(KeyType.ArrowDown)) {
            yPos++;
            return;
        }

        // left
        if(key.getKeyType().equals(KeyType.ArrowLeft)) {
            //if(p1.getRiga())
            if(!p1.collisioneStruttura()) {
                System.out.println("Vai a sinistra");
                p1.muovi(campo, -1);
                screen.refresh();
            }
            return;
        }

        // right
        if(key.getKeyType().equals(KeyType.ArrowRight)) {
            if(!p1.collisioneStruttura()) {
                System.out.println("Vai a destra");
                p1.muovi(campo, 1);
                screen.refresh();
            }

            return;
        }

        // rotate left
        if(c2.equals(key.getCharacter())) {

            return;
        }

        // rotate right
        if(c3.equals(key.getCharacter())) {

            return;
        }
    }
}
