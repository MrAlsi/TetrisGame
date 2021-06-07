import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.DefaultWindowManager;
import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import javax.swing.*;
import javax.swing.plaf.basic.BasicSplitPaneUI;
import java.awt.*;
import java.awt.event.KeyListener;
import java.io.IOException;

public class Main {

    public static void main(String[] args){
        try{
            //Creazione del terminale
            Terminal terminal=new DefaultTerminalFactory().createTerminal();
            //Panel panel = new Panel();
           // panel.setPreferredSize(new TerminalSize(40,30));
            //Quello che devo risolvere è:
            //Inizializzare il terminale con una "size" e tenerla fissa
            // terminal.addResizeListener();
            //new TerminalSize(1000,100);

            BasicWindow window = new BasicWindow();
            window.setFixedSize((new TerminalSize(100,50)));

            Screen screen = new TerminalScreen(terminal);
            screen.startScreen();

/*
            MultiWindowTextGUI gui=new MultiWindowTextGUI(screen, new DefaultWindowManager());
            gui.addWindowAndWait(panel)
*/
            TextGraphics schermo=screen.newTextGraphics();

            //Creazione del campo da gioco

            Griglia campo = new Griglia(schermo);
            campo.CreazioneCampo();
            screen.refresh();

            //Gioco

            //Cordinate nella griglia
            int x=4;    //Colonne  |||
            int y=0;    //Righe    ===

            //Ho creato un blocco chiamato Mago (il mago del blocco) per testare il codice
            //Ovviamente andrà tolto
            campo.griglia[x][y]=new BloccoPieno(schermo, campo.griglia[x][y].getPosizioneColonna(), campo.griglia[x][y].getPosizioneRiga());
            BloccoPieno mago = (BloccoPieno) campo.griglia[x][y];
            screen.refresh();

            while(true){
                KeyStroke keyStroke = screen.pollInput();

               // x=mago.destra(campo, x, y);
                for(y=0; y<7; y++){

                    //Spostamento
                    Thread.sleep(100);

                    //Gravità
                    mago.gravita(campo, x, y);

                    screen.refresh();
                }
                if(x==200){
                    break;
                }
            }
            screen.refresh();
            //screen.stopScreen();
        } catch (IOException | InterruptedException e) {
            System.out.println("Problema con il terminale");
            e.printStackTrace();
        }


    }

    public void stampaGriglia(Griglia campo){
        for(int i=0; i<12; i++){
            for(int e=0; e<24; e++){
                System.out.print(campo.griglia[i][e].getStato()+"  ");
            }
            System.out.println("");
        }
    }
}
