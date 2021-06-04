package com.company;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;

import com.googlecode.lanterna.*;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.GridLayout;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.awt.*;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

import static com.googlecode.lanterna.TextColor.ANSI.BLACK;
import static com.googlecode.lanterna.TextColor.ANSI.BLUE;

public class MainSchermata {
    public static void main(String[] args) throws IOException {


        //codice per avere uno chermo
        Terminal terminal = new DefaultTerminalFactory().createTerminal();
        final Screen screen = new TerminalScreen(terminal);
        screen.startScreen();


        // Creo pannello
        final Panel panel = new Panel();
        panel.setLayoutManager(new GridLayout(1));
        panel.setFillColorOverride(BLACK);
        panel.addComponent(new EmptySpace(new TerminalSize(0,0))); // Empty space underneath labels
        //richiamo metodo per avviare la grafica
        Schermata(panel);


        BasicWindow window = new BasicWindow();
        window.setComponent(panel);


        MultiWindowTextGUI gui = new MultiWindowTextGUI(screen, new DefaultWindowManager(), new EmptySpace(BLACK));
        gui.addWindowAndWait(window);
    }
    //codice scritta bella colorata tetris
    public static void Tetris(Panel panel){
        Label tetris1=new Label("\n ______  ____ ______ ____  __  __ \n").setBackgroundColor(BLACK).setForegroundColor
                (TextColor.ANSI.RED);
        Label tetris2=new Label(" | || | ||    | || | || \\\\ || (( \\\n" ).setBackgroundColor(BLACK).setForegroundColor
                (TextColor.ANSI.RED_BRIGHT);
        Label tetris3=new Label("   ||   ||==    ||   ||_// ||  \\\\ \n").setBackgroundColor(BLACK).setForegroundColor
                (TextColor.ANSI.GREEN_BRIGHT);
        Label tetris4=new Label("   ||   ||___   ||   || \\\\ || \\_))\n").setBackgroundColor(BLACK).setForegroundColor
                (TextColor.ANSI.BLUE_BRIGHT);

        panel.addComponent(tetris1);
        panel.addComponent(tetris2);
        panel.addComponent(tetris3);
        panel.addComponent(tetris4);
    }

    //codice home
    public static void Schermata(final Panel panel){
        final TextColor coloreLabel=TextColor.ANSI.GREEN_BRIGHT;
        Label testo=new Label("Benvenuto in:\n").setBackgroundColor(BLACK).setForegroundColor(coloreLabel);
        panel.addComponent(testo);

        Tetris(panel);

        Label lab=new Label("Vuoi accedere come Server o Client?\n").setBackgroundColor(BLACK).setForegroundColor(
                coloreLabel);
        panel.addComponent(lab);


        //bottone client
        new Button("Client", new Runnable() {
            @Override
            public void run() {
                panel.removeAllComponents();
                panel.setFillColorOverride(BLACK);
                //solita scrittina bellina
                Tetris(panel);

                //registrazione utente
                Label user=new Label("\nName: ").setBackgroundColor(BLACK).setForegroundColor(coloreLabel);
                TextBox textUser=new TextBox();// nome nuovo client
                Label IP=new Label("\nIndirizzo IP Server: ").setBackgroundColor(BLACK).setForegroundColor(coloreLabel);
                TextBox textIP=new TextBox();//indirizzo IP del server a cui voglio collegarmi

                panel.addComponent(user);
                panel.addComponent(textUser);
                panel.addComponent(IP);
                panel.addComponent(textIP);

                //bottone che mi riporta alla home
                new Button("Indietro",new Runnable(){
                    @Override
                    public void run(){
                        panel.removeAllComponents();
                        panel.setFillColorOverride(BLACK);
                        Schermata(panel);
                    }
                }).addTo(panel);

            }
        }).addTo(panel);

        //bottone per accedere come server
        new Button("Server", new Runnable() {

            public void run() {

              //  System.out.println("sto eseguendo");
                panel.removeAllComponents();
                panel.setFillColorOverride(BLACK);
                //metodo per la scritta tetris
                Tetris(panel);

                //registro il nome del server


                Label user = new Label("\nName: ").setBackgroundColor(BLACK).setForegroundColor(coloreLabel);
                final TextBox textUser = new TextBox();
                panel.addComponent(user);
                panel.addComponent(textUser);

                new Button("Avvia Server",new Runnable(){
                        @Override
                        public void run(){
                            String name = textUser.getText();
                            panel.removeAllComponents();
                            panel.setFillColorOverride(BLACK);
                            //dovrei chiamare il codice del server

                            Server server = new Server(name,panel,coloreLabel);
                            server.StartServer(server);

                        }
                    }).addTo(panel);



                //System.out.println(" ciao");
                //qui ci proviamo a mettere un bel semaforino
                //server


                //bottone per tornare alla home
                new Button("Indietro",new Runnable(){
                    @Override
                    public void run(){
                        panel.removeAllComponents();
                        panel.setFillColorOverride(BLACK);
                        Schermata(panel);
                    }
                }).addTo(panel);

            }
        }).addTo(panel);
        Label felice=new Label("O vuoi farci felici e leggere chi siano i creatori del gioco? ;)\n").setBackgroundColor
                (BLACK).setForegroundColor(coloreLabel);
        panel.addComponent(felice);
        //bottone per accedere al regolamento e ai crediti(sarebbe carino che se un client sta guardando il regolamento
        // il server non possa avviare il gioco)
        new Button("Crediti e regolamento", new Runnable(){
            @Override
            public void run() {
                panel.removeAllComponents();
                panel.setFillColorOverride(BLACK);
                Label regolamento=new Label("Ciao, queste sono le regole del nostro gioco:\n Si gioca da 2 fino a " +
                        "un massimo di 8 giocatori,...").setBackgroundColor(BLACK).setForegroundColor(coloreLabel);
                Label crediti=new Label("\n Questo gioco è stato realizzato da:\n Gabriel Riccardo Alsina,\n " +
                        "Carlotta Carboni,\n Luca Palmieri,\n Alssandro Pasi.").setBackgroundColor(BLACK).setForegroundColor
                        (coloreLabel);
                panel.addComponent(regolamento);
                panel.addComponent(crediti);
                //bottone per tornare alla home
                new Button("Indietro",new Runnable(){
                    @Override
                    public void run(){
                        panel.removeAllComponents();
                        panel.setFillColorOverride(BLACK);
                        Schermata(panel);
                    }
                }).addTo(panel);
            }

        }).addTo(panel);

    }

}
