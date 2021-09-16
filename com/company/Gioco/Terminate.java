package com.company.Gioco;

import com.company.MainSchermata;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.GridLayout;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.Window;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

import static com.googlecode.lanterna.TextColor.ANSI.BLACK;
import static com.googlecode.lanterna.TextColor.ANSI.WHITE_BRIGHT;

//classe per la schermata di inizializzazione del gioco
public class Terminate implements Runnable{

    private String username;
    private String IP;
    private int PORT;
    private Panel panel;
    private TextColor coloreLabel;
    public static Screen screen;
    public static Boolean nextGame = false;
    private List<String> connectedClients;

    public Terminate(Panel panel, TextColor coloreLabel) {
        this.panel = panel;
        this.coloreLabel = coloreLabel;
    }

    @Override
    public void run() {

        try {
            Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
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
            defaultTerminalFactory.setInitialTerminalSize(new TerminalSize(COLS, ROWS));
            Terminal terminal = defaultTerminalFactory.createTerminal();

            screen = new TerminalScreen(terminal);
            screen.startScreen();

            // Creo pannello
            panel.setLayoutManager(
                    new GridLayout(1)
                            .setLeftMarginSize(leftMargin)
                            .setRightMarginSize(0)
                            .setTopMarginSize(topMargin));
            panel.setFillColorOverride(BLACK);

            panel.addComponent(new EmptySpace(new TerminalSize(0,0)));

            //  panel.addComponent(new EmptySpace(new TerminalSize(0,0))); // Empty space underneath labels
            // richiamo metodo per avviare la grafica
            Schermata(panel);


            BasicWindow window = new BasicWindow();
            // importante
            window.setHints(Arrays.asList(Window.Hint.FULL_SCREEN));
            //window.setFixedSize((new TerminalSize(10,20)));

            window.setComponent(panel);

            MultiWindowTextGUI gui = new MultiWindowTextGUI(screen, new DefaultWindowManager(), new EmptySpace(BLACK));
            gui.addWindowAndWait(window);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //codice scritta colorata you win
    public void termina(Panel panel){
        panel.removeAllComponents();
        panel.setFillColorOverride(BLACK);
        Label nome=new Label("\nNickname già in utilizzo da un altro utente.\n").setBackgroundColor(BLACK).setForegroundColor(WHITE_BRIGHT);
        panel.addComponent(nome);

        MainSchermata.Empty(panel, 1);

        new Button("Indietro",new Runnable(){
            @Override
            public void run(){
                //svuoto la schermo
                panel.removeAllComponents();
                panel.setFillColorOverride(BLACK);
                //richiamo schermata inziale
                MainSchermata.Schermata(panel);
                Thread.currentThread().interrupt();
            }
        }).addTo(panel);
    }

    // separatore di dimensioni pari a size
    public static void Empty(Panel panel, int size){
        panel.addComponent(new EmptySpace(new TerminalSize(0,size)));
    }

    //codice home
    public void Schermata(final Panel panel){

        final TextColor coloreLabel=TextColor.ANSI.GREEN_BRIGHT;

        termina(panel);
    }
}
