package com.company.Gioco.Mini;

import com.googlecode.lanterna.graphics.TextGraphics;

/**
 * Una Mini_Griglia è un un campo da gioco più piccolo, questo rappresenta il campo degli altri giocatori in partita
 * in corso, la logica della mini_Griglia è la stessa della griglia normale ma senza tutti i controlli che faccio sul
 * controllo delle righe, aggiunta della spazzatura, ecc..., questo perché lo fa già la griglia normale questa è solo
 * la grafica per mostrarti cosa sta succedendo.
 */
public class Mini_Griglia {
    public String nome;
    public Mini_Blocco[][] griglia;
    public TextGraphics screen;
    public int numeroCampo;

    public Mini_Griglia(TextGraphics screen, int numeroCampo, String nome) {
        this.screen = screen;
        this.numeroCampo=numeroCampo;
        griglia = new Mini_Blocco[12][24];
        this.nome=nome;
    }

    public void creaCampo() {
        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < 24; j++) {
                griglia[i][j] = new Mini_BloccoVuoto(screen, i, j, numeroCampo);
            }
        }
    }
}
