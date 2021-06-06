import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.awt.*;
import java.io.IOException;

public class Main {

    public static void main(String[] args){
       //Creazione del terminale
        try{
            Terminal terminal=new DefaultTerminalFactory().createTerminal();
            Panel panel = new Panel();
           // panel.setPreferredSize(new TerminalSize(40,30));
            //Quello che devo risolvere è:
            //Inizializzare il terminale con una "size" e tenerla fissa
            // terminal.addResizeListener();
            Screen screen = new TerminalScreen(terminal);
            screen.startScreen();
            TextGraphics schermo=screen.newTextGraphics();


            Griglia campo = new Griglia(schermo);
            campo.CreazioneCampo();
            screen.refresh();


            //Loop del gioco

            campo.griglia[1][0]=new BloccoPieno(schermo, campo.griglia[1][0].getPosizioneColonna(), campo.griglia[1][0].getPosizioneRiga());
            BloccoPieno mago= (BloccoPieno) campo.griglia[1][0];
            //Var temp
            int a=1;
            int b=7;
            campo.griglia[a][b]=new BloccoStruttura(schermo, campo.griglia[a][b].getPosizioneColonna(), campo.griglia[a][b].getPosizioneRiga());
            System.out.println(campo.griglia[a][b].getPosizioneRiga()+ "  -   "+ campo.griglia[a][b].getPosizioneColonna());

            screen.refresh();
            System.out.println(campo.griglia[a][b].getStato());


            //Serve essere moltiplicata per i, indice del for, e capire bene l'altezza del blocco
            int risultatoColonna=0;
            while(true){
                for(int i=0; i<7; i++){
                    //Spostamento

                    //Gravità
                    mago.gravita(campo, 1, i);
                    System.out.println();
                    screen.refresh();
                }
                mago.aumentaVelocita();

                if(risultatoColonna==200){
                    break;
                }
            }



            screen.refresh();
            //screen.stopScreen();
        } catch (IOException e) {
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
