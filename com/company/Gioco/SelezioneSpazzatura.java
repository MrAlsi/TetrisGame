package com.company.Gioco;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.GridLayout;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.Window;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.awt.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static com.googlecode.lanterna.TextColor.ANSI.BLACK;

public class SelezioneSpazzatura implements Runnable {
    private HashMap<String, PrintWriter> connectedClients;
    private String nome;

    public SelezioneSpazzatura(HashMap connectedClient, String nome) {
        this.connectedClients = connectedClients;
        this.nome = nome;
    }

    @Override
    public void run() {


        try {
            //@todo
            //passare connectedclients
            //passare numero giocatori
            //devo sapere chi sono(almeno il mio nome)
            //almeno un nome deve essere preselezionato
            //i bottoni sono sempre attivi
            //quando si genera la linea spazzatura automaticamente legge l'ultimo bottone selezionato


            final TextColor coloreLabel = TextColor.ANSI.GREEN_BRIGHT;

            final int COLS = 500 / 8;
            final int ROWS = 120 / 16;
            DefaultTerminalFactory defaultTerminalFactory = new DefaultTerminalFactory();
            defaultTerminalFactory.setInitialTerminalSize(new TerminalSize(COLS, ROWS));
            Terminal terminal = defaultTerminalFactory.createTerminal();
            TerminalPosition terminalPosition = new TerminalPosition(10, 20);

            final Screen screen = new TerminalScreen(terminal);
            screen.startScreen();
            final Panel pannello = new Panel();
            pannello.setLayoutManager(
                    new GridLayout(4));
            pannello.setFillColorOverride(BLACK);
            Label presentazione = new Label("Selezione il giocatore a cui mandare righe spazzatura:\n").setBackgroundColor(BLACK)
                    .setForegroundColor(coloreLabel);
            //queste label sono fittizie mi servono solo per avere i bottoni sullo stesso livello
            Label spazio1 = new Label("");
            Label spazio2 = new Label("");
            Label spazio3 = new Label("");
            pannello.addComponent(spazio1);
            pannello.addComponent(spazio2);
            pannello.addComponent(spazio3);


            pannello.addComponent(presentazione);
            // salvo la dimensione di connectedClients
            //int dim = 4;
            int dim = connectedClients.size();
            //creo bottoni in base al numero dei giocatori
            for (String i : connectedClients.keySet()) {
                String name = String.valueOf(connectedClients.get(i));
                //controllo che il nome sia diverso dal mio per non avere il bottone con il mio nome
                if (!name.equals(nome)) {
                    //assegno ad ogni bottone il nome di un giocatore
                    new Button(name, new Runnable() {
                        @Override
                        public void run() {
                            //metodo per lanciare linee spazzatura
                            //devo prendere i valori del bottone cliccato e richiamare il metodo per mandare linee spazzatura
                            //Righespazzatura();
                            pannello.removeAllComponents();
                        }
                    }).addTo(pannello);

                }
            }

                BasicWindow window = new BasicWindow();
                window.setHints(Arrays.asList(Window.Hint.FULL_SCREEN));


                window.setComponent(pannello);
                MultiWindowTextGUI gui = new MultiWindowTextGUI(screen, new DefaultWindowManager(), new EmptySpace(BLACK));
                gui.addWindowAndWait(window);
        } catch(Exception e){
                e.printStackTrace();
        }


    }
}

