package com.company.Gioco.Mini;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;

public class Mini_BloccoPieno extends Mini_Blocco {
    public Mini_BloccoPieno(TextGraphics screen, int colGriglia, int rigGriglia, TextColor colore){
        super(screen, colGriglia, rigGriglia, colore,1);
        stato=1;
    }
}
