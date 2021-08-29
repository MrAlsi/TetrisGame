package com.company.Gioco.Mini;

import com.company.Gioco.Mini.Mini_Blocco;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;

public class Mini_BloccoVuoto extends Mini_Blocco {
    public Mini_BloccoVuoto(TextGraphics screen, int col, int rig, int scostamento) {
        super(screen, col, rig, TextColor.ANSI.BLACK_BRIGHT, scostamento);
        stato = 0;
    }
}
