package com.company.Gioco;

import com.googlecode.lanterna.TextColor;
import java.util.concurrent.Semaphore;
import com.company.Gioco.Mini.*;
import static com.company.Gioco.Schermo.*;

/**
 * serve per ricevere lo stato della griglia degli altri giocati e tradurlo nei minicampi
 */
public class RiceviStato extends Thread{
    public static Semaphore traduzione = new Semaphore(1);

    public void run(String stringa) {
        traduciStringToInt(stringa);
    }

    /**
     * @param stringa ricevuta dagli altri giocatori, splittiamo la stringa in tre parti ogni ':'
     *                [0]: indica il giocatore che ci ha inviato la stringa, è colui che dobbiamo modificare il mini_Campo
     *                [1]: lo stato della sua griglia in quel momento
     *                [2]: il colore del pezzo che sta scendendo in quel momento.
     *                la Stringa [1] la splittiamo per ogni elemento e lo mettiamo nell'array blocchi, ogni elemento di
     *                questo array lo 'castiamo' lo mettiamo dentro una matrice[12][24] di interi.
     *                Richiamo il metodo getColore
     *                Traduco la matrice di interi in una matrice di mini_Blocco
     */
    public void traduciStringToInt (String stringa){
        try {
            traduzione.acquire();
            String[] s = stringa.split(":");
            String[] blocchi = s[1].split("");
            int index = 0;
            int[][] campoAvv = new int[12][24];

            for (int i = 0; i < 12; i++) {
                for (int e = 0; e < 24; e++) {
                    campoAvv[i][e] = Integer.parseInt(blocchi[index]);
                    index++;
                }
            }
            TextColor colore = getColore(s[2]);
            traduciIntToMiniGriglia(campoAvv, s[0], colore);
            traduzione.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param campo matrice di interi che ci indica lo stato del blocco
     * @param nome nome del giocatore a cui dobbiamo aggiornare il campo
     * @param colore colore del pezzo che sta scendendo in quel momento
     *
     * faccio un for per trovare il numero del campo il cui giocatore ha quel nome, dopo di che per ogni elemento della
     * griglia di interi lo traduco nel suo corrispondente in mini_Blocco
     */
    public void traduciIntToMiniGriglia(int[][] campo, String nome, TextColor colore) {
            int id = 0;                                     //Id del campo del giocatore
            for (int i = 0; i < miniCampo.length; i++) {    //For per trovare il giocatore con quel nome
                if (nome.equals(miniCampo[i].nome)) {
                    id = i;
                    break;
                }
            }

            //Traduttore da interi a mini_Blocco
            for (int i = 0; i < campo.length; i++) {
                for (int e = 0; e < campo[i].length; e++) {
                    switch (campo[i][e]) {
                        case 0 -> {
                            miniCampo[id].griglia[i][e] = new Mini_BloccoVuoto(schermo, i, e, id);
                            break;
                        }
                        case 1 -> {
                            miniCampo[id].griglia[i][e] = new Mini_BloccoPieno(schermo, i, e, colore, id);
                            break;
                        }
                        case 2 -> {
                            miniCampo[id].griglia[i][e] = new Mini_BloccoStruttura(schermo, i, e, id);
                            break;
                        }
                        case 3 -> miniCampo[id].griglia[i][e] = new Mini_BloccoSpazzatura(schermo, i, e, id);
                    }
                }
            }


    }

    /**
     * Venendomi passata una Stringa all'inizio faccio uno Switch sulla stringa per ritornare il corrispettivo TextColor
     */
    public TextColor getColore(String colore){
        switch(colore){
            case "BLUE_BRIGHT": return TextColor.ANSI.BLUE_BRIGHT;
            case "MAGENTA_BRIGHT": return TextColor.ANSI.MAGENTA_BRIGHT;
            case "YELLOW_BRIGHT": return TextColor.ANSI.YELLOW_BRIGHT;
            case "RED_BRIGHT": return TextColor.ANSI.RED_BRIGHT;
            case "CYAN_BRIGHT": return TextColor.ANSI.CYAN_BRIGHT;
            case "GREEN_BRIGHT": return TextColor.ANSI.GREEN_BRIGHT;
        }
        return null;
    }
}
