package com.company.Gioco;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;

public class Mini_BloccoPieno extends Mini_Blocco{
    public Mini_BloccoPieno(TextGraphics screen, int colGriglia, int rigGriglia, TextColor colore){
        super(screen, colGriglia, rigGriglia, colore,1);
        stato=1;
    }

    @Override
    public void muovi(Mini_Griglia campo, int colGriglia, int rigGriglia, int orizzontale, int verticale, TextColor colore, int scostamento){
        //Riempo la successiva con un bloccoPieno
        campo.griglia[colGriglia+orizzontale][rigGriglia+verticale] =
                new Mini_Blocco(schermo,
                        campo.griglia[colGriglia+orizzontale][rigGriglia+verticale].getColonna(),
                        campo.griglia[colGriglia+orizzontale][rigGriglia+verticale].getRiga(), colore, 1);

    }

    @Override
    public void rimuovi(Mini_Griglia campo, int colGriglia, int rigGriglia){
        //Metto la casella dove sono stato come blocco vuoto
        campo.griglia[colGriglia][rigGriglia] = new Mini_BloccoVuoto(schermo, colGriglia, rigGriglia, 1);
    }

}

