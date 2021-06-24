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

public class Main {

    public static void main(String[] args) throws InterruptedException {
        try {
            //Creazione del terminale
            final int COLS = 180;
            final int ROWS = 50;

            DefaultTerminalFactory defaultTerminalFactory = new DefaultTerminalFactory();

            defaultTerminalFactory.setInitialTerminalSize(new TerminalSize(COLS, ROWS));

            Terminal terminal = defaultTerminalFactory.createTerminal();

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
    // PROVA UNO
            KeyStroke keyStroke;
            //KeyStroke key = screen.pollInput();
            while(true) {
                keyStroke = screen.pollInput();
                if (keyStroke.equals(KeyType.ArrowDown)) {
                    break;
                }
            }
            
    //PROVA DUE
/*
            KeyInputThread keyInput = new KeyInputThread((TerminalScreen) screen);
            keyInput.start();

            List<KeyStroke> keyStrokes = keyInput.getKeyStrokes();
            for(KeyStroke key : keyStrokes) {
                Character c1 = ' ';
                Character c2 = 'z';
                Character c3 = 'x';

                if(c1.equals(key.getCharacter())) {

                }

                // down
                if(key.getKeyType().equals(KeyType.ArrowDown)) {
                    System.out.println("giu");
                }

                // left
                if(key.getKeyType().equals(KeyType.ArrowLeft)) {
                    System.out.println("sinistra");

                }

                // right
                if(key.getKeyType().equals(KeyType.ArrowRight)) {

                }

                // rotate left
                if(c2.equals(key.getCharacter())) {

                }

                // rotate right
                if(c3.equals(key.getCharacter())) {

                }

            }
            
    //PROVA TRE
 */
        /*    PezzoLungo p1 = (PezzoLungo) creaPezzo(schermo, campo);
            Character c1 = ' ';
            Character c2 = 'z';
            Character c3 = 'x';
            while (true) {


                KeyType a = key.getKeyType();

                if (c1.equals(key.getCharacter())) {

                }


                // down
                if (a.equals(KeyType.ArrowDown)) {
                    System.out.println("B");
                }

                // left
                if (a.equals(KeyType.ArrowLeft)) {
                    System.out.println("A");
                }

                // right
                if (key.getKeyType().equals(KeyType.ArrowRight)) {
                    p1.muovi(campo, 1);
                    screen.refresh();
                    System.out.println("Vai a destra");
                }

                // rotate left
                if (c2.equals(key.getCharacter())) {

                }

                // rotate right
                if (c3.equals(key.getCharacter())) {

                }
            }
*/
            //PROVA ROTAZIONE
            //Sostituisco le decisioni dell'utente con un numero random

            //PezzoLungo p1 = (PezzoLungo) creaPezzo(schermo, campo);
            /*screen.refresh();
            p1.scendi(campo);
            screen.refresh();
            p1.scendi(campo);
            screen.refresh();
            p1.scendi(campo);
            screen.refresh();
            p1.scendi(campo);
            screen.refresh();
            Thread.sleep(800);
            p1.FormaDue(schermo, p1.pezzo[1].colonnaGriglia, p1.pezzo[1].rigaGriglia);
            screen.refresh();
            Thread.sleep(10);
            /*p1.FormaUno(schermo,p1.pezzo[0].colonnaGriglia, p1.pezzo[0].rigaGriglia);
            screen.refresh();
            Thread.sleep(500);
            p1.FormaDue(schermo,p1.pezzo[1].colonnaGriglia, p1.pezzo[1].rigaGriglia);
            screen.refresh();
            Thread.sleep(500);

            p1.scendi(campo);
            screen.refresh();
            p1.scendi(campo);
            screen.refresh();
            /*p1.scendi(campo);
            screen.refresh();
            p1.scendi(campo);
            screen.refresh();*/


            //RIEMPI BLOCCHI A CASO


            //int x = rand1.nextInt();

/*
            for(int i=0; i<12; i++){
                campo.griglia[i][0] = new BloccoStruttura(schermo, i, 0);
                screen.refresh();
                Thread.sleep(50);
            }


            campo.cancellaRiga();
            screen.refresh();

*/


    //PROVA GIOCO
        /*
            Random rand = new Random();
            int scelta;
            do {
                PezzoLungo p1 = (PezzoLungo) creaPezzo(schermo, campo);
                screen.refresh();
                for (int i = 0; i < 24;) {
                    scelta=rand.nextInt(3);
                   if(scelta==0) {

                    } else if(scelta==1) {
                        p1.muovi(campo, -1);
                        screen.refresh();
                        System.out.println("Vai a sinistra");
                    }   else {
                        p1.scendi(campo);
                        System.out.println("Scende sto cazzo di blocco");
                        screen.refresh();
                        i++;
                    }


                }
               campo.cancellaRiga();
            } while (true);
*/
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
