package com.company.Gioco.Mini;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;

public class Mini_BloccoSpazzatura extends Mini_Blocco {
    public Mini_BloccoSpazzatura(TextGraphics screen, int colGriglia, int rigGriglia,  int scostamento){
        super(screen, colGriglia, rigGriglia, TextColor.ANSI.BLACK,scostamento);
        stato=3;
    }
}
