package com.company.Gioco;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;

/**
 * Un blocco è un quadrato del campo da gioco, ogni blocco può essere di quattro tipi:
 * blocco vuoto è un quadrato ancora libero.
 * blocco pieno è un quadrato del pezzo che sta scendendo.
 * blocco struttura è un quadrato occupato da un pezzo che ha avuto una collisione sotto e che aspetta di essere eliminato
 * blocco spazzatura è un quadrato di una riga spazzatura mandato da un altro giocatore
 *
 */
public class Blocco {

    TextGraphics schermo; // Griglia nel terminale
    private final int coefColonna = 4; // Numero di quadratini che formano la larghezza di un Blocco nel terminale
    private final int coefRiga = 2; // Numero di quadratini che formano l'altezza di un Blocco nel terminale

    TextColor colore;
    public int colonnaGriglia;
    public int rigaGriglia;
    int stato; // 0=Vuoto - 1=Pezzo che sta scendendo - 2=Struttura  - 3=Spazzattura

    public Blocco(TextGraphics schermo, int colonnaGriglia, int rigaGriglia, TextColor colore, char simbolo) {
        this.schermo = schermo;
        this.colonnaGriglia = colonnaGriglia;
        this.rigaGriglia = rigaGriglia;
        this.colore = colore;
        try {
            Schermo.semaforoColore.acquire(); //serve per gestire l'accesso a "schermo.setForegroundColor" essendo una risorsa condivsa
            schermo.setForegroundColor(colore);
            schermo.fillRectangle(new TerminalPosition(colonnaGriglia * coefColonna+1, rigaGriglia * coefRiga+1), new TerminalSize(coefColonna, coefRiga), simbolo);
            Schermo.semaforoColore.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return la posizione della colonna sulla griglia
     */
    public int getColonna() { 
        return colonnaGriglia; 
    }

    /**
     * @return la posizione della riga sullo schermo, coordinata * coefficente
     */
    public int getRiga() {
        return rigaGriglia;
    }

    /**
     * @return il colore del blocco
     */
    public TextColor getColor() {
        return colore;
    }

    /**
     * @return lo stato del blocco
     */
    public int getStato() {
        return stato;
    }

    /**
     * Controlla se il blocco si trova all'ultima riga della griglia oppure se nel blocco sotto di lui (this.getRiga() + 1)
     * si trova un blocco con stato > 1 (Struttura=2 o Spazzatura=3)
     * @return True: se c'è qualcosa sotto       False: non c'è ancora spazio per scendere
     */
    public boolean collisioneSotto(Griglia campo) {
        return this.getRiga() == 23 || campo.griglia[this.getColonna()][this.getRiga() + 1].getStato() > 1;
    }

    /**
     * Controlla se con lo spostamento in orizzontale andrà fuori della griglia in larghezza, se con lo spostamento
     * in verticale andrà fuori in altezza o se dove andrà c'è già un altro blocco (struttura o spazzatura)
     * @param campo
     * @param spostamentoOrizzontale
     * @param spostamentoVerticale
     * @return
     */
    public boolean collisioneLaterale(Griglia campo, int spostamentoOrizzontale, int spostamentoVerticale) {
        return this.getColonna() + spostamentoOrizzontale < 0 || //Collisione con muro di sinistra
                this.getColonna() + spostamentoOrizzontale > 11 || //Collisione con muro di destra
                this.getRiga() + spostamentoVerticale < 0 || //Collisione con muro in alto
                this.getRiga() + spostamentoVerticale > 23 || //Collisione con muro in basso
                campo.griglia[this.getColonna() + spostamentoOrizzontale][this.getRiga() + spostamentoVerticale].getStato() >= 2; //Collisione con blocco struttura/spazzatura
    }

    public void muovi(Griglia campo, int colGriglia, int rigGriglia, int orizzontale, int verticale, TextColor colore) {

    }

    public void rimuovi(Griglia campo, int colGriglia, int rigGriglia) {

    }
}
