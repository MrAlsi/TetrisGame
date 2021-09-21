package com.company.Gioco.Mini;

import com.company.Gioco.Schermo;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;


/**
 * I miniBlocchi sono i blocchi della miniGriglia, servono per mostrare i campi degli altri gioatori, non fanno
 * controlli perché saranno già fatti tutti da griglia e blocchi
 */
public class Mini_Blocco {
    public TextGraphics schermo; // Griglia nel terminale

    private int coefColonna = 2; // Numero di quadratini che formano la larghezza di un Blocco nel terminale
    private int coefRiga = 1; // Numero di quadratini che formano l'altezza di un Blocco nel terminale

    public TextColor colore;
    public int colonnaGriglia;
    public int rigaGriglia;
    public int stato; // 0=Vuoto - 1=Pezzo che sta scendendo - 2=Struttura  - 3=Spazzattura

    public Mini_Blocco(TextGraphics schermo, int colonnaGriglia, int rigaGriglia, TextColor colore, char simbolo, int scostamento) {
        this.schermo = schermo;
        this.colonnaGriglia = colonnaGriglia;
        this.rigaGriglia = rigaGriglia;
        this.colore = colore;
        scostamento=scostamento*40+60; //Lo scostamento è una variabile che serve per far capire il minicampo di cui stiamo parlando
        try {
            Schermo.semaforoColore.acquire();
            schermo.setForegroundColor(colore);
            schermo.fillRectangle(new TerminalPosition(colonnaGriglia * coefColonna+scostamento, rigaGriglia * coefRiga+3),
                    new TerminalSize(coefColonna, coefRiga),
                    simbolo);
            Schermo.semaforoColore.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void setColore(TextColor colore){
        try {
            Schermo.semaforoColore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.colore=colore;
        Schermo.semaforoColore.release();
    }
}
