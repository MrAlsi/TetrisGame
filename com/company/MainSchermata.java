package com.company;

import com.company.client.Client;
import com.company.server.Server;
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
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;

import static com.googlecode.lanterna.TextColor.ANSI.BLACK;
import static com.googlecode.lanterna.TextColor.ANSI.RED;

//classe per la schermata di inizializzazione del gioco
public class MainSchermata {

    public static Screen screen;
    public static Thread clientThread;
    private static Boolean erroreNome = false;
    private static Boolean erroreCaratteri = false;
    private static Boolean erroreIP = false;
    private static Boolean errorePORT = false;

    public static void main(String[] args) throws IOException {


        //organizzazione della schermata
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();

        System.out.println(size);
        System.out.println(size.getWidth());
        System.out.println(size.getHeight());

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
        defaultTerminalFactory.setInitialTerminalSize(new TerminalSize(COLS,ROWS));
        Terminal terminal = defaultTerminalFactory.createTerminal();

        //Terminal terminal = new DefaultTerminalFactory().createTerminal();
        //
        screen = new TerminalScreen(terminal);
        screen.startScreen();

        // Creo pannello
        final Panel panel = new Panel();
        panel.setLayoutManager(
                new GridLayout(1)
                        .setLeftMarginSize(leftMargin)
                        .setRightMarginSize(0)
                        .setTopMarginSize(topMargin));
        panel.setFillColorOverride(BLACK);

        panel.addComponent(new EmptySpace(new TerminalSize(0,0))); // Empty space underneath labels
        // richiamo metodo per avviare la grafica
        Schermata(panel);

        BasicWindow window = new BasicWindow();
        // importante
        window.setHints(Arrays.asList(Window.Hint.FULL_SCREEN));
        //window.setFixedSize((new TerminalSize(10,20)));

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

    // separatore di dimensioni pari a size
    public static void Empty(Panel panel, int size){
        panel.addComponent(new EmptySpace(new TerminalSize(0,size)));
    }

    //codice home
    public static void Schermata(final Panel panel){
        final TextColor coloreLabel=TextColor.ANSI.GREEN_BRIGHT;

        Tetris(panel);

        Empty(panel, 1);


        //bottone client
        new Button("Find Game", new Runnable() {
            @Override
            public void run() {
                //svuoto lo schermo
                panel.removeAllComponents();
                panel.setFillColorOverride(BLACK);
                //solita scrittina bellina
                Tetris(panel);

                //registrazione utente
                Label user=new Label("\ninserisci il tuo nome: ").setBackgroundColor(BLACK).setForegroundColor(coloreLabel);
                final TextBox textUserClient=new TextBox();// nome nuovo client
                Empty(panel, 1);
                Label IP=new Label("\nServer IP: ").setBackgroundColor(BLACK).setForegroundColor(coloreLabel);
                final TextBox textIP=new TextBox();//indirizzo IP del server a cui voglio collegarmi
                Label PORT=new Label("\nServer PORT: ").setBackgroundColor(BLACK).setForegroundColor(coloreLabel);
                final TextBox textPORT=new TextBox();//porta del server a cui voglio collegarmi

                panel.addComponent(user);
                panel.addComponent(textUserClient);
                panel.addComponent(IP);
                panel.addComponent(textIP);
                panel.addComponent(PORT);
                panel.addComponent(textPORT);

                Empty(panel, 1);
                //bottone per startare il codice del client
                new Button("Start Client",new Runnable(){
                    @Override
                    public void run(){
                        //passo alle variabili i contenuti delle textbox
                        String name = textUserClient.getText();
                        String IP = textIP.getText();
                        String PORT = textPORT.getText();
                        String str = " !#$%&'()*+,-./:;<=>?@[]^_`{|}~";
                        Boolean check = true;

                        char[] ch = new char[str.length()];

                        for (int i = 0; i < str.length(); i++) {
                            ch[i] = str.charAt(i);
                        }

                        for (char c : ch) {
                            String s = Character.toString(c);
                            if(name.contains(s) && !erroreCaratteri) {
                                erroreCaratteri = true;
                                Label invalidName = new Label("\nERRORE: Nickname non valido. Non utilizzare spazi o " +
                                        "caratteri speciali").setBackgroundColor(BLACK).setForegroundColor(RED);
                                panel.addComponent(invalidName);
                                check = false;
                                break;
                            }
                            if(name.contains(s)) {
                                erroreCaratteri = true;
                                check = false;
                                break;
                            }
                        }

                        if(name.equals("") && !erroreNome){
                            erroreNome = true;
                            Label nickNull = new Label("\nERRORE: Compilare il campo nome").setBackgroundColor(BLACK).setForegroundColor(RED);
                            panel.addComponent(nickNull);
                            check = false;
                        }

                        if(IP.equals("") && !erroreIP){
                            erroreIP = true;
                            Label ipNull = new Label("\nERRORE: Compilare il campo IP").setBackgroundColor(BLACK).setForegroundColor(RED);
                            panel.addComponent(ipNull);
                            check = false;
                        }

                        if(PORT.equals("") && !errorePORT){
                            errorePORT = true;
                            Label portNull = new Label("\nERRORE: Compilare il campo PORT").setBackgroundColor(BLACK).setForegroundColor(RED);
                            panel.addComponent(portNull);
                            check = false;
                        }

                        if(name.equals("") || IP.equals("") || PORT.equals("")){
                            check = false;
                        }

                        if(check) {
                            try {
                                panel.removeAllComponents();
                                panel.setFillColorOverride(BLACK);
                                //richiamo codice client
                                Client client = new Client(name, IP, PORT, panel, coloreLabel, new LinkedList<>());
                                clientThread = new Thread(client);
                                clientThread.start();

                            } catch (Exception ex) {
                                //ex.printStackTrace();
                                panel.removeAllComponents();
                                panel.setFillColorOverride(BLACK);
                                MainSchermata.Schermata(panel);
                            }
                        }
                    }
                }).addTo(panel);
                Empty(panel, 1);
                //bottone che mi riporta alla home
                new Button("Indietro",new Runnable(){
                    @Override
                    public void run(){
                        //svuoto la schermo
                        panel.removeAllComponents();
                        panel.setFillColorOverride(BLACK);
                        //richiamo schermata inziale
                        Schermata(panel);
                    }
                }).addTo(panel);

            }
        }).addTo(panel);

        Empty(panel, 1);

        //bottone per accedere come server
        new Button("Host Game", new Runnable() {

            public void run() {

                //  System.out.println("sto eseguendo");
                //svuoto lo schermo
                panel.removeAllComponents();
                panel.setFillColorOverride(BLACK);
                //metodo per la scritta tetris
                Tetris(panel);

                //registro il nome del server
                Label user = new Label("\nName: ").setBackgroundColor(BLACK).setForegroundColor(coloreLabel);
                final TextBox textUser = new TextBox();
                Label SERVERPORT=new Label("\nServer PORT: ").setBackgroundColor(BLACK).setForegroundColor(coloreLabel);
                final TextBox textSERVERPORT=new TextBox();//porta del server a cui voglio collegarmi

                panel.addComponent(user);
                panel.addComponent(textUser);
                panel.addComponent(SERVERPORT);
                panel.addComponent(textSERVERPORT);

                Empty(panel, 1);
                //bottone per strtare il server
                new Button("Start Server",new Runnable(){
                    @Override
                    public void run(){
                        try {
                            String name = textUser.getText();
                            String SERVERPORT = textSERVERPORT.getText();
                            //svuoto lo schermo
                            panel.removeAllComponents();
                            panel.setFillColorOverride(BLACK);
                            //richiamo il codice del server

                            Server server = new Server(name, SERVERPORT, panel, coloreLabel);
                            server.StartServer(server);
                        }catch (Exception ex) {
                            //ex.printStackTrace();
                            panel.removeAllComponents();
                            panel.setFillColorOverride(BLACK);
                            Label erroreserver=new Label("- - - Errore nella creazione del server: riprovare - - -"
                            ).setBackgroundColor(BLACK).setForegroundColor(RED);
                            panel.addComponent(erroreserver);
                            MainSchermata.Schermata(panel);

                        }
                    }
                }).addTo(panel);

                Empty(panel, 1);

                //bottone per tornare alla home
                new Button("Indietro",new Runnable(){
                    @Override
                    public void run(){
                        //svuoto lo schermo
                        panel.removeAllComponents();
                        panel.setFillColorOverride(BLACK);
                        //richiamo schermata iniziale
                        Schermata(panel);
                    }
                }).addTo(panel);

            }
        }).addTo(panel);

        Empty(panel, 2);

        Label separatore =new Label("~^~^~^~^~^~^~^~^~^~^~^~^~^~^~^~^~^~^~").setBackgroundColor(BLACK).setForegroundColor(TextColor.ANSI.WHITE);
        panel.addComponent(separatore);

        Empty(panel, 2);
        //bottone per leggere le regole del gioco
        new Button("Rules", new Runnable(){
            @Override
            public void run() {
                //svuoto lo schermo
                panel.removeAllComponents();
                panel.setFillColorOverride(BLACK);
   Label regolamento = new Label("Benvenuto in questa nuova versione multiplayer di Tetris™!\n\nRegolamento di gioco:" +
                        "\n\n -Una partita è formata da un minimo di 2 a un massimo di \n  4 giocatori connessi ad un server;" +
                        "\n\n -Ogni giocatore deve ruotare i pezzi che cadono in modo \n  tale da riempire una riga orizzontale di blocchi senza interruzioni;" +
                        "\n\n -Quando si riempie una riga orizzontale questa diventa una \n  'riga spazzatura' che viene inviata all'avversario selezionato;" +
                        "\n\n -Game Over: i blocchi si accumulano fino alla sommità \n  dell'area di gioco;" +
                        "\n\n -Vittoria: tutti gli avversari hanno subito Game Over \n  mentre tu sei rimasto ancora in gioco;" +
                        "\n\n\n Comandi utili:" +
                        "\n\n Server:" +
                        "\n /start: fai partire il gioco se ci sono almeno 2 giocatori" +
                        "\n /quit o close: interrompi la partita ed esci" +
                        "\n /pause: metti in pausa la partita" +
                        "\n /resume: riprendi una partita in pausa" +
                        "\n /startagain: ricomincia una partita dall'inizio " +
                        "\n /restart: termina la partita e disconnette i client" +
                        "\n\n Giocatore:" +
                        "\n Frecce direzionali ← ↓ →: movimento del pezzo" +
                        "\n s: ruota il pezzo in senso antiorario" +
                        "\n d: ruota il pezzo in senso orario" +
                        "\n Barra spaziatrice: fai cadere il pezzo istantaneamente" +
                        "\n Numeri 1 2 3: seleziona l'avversario a cui inviare le righe spazzatura.").setBackgroundColor(BLACK).setForegroundColor(coloreLabel);
               
                panel.addComponent(regolamento);

                Empty(panel, 1);
                //bottone per tornare alla home
                new Button("Indietro",new Runnable(){
                    @Override
                    public void run(){
                        //svuoto lo schermo
                        panel.removeAllComponents();
                        panel.setFillColorOverride(BLACK);
                        //richiamo la schermata iniziale
                        Schermata(panel);
                    }
                }).addTo(panel);
            }

        }).addTo(panel);

        Empty(panel, 1);

        //bottone per accedere al regolamento e ai crediti
        new Button("Credits", new Runnable(){
            @Override
            public void run() {
                //svuoto lo schermo
                panel.removeAllComponents();
                panel.setFillColorOverride(BLACK);

                Label crediti=new Label("\nQuesto gioco è stato realizzato da:\n\n\nGabriel Riccardo Alsina,\n\nCarlotta Carboni,\n\nLuca Palmieri,\n\nAlessandro Pasi,\n\nAnhelina Halychanska.").setBackgroundColor(BLACK).setForegroundColor
                        (coloreLabel);

                panel.addComponent(crediti);
                Empty(panel, 1);
                //bottone per tornare alla home
                new Button("Indietro",new Runnable(){
                    @Override
                    public void run(){
                        //svuoto lo schermo
                        panel.removeAllComponents();
                        panel.setFillColorOverride(BLACK);
                        //richiamo schermata iniziale
                        Schermata(panel);
                    }
                }).addTo(panel);
            }

        }).addTo(panel);

    }

}
