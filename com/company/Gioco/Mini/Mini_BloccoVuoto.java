package com.company.Gioco.Mini;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;

import static com.googlecode.lanterna.Symbols.BLOCK_SOLID;

public class Mini_BloccoVuoto extends Mini_Blocco {
    public Mini_BloccoVuoto(TextGraphics screen, int col, int rig, int scostamento) {
        super(screen, col, rig, TextColor.ANSI.BLACK_BRIGHT, BLOCK_SOLID, scostamento);
        stato = 0;
    }
}
