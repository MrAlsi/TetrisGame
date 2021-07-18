package com.company.Gioco;

import com.googlecode.lanterna.Symbols;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;

public class Blocco {

    TextGraphics schermo; // Griglia nel terminale
    // TextGraphics quadrato; // Quadratino nel terminale
    private int coefColonna = 4; // Numero di quadratini che formano la larghezza di un Blocco nel terminale
    private int coefRiga = 2; // Numero di quadratini che formano l'altezza di un Blocco nel terminale

    TextColor colore;
    int colonnaGriglia;
    int rigaGriglia;
    int stato = 3; // 0=Vuoto - 1=Pezzo che sta scendendo - 2=Struttura  - 3=Spazzattura

    public Blocco(TextGraphics schermo, int colonnaGriglia, int rigaGriglia, TextColor colore) {
        this.schermo = schermo;
        schermo.setForegroundColor(colore);
        this.colonnaGriglia = colonnaGriglia;
        this.rigaGriglia = rigaGriglia;
        this.colore = colore;
        // quadrato = schermo.fillRectangle(new TerminalPosition(colonnaGriglia * coefColonna, rigaGriglia * coefRiga), new TerminalSize(coefColonna, coefRiga), Symbols.BLOCK_SOLID);
        schermo.fillRectangle(new TerminalPosition(colonnaGriglia * coefColonna, rigaGriglia * coefRiga), new TerminalSize(coefColonna, coefRiga), Symbols.BLOCK_SOLID);
    }

    public void cambiaColore(TextColor colore) {
        schermo.setBackgroundColor(colore);
    }

    // Ritorna la posizione della colonna sullo schermo, coordinata * coefficente
    public int getColonna() {
        return colonnaGriglia;
    }

    // Ritorna la posizione della riga sullo schermo, coordinata * coefficente
    public int getRiga() {
        return rigaGriglia;
    }

    public TextColor getColor() {
        return colore;
    }

    public int getStato() {
        return stato;
    }

    public void setStato() {
    }

    public boolean collisioneSotto(Griglia campo) {
        //Non capisco perché funzioni con 1, potrebbe dare problemi in futuro ma al momento funziona
        if (this.getRiga() == 23 || campo.griglia[this.getColonna()][this.getRiga()+1].getStato() > 1) {
            return true;
        } else {
            //System.out.println("il colore è: " + campo.griglia[this.getColonna()][this.getRiga()+1].getStato());
            return false;
        }
    }

    public boolean collisioneLaterale(Griglia campo, int spostamento){
        if(this.getColonna()+spostamento < 0 ||
                this.getColonna()+spostamento == 12 ||
                campo.griglia[this.getColonna()+spostamento][this.getRiga()].getStato() >= 2){
            return true;
        } else {
            return false;
        }
    }

    public void muovi(Griglia campo, int colGriglia, int rigGriglia, int orizzontale, int verticale) {

    }

    public void rimuovi(Griglia campo, int colGriglia, int rigGriglia) {

    }
}
