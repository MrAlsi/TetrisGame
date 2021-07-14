package com.company.Gioco;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;

import static com.googlecode.lanterna.TextColor.ANSI.WHITE;

public class Griglia {
    Blocco[][] griglia;
    TextGraphics screen;

    public Griglia(TextGraphics screen) {
        this.screen = screen;
        griglia = new Blocco[12][24];
    }

    public void creaCampo() {
        Boolean chess = false;
        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < 24; j++) {
                griglia[i][j] = new BloccoVuoto(screen, i, j);
            }
        }
    }
    public void eliminaRiga(){
        Boolean riga = false;
        for (int i = 0; i < 24; i++) {
            riga = false;
            for (int j = 0; j < 12; j++) {
                if(griglia[j][i].getStato() == 2){
                    riga = true;
                }
                riga = false;
            }
            if(riga){
                for (int y = 0; y < 12; y++) {
                    griglia[y][i] = new BloccoVuoto(screen, y, i);
                }
            }
        }
    }
}
