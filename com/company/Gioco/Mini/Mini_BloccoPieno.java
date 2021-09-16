package com.company.Gioco.Mini;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;

import static com.googlecode.lanterna.Symbols.BLOCK_SOLID;

public class Mini_BloccoPieno extends Mini_Blocco {
    public Mini_BloccoPieno(TextGraphics screen, int colGriglia, int rigGriglia, TextColor colore, int scostamento){
        super(screen, colGriglia, rigGriglia, colore, BLOCK_SOLID, scostamento);
        stato=1;
    }
}
