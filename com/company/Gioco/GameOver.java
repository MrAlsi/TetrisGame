package com.company.Gioco;

import com.company.MainSchermata;
import com.company.client.Client;
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
import java.util.LinkedList;
import java.util.List;

import static com.googlecode.lanterna.TextColor.ANSI.BLACK;
//classe per la schermata di inizializzazione del gioco
public class GameOver implements Runnable{

    private String username;
    private String IP;
    private int PORT;
    private Panel panel;
    private TextColor coloreLabel;
    public static Screen screen;
    public static Boolean nextGame = false;
    private List<String> connectedClients;

    public GameOver(String name, String IP, int PORT, Panel panel, TextColor coloreLabel, List<String> connectedClients){
        this.IP = IP;
        this.PORT = PORT;
        this.panel = panel;
        this.coloreLabel = coloreLabel;
        this.connectedClients=connectedClients;
        username = name;

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

            // Check che serve al client per proseguire la sconfitta
            Client.loseCheck = false;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //codice scritta colorata gameover
    public void GameOver(Panel panel) {
        Label gameover1 = new Label("\n  ____ ____ _  _ ____    ____ _  _ ____ ____  \n").setBackgroundColor(BLACK).setForegroundColor
                (TextColor.ANSI.RED);
        Label gameover2 = new Label("  | __ |__| |\\/| |___    |  | |  | |___ |__/ \n").setBackgroundColor(BLACK).setForegroundColor
                (TextColor.ANSI.RED_BRIGHT);
        Label gameover3 = new Label("  |__] |  | |  | |___    |__|  \\/  |___ |  \\ \n").setBackgroundColor(BLACK).setForegroundColor
                (TextColor.ANSI.GREEN_BRIGHT);


        panel.addComponent(gameover1);
        panel.addComponent(gameover2);
        panel.addComponent(gameover3);

        MainSchermata.Empty(panel, 2);

        new Button("Play again",new Runnable(){
            @Override
            public void run(){
                //svuoto la schermo
                //panel.removeAllComponents();
                panel.setFillColorOverride(BLACK);
                nextGame = true;
                //richiamo schermata inziale
                Client client=new Client(username, IP, String.valueOf(PORT), panel, coloreLabel, new LinkedList<>());
                MainSchermata.clientThread = new Thread(client);
                MainSchermata.clientThread.start();
            }
        }).addTo(panel);
        Empty(panel, 1);
        new Button("Close", new Runnable() {
            @Override
            public void run() {
                try {
                    System.exit(0);
                    panel.removeAllComponents();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).addTo(panel);
        Empty(panel, 1);
        new Button("Esci dal server", new Runnable() {
            @Override
            public void run() {
                //shown = false;
                try {
                    panel.removeAllComponents();
                    MainSchermata.Schermata(panel);
                    nextGame = true;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).addTo(panel);
    }

    // separatore di dimensioni pari a size
    public static void Empty(Panel panel, int size) {
        panel.addComponent(new EmptySpace(new TerminalSize(0, size)));
    }

    //codice home
    public void Schermata(final Panel panel) {
        final TextColor coloreLabel = TextColor.ANSI.GREEN_BRIGHT;

        GameOver(panel);


    }
}
