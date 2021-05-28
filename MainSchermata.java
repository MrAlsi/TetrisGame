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
        Label tetris1=new Label("\n ______  ____ ______ ____  __  __ \n").setBackgroundColor(BLACK).setForegroundColor(TextColor.ANSI.RED);
        Label tetris2=new Label(" | || | ||    | || | || \\\\ || (( \\\n" ).setBackgroundColor(BLACK).setForegroundColor(TextColor.ANSI.RED_BRIGHT);
        Label tetris3=new Label("   ||   ||==    ||   ||_// ||  \\\\ \n").setBackgroundColor(BLACK).setForegroundColor(TextColor.ANSI.GREEN_BRIGHT);
        Label tetris4=new Label("   ||   ||___   ||   || \\\\ || \\_))\n").setBackgroundColor(BLACK).setForegroundColor(TextColor.ANSI.BLUE_BRIGHT);

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
        //panel.addComponent(new Label("Benvenuto in:\n"));
        //richiamo scritta bella
        Tetris(panel);

        Label lab=new Label("Vuoi accedere come Server o Client?\n").setBackgroundColor(BLACK).setForegroundColor(coloreLabel);
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
        //bottone per accedeere come server
        new Button("Server", new Runnable() {
            @Override
            public void run() {
                panel.removeAllComponents();
                panel.setFillColorOverride(BLACK);
                //metodo per la scritta tetris
                Tetris(panel);

                //registro il nome del server
                Label user=new Label("\nName: ").setBackgroundColor(BLACK).setForegroundColor(coloreLabel);
                TextBox textUser=new TextBox();
                panel.addComponent(user);
                panel.addComponent(textUser);
                System.out.println(textUser.getText());
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
