package com.company.Gioco;

import com.googlecode.lanterna.TextColor;
import java.io.PrintWriter;
import java.util.concurrent.Semaphore;

/**
 * Questa classe serve per inviare lo stato della propria griglia agli altri giocatori
 */
public class InviaStato implements Runnable{
    int[][] miaGriglia = new int[12][24];
    PrintWriter pw;
    String username;
    TextColor colore;
    Semaphore traduzione = new Semaphore(1);

    public InviaStato(String username, PrintWriter pw){
        this.pw=pw;
        this.username=username;
    }

    @Override
    public void run() {

    }

    public synchronized void run(Griglia griglia) {
        traduciGrigliaToInt(griglia);
    }

    /**
     * @param griglia il proprio campo da gioco in questo momento, traduce la griglia di blocco in una griglia di
     *                ugual misura ma di numeri, i numeri (0, 1, 2, 3) indicano lo stato di ogni blocco della griglia
     */
    public synchronized void traduciGrigliaToInt(Griglia griglia){
        try {
            traduzione.acquire();
            for(int i=0; i<griglia.griglia.length; i++){
                for(int e=0; e<griglia.griglia[i].length; e++){
                    switch (griglia.griglia[i][e].getStato()) {
                        case 0 :{miaGriglia[i][e]=0;
                            break;}
                        case 1 :{miaGriglia[i][e]=1;
                            colore=griglia.griglia[i][e].colore;
                            break;}
                        case 2 :{miaGriglia[i][e]=2;
                            break;}
                        case 3 : {
                            miaGriglia[i][e] = 3;
                            break;
                        }
                    }
                }
            }
            traduciInttoString(miaGriglia);
            traduzione.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    /**
     * @param mat, traduco la matrice di int in una Stringa, questa stringa inizia con il proprio username, cosi gli altri giocatori capiranno di chi sia la griglia
     */
    public synchronized void traduciInttoString(int[][] mat){
        String stringa= username + ":";
        for(int i=0; i<12; i++){
            for(int e=0; e<24; e++){
                stringa=stringa+(mat[i][e]);
            }
        }
        stringa=stringa+":"+colore;
        Schermo.invia(stringa, pw);
    }
}
