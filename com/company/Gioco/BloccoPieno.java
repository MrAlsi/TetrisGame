package com.company.Gioco;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import static com.googlecode.lanterna.Symbols.BLOCK_SOLID;

public class BloccoPieno extends Blocco{

    public BloccoPieno(TextGraphics screen, int colTerminale, int rigTerminale, TextColor colore) {
        super(screen, colTerminale, rigTerminale, colore, BLOCK_SOLID);
        stato=1;
    }

    //orizzontale: +1: sposta a destra  -1:sposta a sinistra
    //Verticale: +1 scende      -1:sale

    @Override
    public void muovi(Griglia campo, int colGriglia, int rigGriglia, int orizzontale, int verticale, TextColor colore){
        //Riempo la successiva con un bloccoPieno
        campo.griglia[colGriglia+orizzontale][rigGriglia+verticale] =
                new BloccoPieno(schermo,
                        campo.griglia[colGriglia+orizzontale][rigGriglia+verticale].getColonna(),
                        campo.griglia[colGriglia+orizzontale][rigGriglia+verticale].getRiga(), colore);
    }

    public void rimuovi(Griglia campo, int colGriglia, int rigGriglia){
        //Metto la casella dove sono stato come blocco vuoto
        campo.griglia[colGriglia][rigGriglia] = new BloccoVuoto(schermo, colGriglia, rigGriglia);
    }

    @Override
    public boolean collisioneSotto(Griglia campo){
        return super.collisioneSotto(campo);
    }

}
