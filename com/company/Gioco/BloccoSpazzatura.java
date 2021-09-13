package com.company.Gioco;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;

public class BloccoSpazzatura extends Blocco{
    public BloccoSpazzatura(TextGraphics screen, int col, int rig) {
         super(screen, col, rig, TextColor.ANSI.RED, Symbols.BLOCK_SPARSE);
        stato = 3;
    }
    @Override
    public void setStato() {
        stato = 3;
    }
}
