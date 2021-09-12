package com.company.Gioco.Mini;

import com.company.Gioco.Schermo;
import com.googlecode.lanterna.Symbols;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;

public class Mini_Blocco {
    public TextGraphics schermo; // Griglia nel terminale

    private int coefColonna = 2; // Numero di quadratini che formano la larghezza di un Blocco nel terminale
    private int coefRiga = 1; // Numero di quadratini che formano l'altezza di un Blocco nel terminale

    public TextColor colore;
    public int colonnaGriglia;
    public int rigaGriglia;
    public int stato = 3; // 0=Vuoto - 1=Pezzo che sta scendendo - 2=Struttura  - 3=Spazzattura

    public Mini_Blocco(TextGraphics schermo, int colonnaGriglia, int rigaGriglia, TextColor colore, int scostamento) {
        //super(schermo, colonnaGriglia, rigaGriglia, colore);
        this.schermo = schermo;
        this.colonnaGriglia = colonnaGriglia;
        this.rigaGriglia = rigaGriglia;
        this.colore = colore;
        scostamento=scostamento*40+60;
        try {
            Schermo.semaforoColore.acquire();// serve per gestire l'accesso a "schermo.setForegroundColor" essendo una risorsa condivsa
            schermo.setForegroundColor(colore);
            schermo.fillRectangle(new TerminalPosition(colonnaGriglia * coefColonna+scostamento, rigaGriglia * coefRiga+3),
                    new TerminalSize(coefColonna, coefRiga),
                    Symbols.BLOCK_SOLID);
            Schermo.semaforoColore.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }



    }


    // Ritorna la posizione della colonna sullo schermo, coordinata * coefficente
    public int getColonna() {
        return colonnaGriglia;
    }

    // Ritorna la posizione della riga sullo schermo, coordinata * coefficente
    public int getRiga() {
        return rigaGriglia;
    }

    public void muovi(Mini_Griglia campo, int colGriglia, int rigGriglia, int orizzontale, int verticale, TextColor colore, int scostamento) { }

    public void rimuovi(Mini_Griglia campo, int colGriglia, int rigGriglia) { }
}
