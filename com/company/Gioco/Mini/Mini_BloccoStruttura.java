package com.company.Gioco.Mini;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;

public class Mini_BloccoStruttura extends Mini_Blocco {
    public Mini_BloccoStruttura(TextGraphics schermo, int col, int rig, int scostamento) {
        super(schermo, col, rig, TextColor.ANSI.WHITE_BRIGHT, scostamento);
        stato = 2;
    }
}
