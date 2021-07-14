package com.company.Gioco;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;

public class BloccoVuotoBlack extends Blocco{
    public BloccoVuotoBlack(TextGraphics screen, int col, int rig) {
        super(screen, col, rig, TextColor.ANSI.BLACK_BRIGHT);
        stato = 0;
    }
}
