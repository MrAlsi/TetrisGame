package com.company.Gioco;

import com.googlecode.lanterna.graphics.TextGraphics;

public class Mini_Griglia implements Runnable{
    String nome;
    Mini_Blocco[][] griglia;
    TextGraphics screen;
    int numeroCampo;

    public Mini_Griglia(TextGraphics screen, int numeroCampo, String nome) {
        this.screen = screen;
        this.numeroCampo=numeroCampo;
        griglia = new Mini_Blocco[12][24];
        this.nome=nome;
    }

    public void creaCampo() {
        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < 24; j++) {
                griglia[i][j] = new Mini_BloccoVuoto(screen, i, j,numeroCampo);
            }
        }
    }

    @Override
    public void run() {

    }
}
