package com.company.Gioco;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;
import java.util.Arrays;

import static com.googlecode.lanterna.TextColor.ANSI.*;

//classe per la schermata di inizializzazione del gioco
public class YouWin implements Runnable{
    public YouWin() {
    }

    @Override
    public void run() {
        
        try {
        final int COLS = 42;
        final int ROWS = 18;
        DefaultTerminalFactory defaultTerminalFactory = new DefaultTerminalFactory();
        defaultTerminalFactory.setInitialTerminalSize(new TerminalSize(COLS,ROWS));
        Terminal terminal = defaultTerminalFactory.createTerminal();
        BasicWindow window = new BasicWindow();
        window.setFixedSize((new TerminalSize(38, 15)));
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
        panel.addComponent(new EmptySpace(new TerminalSize(0,0))); // Empty space underneath labels
        // richiamo metodo per avviare la grafica
        Schermata(panel);


        BasicWindow windowww = new BasicWindow();
        // importante
        windowww.setHints(Arrays.asList(Window.Hint.FULL_SCREEN));
        //window.setFixedSize((new TerminalSize(10,20)));

        window.setComponent(panel);



        MultiWindowTextGUI gui = new MultiWindowTextGUI(screen, new DefaultWindowManager(), new EmptySpace(BLACK));
        gui.addWindowAndWait(window);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //codice scritta colorata you win
    public static void provaWin(Panel panel){

        Label provaWin1=new Label("\n           '._==_==_=_.'      \n").setBackgroundColor(BLACK).setForegroundColor
                (TextColor.ANSI.YELLOW_BRIGHT);
        Label provaWin2=new Label("           .-\\:      /-.     \n").setBackgroundColor(BLACK).setForegroundColor
                (TextColor.ANSI.YELLOW_BRIGHT);
        Label provaWin3=new Label("          | (|:.     |) |     ").setBackgroundColor(BLACK).setForegroundColor
                (TextColor.ANSI.YELLOW_BRIGHT);
        Label provaWin4=new Label("           '-|:.     |-'     \n" ).setBackgroundColor(BLACK).setForegroundColor
                (TextColor.ANSI.YELLOW_BRIGHT);
        Label provaWin5=new Label("             \\::.    /     \n").setBackgroundColor(BLACK).setForegroundColor
                (TextColor.ANSI.YELLOW_BRIGHT);
        Label provaWin6=new Label("              '::. .' \n").setBackgroundColor(BLACK).setForegroundColor
                (TextColor.ANSI.YELLOW_BRIGHT);
        Label provaWin7=new Label("                ) ( \n").setBackgroundColor(BLACK).setForegroundColor
                (TextColor.ANSI.YELLOW_BRIGHT);
        Label provaWin8=new Label("              _.' '._ \n").setBackgroundColor(BLACK).setForegroundColor
                (TextColor.ANSI.YELLOW_BRIGHT);
        Label provaWin9=new Label("             `\"\"\"\"\"\"\"` \n").setBackgroundColor(BLACK).setForegroundColor
                (TextColor.ANSI.YELLOW_BRIGHT);
        Label provaWin10=new Label("    _   _ ____ _  _    _ _ _ _ _  _     \n").setBackgroundColor(BLACK).setForegroundColor
                (BLUE);
        Label provaWin11=new Label("     \\_/  |  | |  |    | | | | |\\ |   \n" ).setBackgroundColor(BLACK).setForegroundColor
                (BLUE_BRIGHT);
        Label provaWin12=new Label("      |   |__| |__|    |_|_| | | \\|     \n").setBackgroundColor(BLACK).setForegroundColor
                (GREEN_BRIGHT);

        panel.addComponent(provaWin1);
        panel.addComponent(provaWin2);
        panel.addComponent(provaWin3);
        panel.addComponent(provaWin4);
        panel.addComponent(provaWin5);
        panel.addComponent(provaWin6);
        panel.addComponent(provaWin7);
        panel.addComponent(provaWin8);
        panel.addComponent(provaWin9);
        panel.addComponent(provaWin10);
        panel.addComponent(provaWin11);
        panel.addComponent(provaWin12);
    }

    // separatore di dimensioni pari a size
    public static void Empty(Panel panel, int size){
        panel.addComponent(new EmptySpace(new TerminalSize(0,size)));
    }

    //codice home
    public static void Schermata(final Panel panel){
        final TextColor coloreLabel=TextColor.ANSI.GREEN_BRIGHT;

        provaWin(panel);
    }
}
