package com.company.Gioco.Mini;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;

import static com.googlecode.lanterna.Symbols.BLOCK_SOLID;

public class Mini_BloccoStruttura extends Mini_Blocco {
    public Mini_BloccoStruttura(TextGraphics schermo, int col, int rig, int scostamento) {
        super(schermo, col, rig, TextColor.ANSI.WHITE_BRIGHT, BLOCK_SOLID, scostamento);
        stato = 2;
    }
}
