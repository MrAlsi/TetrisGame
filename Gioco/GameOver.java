package com.company.Gioco;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.util.Arrays;

import static com.googlecode.lanterna.TextColor.ANSI.BLACK;
//classe per la schermata di inizializzazione del gioco
public class GameOver implements Runnable{
    public GameOver(){

    }
    @Override
    public void run() {


        try {
            final int COLS = 50;
            final int ROWS = 10;
            DefaultTerminalFactory defaultTerminalFactory = new DefaultTerminalFactory();
            defaultTerminalFactory.setInitialTerminalSize(new

                    TerminalSize(COLS, ROWS));
            Terminal terminal = defaultTerminalFactory.createTerminal();
            BasicWindow window = new BasicWindow();
            window.setFixedSize((new

                    TerminalSize(45, 5)));
            //Terminal terminal = new DefaultTerminalFactory().createTerminal();
            final Screen screen = new TerminalScreen(terminal);
            screen.startScreen();


            // Creo pannello
            final Panel panel = new Panel();
     /*  panel.setLayoutManager(
                new GridLayout(1)
                        .setLeftMarginSize(leftMargin)
                        .setRightMarginSize(0)
                        .setTopMarginSize(topMargin)); */
            panel.setFillColorOverride(BLACK);

            //  panel.addComponent(new EmptySpace(new TerminalSize(0,0))); // Empty space underneath labels
            // richiamo metodo per avviare la grafica
            Schermata(panel);


            BasicWindow windoww = new BasicWindow();
            // importante
            windoww.setHints(Arrays.asList(Window.Hint.FULL_SCREEN));
            //window.setFixedSize((new TerminalSize(10,20)));

            window.setComponent(panel);


            MultiWindowTextGUI gui = new MultiWindowTextGUI(screen, new DefaultWindowManager(), new EmptySpace(BLACK));
            gui.addWindowAndWait(window);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //codice scritta colorata gameover
    public static void GameOver(Panel panel) {
       /* Label gameover1=new Label("\n   ___   ___  ___  ___  ____      ___   __ __  ____ ____ \n").setBackgroundColor(BLACK).setForegroundColor
                (TextColor.ANSI.RED);
        Label gameover2=new Label("  // \\\\ // \\\\ ||\\\\//|| ||        // \\\\  || || ||    || \\\\\n" ).setBackgroundColor(BLACK).setForegroundColor
                (TextColor.ANSI.RED_BRIGHT);
        Label gameover3=new Label(" (( ___ ||=|| || \\/ || ||==     ((   )) \\\\ // ||==  ||_// \n").setBackgroundColor(BLACK).setForegroundColor
                (TextColor.ANSI.GREEN_BRIGHT);
        Label gameover4=new Label("  \\\\_|| || || ||    || ||___     \\\\_//   \\V/  ||___ || \\\\\n").setBackgroundColor(BLACK).setForegroundColor
                (TextColor.ANSI.BLUE_BRIGHT);*/
        Label gameover1 = new Label("\n  ____ ____ _  _ ____    ____ _  _ ____ ____  \n").setBackgroundColor(BLACK).setForegroundColor
                (TextColor.ANSI.RED);
        Label gameover2 = new Label("  | __ |__| |\\/| |___    |  | |  | |___ |__/ \n").setBackgroundColor(BLACK).setForegroundColor
                (TextColor.ANSI.RED_BRIGHT);
        Label gameover3 = new Label("  |__] |  | |  | |___    |__|  \\/  |___ |  \\ \n").setBackgroundColor(BLACK).setForegroundColor
                (TextColor.ANSI.GREEN_BRIGHT);


        panel.addComponent(gameover1);
        panel.addComponent(gameover2);
        panel.addComponent(gameover3);
        //  panel.addComponent(gameover4);
    }

    // separatore di dimensioni pari a size
    public static void Empty(Panel panel, int size) {
        panel.addComponent(new EmptySpace(new TerminalSize(0, size)));
    }

    //codice home
    public static void Schermata(final Panel panel) {
        final TextColor coloreLabel = TextColor.ANSI.GREEN_BRIGHT;

        GameOver(panel);


    }
}
