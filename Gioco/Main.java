import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;
import java.util.Random;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        try {
            //Creazione del terminale
            Terminal terminal = new DefaultTerminalFactory().createTerminal();

            BasicWindow window = new BasicWindow();
            window.setFixedSize((new TerminalSize(100, 50)));

            Screen screen = new TerminalScreen(terminal);
            screen.startScreen();

            TextGraphics schermo = screen.newTextGraphics();

            //Creazione del campo da gioco

            Griglia campo = new Griglia(schermo);
            campo.creaCampo();
            screen.refresh();

            //Gioco

            //Cordinate nella griglia
            int x = 4;    //Colonne  |||
            int y = 0;    //Righe    ===

            //Ho creato un blocco chiamato Mago (il mago del blocco) per testare il codice
            //Ovviamente andrà tolto
            //BloccoPieno mago = (BloccoPieno) campo.griglia[x][y];
            //screen.refresh();

            KeyStroke keyStroke = screen.pollInput();
            //Sostituisco le decisioni dell'utente con un numero random
            Random rand = new Random();
            int scelta;

            do {
                PezzoLungo p1 = (PezzoLungo) creaPezzo(schermo, campo);
                screen.refresh();
                for (int i = 0; i < 7; i++) {
                    p1.scendi(campo, 0, 1);
                    System.out.println("Scende sto cazzo di blocco");
                    screen.refresh();
                }
            } while (true);

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
}
